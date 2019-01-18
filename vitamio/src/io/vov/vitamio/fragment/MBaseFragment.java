package io.vov.vitamio.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by addis on 2017/3/30.
 */

public abstract class MBaseFragment extends Fragment {
    protected static MBaseFragment instance;
    protected View main;
    protected Context mContext;
    protected Activity mActivity;

    public MBaseFragment(Activity activity) {
        setmActivity(activity);
    }


    public MBaseFragment() {
    }

    /**
     * 根据id查找组件
     */
    protected View findViewById(int id) {
        return main.findViewById(id);
    }

    public void setmActivity(Activity activity) {
        this.mActivity = activity;
//        mContext = activity.getApplicationContext();
    }

//    public static void stop() {
//        Log.i("info", "   stop   ");
//        if (instance != null) {
//            instance.onDestroy();
//    }
//        instance = null;
//    }

    @Nullable
    @Override
    public View getView() {
        return main;
    }

    protected View findId(int id) {
        return main.findViewById(id);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("info", "onCreateView   2   ");
        main = inflater.inflate(getLayoutId(), null);
        init();
        return main;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("info", "onCreate    ");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        Log.i("info", "onAttach    ");
    }

    /**
     * 获取fragment名字，用于统计
     */
    protected abstract String getFragmentName();

    /**
     * 初始化需要的
     */
    protected void init() {
        initView();
        initData();
        initListener();
    }

    /**
     * 初始化view
     */
    public abstract void initView();

    /**
     * 初始化view
     */
    protected abstract void initData();

    /**
     * 初始化监听
     */
    public abstract void initListener();

    /**
     * 获取layout_id
     */
    protected abstract int getLayoutId();

}
