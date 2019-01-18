package com.seition.cloud.pro.newcloud.app.bean.message;

import com.jess.arms.base.bean.MBaseBean;
import com.seition.cloud.pro.newcloud.app.bean.user.MessageUserInfo;

public class MessageComment extends MBaseBean {
	private String id;	//评论ID
	private String uid;	//用户ID
	private String fid;
	private String app_id;
	private String app_uid;
	private String app_table;
	private String comment_id;
	private String to_comment_id;
	private String info;
	private String to_comment;
	private String is_read;
	private String is_del;
	private String ctime;		//回复时间
	private MessageUserInfo uidinfo;	//回复人信息
	private MessageUserInfo fidinfo;    //提问人信息
	private String app_name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}


	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}


	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getApp_uid() {
		return app_uid;
	}

	public void setApp_uid(String app_uid) {
		this.app_uid = app_uid;
	}

	public String getApp_table() {
		return app_table;
	}

	public void setApp_table(String app_table) {
		this.app_table = app_table;
	}

	public String getComment_id() {
		return comment_id;
	}

	public void setComment_id(String comment_id) {
		this.comment_id = comment_id;
	}

	public String getTo_comment_id() {
		return to_comment_id;
	}

	public void setTo_comment_id(String to_comment_id) {
		this.to_comment_id = to_comment_id;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getTo_comment() {
		return to_comment;
	}

	public void setTo_comment(String to_comment) {
		this.to_comment = to_comment;
	}

	public String getIs_read() {
		return is_read;
	}

	public void setIs_read(String is_read) {
		this.is_read = is_read;
	}

	public String getIs_del() {
		return is_del;
	}

	public void setIs_del(String is_del) {
		this.is_del = is_del;
	}

	public MessageUserInfo getUidinfo() {
		return uidinfo;
	}

	public void setUidinfo(MessageUserInfo uidinfo) {
		this.uidinfo = uidinfo;
	}

	public MessageUserInfo getFidinfo() {
		return fidinfo;
	}

	public void setFidinfo(MessageUserInfo fidinfo) {
		this.fidinfo = fidinfo;
	}

	public String getApp_name() {
		return app_name;
	}

	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}
}
