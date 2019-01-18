package com.seition.cloud.pro.newcloud.app.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

public class URLDrawable extends BitmapDrawable {
    protected Bitmap bitmap;

    @Override
    public void draw(Canvas canvas) {
        // TODO Auto-generated method stub
        if (bitmap != null && !bitmap.isRecycled()) {
            canvas.drawBitmap(bitmap, 0, 0, getPaint());
        }
    }

}