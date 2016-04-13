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
import com.example.dllo.mirror.baseworks.BitMapTools;
import com.example.dllo.mirror.bean.GoodsListBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by dllo on 16/3/30.
 * ListFragment  recyclerview的适配器
 */
public class ListFragmentAdapter extends RecyclerView.Adapter<ListFragmentAdapter.ListViewHolder> {

    private GoodsListBean bean;
    private LinearLayout listLine;
    private Context context;

    /**
     * 自定义 添加数据方法
     */
    public void addData(GoodsListBean bean, Context context) {
        this.context = context;
        this.bean = bean;
        Log.d("111111", bean.toString());
        notifyDataSetChanged();  // 通知适配器  数据是实时更新的
        Log.d("图", bean.getData().getList().get(0).getGoods_img());

    }

    // 缓存类
    class ListViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name, tv_goods_price, tv_discount_price, tv_produce_area, tv_brand, tv_rmb;
        private ImageView iv;
//        private int position;  // 用于监听事件
//        private LinearLayout linearLayout;

        //  缓存类 构造方法
        public ListViewHolder(View itemView) {
            super(itemView);

            iv = (ImageView) itemView.findViewById(R.id.fragment_list_item_iv);
            tv_name = (TextView) itemView.findViewById(R.id.fragment_list_item_name);
            tv_goods_price = (TextView) itemView.findViewById(R.id.fragment_list_item_goods_price);
            tv_discount_price = (TextView) itemView.findViewById(R.id.fragment_list_item_discount_price);
            tv_produce_area = (TextView) itemView.findViewById(R.id.fragment_list_item_city);
            tv_brand = (TextView) itemView.findViewById(R.id.fragment_list_item_brand);
            tv_rmb = (TextView) itemView.findViewById(R.id.fragment_list_item_rmb);
            listLine = (LinearLayout) itemView.findViewById(R.id.fragment_list_item_glasses_line);
        }

    }


    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_list_glassesitem, parent, false);
        return new ListViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ListViewHolder holder, final int position) {

        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)   //加载过程中的图片
                .showImageOnFail(R.mipmap.ic_launcher) //加载失败的图片
                .cacheInMemory(true)//是否放到内存缓存中
                .cacheOnDisk(true)//是否放到硬盘缓存中
                .bitmapConfig(Bitmap.Config.RGB_565)//图片的类型
                .build();//创建

        if (bean != null) {   // 判断  数据存在时
            ImageLoader.getInstance().displayImage(bean.getData().getList().get(position).getGoods_img(), holder.iv, options);

            holder.tv_name.setText(bean.getData().getList().get(position).getGoods_name());
            listLine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EveryGlassesActivity.class);
                    intent.putExtra("id", bean.getData().getList().get(position).getGoods_id());
                    context.startActivity(intent);
                }
            });

            // 商品是否打折,根据判断显示不同的内容
            if (!bean.getData().getList().get(position).getDiscount_price().equals("")) {
                holder.tv_rmb.setText("¥");
                holder.tv_rmb.setVisibility(View.VISIBLE);
                holder.tv_discount_price.setText(bean.getData().getList().get(position).getDiscount_price());
                holder.tv_discount_price.setVisibility(View.VISIBLE);
                holder.tv_goods_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                holder.tv_goods_price.setText("¥" + bean.getData().getList().get(position).getGoods_price());
            } else {
                holder.tv_rmb.setVisibility(View.INVISIBLE);
                holder.tv_goods_price.setText("¥" + bean.getData().getList().get(position).getGoods_price());
            }
            holder.tv_produce_area.setText(bean.getData().getList().get(position).getProduct_area());
            holder.tv_brand.setText(bean.getData().getList().get(position).getBrand());
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


