package com.example.dllo.mirror.baseworks;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.zhy.autolayout.config.AutoLayoutConifg;

import java.io.File;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by jialiang on 16/3/28.
 * application的基类
 */
public class BaseApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initImageLoader(this);

        AutoLayoutConifg.getInstance().useDeviceSize();

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

    }

    /*
    *初始化imageloader
    */
    private void initImageLoader(Context context) {
//缓存文件的目录
        File cacheDir = StorageUtils.getOwnCacheDirectory(context,
                "universalimageloader/Cache");
        if (!cacheDir.exists()) {
            cacheDir.mkdir();
        }
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800)//每个缓存最大宽高
                .threadPoolSize(3)//线程数
                .threadPriority(Thread.NORM_PRIORITY - 2)//优先级
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)//内存最大值
                .diskCacheSize(50 * 1024 * 1024)//sd卡缓存最大值
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000))
                .writeDebugLogs()
                .build();
        //全局初始化
        ImageLoader.getInstance().init(config);
    }

    public static Context getContext(){
        return context;
    }

}
