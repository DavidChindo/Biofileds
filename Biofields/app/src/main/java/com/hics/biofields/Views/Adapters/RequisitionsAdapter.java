package com.hics.biofields.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hics.biofields.Library.Validators;
import com.hics.biofields.Network.Responses.RequisitionItemResponse;
import com.hics.biofields.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by david.barrera on 8/21/17.
 */

public class RequisitionsAdapter extends ArrayAdapter<RequisitionItemResponse> {

    private Context mContext;
    private ArrayList<RequisitionItemResponse> mRequisitions;

    public RequisitionsAdapter(Context context, int resource, ArrayList<RequisitionItemResponse> requisitions) {
        super(context, resource, requisitions);
        this.mContext = context;
        this.mRequisitions = requisitions;
    }

    @Override
    public int getCount() {
        return mRequisitions.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_requisition, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        final RequisitionItemResponse requisition = mRequisitions.get(position);
        holder.num.setText("No. "+requisition.getNumRequisition());
        holder.title.setText(Validators.validateString(requisition.getCompanyNameRequisition()));
        holder.description.setText(Validators.validateString(requisition.getDescRequsition()));
        //holder.date.setText(requisition.getDateRequisition());
        holder.providerTxt.setText(Validators.validateString(requisition.getSalesManNumberRequisition()));
        holder.amount.setText(Validators.validateString(requisition.getAmountRequsition()));
        if (requisition.getUrgentRequsition() != null && !requisition.getUrgentRequsition().isEmpty()) {
            if (requisition.getUrgentRequsition().toLowerCase().contains("urgente")) {
                holder.statusBar.setVisibility(View.VISIBLE);
                holder.urgentTxt.setVisibility(View.VISIBLE);
                holder.statusBarGray.setVisibility(View.GONE);
            } else {
                holder.statusBarGray.setVisibility(View.VISIBLE);
                holder.statusBar.setVisibility(View.GONE);
                holder.urgentTxt.setVisibility(View.GONE);
            }
        }else{
            holder.statusBarGray.setVisibility(View.VISIBLE);
            holder.statusBar.setVisibility(View.GONE);
            holder.urgentTxt.setVisibility(View.GONE);
        }


        holder.status.setText(getStatus(requisition.getStatusRequisition()));
        return convertView;
    }

    class ViewHolder{

        @BindView(R.id.item_requisition_num)TextView num;
        @BindView(R.id.item_requisition_item_company)TextView title;
        @BindView(R.id.item_requisition_description)TextView description;
        @BindView(R.id.item_requisition_date )TextView date;
        @BindView(R.id.item_requisition_amount)TextView amount;
        @BindView(R.id.item_requisition_status)TextView status;
        @BindView(R.id.item_requisition_urgent)ImageView iconUrgent;
        @BindView(R.id.item_requisition_statusbar)LinearLayout statusBar;
        @BindView(R.id.item_requisition_statusbar_gray)LinearLayout statusBarGray;
        @BindView(R.id.item_requisition_urgent_txt)TextView urgentTxt;
        @BindView(R.id.item_requisition_provider)TextView providerTxt;

        public ViewHolder(View v){
            ButterKnife.bind(this, v);
        }
    }

    private String getStatus(String status){
        if (status != null && !status.isEmpty()) {
            if (status.contains("<br>")) {
                return status.substring(0, status.indexOf("<") - 1);
            } else {
                return "N/A";
            }
        }else{
            return  "N/A";
        }
    }

}