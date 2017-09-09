package com.hics.biofields.Views.Dialogs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.hics.biofields.BioApp;
import com.hics.biofields.Library.DesignUtils;
import com.hics.biofields.Library.Statics;
import com.hics.biofields.Network.Requests.RecoveryPasswordRequest;
import com.hics.biofields.Network.Responses.RecoveryPasswordResponse;
import com.hics.biofields.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecoveryPasswordActivity extends Activity {

    @BindView(R.id.act_recovery_email)EditText emailEdt;

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_password);
        ButterKnife.bind(this);
        this.setFinishOnTouchOutside(false);
    }

    @OnClick(R.id.act_recovery_sent)
    void onSentEmailClick(){
        String email = emailEdt.getText().toString().trim().toLowerCase();
        if (!email.isEmpty()){
            if (isValidEmail(email)){
                mProgressDialog = ProgressDialog.show(this, null, "Enviando...");
                mProgressDialog.setCancelable(false);
                recoveryPasswd(email);
            }else{
                Toast.makeText(RecoveryPasswordActivity.this,"Formato de correo incorrecto. Intente nuevamente",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(RecoveryPasswordActivity.this,"Favor de ingresar correo electrónico",Toast.LENGTH_LONG).show();
        }
    }

    private void recoveryPasswd(String email){
        RecoveryPasswordRequest recoveryPasswordRequest = new RecoveryPasswordRequest(email);
        Call<RecoveryPasswordResponse> call = BioApp.getHicsService().recoveryPassword(recoveryPasswordRequest);
        call.enqueue(new Callback<RecoveryPasswordResponse>() {
            @Override
            public void onResponse(Call<RecoveryPasswordResponse> call, Response<RecoveryPasswordResponse> response) {
                mProgressDialog.dismiss();
                if (response.code() == Statics.code_OK_Get) {
                    Toast.makeText(RecoveryPasswordActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(RecoveryPasswordActivity.this, "No existe el usuario", Toast.LENGTH_LONG).show();
                }
                finish();
            }
            @Override
            public void onFailure(Call<RecoveryPasswordResponse> call, Throwable t) {
                Toast.makeText(RecoveryPasswordActivity.this,t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public final static boolean isValidEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }
}
