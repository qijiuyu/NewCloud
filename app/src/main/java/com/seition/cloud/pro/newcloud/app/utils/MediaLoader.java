/*
 * Copyright 2017 Yan Zhenjie.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.seition.cloud.pro.newcloud.app.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.seition.cloud.pro.newcloud.R;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;


/**
 * Created by Yan Zhenjie on 2017/3/31.
 */
public class MediaLoader implements AlbumLoader {

    @Override
    public void load(ImageView imageView, AlbumFile albumFile) {
        load(imageView, albumFile.getPath());
    }

    @Override
    public void load(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url)
                .apply(new RequestOptions()
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.placeholder)
//                        .dontAnimate()
                )
//                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageView);
//        Glide.with(imageView.getContext())
//                .load(url)
//                .err
//                .error(R.drawable.placeholder)
//                .placeholder(R.drawable.placeholder)
//                .crossFade()
//                .into(imageView);
    }
}