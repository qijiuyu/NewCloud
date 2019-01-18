package com.seition.cloud.pro.newcloud.app.bean.mall;

import com.jess.arms.base.bean.DataBean;

public class Mall extends DataBean<Mall> {

	private static final long serialVersionUID = 1L;
	private int uid;
	private String title;
	private String goods_category;
	private String fullcategorypath;
	private String cover;
	private int price;
	private int stock;
	private int fare;
	private String info;
	private int status;
	private String ctime;
	private int goods_id;
	private int num;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getFullcategorypath() {
		return fullcategorypath;
	}

	public void setFullcategorypath(String fullcategorypath) {
		this.fullcategorypath = fullcategorypath;
	}

	public int getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
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

	public String getGoods_category() {
		return goods_category;
	}

	public void setGoods_category(String goods_category) {
		this.goods_category = goods_category;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getFare() {
		return fare;
	}

	public void setFare(int fare) {
		this.fare = fare;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
