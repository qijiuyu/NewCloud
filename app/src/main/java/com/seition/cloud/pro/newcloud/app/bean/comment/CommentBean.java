package com.seition.cloud.pro.newcloud.app.bean.comment;

import com.jess.arms.base.bean.MBaseBean;

/**
 * Created by addis on 2018/5/16.
 */
public class CommentBean extends MBaseBean {
    long id;
    long ctime;
    long parent_id;
    long reply_uid;
    long pid;
    long uid;
    long oid;
    long tid;
    int star;
    int is_secret;
    int type;
    int review_comment_count;
    int review_vote_count;
    int review_collect_count;
    int is_del;
    int skill;
    int professional;
    int attitude;
    int count;
    int isvote;

    String review_description;
    String review_source;
    String yong;
    String strtime;
    String username;
    String userface;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public long getParent_id() {
        return parent_id;
    }

    public void setParent_id(long parent_id) {
        this.parent_id = parent_id;
    }

    public long getReply_uid() {
        return reply_uid;
    }

    public void setReply_uid(long reply_uid) {
        this.reply_uid = reply_uid;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getIs_secret() {
        return is_secret;
    }

    public void setIs_secret(int is_secret) {
        this.is_secret = is_secret;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getReview_comment_count() {
        return review_comment_count;
    }

    public void setReview_comment_count(int review_comment_count) {
        this.review_comment_count = review_comment_count;
    }

    public int getReview_vote_count() {
        return review_vote_count;
    }

    public void setReview_vote_count(int review_vote_count) {
        this.review_vote_count = review_vote_count;
    }

    public int getReview_collect_count() {
        return review_collect_count;
    }

    public void setReview_collect_count(int review_collect_count) {
        this.review_collect_count = review_collect_count;
    }

    public int getIs_del() {
        return is_del;
    }

    public void setIs_del(int is_del) {
        this.is_del = is_del;
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public int getProfessional() {
        return professional;
    }

    public void setProfessional(int professional) {
        this.professional = professional;
    }

    public int getAttitude() {
        return attitude;
    }

    public void setAttitude(int attitude) {
        this.attitude = attitude;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getIsvote() {
        return isvote;
    }

    public void setIsvote(int isvote) {
        this.isvote = isvote;
    }

    public String getReview_description() {
        return review_description;
    }

    public void setReview_description(String review_description) {
        this.review_description = review_description;
    }

    public String getReview_source() {
        return review_source;
    }

    public void setReview_source(String review_source) {
        this.review_source = review_source;
    }

    public String getYong() {
        return yong;
    }

    public void setYong(String yong) {
        this.yong = yong;
    }

    public String getStrtime() {
        return strtime;
    }

    public void setStrtime(String strtime) {
        this.strtime = strtime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserface() {
        return userface;
    }

    public void setUserface(String userface) {
        this.userface = userface;
    }
}
