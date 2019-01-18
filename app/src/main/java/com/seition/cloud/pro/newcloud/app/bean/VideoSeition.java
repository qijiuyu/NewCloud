package com.seition.cloud.pro.newcloud.app.bean;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by addis on 2016/12/5.
 */

public class VideoSeition implements Serializable {
    private String zy_video_section_id;
    private String title;
    private String pid;
    private String vid;
    private String cid;
    private String eid;
    private String wid;
    private String sort;
    private String is_free;
    private String video_address;

    public VideoSeition() {
    }

    public VideoSeition(JSONObject item) {
        Log.i("info", "item.toString() = " + item.toString());
        if (item.has("zy_video_section_id"))
            setZyVideoSectionId(item.optString("zy_video_section_id"));
        if (item.has("title"))
            setTitle(item.optString("title"));
        if (item.has("pid"))
            setPid(item.optString("pid"));
        if (item.has("vid"))
            setVid(item.optString("vid"));
        if (item.has("cid"))
            setCid(item.optString("cid"));
        if (item.has("eid"))
            setEid(item.optString("eid"));
        if (item.has("wid"))
            setWid(item.optString("wid"));
        if (item.has("sort"))
            setSort(item.optString("sort"));
        if (item.has("is_free"))
            setIsFree(item.optString("is_free"));
        if (item.has("video_address"))
            setVideoAddress(item.optString("video_address"));
    }

    @Override
    public String toString() {
        return "zy_video_section_id = " + zy_video_section_id + " && title = " + title + " && video_address = " + video_address;
    }

    public void setZyVideoSectionId(String zyVideoSectionId) {
        this.zy_video_section_id = zyVideoSectionId;
    }

    public String getZyVideoSectionId() {
        return zy_video_section_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPid() {
        return pid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getVid() {
        return vid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCid() {
        return cid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getEid() {
        return eid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getWid() {
        return wid;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSort() {
        return sort;
    }

    public void setIsFree(String isFree) {
        this.is_free = isFree;
    }

    public String getIsFree() {
        return is_free;
    }

    public void setVideoAddress(String videoAddress) {
        this.video_address = videoAddress;
    }

    public String getVideoAddress() {
        return video_address;
    }


}
