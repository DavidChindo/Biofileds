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

import com.airbnb.lottie.LottieAnimationView;
import com.hics.biofields.BioApp;
import com.hics.biofields.Library.Connection;
import com.hics.biofields.Library.DesignUtils;
import com.hics.biofields.Library.Statics;
import com.hics.biofields.Models.Managment.RealmManager;
import com.hics.biofields.Network.Responses.RequisitionItemResponse;
import com.hics.biofields.Presenters.Events.RefreshRequisitionsEvent;
import com.hics.biofields.Presenters.Events.RequisitionEvent;
import com.hics.biofields.Presenters.Events.ResultsRequisitionsSearch;
import com.hics.biofields.R;
import com.hics.biofields.Views.Activity.FormRequisitionActivity;
import com.hics.biofields.Views.Activity.RequisitionDetailActivity;
import com.hics.biofields.Views.Adapters.RequisitionsAdapter;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequisitionFragment extends Fragment {

    @BindView(R.id.act_requisitions_list)ListView listView;
    @BindView(R.id.fr_requisitions_error)TextView errorTxt;
    @BindView(R.id.animation_error)LottieAnimationView animationView;
    //@BindView(R.id.swiperefresh_auth)SwipeRefreshLayout swiperefresh_auth;
    @BindView(R.id.swiperefresh_auth)SwipyRefreshLayout swiperefresh_auth;
    RequisitionsAdapter msAdapter;
    ArrayList<RequisitionItemResponse> requisitions;
    ProgressDialog mProgressDialog;
    int optionSelect = -1;
    int id = 1;
    boolean isRequesting = false;

    public RequisitionFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        requisitions = new ArrayList<RequisitionItemResponse>();

        msAdapter = new RequisitionsAdapter(getActivity(), R.layout.item_requisition, requisitions);
        listView.setAdapter(msAdapter);
        if (!isRequesting) {
            requisitionsAuth(true);
        }
        /*swiperefresh_auth.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swiperefresh_auth.setRefreshing(true);
                requisitionsAuth();
            }
        });*/
        swiperefresh_auth.setDirection(SwipyRefreshLayoutDirection.BOTH);
        swiperefresh_auth.setColorSchemeColors(R.color.colorPrimary);
        swiperefresh_auth.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                swiperefresh_auth.setRefreshing(true);
                if (!isRequesting) {
                    requisitionsAuth(direction == SwipyRefreshLayoutDirection.TOP);
                }
            }
        });
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
        EventBus.getDefault().postSticky(new RequisitionEvent(Integer.parseInt(requisitions.get(position).getNumRequisition()),true));
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

    private void requisitionsAuth(final boolean isPullToTop){
        if (Connection.isConnected(getActivity())) {
            isRequesting = true;
            if (!isPullToTop) {
                if (requisitions.size() > 0) {
                    id = Integer.parseInt(requisitions.get(requisitions.size() - 1).getNumRequisition()) + 1;
                }
            }else{
                id = 1;
            }

            Call<ArrayList<RequisitionItemResponse>> call = BioApp.getHicsService().requisitionsAuth("Bearer " + RealmManager.token(), id);
            mProgressDialog = ProgressDialog.show(getActivity(), null, "Cargando...");
            mProgressDialog.setCancelable(false);
            call.enqueue(new Callback<ArrayList<RequisitionItemResponse>>() {
                @Override
                public void onResponse(Call<ArrayList<RequisitionItemResponse>> call, Response<ArrayList<RequisitionItemResponse>> response) {
                    if (response.code() == Statics.code_OK_Get) {
                       //
                        swiperefresh_auth.setRefreshing(false);
                      //  if (mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        //}
                        if (!isPullToTop) {
                            requisitions.addAll(response.body());
                        }else{
                            requisitions = response.body();
                        }
                        if (response.body().isEmpty() && response.body().size() < 1 && requisitions.size() < 1){
                            annimation(0);
                        }else {
                            if (isPullToTop) {
                                RealmManager.deleteClass(RequisitionItemResponse.class, "1");
                            }
                            ArrayList<RequisitionItemResponse> requision = new ArrayList<RequisitionItemResponse>();
                            for (RequisitionItemResponse r : response.body()) {
                                RequisitionItemResponse rtemp = r;
                                rtemp.setNeedAuth("1");
                                rtemp.compoundPrimaryKey();
                                requision.add(rtemp);
                            }
                            Realm realm = Realm.getDefaultInstance();
                            RealmManager.insert(realm,requision);
                            realm.close();
                            msAdapter = new RequisitionsAdapter(getActivity(), R.layout.item_requisition, requisitions);
                            listView.setAdapter(msAdapter);
                        }
                    } else {
                       swiperefresh_auth.setRefreshing(false);
                       // if (mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        //}
                        annimation(0);
                        DesignUtils.errorMessage(getActivity(), "Error", "No fue posible obtener las requisiciones");
                    }
                    isRequesting = false;
                }

                @Override
                public void onFailure(Call<ArrayList<RequisitionItemResponse>> call, Throwable t) {
                    swiperefresh_auth.setRefreshing(false);
                    //if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    //}
                    annimation(0);
                    DesignUtils.errorMessage(getActivity(), "Error", t.getLocalizedMessage());
                    isRequesting = false;
                }
            });
        }else{
            swiperefresh_auth.setRefreshing(false);
            DesignUtils.errorMessage(getActivity(),"Error de Red","No hay conexión a internet");
            annimation(1);
            isRequesting = false;
        }
    }

    private void annimation(int isEmpty){
        errorTxt.setText(isEmpty == 0  ? R.string.empty_list : isEmpty == 1 ?  R.string.network_error : R.string.not_results);
        errorTxt.setVisibility(View.VISIBLE);
        if (isEmpty == 1) {
            animationView.setVisibility(View.VISIBLE);
            animationView.setAnimation("network_wifi.json");
            if (isEmpty == 1) {
                animationView.setBackgroundColor(R.color.gray_font_form);
            }
            animationView.loop(true);
            animationView.playAnimation();
        }
        listView.setVisibility(View.GONE);
    }

    @Subscribe(sticky = true)
    public void onLoadResults(ResultsRequisitionsSearch event){
        EventBus.getDefault().removeStickyEvent(event);
        if (event.requisitions != null){
                errorTxt.setVisibility(View.GONE);
                animationView.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                listView.setAdapter(null);
                msAdapter = new RequisitionsAdapter(getActivity(), R.layout.item_requisition, event.requisitions);
                listView.setAdapter(msAdapter);
        }else{
            listView.setVisibility(View.GONE);
            annimation(2);
        }
    }

    @Subscribe(sticky = true)
    public void onRefreshRequisitions(RefreshRequisitionsEvent event){
        EventBus.getDefault().removeStickyEvent(event);
        if (event.option == 0){
            if (!isRequesting) {
                requisitionsAuth(true);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}