package com.seition.cloud.pro.newcloud.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.util.Util;
import com.seition.cloud.pro.newcloud.app.config.Service;

import java.util.ArrayList;

public class URLImageParser implements Html.ImageGetter {

    private TextView mTextView;
    private Context mContext;
    private BaseAdapter adapter;
    private ArrayList<Bitmap> bitmaps;

    public URLImageParser(Context context, TextView view) {
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.mTextView = view;
    }

    public void addBitmap(Bitmap bitmap) {
        if (bitmap == null) return;
        if (bitmaps == null) bitmaps = new ArrayList<>();
        bitmaps.add(bitmap);
    }

    public void onDestory() {
        if (bitmaps == null) return;
        for (int i = 0; i < bitmaps.size(); i++) {
            recycleBitmap(bitmaps.get(i));
        }
        bitmaps.clear();
        bitmaps = null;
    }

    public void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
    }


    @Override
    public Drawable getDrawable(String source) {
        // TODO Auto-generated method stub
        if (!source.startsWith("http://") && !source.startsWith("https://")) {
            source = Service.DOMAIN + source;
        }
        final URLDrawable urlDrawable = new URLDrawable();
//        Glide.with(mContext).asBitmap().load(source).into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                showImg(urlDrawable, resource);
//            }
//        });
//        if (true)//TODO1 暂不加图片测试
        Glide.with(mContext).asBitmap().load(source).into(new Target<Bitmap>() {
            int width = SIZE_ORIGINAL;
            int height = SIZE_ORIGINAL;
            Request request = null;

            @Override
            public void onStart() {
            }

            @Override
            public void onStop() {
            }

            @Override
            public void onDestroy() {
            }

            @Override
            public void onLoadStarted(@Nullable Drawable placeholder) {
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
            }

            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                try {
                    showImg(urlDrawable, resource);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
            }

            @Override
            public void getSize(@NonNull SizeReadyCallback cb) {
                if (!Util.isValidDimensions(width, height)) {
                    throw new IllegalArgumentException(
                            "Width and height must both be > 0 or Target#SIZE_ORIGINAL, but given" + " width: "
                                    + width + " and height: " + height + ", either provide dimensions in the constructor"
                                    + " or call override()");
                }
                cb.onSizeReady(width, height);
            }

            @Override
            public void removeCallback(@NonNull SizeReadyCallback cb) {
            }

            @Override
            public void setRequest(@Nullable Request request) {
                this.request = request;
            }

            @Nullable
            @Override
            public Request getRequest() {
                return request;
            }
        });
        return urlDrawable;
    }

    public void showImg(URLDrawable urlDrawable, Bitmap bitmap) {
        urlDrawable.bitmap = bitmap;
        addBitmap(urlDrawable.bitmap);
        addBitmap(bitmap);
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int aWidth = dm.widthPixels;         // 屏幕宽度（像素）
        int aHeight = dm.heightPixels;         // 屏幕宽度（像素）
        //图片高宽处理
        double height = urlDrawable.bitmap.getHeight();
        double width = urlDrawable.bitmap.getWidth();
        if (width > aWidth) {
            double scale = (aWidth - 40d) / width;
            width = scale * width;
            height = scale * height;
            urlDrawable.bitmap = big(urlDrawable.bitmap, scale);
            addBitmap(urlDrawable.bitmap);
        }
        urlDrawable.setBounds(0, 0, (int) width, (int) height);
        mTextView.invalidate();
        mTextView.setText(mTextView.getText()); // 解决图文重叠
    }

    public void checkImg(int height, int weight, Bitmap bmp) {
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(weight, height, 0, 0);

        Canvas canvas = new Canvas(Bitmap.createScaledBitmap(bmp, weight, height, true));
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));
    }

    public Bitmap big(Bitmap b, double scale) {
        if (b == null) return b;
        int w = b.getWidth();
        int h = b.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale((float) scale, (float) scale); // 长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(b, 0, 0, w - 40, h, matrix, true);
        addBitmap(b);
//        recycleBitmap(b);
//        checkImg(h, w - 40, resizeBmp);
        return resizeBmp;
    }
}