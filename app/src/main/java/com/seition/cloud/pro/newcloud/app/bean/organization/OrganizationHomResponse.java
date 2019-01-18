package com.seition.cloud.pro.newcloud.app.bean.organization;

import com.jess.arms.base.bean.DataBean;

/**
 * Created by xzw on 2018/4/13.
 */

public class OrganizationHomResponse extends DataBean<OrganizationHomResponse>{
    private String count;
    private int totalPages;
    private String totalRows;
    private int nowPage;
    private boolean gtLastPage;
    private String html;
    private Organizations data;
//    private ArrayList<Organization> data;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public String getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(String totalRows) {
        this.totalRows = totalRows;
    }

    public int getNowPage() {
        return nowPage;
    }

    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
    }

    public boolean isGtLastPage() {
        return gtLastPage;
    }

    public void setGtLastPage(boolean gtLastPage) {
        this.gtLastPage = gtLastPage;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }


  /*  public Organizations getData() {
        return data;
    }

    public void setData(Organizations data) {
        this.data = data;
    }*/

//    public ArrayList<Organization> getOrganizations() {
//        return data;
//    }
//
//    public void setOrganizations(ArrayList<Organization> data) {
//        this.data = data;
//    }

    /*private String msg;
    private int code;
    private Data data;


    public static class Data{
        private String count;
        private int totalPages;
        private String totalRows;
        private int nowPage;
        private boolean gtLastPage;
        private String html;
        private ArrayList<Organization> data;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public String getTotalRows() {
            return totalRows;
        }

        public void setTotalRows(String totalRows) {
            this.totalRows = totalRows;
        }

        public int getNowPage() {
            return nowPage;
        }

        public void setNowPage(int nowPage) {
            this.nowPage = nowPage;
        }

        public boolean isGtLastPage() {
            return gtLastPage;
        }

        public void setGtLastPage(boolean gtLastPage) {
            this.gtLastPage = gtLastPage;
        }

        public String getHtml() {
            return html;
        }

        public void setHtml(String html) {
            this.html = html;
        }

        public ArrayList<Organization> getData() {
            return data;
        }

        public void setData(ArrayList<Organization> data) {
            this.data = data;
        }
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }*/
}
