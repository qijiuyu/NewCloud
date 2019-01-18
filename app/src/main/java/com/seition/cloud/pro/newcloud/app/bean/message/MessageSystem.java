package com.seition.cloud.pro.newcloud.app.bean.message;

import com.jess.arms.base.bean.MBaseBean;

public class MessageSystem extends MBaseBean {

	private int id;
	private int uid;
	private String node;
	private String appname;
	private String title;
	private String body;
	private String contents;
	private String ctime;
	private String is_read;			//消息是否已读， 1：是 0：否

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String isIs_read() {
		return is_read;
	}

	public void setIs_read(String is_read) {
		this.is_read = is_read;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getCtime() {
		return ctime;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setCtime(String time) {
		this.ctime = time;
	}


	
}
