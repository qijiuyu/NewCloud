package com.seition.cloud.pro.newcloud.app.utils.download;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadConnectListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.seition.cloud.pro.newcloud.app.bean.download.DownloadBean;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;

import java.lang.ref.WeakReference;

/**
 * Created by addis on 2018/4/3.
 */

public class TasksManager {
    private final static class HolderClass {
        private static TasksManager INSTANCE = null;
    }

    private SparseArray<BaseDownloadTask> taskSparseArray = new SparseArray<>();
    private FileDownloadConnectListener listener;

    public static TasksManager getImpl(Context mContext) {
        if (HolderClass.INSTANCE == null)
            HolderClass.INSTANCE = new TasksManager(mContext);
        return HolderClass.INSTANCE;
    }

    private TasksManager(Context mContext) {
    }


    public void onCreate(final WeakReference<DownloadChangeViewInterface> activityWeakReference) {
        if (!FileDownloader.getImpl().isServiceConnected()) {
            FileDownloader.getImpl().bindService();
            registerServiceConnectionListener(activityWeakReference);
        }
    }

    public void addTaskForViewHolder(BaseDownloadTask task) {
        taskSparseArray.put(task.getId(), task);
    }

    public void removeTaskForViewHolder(int id) {
        taskSparseArray.remove(id);
    }

    public BaseDownloadTask getTaskByArray(int id) {
        return taskSparseArray.get(id);
    }

    public void updateViewHolder(long id, TaskItemViewHolder holder) {
        BaseDownloadTask task = getTaskByArray((int) id);
        if (task == null) {
            return;
        }

        task.setTag(holder);
    }

    public void releaseTask() {
        taskSparseArray.clear();
    }

    private void registerServiceConnectionListener(final WeakReference<DownloadChangeViewInterface> activityWeakReference) {
        if (listener != null) {
            FileDownloader.getImpl().removeServiceConnectListener(listener);
        }
        listener = new FileDownloadConnectListener() {
            @Override
            public void connected() {
                if (activityWeakReference == null || activityWeakReference.get() == null) {
                    return;
                }
                activityWeakReference.get().postNotifyDataChanged();
            }

            @Override
            public void disconnected() {
                if (activityWeakReference == null || activityWeakReference.get() == null) {
                    return;
                }
                activityWeakReference.get().postNotifyDataChanged();
            }
        };
        FileDownloader.getImpl().addServiceConnectListener(listener);
    }

    private void unregisterServiceConnectionListener() {
        FileDownloader.getImpl().removeServiceConnectListener(listener);
        listener = null;
    }

    public void onDestroy() {
        unregisterServiceConnectionListener();
        releaseTask();
    }

    public boolean isReady() {
        return FileDownloader.getImpl().isServiceConnected();
    }

    /**
     * @param status Download Status
     * @return has already downloaded
     * @see FileDownloadStatus
     */
    public boolean isDownloaded(final int status) {
        return status == FileDownloadStatus.completed;
    }

    public int getStatus(final int id, String path) {
        return FileDownloader.getImpl().getStatus(id, path);
    }

    public long getTotal(int id) {
        return FileDownloader.getImpl().getTotal(id);
    }

    public long getSoFar(int id) {
        return FileDownloader.getImpl().getSoFar(id);
    }

    public BaseDownloadTask addTask(Context mContext, DownloadBean bean) {
        bean.setState(MessageConfig.DOWNLOADING);
        bean = DBUtils.init(mContext).insert(bean);
        if (bean.getDownloadId() == 0)
            bean.setDownloadId(FileDownloadUtils.generateId(bean.getUrl(), bean.getFileSavePath()));
        BaseDownloadTask task = getTaskByArray(bean.getDownloadId());
        if (task == null) {
            task = FileDownloader.getImpl().create(bean.getUrl())
                    .setPath(bean.getFileSavePath())
                    .setCallbackProgressTimes(360)
                    .setListener(new MDownloadListener(mContext));
            addTaskForViewHolder(task);
        }
        return task;
    }

    public void startAll() {

    }

    public void start(int startId) {

    }

    public void pauseAll() {
        for (int i = 0; i < taskSparseArray.size(); i++)
            pause(taskSparseArray.indexOfValue(taskSparseArray.valueAt(i)));
    }

    public void pause(int downloadId) {
        if (downloadId == 0) return;
        FileDownloader.getImpl().pause(downloadId);
    }

    public String createPath(final String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }

        return FileDownloadUtils.getDefaultSaveFilePath(url);
    }
}