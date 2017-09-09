package com.hics.biofields.Views.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.hics.biofields.BuildConfig;
import com.hics.biofields.Library.Prefs;
import com.hics.biofields.Library.Statics;
import com.hics.biofields.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.act_splash_version)TextView txtVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        setVerion();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Prefs prefs = Prefs.with(SplashActivity.this);
                if (prefs.getBoolean(Statics.LOGIN_PREFS)){
                    start(MainActivity.class);
                }else{
                    start(LoginActivity.class);
                }
            }
        },2000);
    }

    private void setVerion(){
        txtVersion.setText(getString(R.string.text_version, BuildConfig.VERSION_NAME));
    }

    private void start(Class<?> aClass) {
        Intent intent = new Intent(this, aClass);
        startActivity(intent);
        finish();
    }
}
