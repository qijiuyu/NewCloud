package com.seition.cloud.pro.newcloud.app.bean.library;

import com.jess.arms.base.bean.MBaseBean;
import com.seition.cloud.pro.newcloud.app.bean.download.DownloadBean;

public class LibraryItemBean extends MBaseBean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int uid;
    private int down_nums;
    private int axchange_num;//兑换次数
    private int price;
    private int cover_id;
    private int doc_id;
    private int status;
    private long ctime;
    private int is_buy;
    private String title;
    private String category;
    private String info;
    private String attach;
    private String is_reviewed;
    private String is_re;
    private String aid;
    private String cover;
    private LibraryAttachInfoBean attach_info;

    private transient DownloadBean downloadbean;
    private String mExtension;

    public LibraryAttachInfoBean getAttach_info() {
        return attach_info;
    }

    public void setExtension(String mExtension) {
        this.mExtension = mExtension;
    }

    public String getExtension() {
        return mExtension;
    }

    public void setCover_id(int cover_id) {
        this.cover_id = cover_id;
    }

    public int getCover_id() {
        return cover_id;
    }

    public void setDownloadbean(DownloadBean downloadbean) {
        this.downloadbean = downloadbean;
    }

    public DownloadBean getDownloadbean() {
        return downloadbean;
    }

    public int getIs_buy() {
        return is_buy;
    }

    public void setAttach_info(LibraryAttachInfoBean attach_info) {
        this.attach_info = attach_info;
    }

    public void setAxchange_num(int axchange_num) {
        this.axchange_num = axchange_num;
    }

    public int getAxchange_num() {
        return axchange_num;
    }

    public void setIs_reviewed(String is_reviewed) {
        this.is_reviewed = is_reviewed;
    }

    public String getIs_reviewed() {
        return is_reviewed;
    }

    public void setIs_re(String is_re) {
        this.is_re = is_re;
    }

    public String getIs_re() {
        return is_re;
    }

    public void setDown_nums(int down_nums) {
        this.down_nums = down_nums;
    }

    public int getDown_nums() {
        return down_nums;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getAid() {
        return aid;
    }

    public void setDoc_id(int doc_id) {
        this.doc_id = doc_id;
    }

    public int getDoc_id() {
        return doc_id;
    }

    public void setIs_buy(int is_buy) {
        this.is_buy = is_buy;
    }

    public boolean isBuy() {
        return is_buy == 1;
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


    public String getCategory() {
        return category;
    }


    public void setCategory(String category) {
        this.category = category;
    }


    public int getPrice() {
        return price;
    }


    public void setPrice(int price) {
        this.price = price;
    }


    public String getInfo() {
        return info;
    }


    public void setInfo(String info) {
        this.info = info;
    }


    public long getCtime() {
        return ctime;
    }


    public void setCtime(long ctime) {
        this.ctime = ctime;
    }


    public int getStatus() {
        return status;
    }


    public void setStatus(int status) {
        this.status = status;
    }


    public String getAttach() {
        return attach;
    }


    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getCover() {
        return cover;
    }


    public void setCover(String cover) {
        this.cover = cover;
    }


    public static long getSerialversionuid() {
        return serialVersionUID;
    }


}
