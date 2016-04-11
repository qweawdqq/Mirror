package com.example.dllo.mirror.baseworks;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import java.io.InputStream;

/**
 * Created by dllo on 16/4/9.
 */
public class BitMapTools {


    public static BitmapDrawable readBitMap(Context context, int resId) {
        InputStream is = context.getResources().openRawResource(resId);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = 10;   //width，hight设为原来的十分一
        Bitmap btp = BitmapFactory.decodeStream(is, null, options);
        BitmapDrawable bd = new BitmapDrawable(Resources.getSystem(),btp);
        return bd;
    }


}
