package com.seition.cloud.pro.newcloud.home.mvp.ui.offline.fragment;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.utils.URLImageParser;

import butterknife.BindView;


public class OfflineIntroFragment extends BaseBackFragment  {

    @BindView(R.id.offline_instro)
    TextView offline_instro;


    private String instro ="";



    public static OfflineIntroFragment newInstance( ) {
        Bundle args = new Bundle();
//        args.putString("intro",intro);
        OfflineIntroFragment fragment = new OfflineIntroFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_offline_intro,container,false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
//        instro = getArguments().getString("intro");
//        offline_instro.setText(Html.fromHtml(instro,
//                new URLImageParser(_mActivity, offline_instro), null));
    }

    public void setUi(String intros){
//        instro = getArguments().getString("intro");
        offline_instro.setText(Html.fromHtml(intros,
                new URLImageParser(_mActivity, offline_instro), null));
    }
    @Override
    public void setData(Object data) {

    }







}
