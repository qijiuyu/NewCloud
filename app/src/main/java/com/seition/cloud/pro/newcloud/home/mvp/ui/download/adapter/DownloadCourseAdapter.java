package com.seition.cloud.pro.newcloud.home.mvp.ui.download.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.download.CourseCacheBean;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;

/**
 * Created by addis on 2018/4/11.
 */

public class DownloadCourseAdapter extends BaseQuickAdapter<CourseCacheBean, BaseViewHolder> {

    public DownloadCourseAdapter() {
        super(R.layout.item_download_course);
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseCacheBean item) {
        helper.setText(R.id.title, item.getTitle());
        helper.setText(R.id.download_count, "已下载" + 0 + "个任务");
        helper.setText(R.id.video_count, /*item.getVideo_order_count() + "人在学·共"*/ "共" + item.getSection_count() + "节");
        GlideLoaderUtil.LoadImage(mContext, item.getCover(), helper.getView(R.id.cover));
    }
}
