package com.hics.biofields.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hics.biofields.Network.Responses.FilesReqResponse;
import com.hics.biofields.Network.Responses.RequisitionItemResponse;
import com.hics.biofields.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by david.barrera on 9/15/17.
 */

public class FilesDetailAdapter extends ArrayAdapter<FilesReqResponse> {

    private Context mContext;
    private ArrayList<FilesReqResponse> mFiles;

    public FilesDetailAdapter(Context context, int resource, ArrayList<FilesReqResponse> files) {
        super(context, resource, files);
        this.mContext = context;
        this.mFiles = files;
    }

    @Override
    public int getCount() {
        return mFiles.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        FilesDetailAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_files_detail, null);
            holder = new FilesDetailAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (FilesDetailAdapter.ViewHolder) convertView.getTag();
        }
        final FilesReqResponse file = mFiles.get(position);
        if (file != null){
            holder.url.setText(nameFile(file.getUrl()));
        }

        return convertView;
    }

    class ViewHolder {

        @BindView(R.id.item_file_detail_name)
        TextView url;

        public ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }


    private String nameFile(String path){
        String name = "";
        if (path != null && !path.isEmpty()){
            String[] paths = path.split("/");
            name = paths[paths.length-1];
        }
        return  name;
    }

}


