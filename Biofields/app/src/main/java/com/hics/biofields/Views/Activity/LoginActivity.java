package com.hics.biofields.Views.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;
import com.hics.biofields.BioApp;
import com.hics.biofields.Library.Connection;
import com.hics.biofields.Library.DesignUtils;
import com.hics.biofields.Library.JWTUtils;
import com.hics.biofields.Library.LogicUtils;
import com.hics.biofields.Library.Statics;
import com.hics.biofields.Models.Managment.RealmManager;
import com.hics.biofields.Models.Objects.BodyJwt;
import com.hics.biofields.Models.Objects.User;
import com.hics.biofields.Network.Requests.LoginRequest;
import com.hics.biofields.Network.Responses.LoginResponse;
import com.hics.biofields.Presenters.Events.CompaniesSelectionEvent;
import com.hics.biofields.R;
import com.hics.biofields.Views.Dialogs.RecoveryPasswordActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 222;

    @BindView(R.id.act_login_username)EditText usernameEdt;
    @BindView(R.id.act_login_password)EditText passwordEdt;
    ProgressDialog mProgressDialog;
    private static String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >=23) {
            initPermissionsNative();
        }
    }

    @OnClick(R.id.act_login_enter)
    void onLoginClick(){
        String message = validateCredentials(usernameEdt.getText().toString(),passwordEdt.getText().toString());
        if (message.isEmpty()) {
            final String pass = LogicUtils.MD5(passwordEdt.getText().toString().trim());
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setEmail(usernameEdt.getText().toString().trim());
            loginRequest.setPassword(pass);
            Call<LoginResponse> call = BioApp.getHicsService().Login(loginRequest);
            mProgressDialog = ProgressDialog.show(this, null, "Autenticando...");
            mProgressDialog.setCancelable(false);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.code() == Statics.code_OK) {
                        mProgressDialog.dismiss();
                        int id =  0; //Integer.parseInt(claim.asString());
                        try {
                            String jsonJwt = JWTUtils.decoded(response.body().getArray().getJwt());
                            Gson gson = new Gson();
                            BodyJwt userData = gson.fromJson(jsonJwt,BodyJwt.class);
                            id = Integer.parseInt(userData.getUserData().getId());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        User user = new User();
                        user.setEmail(response.body().getEmail());
                        user.setCreatedAt(response.body().getCreated());
                        user.setJwt(response.body().getArray().getJwt());
                        user.setId(id);
                        Realm realm = Realm.getDefaultInstance();
                        RealmManager.saveUser(user, realm);
                        Statics.PASS = pass;
                        realm.close();
                        EventBus.getDefault().postSticky(new CompaniesSelectionEvent(response.body().getCompanies()));
                        start(ChooseCompanyActivity.class, true);

                    } else {
                        try {
                            mProgressDialog.dismiss();
                            LoginResponse errorService = new Gson().fromJson(response.errorBody().string(), LoginResponse.class);
                            DesignUtils.errorMessage(LoginActivity.this,"Error",errorService.getMesssage());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    mProgressDialog.dismiss();
                    Log.d(TAG, t.getLocalizedMessage());
                    DesignUtils.errorMessage(LoginActivity.this,"Error", t.getLocalizedMessage());
                }
            });
        }else{
            DesignUtils.errorMessage(LoginActivity.this,"Error de Red",message);
        }
    }

    @OnClick(R.id.act_login_recovery)
    void onRecoveryOpenClick(){start(RecoveryPasswordActivity.class,false);}

    private void start(Class<?> aClass,boolean isFinished ) {
        Intent intent = new Intent(this, aClass);
        startActivity(intent);
        if (isFinished) {
            finish();
        }
    }

    /*
     Init permissions
    */
    @TargetApi(Build.VERSION_CODES.M)
    public void initPermissionsNative()
    {
        final List<String> permissions = new ArrayList<String>();
        if(!addPermission(permissions, Manifest.permission.MANAGE_DOCUMENTS))
                            if(!addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                                    if(!addPermission(permissions, Manifest.permission.READ_EXTERNAL_STORAGE))
                                            if (permissions.size() > 0) {
                                                requestPermissions(permissions.toArray(new String[permissions.size()]),
                                                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                                                return;
                                            }
    }

    /*
    * Add permissions
    * */
    @TargetApi(Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
            {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                perms.put(Manifest.permission.MANAGE_DOCUMENTS, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);


                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);

                if (perms.get(Manifest.permission.MANAGE_DOCUMENTS) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                } else {
                    DesignUtils.errorMessage(LoginActivity.this,"Pemisos Aplicación","Permisos denegados por el usuario");
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private String validateCredentials(String username,String password){
        if (Connection.isConnected(this)) {
            if (username.isEmpty() && password.isEmpty()) {
                return "Favor de ingresar usuario y contraseña";
            } else {
                if (!username.isEmpty()) {
                    if (!password.isEmpty()) {
                        return "";
                    } else {
                        return "La contraseña es requerida";
                    }
                } else {
                    return "El usuario es requerido";
                }
            }
        }else{
            return "No hay conexión a internet";
        }
    }

}
