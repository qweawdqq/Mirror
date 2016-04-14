package com.example.dllo.mirror.activityworks;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.baseworks.BaseActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;

/**
 * Created by dllo on 16/4/8.
 */
public class PhotoActivity extends BaseActivity implements View.OnClickListener {
    private ImageView imageView;


    @Override
    protected int initLayout() {
        return R.layout.activity_photo;
    }

    @Override
    protected void initView() {
        imageView = bindView(R.id.photo_image);
    }

    @Override
    protected void initData() {

        String url = getIntent().getStringExtra("photoacticity");

        setImageLoader(url);

        imageView.setOnClickListener(this);
    }


    /**
     * 设置imageloader
     * @param url   图片请求的网址
     */
    private void setImageLoader(String url) {
        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(false)
                .cacheOnDisk(false)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        setImageView(url, options);

    }


    /**
     * 设置图片显示
     * @param url   图片请求的网址
     * @param options   imageLoader 的配置
     */
    private void setImageView(String url, DisplayImageOptions options) {
        ImageLoader.getInstance().loadImage(url, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                WeakReference<Bitmap> weak =new WeakReference<Bitmap>(loadedImage);
                imageView.setImageBitmap(weak.get());
                // TODO: 16/4/14  bitmap转化为drawable
//                BitmapDrawable bd = new BitmapDrawable(Resources.getSystem(),weak.get());
//                holder.imageView.setBackground(bd);
            }
        });
    }


    /**
     * 剪切bitmap
     * @param bitmap
     * @return   一个剪切好的Bitmap
     */
    private Bitmap setImageCrop(Bitmap bitmap) {
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();

        int x = w - 60;
        int y = h - 60;
        //下面这句是关键
        return Bitmap.createBitmap(bitmap, 60, 60, x, y, null, false);
    }

    @Override
    public void onClick(View v) {
        this.finishAfterTransition();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearCache();
        System.gc();

    }


    /**
     * 释放图片资源
     */
    private void clearCache() {
        imageView.setImageBitmap(null);
        imageView = null;
        ImageLoader.getInstance().clearMemoryCache();

    }
}
