package com.example.dllo.mirror.adapterworks;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.mirror.Bean;
import com.example.dllo.mirror.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/1.
 */
public class EveryGlassesFrontRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Bean bean;


    /**
     * 自定义 添加数据方法
     */
    public void addData(Bean bean) {
        this.bean = bean;

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
    ((FirstViewHolder) holder).tvCity.setText(bean.getData().getGoods_data().get(position).getCountry());
    ((FirstViewHolder) holder).tvFrom.setText(bean.getData().getGoods_data().get(position).getLocation());
    ((FirstViewHolder) holder).tvEnglishFrom.setText(bean.getData().getGoods_data().get(position).getEnglish());
    ((FirstViewHolder) holder).tvContent.setText(bean.getData().getGoods_data().get(position).getIntroContent());
        } else if (holder instanceof OtherViewHolder) {
            ((OtherViewHolder) holder).tvTitle.setText(bean.getData().getGoods_data().get(position).getName());
            ((OtherViewHolder) holder).tvContent.setText(bean.getData().getGoods_data().get(position).getIntroContent());
        }
    }

    /**
     * 返回数据个数
     */
    @Override
    public int getItemCount() {

        return bean.getData().getGoods_data().size();
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
