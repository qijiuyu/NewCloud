package com.seition.cloud.pro.newcloud.home.mvp.ui.download.view_holder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.download.DownloadBean;
import com.seition.cloud.pro.newcloud.app.bean.library.LibraryItemBean;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.utils.download.TaskItemViewHolder;
import com.seition.cloud.pro.newcloud.app.utils.download.TasksManager;
import com.seition.cloud.pro.newcloud.widget.DownProgressView;

import java.io.File;

/**
 * Created by addis on 2018/4/11.
 */

public class DownloadViewHolder extends BaseViewHolder {
    public DownProgressView progressView;
    public TextView download;
    Context mContext;

    public DownloadViewHolder(View view) {
        super(view);
        progressView = getView(R.id.download_progress);
        download = getView(R.id.download);
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    private void setClick() {
        if (download != null)
            addOnClickListener(R.id.download);
    }

    public void showBuy(DownloadBean item) {
        //更新下载按钮样式
        showIsDownload(item, true);
        if (item.getExtension() != null && !item.getExtension().trim().isEmpty()) {
            //有后缀名则添加下载的回调
            startDownload(item);
        }
    }

    public void showBuy(LibraryItemBean item) {
        //更新下载按钮样式
        showIsDownload(item.getDownloadbean(), item.isBuy());
        if (item.getExtension() != null && !item.getExtension().trim().isEmpty()) {
            //有后缀名则添加下载的回调
            startDownload(item.getDownloadbean());
        }
    }

    public void showIsDownload(DownloadBean bean, boolean isBuy) {
        download.setVisibility(View.VISIBLE);
        progressView.setVisibility(View.GONE);
        setClick();
        boolean isExists = new File(bean.getFileSavePath()).exists();
        if (isExists) {
            download.setText("已下载");
            download.setClickable(false);
        } else if (isBuy)
            switch (bean.getState()) {
                case MessageConfig.DOWNLOADFINISH:
                    if (isExists) {
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
                                status == FileDownloadStatus.connected || status == FileDownloadStatus.progress)
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
        else {
            download.setText("兑换");
            download.setClickable(true);
        }
    }


    public void startDownload(DownloadBean bean) {
        if (bean.getTotal() == 0)
            progressView.setmProgress(0);
        else
            progressView.setmProgress((int) (bean.getProgress() * 360 / bean.getTotal()));
        //下载的监听
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

    public void showCover(LibraryItemBean item) {
        Drawable drawable;
        String url = item.getAttach();
        String extension = "";
        int drawableResources = R.drawable.ic_default;
        if ("".equals(item.getCover()) || "0".equals(item.getCover())) {
            if (item.getAttach_info().getExtension() != null)
                extension = item.getAttach_info().getExtension();
            switch (extension.toLowerCase()) {
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
                case "rar":
                case "7z":
                    drawableResources = R.drawable.ic_lib_zip;
//                    drawable = mContext.getResources().getDrawable(R.mipmap.zip);
                    break;
            }
            setBackgroundRes(R.id.book_cover, drawableResources);
        } else {
            GlideLoaderUtil.LoadImage(mContext, url, getView(R.id.book_cover));
        }
    }
}
