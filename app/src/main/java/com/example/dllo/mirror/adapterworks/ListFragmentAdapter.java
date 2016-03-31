package com.example.dllo.mirror.adapterworks;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.dllo.mirror.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/30.
 *  ListFragment  recyclerview的适配器
 */
public class  ListFragmentAdapter extends RecyclerView.Adapter<ListFragmentAdapter.ListViewHolder> {

    private ArrayList<String> data;

//    private MyItemListener listener;

//    public void setMyItemListener(MyItemListener listener) {
//        this.listener = listener;
//    }

    /**
     * 自定义 添加数据方法
     */
    public void addData(ArrayList<String> data) {
        this.data = data;
        notifyDataSetChanged();  // 通知适配器  数据是实时更新的
    }


    // 缓存类
    class ListViewHolder extends RecyclerView.ViewHolder  {

        //        private TextView tv;
        private ImageView iv;
//        private int position;  // 用于监听事件
//        private LinearLayout linearLayout;

        //  缓存类 构造方法
        public ListViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.fragment_list_item_iv);

        }

    }


    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_list_item, null);
        return new ListViewHolder(v);
    }



    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        if (data != null && data.size() > 0) {   // 判断  数据存在时

        }
    }


    /**
     * 返回数据个数
     */
    @Override
    public int getItemCount() {
        if (data != null && data.size() > 0) {
            return data.size();
        }
        return 0;
    }


}


