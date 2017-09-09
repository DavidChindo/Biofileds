package com.hics.biofields.Views.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.hics.biofields.Network.Responses.BudgeItemResponse;
import com.hics.biofields.Network.Responses.RequisitionItemResponse;
import com.hics.biofields.Presenters.Events.RequisitionEvent;
import com.hics.biofields.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    private ArrayList<Integer> prices = new ArrayList<Integer>();

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
        mapField(event.requisition);
    }

    private void  mapField(RequisitionItemResponse itemResponse){
        if (itemResponse != null){
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

    @OnClick(R.id.act_detail_cancel)
    void onRejectRequisitionClick(){
        validationCard.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.act_detail_sent)
    void onAcceptRequisition(){
        Toast.makeText(this,"Se estara enviando la aceptacion de la requisicion",Toast.LENGTH_LONG).show();
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
