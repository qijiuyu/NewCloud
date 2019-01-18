package com.seition.cloud.pro.newcloud.app.bean.offline;

import com.jess.arms.base.bean.DataBean;

import java.util.ArrayList;

public class OfflineSchool extends DataBean<ArrayList<OfflineSchool>> {

	private String id;
	private String title;


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	
}
