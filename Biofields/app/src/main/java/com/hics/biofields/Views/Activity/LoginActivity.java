package com.hics.biofields.Views.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hics.biofields.R;
import com.hics.biofields.Views.Dialogs.RecoveryPasswordActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.act_login_enter)
    void onLoginClick(){
        start(MainActivity.class,true);
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
