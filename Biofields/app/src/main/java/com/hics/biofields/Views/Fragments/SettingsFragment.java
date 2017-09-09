package com.hics.biofields.Views.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hics.biofields.BioApp;
import com.hics.biofields.BuildConfig;
import com.hics.biofields.Library.DesignUtils;
import com.hics.biofields.Library.Prefs;
import com.hics.biofields.Library.Statics;
import com.hics.biofields.Models.Managment.RealmManager;
import com.hics.biofields.Network.Responses.ResponseGeneric;
import com.hics.biofields.R;
import com.hics.biofields.Views.Activity.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment {

    ProgressDialog mProgressDialog;
    @BindView(R.id.fr_settings_version)TextView versionTxt;

    public SettingsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        versionTxt.setText(getResources().getString(R.string.text_version_complete, BuildConfig.VERSION_NAME));
    }

    @OnClick(R.id.fr_setting_logout)
    void onLogoutClick(){
        mProgressDialog = ProgressDialog.show(getActivity(),null,"Cerrando Sesión...");
        Call<ResponseGeneric> call = BioApp.getHicsService().logout("Bearer "+ RealmManager.token());
        call.enqueue(new Callback<ResponseGeneric>() {
            @Override
            public void onResponse(Call<ResponseGeneric> call, Response<ResponseGeneric> response) {
                if(response.code() == Statics.code_OK_Get){
                    if (!response.body().getError()){
                        RealmManager.deleteRealm();
                        Prefs prefs = Prefs.with(getActivity());
                        prefs.putBoolean(Statics.LOGIN_PREFS, false);
                        mProgressDialog.dismiss();
                        start(LoginActivity.class,true);

                    }else{
                        mProgressDialog.dismiss();
                        DesignUtils.errorMessage(getActivity(),"Cerrar Sesión",response.body().getMessage());
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseGeneric> call, Throwable t) {
                mProgressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    private void start(Class<?> aClass,boolean isFinished ) {
        Intent intent = new Intent(getActivity(), aClass);
        startActivity(intent);
        if (isFinished) {
            getActivity().finish();
        }
    }

}
