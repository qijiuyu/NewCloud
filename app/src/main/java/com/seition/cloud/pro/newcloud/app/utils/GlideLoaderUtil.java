package com.seition.cloud.pro.newcloud.app.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.seition.cloud.pro.newcloud.R;

import jp.wasabeef.glide.transformations.MaskTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * desc: .
 * author: Will .
 * date: 2017/7/27 .
 */
public class GlideLoaderUtil {

    /**
     * 常规使用
     *
     * @param context   上下文
     * @param url       图片链接
     * @param imageView 目标view
     */
    public static void LoadImage(Context context, Object url, ImageView imageView) {
        Glide.with(context).load(url)
                .apply(new RequestOptions()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .placeholder(R.mipmap.default_img)
                        .error(R.mipmap.default_img)
                        .dontAnimate()
                )
        .transition(new DrawableTransitionOptions().crossFade(800))
        .into(imageView);
    }

    /**
     *圆角图片
     *
     * @param context   上下文
     * @param url       图片链接
     * @param imageView 目标view
     */
    public static void LoadRoundImage(Context context, Object url, ImageView imageView) {
        Glide.with(context).load(url)
                .apply(new RequestOptions()
                        .transform(new GlideRoundTransform(context, 6))
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.mipmap.default_img)
                        .error(R.mipmap.default_img)
                        .dontAnimate())
                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageView);

    }
    /**
     *圆角图片
     *
     * @param context   上下文
     * @param url       图片链接
     * @param imageView 目标view
     */
    public static void LoadRoundImage1(Context context, Object url, ImageView imageView) {
        Glide.with(context).load(url)
                .apply(bitmapTransform(new MultiTransformation<>(new CenterCrop(),
                        new MaskTransformation(R.drawable.shape_frame_image_round))))
                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageView);

    }


    /**
     *圆形图片
     *
     * @param context   上下文
     * @param url       图片链接
     * @param imageView 目标view
     */
    public static void LoadCircleImage(Context context, Object url, ImageView imageView) {
        Glide.with(context).load(url)
                .apply(new RequestOptions()
                        .transform(new GlideCircleTransform(context))
//                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .placeholder(R.mipmap.default_img)
//                        .error(R.mipmap.default_img)
                        .dontAnimate())
                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageView);

    }


    /**
     * 自定义RequestOptions使用
     *
     * @param context        上下文
     * @param url            图片链接
     * @param requestOptions
     * @param imageView      目标view
     */
    public static void LoadImage(Context context, Object url, ImageView imageView, RequestOptions requestOptions) {
        Glide.with(context).load(url)
                .apply(requestOptions)
                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageView);
    }

    /**
     * 自定义RequestOptions使用
     *
     * @param fragment
     * @param url            图片链接
     * @param requestOptions
     * @param imageView      目标view
     */
    public static void LoadImage(android.support.v4.app.Fragment fragment, Object url, ImageView imageView, RequestOptions requestOptions) {
        Glide.with(fragment).load(url)
                .apply(requestOptions)
                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageView);
    }


    /**
     * 需要回调时使用
     *
     * @param context         上下文
     * @param url             图片链接
     * @param imageViewTarget 回调需求
     */
    public static void LoadImage(Context context, Object url, ImageViewTarget imageViewTarget) {
        Glide.with(context).load(url)
                .apply(new RequestOptions()
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageViewTarget);
    }

    /**
     * 需要回调时使用
     *
     * @param context   上下文
     * @param url       图片链接
     * @param imageView 回调需求
     */
    public static void LoadImage(Context context, Object url, ImageView imageView, RequestListener listener) {
        Glide.with(context).load(url)
                //.thumbnail(0.1f)
                .apply(new RequestOptions()
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(new DrawableTransitionOptions().crossFade(800))
                .listener(listener)
                .into(imageView);
    }




}
