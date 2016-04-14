package com.example.dllo.mirror.adapterworks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageStats;
import android.graphics.Bitmap;
import android.os.StatFs;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.dllo.mirror.bean.Bean;
import com.example.dllo.mirror.R;
import com.example.dllo.mirror.activityworks.PhotoActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.lang.ref.WeakReference;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 * Created by dllo on 16/3/9.
 */
public class HeadRecycleAdapter extends RecyclerView.Adapter<HeadRecycleAdapter.MyViewHolder> {


    private ArrayList<Bean.DataBean.WearVideoBean> data;
    private Context context;
    //添加头要用的东西
    private final int TYPE_HEADER = 0;
    private final int TYPE_NORMAL = 1;
    private final int TYPE_FOOT = 2;
    private View mHeaderView;
    private View mFootView;


    public ArrayList<Bean.DataBean.WearVideoBean> getData() {
        return this.data;
    }

    //得到头部
    public void setHeaderView(View mHeaderView) {
        this.mHeaderView = mHeaderView;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void setFootView(View mFootView) {
        this.mFootView = mFootView;
    }

    public View getFootView() {
        return mFootView;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && position == 0) {
            return TYPE_HEADER;
        }
        if (mFootView != null && position + 1 == getItemCount()) {
            return TYPE_FOOT;
        }
        return TYPE_NORMAL;
    }


    public HeadRecycleAdapter(Context context) {
        this.context = context;
    }

    public void addInfo(ArrayList<Bean.DataBean.WearVideoBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    @Override
    public HeadRecycleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new MyViewHolder(mHeaderView);
        }
        if (mFootView != null && viewType == TYPE_FOOT) {
            return new MyViewHolder(mFootView);
        }
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.video_recycle_item, parent, false));

    }


    @Override
    public void onBindViewHolder(final HeadRecycleAdapter.MyViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;
        if (getItemViewType(position) == TYPE_FOOT) return;
        int pos = getRealPosition(position);
        holder.position = pos;
//        得到屏幕的宽度
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        final double width = outMetrics.widthPixels;


        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading_gif)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

//        ImageLoader.getInstance().displayImage(data.get(pos), holder.img, options);

        ImageLoader.getInstance().loadImage(data.get(pos).getData(), options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                double bitmap_width = loadedImage.getWidth();
                double bitmap_heigh = loadedImage.getHeight();
                double heigh = bitmap_heigh / bitmap_width * width;
                holder.img.getLayoutParams().width = (int) (width);
                holder.img.getLayoutParams().height = (int) heigh - 120;
                WeakReference<Bitmap> weakReference = new WeakReference<Bitmap>(loadedImage);
                holder.img.setImageBitmap(weakReference.get());
            }
        });

    }

    public int getRealPosition(int position) {
        if (mHeaderView != null) return position - 1;
        return position;
    }


    @Override
    public int getItemCount() {
        if (mFootView != null) {
            return mHeaderView == null ? data.size() + 1 : data.size() + 2;
        }
        return mHeaderView == null ? data.size() : data.size() + 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView img;
        private int position;

        public MyViewHolder(View itemView) {
            super(itemView);
//            这里注意不要忘了
            if (itemView == mHeaderView) return;
            img = (ImageView) itemView.findViewById(R.id.recycle_item_image);

            itemView.setOnClickListener(this);
        }

//

        @Override
        public void onClick(View v) {
            scaleUpAnimation(v);
        }

        //        activity 跳转的动画
        private void scaleUpAnimation(View v) {
            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, v, "image");
            startNewActivity(options);
        }

        private void startNewActivity(ActivityOptionsCompat options) {
            Intent intent = new Intent(context, PhotoActivity.class);
            intent.putExtra("photoacticity", data.get(position).getData());
            ActivityCompat.startActivity((Activity) context, intent, options.toBundle());
        }

    }

}
