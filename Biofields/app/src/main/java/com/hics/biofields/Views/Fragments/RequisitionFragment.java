package com.hics.biofields.Views.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hics.biofields.R;
import com.hics.biofields.Views.Activity.RequisitionDetailActivity;
import com.hics.biofields.Views.Adapters.RequisitionsAdapter;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class RequisitionFragment extends Fragment {

    @BindView(R.id.act_requisitions_list)ListView listView;
    RequisitionsAdapter msAdapter;
    ArrayList<String> mAnswer;

    public RequisitionFragment() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAnswer = new ArrayList<String>();
        mAnswer.add("two");
        mAnswer.add("two");
        mAnswer.add("two");
        mAnswer.add("two");
        mAnswer.add("two");
        mAnswer.add("two");
        mAnswer.add("two");
        mAnswer.add("two");
        mAnswer.add("two");
        mAnswer.add("two");
        mAnswer.add("two");

        msAdapter = new RequisitionsAdapter(getActivity(), R.layout.item_requisition, mAnswer);
        listView.setAdapter(msAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_requisition, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnItemClick(R.id.act_requisitions_list)
    void onOpenRequisition(int option){
        start(RequisitionDetailActivity.class);
    }

    @OnClick(R.id.act_sync_btn_sync)
    void onNewRequisition(){
        Toast.makeText(getActivity(), "Open form Requisition", Toast.LENGTH_SHORT).show();
    }

    private void start(Class<?> aClass) {
        Intent intent = new Intent(getActivity(), aClass);
        startActivity(intent);
    }

}