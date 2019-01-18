package com.seition.cloud.pro.newcloud.app.utils.download;

import android.view.View;
import android.widget.TextView;

import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.seition.cloud.pro.newcloud.app.bean.download.DownloadBean;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;
import com.seition.cloud.pro.newcloud.widget.DownProgressView;

/**
 * Created by addis on 2018/4/4.
 * 需要展示下载样式的adapter需要实现这个接口来回调
 */

public class TaskItemViewHolder {
    long id;
    DownloadBean bean;
    TextView download;
    DownProgressView progressView;

    public TaskItemViewHolder(long id, TextView download, DownProgressView progressView, DownloadBean bean) {
        update(id, download, progressView, bean);
    }

    public void update(long id, TextView download, DownProgressView progressView, DownloadBean bean) {
        this.id = id;
        this.download = download;
        this.progressView = progressView;
        this.bean = bean;
    }

    /**
     * @param status 状态
     * @param sofar  现在下载量
     * @param total  总量
     *               float percent = sofar / (float) total;
     *               taskPb.setMax(100);
     *               taskPb.setProgress((int) (percent * 100));
     */
    void updateDownloading(int status, long sofar, long total) {
        if (download != null)
            download.setVisibility(View.GONE);
        if (progressView != null) {
            progressView.setVisibility(View.VISIBLE);
            if (total > 0)
                progressView.setmProgress((int) (sofar * 360 / total));
        }

        updataDownloadData(status, sofar, total);
    }

    /**
     * @param status 状态
     * @param sofar  现在下载量
     * @param total  总量
     */
    void updateNotDownloaded(int status, long sofar, long total) {
        if (download != null)
            download.setVisibility(View.GONE);
        if (progressView != null) {
            progressView.setVisibility(View.VISIBLE);
            if (total > 0)
                progressView.setmProgress((int) (sofar * 360 / total));
        }
        //暂停状态或错误状态，需要修改样式

        updataDownloadData(status, sofar, total);
    }


    /**
     * 完成
     */
    void updateDownloaded() {
        if (progressView != null)
            progressView.setVisibility(View.GONE);
        if (download != null) {
            download.setVisibility(View.VISIBLE);
            download.setText("已下载");
            download.setClickable(false);
        }
        if (bean != null)
            updataDownloadData(FileDownloadStatus.completed, bean.getTotal(), bean.getTotal());
    }

    void updataDownloadData(int status, long sofar, long total) {
        if (bean == null || DBUtils.instance == null) return;
        bean.setProgress(sofar);
        bean.setTotal(total);
        if (status == FileDownloadStatus.completed)
            bean.setState(MessageConfig.DOWNLOADFINISH);
        DBUtils.instance.update(bean);
    }
}
