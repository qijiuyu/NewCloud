package com.seition.cloud.pro.newcloud.home.mvp.ui.more.news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.addis.umeng.UmengUtil;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.news.NewsItemBean;
import com.seition.cloud.pro.newcloud.app.config.Service;
import com.seition.cloud.pro.newcloud.app.utils.URLImageParser;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerNewsComponent;
import com.seition.cloud.pro.newcloud.home.di.module.NewsModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.NewsContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.NewsDetailsPresenter;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class NewsDetailsActivity extends BaseActivity<NewsDetailsPresenter> implements NewsContract.DetailsView {
    @BindView(R.id.toolbar_right_text)
    TextView toolbar_right_text;
    @BindView(R.id.tv_content_title)
    TextView tv_content_title;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_read)
    TextView tv_read;
    @BindView(R.id.tv_abstract)
    TextView tv_abstract;
    @BindView(R.id.tv_content)
    TextView tv_content;
    NewsItemBean newsItemBean;
    URLImageParser urlImageParser;

    @OnClick(R.id.toolbar_right_text)
    void share(View view) {
        if (newsItemBean != null)
            UmengUtil.shareUrl(this, getShare_url(), newsItemBean.getTitle(), newsItemBean.getDesc(), newsItemBean.getImage());
    }

    public String getShare_url() {
        return Service.DOMAIN + "/news/" + newsItemBean.getId() + ".html";
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerNewsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .newsModule(new NewsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_news_details; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (urlImageParser != null)
//            urlImageParser.onDestory();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.news_details);
        toolbar_right_text.setBackgroundResource(R.drawable.ic_more_operate);
        newsItemBean = (NewsItemBean) getIntent().getSerializableExtra("newsItemBean");
        if (newsItemBean == null) {
            killMyself();
            return;
        }
        showData();
    }

    public void showData() {
        tv_content_title.setText(newsItemBean.getTitle());
        tv_time.setText("发布时间：" + newsItemBean.getDateline());
        tv_read.setText(getResources().getString(R.string.info_read_it) + newsItemBean.getReadcount());
        tv_abstract.setText("摘要：" + newsItemBean.getDesc());
        tv_content.setText(Html.fromHtml(newsItemBean.getText(), urlImageParser = new URLImageParser(this, tv_content), null));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }
}
