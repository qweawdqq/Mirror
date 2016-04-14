package com.example.dllo.mirror.adapterworks;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.activityworks.EveryGlassesActivity;
import com.example.dllo.mirror.bean.DailyCommandBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by dllo on 16/4/7.
 */
public class AllFragmentAdapter extends RecyclerView.Adapter<AllFragmentAdapter.AllViewHolder> {

    private DailyCommandBean bean;
    private LinearLayout allLine;
    private Context context;

    /**
     * 自定义 添加数据方法
     */
    public void addData(DailyCommandBean bean, Context context) {
        this.bean = bean;
        this.context = context;
        Log.d("111111", bean.toString());
        notifyDataSetChanged();  // 通知适配器  数据是实时更新的

    }

    // 缓存类
    class AllViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name, tv_goods_price, tv_discount_price, tv_produce_area, tv_brand, tv_rmb;
        private ImageView iv;

        //  缓存类 构造方法
        public AllViewHolder(View itemView) {
            super(itemView);

            iv = (ImageView) itemView.findViewById(R.id.fragment_all_item_iv);
            tv_name = (TextView) itemView.findViewById(R.id.fragment_all_item_name);
            tv_goods_price = (TextView) itemView.findViewById(R.id.fragment_all_item_goods_price);
            tv_discount_price = (TextView) itemView.findViewById(R.id.fragment_all_item_discount_price);
            tv_produce_area = (TextView) itemView.findViewById(R.id.fragment_all_item_city);
            tv_brand = (TextView) itemView.findViewById(R.id.fragment_all_item_brand);
            tv_rmb = (TextView) itemView.findViewById(R.id.fragment_all_item_rmb);
            allLine = (LinearLayout) itemView.findViewById(R.id.fragment_all_item_line);
            // 给RecyclerView设置跳转,到二级商品详情界面
            allLine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EveryGlassesActivity.class);
                    intent.putExtra("id", bean.getData().getList().get(0).getData_info().getGoods_id());
                    context.startActivity(intent);
                }
            });
        }

    }


    @Override
    public AllViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_all_item, parent, false);
        return new AllViewHolder(v);
    }


    @Override
    public void onBindViewHolder(AllViewHolder holder, int position) {

        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading_gif)   //加载过程中的图片
                .showImageOnFail(R.mipmap.ic_launcher) //加载失败的图片
                .cacheInMemory(true)//是否放到内存缓存中
                .cacheOnDisk(true)//是否放到硬盘缓存中
                .bitmapConfig(Bitmap.Config.RGB_565)//图片的类型
                .build();//创建

        if (bean != null) {   // 判断  数据存在时
            ImageLoader.getInstance().displayImage(bean.getData().getList().get(0).getData_info().getGoods_img(), holder.iv, options);
            holder.tv_name.setText(bean.getData().getList().get(0).getData_info().getGoods_name());
            // 商品是否打折,根据判断显示不同的内容
            if (!bean.getData().getList().get(0).getData_info().equals("")) {
                holder.tv_rmb.setText("¥");
                holder.tv_rmb.setVisibility(View.VISIBLE);
                holder.tv_discount_price.setText(bean.getData().getList().get(0).getData_info().getDiscount_price());
                holder.tv_discount_price.setVisibility(View.VISIBLE);
                holder.tv_goods_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                holder.tv_goods_price.setText("¥" + bean.getData().getList().get(0).getData_info().getGoods_price());
            } else {
                holder.tv_rmb.setVisibility(View.INVISIBLE);
                holder.tv_goods_price.setText("¥" + bean.getData().getList().get(0).getData_info().getGoods_price());
            }
            holder.tv_produce_area.setText(bean.getData().getList().get(0).getData_info().getProduct_area());
            holder.tv_brand.setText(bean.getData().getList().get(0).getData_info().getBrand());
        }
    }


    /**
     * 返回数据个数
     */
    @Override
    public int getItemCount() {
        if (bean != null) {
            return 1;
        }
        return 0;
    }


}
