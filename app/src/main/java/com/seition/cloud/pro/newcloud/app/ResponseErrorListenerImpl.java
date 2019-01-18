/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.seition.cloud.pro.newcloud.app;

import android.content.Context;
import android.content.Intent;
import android.net.ParseException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;
import com.seition.cloud.pro.newcloud.app.utils.NetUtils;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.LoginActivity;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.exceptions.CompositeException;
import io.rx_cache2.RxCacheException;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;
import retrofit2.HttpException;
import timber.log.Timber;

/**
 * ================================================
 * 展示 {@link ResponseErrorListener} 的用法
 * <p>
 * Created by JessYan on 04/09/2017 17:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class ResponseErrorListenerImpl implements ResponseErrorListener {

    @Override
    public void handleResponseError(Context context, Throwable t) {
        Timber.tag("Catch-Error").w(t.getMessage());
//        System.out.println("错误码 " +((HttpException) t).code());
        //这里不光是只能打印错误,还可以根据不同的错误作出不同的逻辑处理
        String msg = "";//未知错误
        if (t instanceof UnknownHostException) {
            msg = "网络不可用";
        } else if (t instanceof SocketTimeoutException) {
            msg = "请求网络超时";
        } else if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            msg = convertStatusCode(httpException);
        } else if (t instanceof JsonParseException || t instanceof ParseException || t instanceof JSONException || t instanceof JsonIOException) {
            msg = "数据解析错误";
        } else if (t instanceof RxCacheException) {
            msg = "数据解析错误,缓存失败";
        } else if (t instanceof ConnectException) {
            msg = "连接服务器失败";
        } else if (t instanceof CompositeException) {
            for (Throwable throwable : ((CompositeException) t).getExceptions()) {
                if (throwable instanceof HttpException) {
                    msg = convertStatusCode((HttpException) throwable);
                } else if (throwable instanceof UnknownHostException) {
                    if (!NetUtils.isNetworkConnected(context))
                        msg = "网络访问失败,设备未联网";
                }
               /* else if (throwable instanceof RxCacheException) {
                    msg = "数据解析错误,缓存失败";
                }*/
            }
        }
        if (!msg.contains("Unauthorized"))
            ArmsUtils.snackbarText(msg);
        if (t instanceof HttpException && ((HttpException) t).code() == 422) {
            PreferenceUtil.getInstance(context).clearLoginUser();
            context.startActivity(new Intent(context, LoginActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .setType(MessageConfig.BACK_IS_EXIT));
        }
    }

    private String convertStatusCode(HttpException httpException) {
        String msg;
        if (httpException.code() == 500) {
            msg = "服务器发生错误";
        } else if (httpException.code() == 404) {
            msg = "请求地址不存在";
        } else if (httpException.code() == 403) {
            msg = "请求被服务器拒绝";
        } else if (httpException.code() == 307) {
            msg = "请求被重定向到其他页面";
        } else {
            msg = httpException.message();
        }
        return msg;
    }

}
