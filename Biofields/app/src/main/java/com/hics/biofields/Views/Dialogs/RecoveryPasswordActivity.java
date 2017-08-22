package com.hics.biofields.Views.Dialogs;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hics.biofields.R;

import butterknife.ButterKnife;

public class RecoveryPasswordActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_password);
        ButterKnife.bind(this);
        this.setFinishOnTouchOutside(false);

    }
}
