package com.seition.cloud.pro.newcloud.app.bean.course;

import com.jess.arms.base.bean.MBaseBean;

/**
 * Created by addis on 2018/6/15.
 */
public class SeitionDetails extends MBaseBean {
    private String live_url;
    private int type;
    private SeitionBody body;
    private SeitionLivePlayBack livePlayback;

    public String getLive_url() {
        return live_url;
    }

    public void setLive_url(String live_url) {
        this.live_url = live_url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public SeitionBody getBody() {
        return body;
    }

    public void setBody(SeitionBody body) {
        this.body = body;
    }

    public SeitionLivePlayBack getLivePlayback() {
        return livePlayback;
    }

    public void setLivePlayback(SeitionLivePlayBack livePlayback) {
        this.livePlayback = livePlayback;
    }
}
