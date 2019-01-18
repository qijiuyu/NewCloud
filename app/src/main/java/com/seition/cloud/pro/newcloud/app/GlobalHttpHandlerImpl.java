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
import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.jess.arms.http.GlobalHttpHandler;
import com.jess.arms.http.log.RequestInterceptor;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.app.utils.M;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * ================================================
 * 展示 {@link GlobalHttpHandler} 的用法
 * <p>
 * Created by JessYan on 04/09/2017 17:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class GlobalHttpHandlerImpl implements GlobalHttpHandler {
    private Context context;

    public GlobalHttpHandlerImpl(Context context) {
        this.context = context;
    }

    @Override
    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
                    /* 这里可以先客户端一步拿到每一次http请求的结果,可以解析成json,做一些操作,如检测到token过期后
                       重新请求token,并重新执行请求 */
//        System.out.println("////1\\\\\\  httpResult = " + httpResult);

        boolean isJsonData = true;

        String encryptedData = "";
        StringBuilder declassifiedStringBuilder = null;
//        Response res = null;
        int code;
        try {
            if (isGoodJson(httpResult)) {
                JSONObject object = new JSONObject(httpResult);
                encryptedData = object.optString("data");
                code = object.optInt("code");
                isJsonData = isGoodJson(encryptedData);
                if (encryptedData.equals("{}") || encryptedData.equals("[]"))
                    return response;
                if (code == 0 && isJsonData) {
                    JSONObject data = object.optJSONObject("data");
                    if (data != null)
                        response = response.newBuilder().code(data.optInt("error_code")).message(object.optString("msg")).build();
                    return response;
                }
                if (encryptedData != null && !encryptedData.trim().isEmpty() && !encryptedData.trim().equals("null")) {
                    String declassifiedData = M.getDecodeData(MApplication.getCodedLock(), encryptedData);
//                    isJsonData = isGoodJson(declassifiedData);
                    int jsonDataPosition = declassifiedData.indexOf("|");
                    String s1 = "\"" + encryptedData + "\"";
                    String encryptedJsonData = declassifiedData.substring(jsonDataPosition + 1, declassifiedData.length());
                    declassifiedStringBuilder = new StringBuilder(httpResult.replace(s1, encryptedJsonData));//httpResult.replace(s1, ss);
                    String ctime = declassifiedData.substring(0, jsonDataPosition);
                    declassifiedStringBuilder.insert(1, "\"interfacetime\"" + ":" + "\"" + ctime + "\"" + ",");
                    String jsonData = declassifiedStringBuilder.toString();
                    response = response.newBuilder().body(ResponseBody.create(null, jsonData)).build();
//                    System.out.println("  lastJsont = " + jsonData);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!TextUtils.isEmpty(httpResult) && RequestInterceptor.isJson(response.body().contentType())) {

        }

                 /* 这里如果发现token过期,可以先请求最新的token,然后在拿新的token放入request里去重新请求
                    注意在这个回调之前已经调用过proceed,所以这里必须自己去建立网络请求,如使用okhttp使用新的request去请求
                    create a new request and modify it accordingly using the new token
                    Request newRequest = chain.request().newBuilder().header("token", newToken)
                                         .build();

                    retry the request

                    response.body().close();
                    如果使用okhttp将新的请求,请求成功后,将返回的response  return出去即可
                    如果不需要返回新的结果,则直接把response参数返回出去 */

//        return res;
        return response;
    }

    // 这里可以在请求服务器之前可以拿到request,做一些操作比如给request统一添加token或者header以及参数加密等操作
    @Override
    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
                    /* 如果需要再请求服务器之前做一些操作,则重新返回一个做过操作的的request如增加header,不做操作则直接返回request参数
                       return chain.request().newBuilder().header("token", tokenId)
                              .build(); */
//        System.out.println("////\\\\\\  request = " + request.url().toString());
        return request;
    }

    public static boolean isGoodJson(String json) {
        if (json == null || json.equals("null"))
            return false;
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonParseException e) {
//            System.out.println("bad json: " + json);
            return false;
        }
    }
}
