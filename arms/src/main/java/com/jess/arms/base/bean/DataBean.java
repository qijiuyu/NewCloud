package com.jess.arms.base.bean;

/**
 * Created by addis on 14/03/2018.
 */

public class DataBean<T> extends MBaseBean {

    private long code;

    private long error_code;

    private T data;

    private String msg;

    private String interfacetime;

    public long getError_code() {
        return error_code;
    }

    public void setError_code(long error_code) {
        this.error_code = error_code;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getInterfacetime() {
        return interfacetime;
    }

    public void setInterfacetime(String interfacetime) {
        this.interfacetime = interfacetime;
    }
}
