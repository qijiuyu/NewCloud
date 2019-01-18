package com.seition.cloud.pro.newcloud.home.mvp.ui.download.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.download.DownloadBean;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;
import com.seition.cloud.pro.newcloud.app.utils.FormatterUtils;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.utils.TimeUtils;
import com.seition.cloud.pro.newcloud.app.utils.download.TaskItemViewHolder;
import com.seition.cloud.pro.newcloud.app.utils.download.TasksManager;
import com.seition.cloud.pro.newcloud.widget.DownProgressView;

import java.io.File;

import static com.seition.cloud.pro.newcloud.app.config.MessageConfig.TYPE_BOOK;
import static com.seition.cloud.pro.newcloud.app.config.MessageConfig.TYPE_VIDEO;

/**
 * Created by addis on 2018/3/28.
 */

public class DownloadAdapter extends BaseQuickAdapter<DownloadBean, BaseViewHolder> {

    public DownloadAdapter() {
        super(R.layout.item_download_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, DownloadBean item) {
        switch (item.getFileType()) {
            case TYPE_BOOK:
                helper.getView(R.id.book_ll).setVisibility(View.VISIBLE);
                helper.setText(R.id.title, item.getTitle());
                helper.setText(R.id.time, TimeUtils.MyFormatTime4(item.getAddTime()));
                helper.setText(R.id.file_size, FormatterUtils.fileSizeFormatter(item.getProgress()));
                showCover(item, helper.getView(R.id.book_cover));
                break;
            case TYPE_VIDEO:
//                showCover(item, helper.getView(R.id.book_cover));
                break;
        }
        setClick(helper);
        showIsDownload(item, helper.getView(R.id.download_progress), helper.
                getView(R.id.download));
        startDownload(item, helper.getView(R.id.download_progress), helper.
                getView(R.id.download));
    }

    private void setClick(BaseViewHolder helper) {
        helper.addOnClickListener(R.id.download);
    }

    public void showIsDownload(DownloadBean bean, DownProgressView progressView, TextView download) {
        download.setVisibility(View.VISIBLE);
        progressView.setVisibility(View.GONE);
        switch (bean.getState()) {
            case MessageConfig.DOWNLOADFINISH:
                if (new File(bean.getFileSavePath()).exists()) {
                    download.setText("已下载");
                    download.setClickable(false);
                } else {
                    download.setText("已删除");
                    download.setClickable(true);
                    bean.setProgress(0l);
                }
                break;
            case MessageConfig.DOWNLOADING:
                //下载中，由DownProgressView回调
                download.setVisibility(View.GONE);
                progressView.setVisibility(View.VISIBLE);
                //下载中的更新回调
                TasksManager.getImpl(mContext).updateViewHolder(
                        bean.getDownloadId()
                        , new TaskItemViewHolder(bean.getDownloadId(), download, progressView, bean));
                //修正当前下载状态
                int status = TasksManager.getImpl(mContext).getStatus(bean.getDownloadId(), bean.getFileSavePath());
                if (TasksManager.getImpl(mContext).isReady())
                    if (status == FileDownloadStatus.pending || status == FileDownloadStatus.started ||
                            status == FileDownloadStatus.connected || status == FileDownloadStatus.progress
                            || status == FileDownloadStatus.INVALID_STATUS)
                        progressView.changeDownloadTypeNow(true);
                    else
                        progressView.changeDownloadTypeNow(false);
                else
                    progressView.changeDownloadTypeNow(false);
                break;
            case MessageConfig.UNDOWNLOAD:
                //未下载，由presenter回调
                download.setText("下载");
                download.setClickable(true);
                break;
        }
    }

    public void startDownload(DownloadBean bean, DownProgressView progressView, TextView download) {
        if (bean.getTotal() == 0)
            progressView.setmProgress(0);
        else
            progressView.setmProgress((int) (bean.getProgress() * 360 / bean.getTotal()));
        progressView.setListner(new DownProgressView.StateProgressListner() {
            @Override
            public void onstart() {
                download.setVisibility(View.GONE);
                progressView.setVisibility(View.VISIBLE);
                BaseDownloadTask task = TasksManager.getImpl(mContext).addTask(mContext, bean)
                        .setTag(new TaskItemViewHolder(bean.getDownloadId(), download, progressView, bean));
                task.start();
            }

            @Override
            public void onstop() {
                FileDownloader.getImpl().pause(bean.getDownloadId());
            }

            @Override
            public void onfinish() {
            }
        });
    }

    private void showCover(DownloadBean item, ImageView img) {
        Drawable drawable;
        int drawableResources = R.drawable.ic_lib_word;
        if ("".equals(item.getCover()) || "0".equals(item.getCover())) {
            switch (item.getExtension().toLowerCase()) {
                case "docx":
                case "dotx":
                case "dotm":
                case "docm":
                case "doc":
                    drawableResources = R.drawable.ic_lib_word;
//                    drawable = mContext.getResources().getDrawable(R.mipmap.word);
                    break;
                case "txt":
                    drawableResources = R.drawable.ic_lib_txt;
//                    drawable = mContext.getResources().getDrawable(R.mipmap.txt);
                    break;
                case "pdf":
                    drawableResources = R.drawable.ic_lib_pdf;
//                    drawable = mContext.getResources().getDrawable(R.mipmap.pdf);
                    break;
                case "xls":
                case "xlsx":
                case "xlsm":
                case "xltx":
                case "xltm":
                case "xlsb":
                case "xlam":
                    drawableResources = R.drawable.ic_lib_excel;
//                    drawable = mContext.getResources().getDrawable(R.mipmap.excel);
                    break;
                case "pptx":
                case "pptm":
                case "ppsx":
                case "potx":
                case "potm":
                case "ppam":
                case "ppsm":
                    drawableResources = R.drawable.ic_lib_ppt;
//                    drawable = mContext.getResources().getDrawable(R.mipmap.ppt);
                    break;
                case "zip":
                    drawableResources = R.drawable.ic_lib_zip;
//                    drawable = mContext.getResources().getDrawable(R.mipmap.zip);
                    break;
            }
            img.setBackgroundResource(drawableResources);
        } else {
            GlideLoaderUtil.LoadImage(mContext, item.getCover(), img);
        }
    }
}
