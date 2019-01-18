package com.seition.cloud.pro.newcloud.home.api.service;

import com.jess.arms.base.bean.DataBean;
import com.seition.cloud.pro.newcloud.app.bean.UploadResponse;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by addis on 2018/5/8.
 */
public interface UploadService {
    String UpLoad = "attach.upload";
    @Multipart
    @POST(UpLoad)
    Observable<DataBean> uploadFiles(@Part() List<MultipartBody.Part> filse);

    @Multipart
    @POST(UpLoad)
    Observable<UploadResponse> uploadFile(@Part() MultipartBody.Part fils);
}