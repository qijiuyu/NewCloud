package com.seition.cloud.pro.newcloud.app.bean.member;

import android.os.Parcel;
import android.os.Parcelable;

import com.jess.arms.base.bean.DataBean;

import java.util.ArrayList;

public class Member extends DataBean<ArrayList<Member>> implements Parcelable {

    private String id;
    private String title;
    private String vip_month;
    private String vip_year;
    private String sort;
    private String cover;
    private String ctime;
    private String is_del;

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

    public String getVip_month() {
        return vip_month;
    }

    public void setVip_month(String vip_month) {
        this.vip_month = vip_month;
    }

    public String getVip_year() {
        return vip_year;
    }

    public void setVip_year(String vip_year) {
        this.vip_year = vip_year;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getIs_del() {
        return is_del;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel arg0, int flags) {
        arg0.writeString(id);
        arg0.writeString(title);
        arg0.writeString(vip_month);
        arg0.writeString(vip_year);
        arg0.writeString(sort);
        arg0.writeString(cover);
        arg0.writeString(ctime);
        arg0.writeString(is_del);
    }

    public static final Parcelable.Creator<Member> CREATOR = new Creator(){

        @Override
        public Member createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            // 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
            Member p = new Member();
            p.setId(source.readString());
            p.setTitle(source.readString());
            p.setVip_month(source.readString());
            p.setVip_year(source.readString());
            p.setSort(source.readString());
            p.setCover(source.readString());
            p.setCtime(source.readString());
            p.setIs_del(source.readString());
            return p;
        }

        @Override
        public Member[] newArray(int size) {
            // TODO Auto-generated method stub
            return new Member[size];
        }
    };
}
