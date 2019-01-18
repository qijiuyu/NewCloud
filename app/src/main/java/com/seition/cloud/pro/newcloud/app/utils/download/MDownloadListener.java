package com.seition.cloud.pro.newcloud.app.utils.download;

import android.content.Context;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.model.FileDownloadStatus;

/**
 * Created by addis on 2018/4/4.
 */

public class MDownloadListener extends FileDownloadSampleListener {

    private Context mContext;

    public MDownloadListener(Context mContext) {
        this.mContext = mContext;
    }

    private TaskItemViewHolder checkCurrentHolder(final BaseDownloadTask task) {
        TaskItemViewHolder tag = (TaskItemViewHolder) task.getTag();
        if (tag.id != task.getId()) {
            return null;
        }

        return tag;
    }

    @Override
    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        super.pending(task, soFarBytes, totalBytes);
        final TaskItemViewHolder tag = checkCurrentHolder(task);
        if (tag == null) {
            return;
        }

        tag.updateDownloading(FileDownloadStatus.pending, soFarBytes
                , totalBytes);
    }

    @Override
    protected void started(BaseDownloadTask task) {
        super.started(task);
        final TaskItemViewHolder tag = checkCurrentHolder(task);
        if (tag == null) {
            return;
        }
    }

    @Override
    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
        super.connected(task, etag, isContinue, soFarBytes, totalBytes);
        final TaskItemViewHolder tag = checkCurrentHolder(task);
        if (tag == null) {
            return;
        }
        tag.updateDownloading(FileDownloadStatus.connected, soFarBytes, totalBytes);
    }

    @Override
    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        super.progress(task, soFarBytes, totalBytes);
        final TaskItemViewHolder tag = checkCurrentHolder(task);
        if (tag == null) {
            return;
        }
        tag.updateDownloading(FileDownloadStatus.progress, soFarBytes
                , totalBytes);
    }

    @Override
    protected void error(BaseDownloadTask task, Throwable e) {
        super.error(task, e);
        final TaskItemViewHolder tag = checkCurrentHolder(task);
        if (tag == null) {
            return;
        }
        tag.updateNotDownloaded(FileDownloadStatus.error, task.getLargeFileSoFarBytes()
                , task.getLargeFileTotalBytes());
        TasksManager.getImpl(mContext).removeTaskForViewHolder(task.getId());
    }

    @Override
    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        super.paused(task, soFarBytes, totalBytes);
        final TaskItemViewHolder tag = checkCurrentHolder(task);
        if (tag == null) {
            return;
        }
        tag.updateNotDownloaded(FileDownloadStatus.paused, soFarBytes, totalBytes);
        TasksManager.getImpl(mContext).removeTaskForViewHolder(task.getId());
    }

    @Override
    protected void completed(BaseDownloadTask task) {
        super.completed(task);
        TaskItemViewHolder tag = checkCurrentHolder(task);
        if (tag == null) {
            return;
        }
        tag.updateDownloaded();
        TasksManager.getImpl(mContext).removeTaskForViewHolder(task.getId());
    }
}
