package com.example.dllo.mirror.adapterworks;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.activityworks.ShareContentActivity;
import com.example.dllo.mirror.bean.ShareFragmentBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by dllo on 16/4/14.
 */
public class ShareFragmentAdapter extends RecyclerView.Adapter<ShareFragmentAdapter.ShareViewHolder> {

    private ShareFragmentBean bean;
    private Context context;
    private LinearLayout line;

    /**
     * 自定义 添加数据方法
     *
     * @param bean    专题分享的实体类
     * @param context 上下文
     */
    public void addData(ShareFragmentBean bean, Context context) {
        this.bean = bean;
        this.context = context;
        Log.d("sj", bean.toString());
        notifyDataSetChanged();  // 通知适配器  数据是实时更新的

    }


    @Override
    public ShareViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShareViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_share_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ShareViewHolder holder, final int position) {

        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading_gif)   //加载过程中的图片
                .showImageOnFail(R.mipmap.ic_launcher) //加载失败的图片
                .cacheInMemory(true)//是否放到内存缓存中
                .cacheOnDisk(true)//是否放到硬盘缓存中
                .bitmapConfig(Bitmap.Config.RGB_565)//图片的类型
                .build();//创建
        if (bean != null) {
            ImageLoader.getInstance().displayImage(bean.getData().getList().get(position).getStory_img(), holder.iv, options);
            holder.tv.setText(bean.getData().getList().get(position).getStory_title());
            line.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShareContentActivity.class);
                    intent.putExtra("story_id", bean.getData().getList().get(position).getStory_id());
                    context.startActivity(intent);
                }
            });
        }

    }

    /**
     * 返回数据个数
     *
     * @return 值为1, 说明有数据; 值为 0,说明bean 为空
     */
    @Override
    public int getItemCount() {
        if (bean != null) {
            return 1;
        }
        return 0;
    }


    /**
     * 缓存类
     */
    class ShareViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;
        private TextView tv;

        public ShareViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.fragment_share_item_iv);
            tv = (TextView) itemView.findViewById(R.id.fragment_share_item_brand);
            line = (LinearLayout) itemView.findViewById(R.id.fragment_share_item_line);
        }
    }
}
