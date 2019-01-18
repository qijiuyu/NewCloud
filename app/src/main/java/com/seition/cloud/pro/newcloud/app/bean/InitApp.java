package com.seition.cloud.pro.newcloud.app.bean;

import com.jess.arms.base.bean.DataBean;

/**
 * Created by addis on 2018/9/20.
 */
public class InitApp extends DataBean<InitApp> {
    private VersionInfo appVersion;
    private McryptKey mcryptKey;

    public VersionInfo getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(VersionInfo appVersion) {
        this.appVersion = appVersion;
    }

    public McryptKey getMcryptKey() {
        return mcryptKey;
    }

    public void setMcryptKey(McryptKey mcryptKey) {
        this.mcryptKey = mcryptKey;
    }
}
