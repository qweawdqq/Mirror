package com.example.dllo.mirror.adapterworks;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.activityworks.ChangeAddressActivity;
import com.example.dllo.mirror.allviewworks.SwipeLayout;
import com.example.dllo.mirror.bean.AllAddressBean;
import com.example.dllo.mirror.eventbusclass.RefreshAddress;
import com.example.dllo.mirror.interfaceworks.ContentViewLinister;
import com.example.dllo.mirror.net.NetConnectionStatus;
import com.example.dllo.mirror.net.NetListener;
import com.example.dllo.mirror.net.OkHttpNetHelper;
import com.example.dllo.mirror.normalstatic.StaticEntityInterface;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;

import de.greenrobot.event.EventBus;

/**
 * Created by dllo on 16/4/11.
 */
public class AddressActivityAdapter extends BaseAdapter implements StaticEntityInterface {

    private AllAddressBean bean;
    private Context context;

    public AddressActivityAdapter(AllAddressBean bean, Context context) {
        this.bean = bean;
        this.context = context;
    }

    @Override
    public int getCount() {
        return bean.getData().getList().size();
    }

    @Override
    public Object getItem(int position) {
        return bean.getData().getList().get(position);
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

        holder.tvName.setText(bean.getData().getList().get(position).getUsername());
        holder.tvAddress.setText(bean.getData().getList().get(position).getAddr_info());
        holder.tvPhone.setText(bean.getData().getList().get(position).getCellphone());
        holder.position = position;

        return convertView;
    }


    public class AddressHolder {
        private TextView tvName, tvAddress, tvPhone;
        private RelativeLayout delLayout;
        private SwipeLayout allLayout;
        private ImageView ivChange;
        private int position;

        public AddressHolder(View itemView) {
            tvName = (TextView) itemView.findViewById(R.id.address_item_tv_name);
            tvAddress = (TextView) itemView.findViewById(R.id.address_item_tv_address);
            tvPhone = (TextView) itemView.findViewById(R.id.address_item_tv_phone);

            allLayout= (SwipeLayout) itemView.findViewById(R.id.address_item_layout_all);
            allLayout.setContentListener(new ContentViewLinister() {
                @Override
                public void doSomeThing() {
                    Toast.makeText(context, "设置地址成功", Toast.LENGTH_SHORT).show();
                    String addressId=bean.getData().getList().get(position).getAddr_id();
                    setMyAddress(addressId);
                    NetConnectionStatus.saveSharedPrefer(context,"myAddress",addressId);
                }
            });

            // 删除按钮
            delLayout = (RelativeLayout) itemView.findViewById(R.id.address_item_btn_delete);
            delLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    delAddress(position);

                    notifyDataSetChanged();

                    Intent delBroadcast = new Intent("com.example.dllo.mirror.DEL_BROADCAST");
                    //  发送一条删除按钮广播
                    context.sendBroadcast(delBroadcast);

                    Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                }
            });

            // 修改按钮
            ivChange = (ImageView) itemView.findViewById(R.id.address_item_btn_change);
            ivChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, ChangeAddressActivity.class);
                    intent.putExtra("id", bean.getData().getList().get(position).getAddr_id());
                    intent.putExtra("name", bean.getData().getList().get(position).getUsername());
                    intent.putExtra("phone", bean.getData().getList().get(position).getCellphone());
                    intent.putExtra("address", bean.getData().getList().get(position).getAddr_info());
                    context.startActivity(intent);

                }
            });

        }
    }


    public void delAddress(int position) {
        OkHttpNetHelper httpNetHelper = new OkHttpNetHelper();

        FormEncodingBuilder builder = new FormEncodingBuilder();
        // 解析数据
        builder.add("token", "fa9d21aa6de9639d76ac1f31d7519326");
        builder.add("device_type", "3");
        builder.add("addr_id", bean.getData().getList().get(position).getAddr_id());

        httpNetHelper.getPostDataFromNet(builder, DEL_ADDRESS, new NetListener() {
            @Override
            public void getSuccess(String s) {

            }

            @Override
            public void getFail(String s) {

            }
        });

    }

    public void setMyAddress(String id) {
        OkHttpNetHelper httpNetHelper = new OkHttpNetHelper();

        FormEncodingBuilder builder = new FormEncodingBuilder();
        // 解析数据
        builder.add("token", "fa9d21aa6de9639d76ac1f31d7519326");
        builder.add("addr_id",id );

        httpNetHelper.getPostDataFromNet(builder, MR_ADDRESS, new NetListener() {
            @Override
            public void getSuccess(String s) {

            }

            @Override
            public void getFail(String s) {
            }
        });

        Intent finishBroadcast = new Intent("com.example.dllo.mirror.FINISH_BROADCAST");
        //  发送一条finish的广播
        context.sendBroadcast(finishBroadcast);

    }

}
