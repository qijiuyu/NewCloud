package com.seition.cloud.pro.newcloud.app.bean.mall;

import android.os.Parcel;
import android.os.Parcelable;

import com.jess.arms.base.bean.DataBean;

import java.util.ArrayList;

public class MallCategory extends DataBean<ArrayList<MallCategory>> implements Parcelable {

	private String goods_category_id;
	private String title;
	private ArrayList<MallCategory>  childs;

	protected MallCategory(Parcel in) {
		goods_category_id = in.readString();
		title = in.readString();
		childs = in.createTypedArrayList(MallCategory.CREATOR);
	}

	public static final Creator<MallCategory> CREATOR = new Creator<MallCategory>() {
		@Override
		public MallCategory createFromParcel(Parcel in) {
			return new MallCategory(in);
		}

		@Override
		public MallCategory[] newArray(int size) {
			return new MallCategory[size];
		}
	};

	public ArrayList<MallCategory> getChilds() {
		return childs;
	}

	public void setChilds(ArrayList<MallCategory> childs) {
		this.childs = childs;
	}

	public String getGoods_category_id() {
		return goods_category_id;
	}

	public void setGoods_category_id(String goods_category_id) {
		this.goods_category_id = goods_category_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}



	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(goods_category_id);
		dest.writeString(title);
		dest.writeTypedList(childs);
	}
}
