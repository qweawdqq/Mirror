package com.example.dllo.mirror.adapterworks;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.dllo.mirror.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/1.
 */
public class EveryGlassesBackRecyclerViewAdapter extends  RecyclerView.Adapter<EveryGlassesBackRecyclerViewAdapter.EveryGlassesBackRecyclerViewHolder> {

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

    @Override
    public EveryGlassesBackRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.activity_everyglasses_backrecyclerview_item, null);
        return new EveryGlassesBackRecyclerViewHolder(v);
    }



    @Override
    public void onBindViewHolder(EveryGlassesBackRecyclerViewHolder holder, int position) {
//        if (data != null && data.size() > 0) {   // 判断  数据存在时
//
//        }
        holder.iv.setImageResource(R.mipmap.glasses);
    }


    /**
     * 返回数据个数
     */
    @Override
    public int getItemCount() {
//        if (data != null && data.size() > 0) {
//            return data.size();
//        }
        return 5;
    }



    class EveryGlassesBackRecyclerViewHolder extends RecyclerView.ViewHolder  {

        //        private TextView tv;
        private ImageView iv;
//        private int position;  // 用于监听事件
//        private LinearLayout linearLayout;

        //  缓存类 构造方法
        public EveryGlassesBackRecyclerViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.everyglasses_backrecyclerview_item_iv);

        }

    }

}
