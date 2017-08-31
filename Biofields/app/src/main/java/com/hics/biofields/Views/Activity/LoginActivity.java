package com.hics.biofields.Views.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;
import com.hics.biofields.BioApp;
import com.hics.biofields.Library.LogicUtils;
import com.hics.biofields.Library.Statics;
import com.hics.biofields.Models.Managment.RealmManager;
import com.hics.biofields.Models.User;
import com.hics.biofields.Network.Requests.LoginRequest;
import com.hics.biofields.Network.Responses.LoginResponse;
import com.hics.biofields.R;
import com.hics.biofields.Views.Dialogs.RecoveryPasswordActivity;

import org.aviran.cookiebar2.CookieBar;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.act_login_username)EditText usernameEdt;
    @BindView(R.id.act_login_password)EditText passwordEdt;
    ProgressDialog mProgressDialog;
    private static String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.act_login_enter)
    void onLoginClick(){
        String pass = LogicUtils.MD5(passwordEdt.getText().toString().trim());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(usernameEdt.getText().toString().trim());
        loginRequest.setPassword(pass);
        Call<LoginResponse> call = BioApp.getHicsService().Login(loginRequest);
        mProgressDialog = ProgressDialog.show(this, null, "Autenticando...");
        mProgressDialog.setCancelable(false);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.code() == Statics.code_OK){
                    mProgressDialog.dismiss();
                    User user = new User();
                    user.setEmail(response.body().getEmail());
                    user.setCreatedAt(response.body().getCreated());
                    user.setJwt(response.body().getArray().getJwt());
                    Realm realm = Realm.getDefaultInstance();
                    RealmManager.saveUser(user,realm);
                    realm.close();
                    start(MainActivity.class,true);

                }else {
                    try {
                        mProgressDialog.dismiss();
                        LoginResponse errorService = new Gson().fromJson(response.errorBody().string(), LoginResponse.class);
                        CookieBar.Build(LoginActivity.this)
                                .setTitle("Error")
                                .setMessage(errorService.getMesssage())
                                .setBackgroundColor(R.color.red)
                                .setTitleColor(R.color.colorWhite)
                                .show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                mProgressDialog.dismiss();
                Log.d(TAG,t.getLocalizedMessage());
            }
        });
        //start(MainActivity.class,true);
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


}
