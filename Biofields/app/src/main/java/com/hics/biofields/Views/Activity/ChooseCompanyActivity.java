package com.hics.biofields.Views.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.hics.biofields.BioApp;
import com.hics.biofields.Library.CatalogID;
import com.hics.biofields.Library.Connection;
import com.hics.biofields.Library.DesignUtils;
import com.hics.biofields.Library.Prefs;
import com.hics.biofields.Library.Statics;
import com.hics.biofields.Models.Managment.RealmManager;
import com.hics.biofields.Models.Objects.User;
import com.hics.biofields.Network.Requests.LoginCompanyRequest;
import com.hics.biofields.Network.Responses.Catalogs.BudgetlistResponse;
import com.hics.biofields.Network.Responses.Catalogs.CompanyCatResponse;
import com.hics.biofields.Network.Responses.Catalogs.CostcenterResponse;
import com.hics.biofields.Network.Responses.Catalogs.ExpenseResponse;
import com.hics.biofields.Network.Responses.Catalogs.ItemResponse;
import com.hics.biofields.Network.Responses.Catalogs.SiteResponse;
import com.hics.biofields.Network.Responses.Catalogs.UoMResponse;
import com.hics.biofields.Network.Responses.Catalogs.VendorResponse;
import com.hics.biofields.Network.Responses.CompanyResponse;
import com.hics.biofields.Network.Responses.LoginResponse;
import com.hics.biofields.Presenters.Events.CompaniesSelectionEvent;
import com.hics.biofields.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.net.ConnectException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseCompanyActivity extends AppCompatActivity {

    @BindView(R.id.act_select_companies)Spinner companies;
    ProgressDialog mProgressDialog;
    private static String TAG = ChooseCompanyActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_company);
        ButterKnife.bind(this);
    }

    @Subscribe(sticky = true)
    public void onInitCompanies(CompaniesSelectionEvent event){
        EventBus.getDefault().removeStickyEvent(event);
        companies.setAdapter(new ArrayAdapter<CompanyResponse>(this,android.R.layout.simple_spinner_dropdown_item,event.companies));
    }

    @OnClick(R.id.act_select_accept)
    void onAccept(){
        if (Connection.isConnected(this)) {
            loginCompany(((CompanyResponse) companies.getSelectedItem()).getCompanyName());
        }else{
            DesignUtils.errorMessage(ChooseCompanyActivity.this,"Error de Red","No hay conexión a internet");
        }
    }

    private void loginCompany(final String company){
        String pass = Statics.PASS;
        LoginCompanyRequest loginRequest = new LoginCompanyRequest();
        loginRequest.setEmail(RealmManager.user());
        loginRequest.setPassword(pass);
        loginRequest.setCompany(company);
        Call<LoginResponse> call = BioApp.getHicsService().LoginCompany("Bearer "+RealmManager.token(),loginRequest);
        mProgressDialog = ProgressDialog.show(this, null, "Ingresando...");
        mProgressDialog.setCancelable(false);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.code() == Statics.code_OK) {
                    Statics.PASS = "" ;
                    Prefs prefs = Prefs.with(ChooseCompanyActivity.this);
                    prefs.putBoolean(Statics.IS_BIOFIELDS_PREFS, company.toLowerCase().contains("biofields")? true : false);
                    User user = new User();
                    user.setEmail(response.body().getEmail());
                    user.setCreatedAt(response.body().getCreated());
                    user.setJwt(response.body().getArray().getJwt());
                    Realm realm = Realm.getDefaultInstance();
                    user.setId(RealmManager.usrID());
                    RealmManager.saveUser(user, realm);
                    realm.close();
                    mProgressDialog.setMessage("Descargando catalogos(1/8)...");
                    catalogVendor();
                } else {
                    try {
                        mProgressDialog.dismiss();
                        LoginResponse errorService = new Gson().fromJson(response.errorBody().string(), LoginResponse.class);
                        DesignUtils.errorMessage(ChooseCompanyActivity.this,"Error",errorService.getMesssage());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                mProgressDialog.dismiss();
                Log.d(TAG, t.getLocalizedMessage());
                DesignUtils.errorMessage(ChooseCompanyActivity.this,"Error", t.getLocalizedMessage());
            }
        });
    }

    private void catalogVendor(){
        if (Connection.isConnected(this)) {
            Call<RealmList<VendorResponse>> call = BioApp.getHicsService().catalogVendor("Bearer " + RealmManager.token(), CatalogID.VENDOR, "2017-08-01");
            call.enqueue(new Callback<RealmList<VendorResponse>>() {
                @Override
                public void onResponse(Call<RealmList<VendorResponse>> call, Response<RealmList<VendorResponse>> response) {
                    if (response.code() == Statics.code_OK_Get) {
                        Realm realm = Realm.getDefaultInstance();
                        RealmManager.insert(realm, response.body());
                        realm.close();
                        mProgressDialog.setMessage("Descargando catalogos(2/8)...");
                        catalogCompany();
                    } else {
                        Log.d("LISTA", "" + response);
                        mProgressDialog.dismiss();
                        DesignUtils.errorMessage(ChooseCompanyActivity.this,"Error Descarga","Por el momento no se pueden descargar los catalogos");
                    }
                }

                @Override
                public void onFailure(Call<RealmList<VendorResponse>> call, Throwable t) {
                    Log.d("Failure", "t " + t.getLocalizedMessage());
                    mProgressDialog.dismiss();
                    DesignUtils.errorMessage(ChooseCompanyActivity.this,"Error Descarga",t.getLocalizedMessage());
                }
            });
        }else{
            mProgressDialog.dismiss();
            DesignUtils.errorMessage(ChooseCompanyActivity.this,"Error de Red","Se ha interrumpido la conexión a internet");
        }
    }

    private void catalogCompany(){
        if (Connection.isConnected(this)) {
            Call<RealmList<CompanyCatResponse>> call = BioApp.getHicsService().catalogCompany("Bearer " + RealmManager.token(), CatalogID.COMPANY, "2017-08-01");
            call.enqueue(new Callback<RealmList<CompanyCatResponse>>() {
                @Override
                public void onResponse(Call<RealmList<CompanyCatResponse>> call, Response<RealmList<CompanyCatResponse>> response) {
                    if (response.code() == Statics.code_OK_Get) {
                        Realm realm = Realm.getDefaultInstance();
                        RealmManager.insert(realm, response.body());
                        realm.close();
                        mProgressDialog.setMessage("Descargando catalogos(3/8)...");
                        catalogCostCenter();
                    } else {
                        Log.d("LISTA", "" + response);
                        mProgressDialog.dismiss();
                        DesignUtils.errorMessage(ChooseCompanyActivity.this,"Error Descarga","Por el momento no se pueden descargar los catalogos");
                    }
                }

                @Override
                public void onFailure(Call<RealmList<CompanyCatResponse>> call, Throwable t) {
                    Log.d("Failure", "t " + t.getLocalizedMessage());
                    mProgressDialog.dismiss();
                    DesignUtils.errorMessage(ChooseCompanyActivity.this,"Error Descarga",t.getLocalizedMessage());
                }
            });
        }else{
            mProgressDialog.dismiss();
            DesignUtils.errorMessage(ChooseCompanyActivity.this,"Error de Red","Se ha interrumpido la conexión a internet");
        }
    }

    private void catalogCostCenter(){
        if (Connection.isConnected(this)){
            Call<RealmList<CostcenterResponse>> call = BioApp.getHicsService().catalogCostcenter("Bearer " + RealmManager.token(), CatalogID.COSTCENTER, "2017-08-01");
            call.enqueue(new Callback<RealmList<CostcenterResponse>>() {
                @Override
                public void onResponse(Call<RealmList<CostcenterResponse>> call, Response<RealmList<CostcenterResponse>> response) {
                    if (response.code() == Statics.code_OK_Get) {
                        Realm realm = Realm.getDefaultInstance();
                        RealmManager.insert(realm, response.body());
                        realm.close();
                        mProgressDialog.setMessage("Descargando catalogos(4/8)...");
                        catalogBudgetList();
                    } else {
                        Log.d("LISTA", "" + response);
                        mProgressDialog.dismiss();
                        DesignUtils.errorMessage(ChooseCompanyActivity.this,"Error Descarga","Por el momento no se pueden descargar los catalogos");
                    }
                }

                @Override
                public void onFailure(Call<RealmList<CostcenterResponse>> call, Throwable t) {
                    Log.d("Failure", "t " + t.getLocalizedMessage());
                    mProgressDialog.dismiss();
                    DesignUtils.errorMessage(ChooseCompanyActivity.this,"Error Descarga",t.getLocalizedMessage());
                }
            });
        }else{
            mProgressDialog.dismiss();
            DesignUtils.errorMessage(ChooseCompanyActivity.this,"Error de Red","Se ha interrumpido la conexión a internet");
        }
    }

    private void catalogBudgetList(){
        if (Connection.isConnected(this)) {
            Call<RealmList<BudgetlistResponse>> call = BioApp.getHicsService().catalogBudgetlist("Bearer " + RealmManager.token(), CatalogID.BUDGETLIST, "2017-08-01");
            call.enqueue(new Callback<RealmList<BudgetlistResponse>>() {
                @Override
                public void onResponse(Call<RealmList<BudgetlistResponse>> call, Response<RealmList<BudgetlistResponse>> response) {
                    if (response.code() == Statics.code_OK_Get) {
                        Realm realm = Realm.getDefaultInstance();
                        RealmManager.insert(realm, response.body());
                        realm.close();
                        mProgressDialog.setMessage("Descargando catalogos(5/8)...");
                        catalogSite();
                    } else {
                        Log.d("LISTA", "" + response);
                        mProgressDialog.dismiss();
                        DesignUtils.errorMessage(ChooseCompanyActivity.this,"Error Descarga","Por el momento no se pueden descargar los catalogos");
                    }
                }

                @Override
                public void onFailure(Call<RealmList<BudgetlistResponse>> call, Throwable t) {
                    Log.d("Failure", "t " + t.getLocalizedMessage());
                    mProgressDialog.dismiss();
                    DesignUtils.errorMessage(ChooseCompanyActivity.this,"Error Descarga",t.getLocalizedMessage());
                }
            });
        }else{
            mProgressDialog.dismiss();
            DesignUtils.errorMessage(ChooseCompanyActivity.this,"Error de Red","Se ha interrumpido la conexión a internet");
        }
    }

    private void catalogSite(){
        if (Connection.isConnected(this)) {
            Call<RealmList<SiteResponse>> call = BioApp.getHicsService().catalogSite("Bearer " + RealmManager.token(), CatalogID.SITE, "2017-08-01");
            call.enqueue(new Callback<RealmList<SiteResponse>>() {
                @Override
                public void onResponse(Call<RealmList<SiteResponse>> call, Response<RealmList<SiteResponse>> response) {
                    if (response.code() == Statics.code_OK_Get) {
                        Realm realm = Realm.getDefaultInstance();
                        RealmManager.insert(realm, response.body());
                        realm.close();
                        mProgressDialog.setMessage("Descargando catalogos(6/8)...");
                        catalogExpense();
                    } else {
                        Log.d("LISTA", "" + response);
                        mProgressDialog.dismiss();
                        DesignUtils.errorMessage(ChooseCompanyActivity.this,"Error Descarga","Por el momento no se pueden descargar los catalogos");
                    }
                }

                @Override
                public void onFailure(Call<RealmList<SiteResponse>> call, Throwable t) {
                    Log.d("Failure", "t " + t.getLocalizedMessage());
                    mProgressDialog.dismiss();
                    DesignUtils.errorMessage(ChooseCompanyActivity.this,"Error Descarga",t.getLocalizedMessage());
                }
            });
        }else{
            mProgressDialog.dismiss();
            DesignUtils.errorMessage(ChooseCompanyActivity.this,"Error de Red","Se ha interrumpido la conexión a internet");
        }
    }

    private void catalogExpense(){
        if (Connection.isConnected(this)) {
            Call<RealmList<ExpenseResponse>> call = BioApp.getHicsService().catalogExpense("Bearer " + RealmManager.token(), CatalogID.EXPENSE, "2017-08-01");
            call.enqueue(new Callback<RealmList<ExpenseResponse>>() {
                @Override
                public void onResponse(Call<RealmList<ExpenseResponse>> call, Response<RealmList<ExpenseResponse>> response) {
                    if (response.code() == Statics.code_OK_Get) {
                        Realm realm = Realm.getDefaultInstance();
                        RealmManager.insert(realm, response.body());
                        realm.close();
                        mProgressDialog.setMessage("Descargando catalogos(7/8)...");
                        catalogItem();
                    } else {
                        Log.d("LISTA", "" + response);
                        mProgressDialog.dismiss();
                        DesignUtils.errorMessage(ChooseCompanyActivity.this,"Error Descarga","Por el momento no se pueden descargar los catalogos");
                    }
                }

                @Override
                public void onFailure(Call<RealmList<ExpenseResponse>> call, Throwable t) {
                    Log.d("Failure", "t " + t.getLocalizedMessage());
                    mProgressDialog.dismiss();
                    DesignUtils.errorMessage(ChooseCompanyActivity.this,"Error Descarga",t.getLocalizedMessage());
                }
            });
        }else{
            mProgressDialog.dismiss();
            DesignUtils.errorMessage(ChooseCompanyActivity.this,"Error de Red","Se ha interrumpido la conexión a internet");
        }
    }

    private void catalogItem(){
        if (Connection.isConnected(this)) {
            Call<RealmList<ItemResponse>> call = BioApp.getHicsService().catalogItem("Bearer " + RealmManager.token(), CatalogID.ITEM, "2017-08-01");
            call.enqueue(new Callback<RealmList<ItemResponse>>() {
                @Override
                public void onResponse(Call<RealmList<ItemResponse>> call, Response<RealmList<ItemResponse>> response) {
                    if (response.code() == Statics.code_OK_Get) {
                        Realm realm = Realm.getDefaultInstance();
                        RealmManager.insert(realm, response.body());
                        realm.close();
                        mProgressDialog.setMessage("Descargando catalogos(8/8)...");
                        catalogUoM();
                    } else {
                        Log.d("LISTA", "" + response);
                        mProgressDialog.dismiss();
                        DesignUtils.errorMessage(ChooseCompanyActivity.this,"Error Descarga","Por el momento no se pueden descargar los catalogos");
                    }
                }

                @Override
                public void onFailure(Call<RealmList<ItemResponse>> call, Throwable t) {
                    Log.d("Failure", "t " + t.getLocalizedMessage());
                    mProgressDialog.dismiss();
                    DesignUtils.errorMessage(ChooseCompanyActivity.this,"Error Descarga",t.getLocalizedMessage());
                }
            });
        }else{
            mProgressDialog.dismiss();
            DesignUtils.errorMessage(ChooseCompanyActivity.this,"Error de Red","Se ha interrumpido la conexión a internet");
        }
    }

    private void catalogUoM(){
        if (Connection.isConnected(this)) {
            Call<RealmList<UoMResponse>> call = BioApp.getHicsService().catalogUom("Bearer " + RealmManager.token(), CatalogID.UOM, "2017-08-01");
            call.enqueue(new Callback<RealmList<UoMResponse>>() {
                @Override
                public void onResponse(Call<RealmList<UoMResponse>> call, Response<RealmList<UoMResponse>> response) {
                    if (response.code() == Statics.code_OK_Get) {
                        mProgressDialog.dismiss();
                        Realm realm = Realm.getDefaultInstance();
                        RealmManager.insert(realm, response.body());
                        realm.close();
                        Prefs prefs = Prefs.with(ChooseCompanyActivity.this);
                        prefs.putBoolean(Statics.LOGIN_PREFS, true);
                        start(MainActivity.class, true);
                    } else {
                        mProgressDialog.dismiss();
                        Log.d("LISTA", "" + response);
                        DesignUtils.errorMessage(ChooseCompanyActivity.this,"Error Descarga","Por el momento no se pueden descargar los catalogos");
                    }
                }

                @Override
                public void onFailure(Call<RealmList<UoMResponse>> call, Throwable t) {
                    Log.d("Failure", "t " + t.getLocalizedMessage());
                    mProgressDialog.dismiss();
                    DesignUtils.errorMessage(ChooseCompanyActivity.this,"Error Descarga",t.getLocalizedMessage());

                }
            });
        }else{
            mProgressDialog.dismiss();
            DesignUtils.errorMessage(ChooseCompanyActivity.this,"Error de Red","Se ha interrumpido la conexión a internet");
        }
    }

    private void start(Class<?> aClass,boolean isFinished ) {
        Intent intent = new Intent(this, aClass);
        startActivity(intent);
        if (isFinished) {
            finish();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
