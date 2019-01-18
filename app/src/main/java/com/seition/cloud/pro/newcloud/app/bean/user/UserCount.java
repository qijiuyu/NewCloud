package com.seition.cloud.pro.newcloud.app.bean.user;

import com.jess.arms.base.bean.DataBean;

/**
 * 用户统计数据
 * Created by xzw on 2018/5/24.
 */

public class UserCount extends DataBean<UserCount> {
    private String videocont;
    private String wdcont;
    private String note;
    private String follow;
    private String fans;
    private String collect_video;
    private String collect_album;
    private String card;
    private String intr;
    private String balance;
    private String no_read_comment;
    private String no_read_notify;
    private String no_read_message;
    private String score;

    public String getVideocont() {
        return videocont;
    }

    public void setVideocont(String videocont) {
        this.videocont = videocont;
    }

    public String getWdcont() {
        return wdcont;
    }

    public void setWdcont(String wdcont) {
        this.wdcont = wdcont;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }

    public String getFans() {
        return fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public String getCollect_video() {
        return collect_video;
    }

    public void setCollect_video(String collect_video) {
        this.collect_video = collect_video;
    }

    public String getCollect_album() {
        return collect_album;
    }

    public void setCollect_album(String collect_album) {
        this.collect_album = collect_album;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getIntr() {
        return intr;
    }

    public void setIntr(String intr) {
        this.intr = intr;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getNo_read_comment() {
        return no_read_comment;
    }

    public void setNo_read_comment(String no_read_comment) {
        this.no_read_comment = no_read_comment;
    }

    public String getNo_read_notify() {
        return no_read_notify;
    }

    public void setNo_read_notify(String no_read_notify) {
        this.no_read_notify = no_read_notify;
    }

    public String getNo_read_message() {
        return no_read_message;
    }

    public void setNo_read_message(String no_read_message) {
        this.no_read_message = no_read_message;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
