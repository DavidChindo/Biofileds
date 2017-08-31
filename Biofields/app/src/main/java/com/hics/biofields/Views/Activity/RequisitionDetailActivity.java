package com.hics.biofields.Views.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.hics.biofields.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RequisitionDetailActivity extends AppCompatActivity {

    @BindView(R.id.act_detail_ln_services)LinearLayout itemsLn;
    @BindView(R.id.animation_view)LottieAnimationView animationView;
    @BindView(R.id.toolbar_detail)Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisition_detail);
        ButterKnife.bind(this);
        toolbar.setTitle("Detalle");
        for (int i = 0; i < 6; i++) {
            addItems();
        }
        annimation();
    }

    private void annimation(){
        animationView.setAnimation("money.json");
        animationView.loop(true);
        animationView.playAnimation();
    }

    private void addItems(){
        View child = getLayoutInflater().inflate(R.layout.item_budge, null);
        itemsLn.addView(child);
    }
}
