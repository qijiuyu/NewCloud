package com.seition.cloud.pro.newcloud.app.bean.questionask;

import com.jess.arms.base.bean.DataBean;
import com.seition.cloud.pro.newcloud.app.bean.common.FollowState;
import com.seition.cloud.pro.newcloud.app.bean.user.User;

/**
 * Created by xzw on 2018/4/19.
 */

public class Questionask extends DataBean<Questionask> {

    /*
    * 问答最基本的数据
    * */

    private String id;
    private String uid; // 发布人的uid
    private String is_del;
    private String ctime = ""; // 发布时间



    //问答列表
    private String uname;
    private String userface = "";
    private QuestionaskComment wd_comment;

    //问答列表 和问答详情
    private String type; // 问答分类
    private String wd_description  ; // 问答内容
    private String wd_help_count ; // 点赞数量
    private String wd_comment_count ; // 评论数量
    private String wd_browse_count ; // 浏览量
    private String recommend; // 是否置顶：1.是 0.否
    private String tag_id ; // 标签集合
    private String solution_state;
    private String wd_attr;
    private FollowState follow_state;

    // 评论列表
    private String parent_id;
    private String fid;
    private String wid;
    private String description;
    private String help_count;
    private String comment_count;
    private User userinfo;


/*    private String wd_title = ""; // 问答标题
    private String username = ""; // 问答人姓名
    private String[] tags = {""}; // 问答包含标签集合*/


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getWd_description() {
        return wd_description;
    }

    public void setWd_description(String wd_description) {
        this.wd_description = wd_description;
    }

    public String getWd_help_count() {
        return wd_help_count;
    }

    public void setWd_help_count(String wd_help_count) {
        this.wd_help_count = wd_help_count;
    }

    public String getWd_comment_count() {
        return wd_comment_count;
    }

    public void setWd_comment_count(String wd_comment_count) {
        this.wd_comment_count = wd_comment_count;
    }

    public String getWd_browse_count() {
        return wd_browse_count;
    }

    public void setWd_browse_count(String wd_browse_count) {
        this.wd_browse_count = wd_browse_count;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getIs_del() {
        return is_del;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getSolution_state() {
        return solution_state;
    }

    public void setSolution_state(String solution_state) {
        this.solution_state = solution_state;
    }

    public String getWd_attr() {
        return wd_attr;
    }

    public void setWd_attr(String wd_attr) {
        this.wd_attr = wd_attr;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUserface() {
        return userface;
    }

    public void setUserface(String userface) {
        this.userface = userface;
    }

    public QuestionaskComment getWd_comment() {
        return wd_comment;
    }

    public void setWd_comment(QuestionaskComment wd_comment) {
        this.wd_comment = wd_comment;
    }

    public FollowState getFollow_state() {
        return follow_state;
    }

    public void setFollow_state(FollowState follow_state) {
        this.follow_state = follow_state;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHelp_count() {
        return help_count;
    }

    public void setHelp_count(String help_count) {
        this.help_count = help_count;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public User getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(User userinfo) {
        this.userinfo = userinfo;
    }
}
