package io.vov.vitamio;

import java.io.Serializable;

public class DBVideoBean implements Serializable {
    private String uri;
    private String tid;
    private String path;
    private String extensions;
    private String name;
    private String cover;
    /**
     * 类型 1视频，2音频，3文本，4文档
     */
    private int type;
    private boolean isExist;
    private boolean is_free;
    private boolean isBuy;

    public boolean isBuy() {
        return isBuy;
    }

    public void setBuy(boolean buy) {
        isBuy = buy;
    }

    public void setIs_free(boolean is_free) {
        this.is_free = is_free;
    }

    public boolean is_free() {
        return is_free;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean isExist) {
        this.isExist = isExist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getExtensions() {
        return extensions;
    }

    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public DBVideoBean(String name, String tid, String uri, String path, String extensions, int type,String cover,boolean is_free,boolean isBuy) {
        super();
        setTid(tid);
        setUri(uri);
        setName(name);
        setPath(path);
        setExtensions(extensions);
        setType(type);
        setCover(cover);
        setIs_free(is_free);
        setBuy(isBuy);
    }

    public DBVideoBean(String tid, String uri, String path, int type) {
        super();
        setTid(tid);
        setUri(uri);
        setPath(path);
        setType(type);
    }

    public DBVideoBean() {
        super();
    }

    @Override
    public String toString() {
        return "DBVideoBean [uri=" + uri + ", path=" + path + ", extensions=" + extensions + ", tid=" + tid;
    }

}