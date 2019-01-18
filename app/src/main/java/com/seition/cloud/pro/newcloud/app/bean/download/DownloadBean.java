package com.seition.cloud.pro.newcloud.app.bean.download;

import com.seition.cloud.pro.newcloud.app.config.MessageConfig;
import com.seition.cloud.pro.newcloud.home.api.Cache;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by addis on 2018/3/28.
 */
@Entity
public class DownloadBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id(autoincrement = true)
    private Long key;
    @Property(nameInDb = "cacheId")
    protected String cacheId;
    protected int downloadId;
    protected String fileSavePath;

    private long progress;
    private long total;

    protected long addTime = 0L;
    private int state;

    @Property(nameInDb = "fileType")
    protected String fileType;
    protected long fId;//文件Id
    @Property(nameInDb = "cId")
    protected long cId;//CourseId
    @Property(nameInDb = "pId")
    protected long pId;//SeitionId
    protected String title;
    protected String extension;
    protected String cover;
    protected String url;


    public DownloadBean(String fileType, Long fId, Long cId, Long pid, String title, String extension, String cover, String url) {
        setFileType(fileType);
        setFId(fId);
        setCId(cId);
        setpId(pid);
        setTitle(title);
        setExtension(extension);
        setCover(cover);
        setUrl(url);
        setAddTime(System.currentTimeMillis());
        setFileSavePath(getFileLocation());
        setState(MessageConfig.UNDOWNLOAD);//默认未下载
        setCacheId(initCacheId());
    }

    @Generated(hash = 742595572)
    public DownloadBean(Long key, String cacheId, int downloadId, String fileSavePath, long progress, long total, long addTime,
                        int state, String fileType, long fId, long cId, long pId, String title, String extension, String cover, String url) {
        this.key = key;
        this.cacheId = cacheId;
        this.downloadId = downloadId;
        this.fileSavePath = fileSavePath;
        this.progress = progress;
        this.total = total;
        this.addTime = addTime;
        this.state = state;
        this.fileType = fileType;
        this.fId = fId;
        this.cId = cId;
        this.pId = pId;
        this.title = title;
        this.extension = extension;
        this.cover = cover;
        this.url = url;
    }

    @Generated(hash = 2040406903)
    public DownloadBean() {
    }

    public String initCacheId() {
        return fileType + "," + cId + "," + pId + "," + fId;
    }

    public String getFileLocation() {
        if (fileType == MessageConfig.TYPE_VIDEO)
            return Cache.Cache_Video + getTitle() + getAddTime() + "." + getExtension();
        else
            return Cache.Cache_File + getTitle() + getAddTime() + "." + getExtension();
    }

    public String getFileCacheLocation() {
        if (fileType == MessageConfig.TYPE_VIDEO)
            return Cache.Cache_Video + getTitle() + getAddTime() + "." + getExtension() + ".temp";
        else
            return Cache.Cache_File + getTitle() + getAddTime() + "." + getExtension() + ".temp";
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getTotal() {
        return total;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCover() {
        return cover;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setCacheId(String cacheId) {
        this.cacheId = cacheId;
    }

    public String getCacheId() {
        return cacheId;
    }

    public void setDownloadId(int downloadId) {
        this.downloadId = downloadId;
    }

    public int getDownloadId() {
        return downloadId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
//        int size = title.getBytes().length;
//        if (title.getBytes().length > 11)
//            return title.substring(0, 11)+"...";
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileSavePath() {
        return fileSavePath;
    }

    public void setFileSavePath(String fileSavePath) {
        this.fileSavePath = fileSavePath;
    }

    public long getFId() {
        return this.fId;
    }

    public void setFId(long fId) {
        this.fId = fId;
    }

    public long getCId() {
        return this.cId;
    }

    public void setCId(long cId) {
        this.cId = cId;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }


    public Long getProgress() {
        return progress;
    }

    public void setProgress(Long progress) {
        this.progress = progress;
    }

    public void setFId(Long fId) {
        this.fId = fId;
    }

    public void setCId(Long cId) {
        this.cId = cId;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getpId() {
        return pId;
    }

    public void setpId(long pId) {
        this.pId = pId;
    }

    public long getPId() {
        return this.pId;
    }

    public void setPId(long pId) {
        this.pId = pId;
    }
}
