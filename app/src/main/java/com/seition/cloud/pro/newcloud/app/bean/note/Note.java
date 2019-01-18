package com.seition.cloud.pro.newcloud.app.bean.note;

import com.jess.arms.base.bean.DataBean;

public class Note extends DataBean<Note> {

	private int id;		//笔记ID
	private int uid;	//笔记发布者ID
	private boolean is_open;	//是否公开 1是 0否
	private	String note_title;	//笔记标题
	private String note_description;	//笔记内容
	private int note_help_count;		//点赞数量
	private int note_comment_count;		//点评数量
	private String strtime;				//格式化后的友好时间
	private String ctime;				//发布时间戳
	private String userface;			//用户头像
	private String note_source;			//源至于
	private String video_title;			//源至于

	public String getVideo_title() {
		return video_title;
	}

	public void setVideo_title(String video_title) {
		this.video_title = video_title;
	}

	public String getNote_source() {
		return note_source;
	}

	public void setNote_source(String note_source) {
		this.note_source = note_source;
	}

	public String getUserface() {
		return userface;
	}

	public void setUserface(String userface) {
		this.userface = userface;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public boolean isIs_open() {
		return is_open;
	}

	public void setIs_open(int is_open) {
		this.is_open = ((is_open==0) ? false : true);
	}

	public String getNote_title() {
		return note_title;
	}

	public void setNote_title(String note_title) {
		this.note_title = note_title;
	}

	public String getNote_description() {
		return note_description;
	}

	public void setNote_description(String note_description) {
		this.note_description = note_description;
	}

	public int getNote_help_count() {
		return note_help_count;
	}

	public void setNote_help_count(int note_help_count) {
		this.note_help_count = note_help_count;
	}

	public int getNote_comment_count() {
		return note_comment_count;
	}

	public void setNote_comment_count(int note_comment_count) {
		this.note_comment_count = note_comment_count;
	}

	public String getStrtime() {
		return strtime;
	}

	public void setStrtime(String strtime) {
		this.strtime = strtime;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}


	

}
