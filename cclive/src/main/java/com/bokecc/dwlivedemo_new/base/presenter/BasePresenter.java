package com.bokecc.dwlivedemo_new.base.presenter;

import android.app.Activity;

import com.bokecc.dwlivedemo_new.base.contract.BaseContract;

/**
 * 作者 ${郭鹏飞}.<br/>
 */

public class BasePresenter<T extends BaseContract.View> implements BaseContract.Presenter {

    protected Activity mContext;
    protected T mView;

    public BasePresenter(Activity context, T view) {
        mContext = context;
        mView = view;
    }
}
