package com.hics.biofields.Views.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.annotations.SerializedName;
import com.hics.biofields.BioApp;
import com.hics.biofields.Library.Connection;
import com.hics.biofields.Library.DesignUtils;
import com.hics.biofields.Library.Statics;
import com.hics.biofields.Models.Managment.RealmManager;
import com.hics.biofields.Network.Responses.RequisitionItemResponse;
import com.hics.biofields.Presenters.Events.RequisitionEvent;
import com.hics.biofields.R;
import com.hics.biofields.Views.Activity.FormRequisitionActivity;
import com.hics.biofields.Views.Activity.RequisitionDetailActivity;
import com.hics.biofields.Views.Adapters.RequisitionsAdapter;

import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequisitionFragment extends Fragment {

    @BindView(R.id.act_requisitions_list)ListView listView;
    @BindView(R.id.fr_requisitions_error)TextView errorTxt;
    @BindView(R.id.animation_error)LottieAnimationView animationView;
    RequisitionsAdapter msAdapter;
    ArrayList<RequisitionItemResponse> requisitions;
    ProgressDialog mProgressDialog;

    public RequisitionFragment() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        requisitions = new ArrayList<RequisitionItemResponse>();

        msAdapter = new RequisitionsAdapter(getActivity(), R.layout.item_requisition, requisitions);
        listView.setAdapter(msAdapter);
        requisitionsOpen();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_requisition, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnItemClick(R.id.act_requisitions_list)
    void onOpenRequisition(int position){
        EventBus.getDefault().postSticky(new RequisitionEvent(requisitions.get(position)));

        start(RequisitionDetailActivity.class);
    }

    @OnClick(R.id.act_sync_btn_sync)
    void onNewRequisition(){
        startActivity(new Intent(getActivity(),FormRequisitionActivity.class));
    }

    private void start(Class<?> aClass) {
        Intent intent = new Intent(getActivity(), aClass);
        startActivity(intent);
    }

    private void requisitionsOpen(){
        if (Connection.isConnected(getActivity())) {

            Call<ArrayList<RequisitionItemResponse>> call = BioApp.getHicsService().requisitionsOpen("Bearer " + RealmManager.token(), 1);
            mProgressDialog = ProgressDialog.show(getActivity(), null, "Cargando...");
            mProgressDialog.setCancelable(false);
            call.enqueue(new Callback<ArrayList<RequisitionItemResponse>>() {
                @Override
                public void onResponse(Call<ArrayList<RequisitionItemResponse>> call, Response<ArrayList<RequisitionItemResponse>> response) {
                    if (response.code() == Statics.code_OK_Get) {
                        mProgressDialog.dismiss();
                        requisitions = response.body();
                        if (response.body().isEmpty() && response.body().size() < 1){
                            annimation(true);
                        }else {
                            msAdapter = new RequisitionsAdapter(getActivity(), R.layout.item_requisition, response.body());
                            listView.setAdapter(msAdapter);
                        }
                    } else {
                        mProgressDialog.dismiss();
                        annimation(true);
                        DesignUtils.errorMessage(getActivity(), "Error", "No fue posible obtener las requisiciones");
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<RequisitionItemResponse>> call, Throwable t) {
                    mProgressDialog.dismiss();
                    annimation(true);
                    DesignUtils.errorMessage(getActivity(), "Error", t.getLocalizedMessage());
                }
            });
        }else{
            DesignUtils.errorMessage(getActivity(),"Error de Red","No hay conexión a internet");
            annimation(false);
        }
    }

    private void requisitionsAuth(){
        if (Connection.isConnected(getActivity())) {
            Call<ArrayList<RequisitionItemResponse>> call = BioApp.getHicsService().requisitionsAuth("Bearer " + RealmManager.token(), 1);
            mProgressDialog = ProgressDialog.show(getActivity(), null, "Cargando...");
            mProgressDialog.setCancelable(false);
            call.enqueue(new Callback<ArrayList<RequisitionItemResponse>>() {
                @Override
                public void onResponse(Call<ArrayList<RequisitionItemResponse>> call, Response<ArrayList<RequisitionItemResponse>> response) {
                    if (response.code() == Statics.code_OK_Get) {
                        mProgressDialog.dismiss();
                        requisitions = response.body();
                        if (response.body().isEmpty() && response.body().size() < 1){
                            annimation(true);
                        }else {
                            msAdapter = new RequisitionsAdapter(getActivity(), R.layout.item_requisition, response.body());
                            listView.setAdapter(msAdapter);
                        }
                    } else {
                        mProgressDialog.dismiss();
                        annimation(true);
                        DesignUtils.errorMessage(getActivity(), "Error", "No fue posible obtener las requisiciones");
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<RequisitionItemResponse>> call, Throwable t) {
                    mProgressDialog.dismiss();
                    annimation(true);
                    DesignUtils.errorMessage(getActivity(), "Error", t.getLocalizedMessage());
                }
            });
        }else{
            DesignUtils.errorMessage(getActivity(),"Error de Red","No hay conexión a internet");
            annimation(false);
        }
    }

    private void annimation(boolean isEmpty){
        errorTxt.setText(isEmpty ? R.string.empty_list : R.string.network_error);
        errorTxt.setVisibility(View.VISIBLE);
        animationView.setVisibility(View.VISIBLE);
        animationView.setAnimation(isEmpty ? "shrug.json" : "network_wifi.json");
        if (!isEmpty) {
            animationView.setBackgroundColor(R.color.gray_font_form);
        }
        animationView.loop(true);
        animationView.playAnimation();
        listView.setVisibility(View.GONE);
    }

}