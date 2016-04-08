package com.example.dllo.mirror.adapterworks;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.mirror.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/1.
 */
public class EveryGlassesFrontRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View v = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.activity_everyglasses_frontrecyclerview_itemfirst, parent, false);
            return new FirstViewHolder(v);
        } else {
            View view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.activity_everyglasses_frontrecyclerview_itemothers, parent, false);
            return new OtherViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FirstViewHolder) {
//    ((FirstViewHolder) holder).tvCity.setText();
//    ((FirstViewHolder) holder).tvFrom.setText();
//    ((FirstViewHolder) holder).tvEnglishFrom.setText();
//    ((FirstViewHolder) holder).tvContent.setText();
        } else if (holder instanceof OtherViewHolder) {
//            ((OtherViewHolder) holder).tvTitle.setText();
//            ((OtherViewHolder) holder).tvContent.setText();
        }
    }

    /**
     * 返回数据个数
     */
    @Override
    public int getItemCount() {
       // TODO
        return 4;
    }


    class OtherViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle, tvContent;

        public OtherViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.everyglasses_frontrecyclerview_itemother_title);
            tvContent = (TextView) itemView.findViewById(R.id.everyglasses_frontrecyclerview_itemother_content);
        }

    }

    class FirstViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCity, tvFrom, tvEnglishFrom, tvContent;
        private ImageView iv;

        //  缓存类 构造方法
        public FirstViewHolder(View itemView) {
            super(itemView);
            tvCity = (TextView) itemView.findViewById(R.id.everyglasses_frontrecyclerview_itemfirst_city);
            tvFrom = (TextView) itemView.findViewById(R.id.everyglasses_frontrecyclerview_itemfirst_from);
            tvEnglishFrom = (TextView) itemView.findViewById(R.id.everyglasses_frontrecyclerview_itemfirst_englisrfrom);
            tvContent = (TextView) itemView.findViewById(R.id.everyglasses_frontrecyclerview_itemfirst_content);
        }

    }

}
