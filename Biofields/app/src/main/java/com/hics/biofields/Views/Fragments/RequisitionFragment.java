package com.hics.biofields.Views.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.hics.biofields.R;
import com.hics.biofields.Views.Activity.RequisitionDetailActivity;
import com.hics.biofields.Views.Adapters.RequisitionsAdapter;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;


public class RequisitionFragment extends Fragment {

    @BindView(R.id.act_sync_list_surveys)ListView listView;
    RequisitionsAdapter msAdapter;
    ArrayList<String> mAnswer;

    public static RequisitionFragment newInstance(String param1, String param2) {
        RequisitionFragment fragment = new RequisitionFragment();
        return fragment;
    }
    public RequisitionFragment() {
        // Required empty public constructor
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        mAnswer = new ArrayList<String>();
        mAnswer.add("one");
        mAnswer.add("one");
        mAnswer.add("one");
        mAnswer.add("one");
        mAnswer.add("one");
        mAnswer.add("one");
        mAnswer.add("one");
        mAnswer.add("one");
        mAnswer.add("one");
        mAnswer.add("one");
        mAnswer.add("one");

        msAdapter = new RequisitionsAdapter(getActivity(), R.layout.item_requisition, mAnswer);
        listView.setAdapter(msAdapter);

    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_requisition, container, false);
    }

    @OnItemClick(R.id.act_sync_list_surveys)
    void onRequisitonOpen(int position){
        start(RequisitionDetailActivity.class);
    }

    private void start(Class<?> aClass) {
        Intent intent = new Intent(getActivity(), aClass);
        startActivity(intent);
    }

}