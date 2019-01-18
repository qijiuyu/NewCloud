/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.seition.cloud.pro.newcloud.app.face.utils;


import com.seition.cloud.pro.newcloud.app.face.exception.FaceError;

public interface OnResultListener<T> {
    void onResult(T result);

    void onError(FaceError error);
}
