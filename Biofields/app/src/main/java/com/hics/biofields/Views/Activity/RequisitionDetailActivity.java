package com.hics.biofields.Views.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.hics.biofields.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RequisitionDetailActivity extends AppCompatActivity {

    @BindView(R.id.act_detail_ln_services)LinearLayout itemsLn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisition_detail);
        ButterKnife.bind(this);
        for (int i = 0; i < 6; i++) {
            addItems();
        }
    }

    private void addItems(){
        View child = getLayoutInflater().inflate(R.layout.item_budge, null);
        itemsLn.addView(child);
    }
}
