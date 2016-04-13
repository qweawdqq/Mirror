package com.example.dllo.mirror.adapterworks;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.dllo.mirror.Bean;
import com.example.dllo.mirror.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/1.
 */
public class EveryGlassesBackRecyclerViewAdapter extends RecyclerView.Adapter<EveryGlassesBackRecyclerViewAdapter.EveryGlassesBackRecyclerViewHolder> {

    private Bean bean;
    private ArrayList<String> backData;

    /**
     * 自定义 添加数据方法
     */
    public void addData(Bean bean) {
        this.bean = bean;
        backData = new ArrayList<String>();
        backData.add(bean.getData().getGoods_pic());
        for (int i = 0; i < bean.getData().getDesign_des().size(); i++) {
            backData.add(bean.getData().getDesign_des().get(i).getImg());
        }
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

        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading_gif)   //加载过程中的图片
                .showImageOnFail(R.mipmap.ic_launcher) //加载失败的图片
                .cacheInMemory(true)//是否放到内存缓存中
                .cacheOnDisk(true)//是否放到硬盘缓存中
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565)//图片的类型
                .build();//创建

        if (bean != null) {   // 判断  数据存在时
            ImageLoader.getInstance().displayImage(backData.get(position), holder.iv, options);
//        holder.iv.setImageResource(R.mipmap.glasses);
        }

    }

    /**
     * 返回数据个数
     */
    @Override
    public int getItemCount() {

        return backData.size();
    }


    class EveryGlassesBackRecyclerViewHolder extends RecyclerView.ViewHolder {

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
