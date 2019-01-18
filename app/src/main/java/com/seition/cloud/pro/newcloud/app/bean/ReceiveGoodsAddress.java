package com.seition.cloud.pro.newcloud.app.bean;

import com.jess.arms.base.bean.DataBean;

public class ReceiveGoodsAddress extends DataBean<ReceiveGoodsAddress> {

	private String uid;
	private String province; 
	private String city; 
	private String area; 
	private String address; 
	private String name;
	private String phone; 
	private String ctime; 
	private String is_default; 
	private String address_id;


	

	public String getUid() {
		return uid;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}


	public String getProvince() {
		return province;
	}


	public void setProvince(String province) {
		this.province = province;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getCtime() {
		return ctime;
	}


	public void setCtime(String ctime) {
		this.ctime = ctime;
	}


	public String getIs_default() {
		return is_default;
	}


	public void setIs_default(String is_default) {
		this.is_default = is_default;
	}


	public String getAddress_id() {
		return address_id;
	}


	public void setAddress_id(String address_id) {
		this.address_id = address_id;
	}



	

}
