package com.hics.biofields.Views.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hics.biofields.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by david.barrera on 8/21/17.
 */

public class RequisitionsAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private ArrayList<String> mListAnswer;

    public RequisitionsAdapter(Context context, int resource, ArrayList<String> Answers) {
        super(context, resource, Answers);
        this.mContext = context;
        this.mListAnswer = Answers;
    }

    @Override
    public int getCount() {
        return mListAnswer.size();
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
        holder.title.setText(mListAnswer.get(position));

        return convertView;
    }

    class ViewHolder{

        @BindView(R.id.item_requisition_item)TextView title;
        public ViewHolder(View v){
            ButterKnife.bind(this, v);
        }
    }

}


