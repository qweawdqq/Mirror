package com.example.dllo.mirror.adapterworks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dllo.mirror.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/11.
 */
public class AddressActivityAdapter extends BaseAdapter {

    private ArrayList<String> data;
    private Context context;

    public AddressActivityAdapter(ArrayList<String> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AddressHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_address_item, null);
            holder = new AddressHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (AddressHolder) convertView.getTag();
        }

//        holder.tvName.setText();
//holder.tvAddress.setText();
//        holder.tvPhone.setText();


        return convertView;
    }


    public class AddressHolder {
        private TextView tvName, tvAddress, tvPhone;
        private RelativeLayout layout;

        public AddressHolder(View itemView) {
            tvName = (TextView) itemView.findViewById(R.id.address_item_tv_name);
            tvAddress = (TextView) itemView.findViewById(R.id.address_item_tv_address);
            tvPhone = (TextView) itemView.findViewById(R.id.address_item_tv_phone);

            layout = (RelativeLayout) itemView.findViewById(R.id.address_item_btn_delete);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "删除", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
