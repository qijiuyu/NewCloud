package com.seition.cloud.pro.newcloud.app.bean;

import com.jess.arms.base.bean.DataBean;

/**
 * Created by xzw on 2018-06-22.
 */

public class VersionInfo extends DataBean<VersionInfo> {
    private Android android;
    private Ios ios;

    public Android getAndroid() {
        return android;
    }

    public void setAndroid(Android android) {
        this.android = android;
    }

    public Ios getIos() {
        return ios;
    }

    public void setIos(Ios ios) {
        this.ios = ios;
    }

    public class Android{
        private  String down_url;
        private  String version;

        public String getDown_url() {
            return down_url;
        }

        public void setDown_url(String down_url) {
            this.down_url = down_url;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }

    public class Ios{
        private  String down_url;
        private  String version;

        public String getDown_url() {
            return down_url;
        }

        public void setDown_url(String down_url) {
            this.down_url = down_url;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
