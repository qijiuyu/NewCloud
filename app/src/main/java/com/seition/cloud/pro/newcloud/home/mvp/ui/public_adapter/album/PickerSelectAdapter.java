/*
 * Copyright 2016 Yan Zhenjie.
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
package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.album;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.imagepicker.bean.ImageItem;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;

import java.util.ArrayList;
import java.util.List;


public class PickerSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mInflater;

    private List<ImageItem> mAlbumFiles = new ArrayList<>();
    static Context context;

    public final static int MAX = 9;
    public final static int TYPE_ADD = 1;
    public final static int TYPE_PHOTO = 2;

    public PickerSelectAdapter(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public void notifyDataSetChanged(List<ImageItem> imagePathList) {
        this.mAlbumFiles = imagePathList;
        super.notifyDataSetChanged();
    }

    public List<ImageItem> getImages() {
        //由于图片未选满时，最后一张显示添加图片，因此这个方法返回真正的已选图片
//        if (TYPE_ADD) return new ArrayList<>(mData.subList(0, mData.size() - 1));
//        else
        return mAlbumFiles;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mAlbumFiles.size() && position != MAX) ? TYPE_ADD : TYPE_PHOTO;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        switch (viewType) {
            case TYPE_ADD:
                itemView = mInflater.inflate(R.layout.item_content_add, parent, false);
                break;
            case TYPE_PHOTO:
                itemView = mInflater.inflate(R.layout.item_content_image, parent, false);
                break;
        }

        return new ImageViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == TYPE_PHOTO) {
            int viewType = getItemViewType(position);
            ((ImageViewHolder) holder).setData(mAlbumFiles.get(position));
        } else if (getItemViewType(position) == TYPE_ADD) {
            int viewType = getItemViewType(position);
            ((ImageViewHolder) holder).setVisibility(addViewVisibility);
        }
    }

    @Override
    public int getItemCount() {
        int count = (mAlbumFiles == null ? 0 : mAlbumFiles.size()) + 1;
        if (count > MAX) {
            count = MAX;
        }
        return count;
    }

    private static class ImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvImage;

        private TextView addView;

        ImageViewHolder(View itemView) {
            super(itemView);
            this.mIvImage = itemView.findViewById(R.id.iv_album_content_image);
            this.addView = itemView.findViewById(R.id.add);
        }

        public void setData(ImageItem albumFile) {
            GlideLoaderUtil.LoadImage(context, albumFile.path, mIvImage);
        }

        public void setVisibility(boolean visibility) {
            addView.setVisibility(visibility ? View.VISIBLE : View.GONE);

        }
    }

    boolean addViewVisibility = true;

    public void setAddViewVisibility(boolean visibility) {
        addViewVisibility = visibility;
    }

}
