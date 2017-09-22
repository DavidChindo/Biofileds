package com.hics.biofields.Views.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.Gson;
import com.hics.biofields.BioApp;
import com.hics.biofields.Library.Connection;
import com.hics.biofields.Library.DesignUtils;
import com.hics.biofields.Library.Statics;
import com.hics.biofields.Models.Managment.RealmManager;
import com.hics.biofields.Network.Requests.RequisitionAuthRequest;
import com.hics.biofields.Network.Responses.BudgeItemResponse;
import com.hics.biofields.Network.Responses.FilesReqResponse;
import com.hics.biofields.Network.Responses.RequisitionAuthResponse;
import com.hics.biofields.Network.Responses.RequisitionDetailResponse;
import com.hics.biofields.Network.Responses.ResponseGeneric;
import com.hics.biofields.Presenters.Events.CloseFormRequisitionEvent;
import com.hics.biofields.Presenters.Events.RequisitionEvent;
import com.hics.biofields.R;
import com.hics.biofields.Views.Adapters.FilesDetailAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequisitionDetailActivity extends AppCompatActivity {

    @BindView(R.id.animation_view)LottieAnimationView animationView;
    @BindView(R.id.toolbar_detail)Toolbar toolbar;
    @BindView(R.id.act_detail_statusbar) LinearLayout statusBarLn;
    @BindView(R.id.act_detail_num) TextView numTxt;
    @BindView(R.id.act_detail_status) TextView statusTxt;
    @BindView(R.id.act_detail_urgent) ImageView urgentImg;
    @BindView(R.id.act_detail_factory) TextView companyTxt;
    @BindView(R.id.act_detail_description) TextView descriptionTxt;
    @BindView(R.id.act_detail_amount) TextView amountTxt;
    @BindView(R.id.act_detail_comments) TextView commentsTxt;
    @BindView(R.id.act_detail_center) TextView costCenterTxt;
    @BindView(R.id.act_detail_provider) TextView providerTxt;
    @BindView(R.id.act_detail_billed) TextView billedTxt;
    @BindView(R.id.act_detail_urgent_pay) ImageView urgentPayImg;
    @BindView(R.id.act_detail_billed_img)ImageView billedImg;
    @BindView(R.id.act_detail_files) ListView filesLv;
    @BindView(R.id.act_detail_ln_services) LinearLayout servicesLn;
    @BindView(R.id.card_validations)CardView validationCard;
    @BindView(R.id.act_detail_ln_involves)LinearLayout involvesLn;
    @BindView(R.id.act_requisition_det_btns)LinearLayout lnBtn;

    private ArrayList<Integer> prices = new ArrayList<Integer>();
    String reason = "";
    RequisitionDetailResponse mItemResponse;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisition_detail);
        ButterKnife.bind(this);
        toolbar.setTitle("Detalle");
        toolbar.setCollapsible(true);
        annimation();
    }

    private void annimation(){
        animationView.setAnimation("money.json");
        animationView.loop(true);
        animationView.playAnimation();
    }

    private void addItems(BudgeItemResponse budge){
        View child = getLayoutInflater().inflate(R.layout.item_budge, null);
        TextView budgeItem =  (TextView)child.findViewById(R.id.item_budge_item);
        TextView qtyItem =  (TextView)child.findViewById(R.id.item_budge_number);
        TextView amountItem =  (TextView)child.findViewById(R.id.item_budge_amount);
        if (budge != null){
            budgeItem.setText(budge.getDescBudge());
            qtyItem.setText(budge.getQtyBudge());
            amountItem.setText(budge.getPriceBudge());
            prices.add(Integer.parseInt(budge.getPriceBudge()));
        }
        servicesLn.addView(child);
    }


    private void addItemTotal(ArrayList<Integer> mPrices){
        if (!mPrices.isEmpty()) {
            View child = getLayoutInflater().inflate(R.layout.item_detail_total_budge, null);
            TextView totalTxt = (TextView) child.findViewById(R.id.item_total_amount);
            int total = 0;
            for (int i = 0; i < mPrices.size(); i++) {
                total += mPrices.get(i);
            }
            totalTxt.setText(getString(R.string.total_budges, total));

            servicesLn.addView(child);
        }
    }

    private void addInvolved(String name){
        if (!name.isEmpty()){
            View involvedLn = getLayoutInflater().inflate(R.layout.item_involved,null);
            TextView involvedTxt = (TextView) involvedLn.findViewById(R.id.item_involved_name);
            involvedTxt.setText(getString(R.string.involved_name,name));
            involvesLn.addView(involvedLn);
        }
    }

    @Subscribe(sticky = true)
    public void onRequisitionEvent(RequisitionEvent event){
        EventBus.getDefault().removeStickyEvent(event);
        lnBtn.setVisibility(event.showBtn ? View.VISIBLE : View.GONE);
        requisitionDetail(event.reqNumber);
    }

    private void  mapField(RequisitionDetailResponse itemResponse){
        if (itemResponse != null){
            mItemResponse = itemResponse;
            numTxt.setText("No. "+itemResponse.getNumRequisition());
            companyTxt.setText(itemResponse.getCompanyNameRequisition());
            descriptionTxt.setText(itemResponse.getDescRequsition());
            amountTxt.setText(itemResponse.getAmountRequsition());
            statusBarLn.setVisibility(itemResponse.getUrgentRequsition().toLowerCase().contains("urgente") ? View.VISIBLE : View.GONE);
            statusTxt.setText(getStatus(itemResponse.getStatusRequisition()));
            commentsTxt.setText(itemResponse.getDescRequsition());
            costCenterTxt.setText(itemResponse.getCostCenterRequisition());
            providerTxt.setText(itemResponse.getSalesManNumberRequisition());
            billedImg.setImageResource(itemResponse.getBilledRequisition().toLowerCase().contains("no") ? R.drawable.ic_no : R.drawable.ic_yes);
            billedImg.setColorFilter(itemResponse.getBilledRequisition().toLowerCase().contains("no") ? getResources().getColor(R.color.red) : getResources().getColor(R.color.colorPrimary));
            urgentPayImg.setImageResource(itemResponse.getUrgentRequsition().toLowerCase().contains("urgente")  ? R.drawable.ic_yes : R.drawable.ic_no);
            urgentPayImg.setColorFilter(itemResponse.getUrgentRequsition().toLowerCase().contains("urgente") ? getResources().getColor(R.color.colorPrimary) : getResources().getColor(R.color.red));
            addInvolved(itemResponse.getApplicantRequisition());
            addInvolved(itemResponse.getTitularRequisition());
            addInvolved(itemResponse.getDirectorRequisition());
            addInvolved(itemResponse.getBuyerRequisition());
            addInvolved(itemResponse.getAuditorRequisition());

            if (!itemResponse.getFiles().isEmpty()){
                filesLv.setAdapter(new FilesDetailAdapter(this,R.layout.item_files_detail,itemResponse.getFiles()));
                DesignUtils.setListViewHeightBasedOnChildrenAdapter(filesLv);
            }

            if (!itemResponse.getItems().isEmpty()){
                for(int i=0; i < itemResponse.getItems().size(); i ++){
                    addItems(itemResponse.getItems().get(i));
                }
            }
            addItemTotal(prices);
        }
    }

    private String getStatus(String status){
        if (status.contains("<br>")){
            return status.substring(0,status.indexOf("<")-1);
        }else{
            return "N/A";
        }
    }

    @OnItemClick(R.id.act_detail_files)
    void onFileOpenClick(int position){
        if (position >= 0){
            Uri uri = Uri.parse(((FilesReqResponse) filesLv.getItemAtPosition(position)).getUrl());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    @OnClick(R.id.act_detail_cancel)
    void onRejectRequisitionClick(){
        validationCard.setVisibility(View.GONE);
        showDialog();
    }

    @OnClick(R.id.act_detail_sent)
    void onAcceptRequisition(){
        mProgressDialog = ProgressDialog.show(this, null, "Enviando...");
        mProgressDialog.setCancelable(false);
        sentAuthorization(reason,true);
    }

    private void requisitionDetail(int id){
        if(Connection.isConnected(this)){
            Call<ArrayList<RequisitionDetailResponse>> call = BioApp.getHicsService().requisitionDetail("Bearer "+RealmManager.token(),id);
            mProgressDialog = ProgressDialog.show(this, null, "Consultando...");
            mProgressDialog.setCancelable(false);
            call.enqueue(new Callback<ArrayList<RequisitionDetailResponse>>() {
                @Override
                public void onResponse(Call<ArrayList<RequisitionDetailResponse>> call, Response<ArrayList<RequisitionDetailResponse>> response) {
                    mProgressDialog.dismiss();
                    if (response.code() == Statics.code_OK_Get){
                        if(response.body().size() > 0 && !response.body().isEmpty()){
                            mapField(response.body().get(0));
                        }
                    }else{
                        Toast.makeText(RequisitionDetailActivity.this,"Por el momento no se puede visualizar el detalle de la requisición", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<RequisitionDetailResponse>> call, Throwable t) {
                    mProgressDialog.dismiss();
                    Toast.makeText(RequisitionDetailActivity.this,t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }else{
            Toast.makeText(this, "No hay conexión a intern", Toast.LENGTH_LONG).show();
            finish();
        }
    }


    private void sentAuthorization(String reason,boolean isAuthorization){
        if (Connection.isConnected(this)) {
            if (mItemResponse != null) {
                RequisitionAuthRequest requisitionAuthRequest = new RequisitionAuthRequest(Integer.parseInt(mItemResponse.getNumRequisition()),
                        isAuthorization, reason, RealmManager.usrID());
                Gson gson = new Gson();
                String json = gson.toJson(requisitionAuthRequest);
                Log.d("JSONREQUISITION", json);
                Call<RequisitionAuthResponse> call = BioApp.getHicsService().sentRequisitionAuth("Bearer " + RealmManager.token(), requisitionAuthRequest);

                call.enqueue(new Callback<RequisitionAuthResponse>() {
                    @Override
                    public void onResponse(Call<RequisitionAuthResponse> call, Response<RequisitionAuthResponse> response) {
                        mProgressDialog.dismiss();
                        if (response.code() == Statics.code_OK_Get) {
                            Log.d(RequisitionDetailActivity.class.getSimpleName(), response.body().getMessage());
                            showDialog("Autorizar Requisición",response.body().getMessage());
                        } else {
                            try {
                                Gson gson = new Gson();
                                ResponseGeneric respone = gson.fromJson(response.errorBody().string(),ResponseGeneric.class);
                                showDialog("Autorizar Requisición",respone.getMessage());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RequisitionAuthResponse> call, Throwable t) {
                        mProgressDialog.dismiss();
                        t.printStackTrace();
                    }
                });
            }
        }else{
            DesignUtils.errorMessage(this,"Autorizar Requisición","No hay conexión a internet");
        }
    }


    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Comentarios");
        final EditText input = new EditText(this);

        builder.setView(input);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mProgressDialog = ProgressDialog.show(RequisitionDetailActivity.this, null, "Enviando...");
                mProgressDialog.setCancelable(false);
                if (!input.getText().toString().trim().isEmpty()){
                    reason = input.getText().toString().trim();
                    sentAuthorization(reason,false);
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void showDialog(String title, String msg){
        android.support.v7.app.AlertDialog.Builder builder;
        builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        EventBus.getDefault().postSticky(new CloseFormRequisitionEvent(true));
                    }
                })
                .show();
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
