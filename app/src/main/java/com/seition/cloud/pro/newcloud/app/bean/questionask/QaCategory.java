package com.seition.cloud.pro.newcloud.app.bean.questionask;

import com.jess.arms.base.bean.DataBean;

public class QaCategory extends DataBean<QaCategory>{
	private int zy_wenda_category_id;
	private String title;
	private String url;

	public int getZy_wenda_category_id() {
		return zy_wenda_category_id;
	}

	public void setZy_wenda_category_id(int zy_wenda_category_id) {
		this.zy_wenda_category_id = zy_wenda_category_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}





}
