package com.seition.cloud.pro.newcloud.home.mvp.ui.course.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.addis.umeng.UmengUtil;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.FragmentBean;
import com.seition.cloud.pro.newcloud.app.bean.Share;
import com.seition.cloud.pro.newcloud.app.bean.bind.FaceStatus;
import com.seition.cloud.pro.newcloud.app.bean.common.Section;
import com.seition.cloud.pro.newcloud.app.bean.course.CourseSeitionVideo;
import com.seition.cloud.pro.newcloud.app.bean.download.CourseCacheBean;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.listener.AppBarStateChangeEvent;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.utils.NetUtils;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.app.utils.ViewContentSettingUtils;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerCourseComponent;
import com.seition.cloud.pro.newcloud.home.di.module.CourseModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.CourseContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.CoursePresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.buy.activity.BuyFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.fragment.CourseSeitionFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.fragment.LiveSeitionFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.popup.CourseDetailMorePopup;
import com.seition.cloud.pro.newcloud.home.mvp.ui.download.activity.CourseDownloadActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.bind.fragment.BindFaceChedkActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.VPFragmentAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_fragment.CommentFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_fragment.DetailsFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.vov.vitamio.fragment.VideoViewFragment;
import io.vov.vitamio.listener.MyVideoViewListener;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.seition.cloud.pro.newcloud.app.config.MyConfig.RequestCodeFace;

public class CourseDetailsActivity extends BaseActivity<CoursePresenter> implements CourseContract.View {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.projectPager)
    ViewPager viewPager;
    @BindView(R.id.toolbar_right_text)
    TextView toolbar_right_text;
    @BindView(R.id.details_title)
    TextView details_title;
    @BindView(R.id.students_number)
    TextView students_number;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.old_price)
    TextView old_price;
    @BindView(R.id.price2)
    TextView price2;
    @BindView(R.id.shopping_button)
    TextView shopping_button;
    @BindView(R.id.ll_bottom)
    LinearLayout ll_bottom;

    @BindView(R.id.cover)
    ImageView cover;
    @BindView(R.id.video_ll)
    RelativeLayout video_ll;

    @BindView(R.id.video)
    RelativeLayout video;
    VideoViewFragment vvf;

    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.toolBar)
    Toolbar mToolbar;

    @BindView(R.id.tv_av)
    TextView mTvAv;
    @BindView(R.id.tv_play_immediately)
    TextView mTvPlayImmediately;
    private DetailsFragment detailsFragment;
    private CourseSeitionFragment courseSectionFragment;
    private LiveSeitionFragment liveSectionFragment;
    private CommentFragment commentFragment;
    private CourseDetailMorePopup morePopup;
    private String courseId;
    private String from;
    private boolean isLive;
    private String sid;// 当 从机构详情界面调到此界面时 需要传机构ID

    @OnClick({R.id.shopping_button, R.id.toolbar_right_text})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.shopping_button:
                mPresenter.toBuy();
                break;
            case R.id.toolbar_right_text:
                //更多
                morePopup.show(toolbar_right_text);
                break;
        }
    }

//    public static CourseDetailsActivity newInstance(String courseId, boolean isLive, String sid, String from) {
//        Bundle args = new Bundle();
//        args.putString("courseId", courseId);
//        args.putBoolean("isLive", isLive);
//        args.putString("sid", sid);
//        args.putString("from", from);
//        if (instance == null) instance = new CourseDetailsActivity();
////        CourseDetailsFragment fragment = new CourseDetailsFragment();
//        instance.setArguments(args);
//        return instance;
//    }


    @Override
    public void toDownload(CourseCacheBean course) {
        if (from != null && from.equals(MessageConfig.FROM_DOWNLOAD))
            killMyself();
        else
            launchActivity(new Intent(this
                    , CourseDownloadActivity.class)
                    .putExtra("course", course)
                    .putExtra("from", MessageConfig.FROM_COURSE)
            );
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerCourseComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .courseModule(new CourseModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_course_details_new;
    }

//    @BindView(R.id.status_view)
//    StatusView mStatusView;


    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getCourseDetails(courseId, isLive, sid);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
//        mStatusView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent));
//        SystemBarHelper.setHeightAndPadding(this, mToolbar);
        courseId = getIntent().getStringExtra("courseId");
        toolbar_right_text.setBackgroundResource(R.drawable.ic_more_operate);
        from = getIntent().getStringExtra("from");
        isLive = getIntent().getBooleanExtra("isLive", false);
        sid = getIntent().getStringExtra("sid");
        mPresenter.getFaceSence();
        if (isLive)
            mTvPlayImmediately.setText(R.string.live_details);//setTitle(R.string.live_details);
        else
            mTvPlayImmediately.setText(R.string.course_details);//setTitle(R.string.course_details);
        initToolbar();

//        mPresenter.getCourseDetails((courseId = getArguments().getString("courseId")), isLive, sid);//测试默认课程Id 11  Swift语言学习教程
        setFragmenList();
        initMorePopwindow();
//        setVideoViewFragment();
    }

    private void initToolbar() {
        // 1.appbarLayout
        mAppbar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> showHideFab(verticalOffset));
        mAppbar.addOnOffsetChangedListener(new AppBarStateChangeEvent() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state, int verticalOffset) {
                if (state == State.EXPANDED) {
                    mTvAv.setVisibility(View.VISIBLE);
                    mTvPlayImmediately.setVisibility(View.GONE);
//                    // 点击了立刻播放
//                    if (isPlayImmediately) {
//                        mFab.performClick();
//                    }
                } else if (state == State.COLLAPSED) {
                    mTvAv.setVisibility(View.GONE);
                    mTvPlayImmediately.setVisibility(View.VISIBLE);
                } else {
                    mTvAv.setVisibility(View.VISIBLE);
                    mTvPlayImmediately.setVisibility(View.GONE);
                }
            }
        });
        // 2.toolbar
//        setSupportActionBar(mToolbar);
//        ActionBar actionBar = getSupportActionBar();
        ((AppCompatActivity) this).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity) this).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPortrait)
                    backOnclick();
                else
                    killMyself();
            }
        });
     /*   mTvPlayImmediately.setOnClickListener(v -> {
            isPlayImmediately = true;
            mAppbar.setExpanded(true, true);
        });*/
        // 3.CollapsingToolbarLayout
        mCollapsingToolbarLayout.setTitleEnabled(false);// 必须关闭文字，否则Toolbar中的自定义控件位置会受影响
    }

    public void showHideFab(int verticalOffset) {
        if (verticalOffset == 0) {
//            showFab();
        } else if (verticalOffset < 0 && Math.abs(verticalOffset) > 100) {
//            hideFab();
        }
    }

    boolean isPortrait = true;

    private void setVideoViewFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (vvf == null)
            vvf = VideoViewFragment.getInstance(this);
        if (vvf.isAdded())
            return;
        transaction.add(R.id.video, vvf);
        transaction.show(vvf);
        transaction.commit();

        vvf.setOnFullScreenListener(new MyVideoViewListener.OnFullScreenListener() {

            @Override
            public void onFullScreenListener(boolean isFullScreen) {
                // TODO Auto-generated method stub
                if (isFullScreen) {
                    // 全屏
                    isPortrait = false;
                    tabs.setVisibility(View.GONE);
                    viewPager.setVisibility(View.GONE);
                    ll_bottom.setVisibility(View.GONE);
                    vvf.mFullScreen((RelativeLayout.LayoutParams) video.getLayoutParams());
                } else {
                    // 不是全屏
                    isPortrait = true;
                    tabs.setVisibility(View.VISIBLE);
                    viewPager.setVisibility(View.VISIBLE);
                    ll_bottom.setVisibility(View.VISIBLE);
                    vvf.mNotFullScreen((RelativeLayout.LayoutParams) video.getLayoutParams());
                }

            }

            @Override
            public void addRecode(String tid, long time) {
                mPresenter.addStudyRecord(courseId, tid, time);
            }
        });
    }

    @Override
    public void onBackPressedSupport() {
        if (!isPortrait)
            backOnclick();
        else
            killMyself();
    }


    private void backOnclick() {
        isPortrait = true;
        tabs.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.VISIBLE);
        ll_bottom.setVisibility(View.VISIBLE);
        vvf.mNotFullScreen((RelativeLayout.LayoutParams) video.getLayoutParams());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //屏幕切换时，设置全屏
//        if (mVideoView != null){
//            mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
//        }
        super.onConfigurationChanged(newConfig);
    }

    private void initMorePopwindow() {
        morePopup = new CourseDetailMorePopup(this, mPresenter, isLive);
    }

    public void setFragmenList() {
        ArrayList<FragmentBean> fragmenList = new ArrayList<FragmentBean>();
        fragmenList.add(new FragmentBean("详情", detailsFragment = DetailsFragment.newInstance()));
        if (isLive) {
            //直播章节列表
            fragmenList.add(new FragmentBean("章节", liveSectionFragment = LiveSeitionFragment.newInstance(this)));
            fragmenList.add(new FragmentBean("点评",
                    commentFragment = CommentFragment.newInstance(courseId, CommentFragment.Comment.Live)));
        } else {
            fragmenList.add(new FragmentBean("章节", courseSectionFragment = CourseSeitionFragment.newInstance(courseId, mPresenter)));
            fragmenList.add(new FragmentBean("点评",
                    commentFragment = CommentFragment.newInstance(courseId, CommentFragment.Comment.Video)));
        }
        viewPager.setAdapter(new VPFragmentAdapter(getSupportFragmentManager(), fragmenList));
        viewPager.setOffscreenPageLimit(fragmenList.size() - 1);
        tabs.setupWithViewPager(viewPager);
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
    public void onDestroy() {
        super.onDestroy();
        if (morePopup != null)
            morePopup.unBind();
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void playVideo(CourseSeitionVideo video) {
        cover.setVisibility(View.GONE);
        this.video_ll.setVisibility(View.VISIBLE);

        // 锁定AppBarLayout
        AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) mAppbar.getChildAt(0).getLayoutParams();
        layoutParams.setScrollFlags(0);
        mAppbar.getChildAt(0).setLayoutParams(layoutParams);


        if (!NetUtils.isWifiConnected(this)) {
            if (NetUtils.isNets(this))
                showDialog(video);//弹框
//           else
//               showMessage("网络连接失败");//没有网络
        } else {
            setVideoViewFragment();
            vvf.setIsBuy(courseOnline.getIs_buy() == 1 ? true : false);
            vvf.setData(video.getDBVideoBean());
        }
    }

    private void showDialog(CourseSeitionVideo video) {
        new MaterialDialog.Builder(this)
                .content("你现在使用的是运营商网络，继续观看可能产生超额流量费")
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        setVideoViewFragment();
                        vvf.setData(video.getDBVideoBean());
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void setIsCollect(boolean isCollect) {
        morePopup.setCollect(isCollect);
    }

    @Override
    public void share(Share data) {
        if (courseOnline != null)
            UmengUtil.shareUrl(this, data.getShare_url(), courseOnline.getVideo_title(), getShareInfo(), courseOnline.getCover());
    }

    @Override
    public void start(CourseOnline course) {
//        launchActivity(new Intent(this, BuyActivity.class).putExtra("course", course));
        detailsFragment.start(BuyFragment.newInstance(course));
    }

    @Override
    public void start(CourseOnline course, CourseSeitionVideo item, Section section) {
        detailsFragment.start(BuyFragment.newInstance(course, item, section));
    }

    @Override
    public void start(Section section) {
        mPresenter.start(section);
    }

    int operationType = -1;

    @Override
    public void showFaceSaveStatus(FaceStatus status) {
        switch (status.getStatus()) {
            case 0:
                operationType = 3;
                break;
            case 1:
                operationType = 2;
                break;
            case 2:
                operationType = 4;
                break;

        }
        showFaceDialog(operationType);
    }

    private void showFaceDialog(int operationType) {
        new MaterialDialog.Builder(CourseDetailsActivity.this)
                .content(operationType == 2 ? "登录需要扫脸验证，是否开始扫脸验证？" : "登录需要完善个人人脸信息，是否立即去完善？")
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        startActivityForResult(new Intent(CourseDetailsActivity.this, BindFaceChedkActivity.class).putExtra("OperationType", operationType), RequestCodeFace);
//                        startForResult(BindFaceChedkActivity.newInstance(operationType),RequestCodeFace);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MyConfig.ResultCodeFaceCheck)
            playVideo(mPresenter.getVideo());

    }

    private String getShareInfo() {
        String intro = Html.fromHtml(courseOnline.getVideo_intro()).toString().trim();
        if (intro.length() > 30)
            intro = intro.substring(0, 30);
        return intro;
    }

    private CourseOnline courseOnline;

    @Override
    public void setPlayTime(int playTime) {
        if (vvf != null) vvf.setPlayTime(playTime);
    }

    @Override
    public void showCourse(CourseOnline data) {
        this.courseOnline = data;
        detailsFragment.setData(data);
        details_title.setText(data.getVideo_title());
        int count = 0;
        if (PreferenceUtil.getInstance(this).getInt(MyConfig.Config_MarketStatus, 0) == 0)
            count = data.getVideo_order_count();
        else
            count = data.getVideo_order_count_mark();
        students_number.setText("在学" + count + "人");
        commentFragment.setComment_star(data.getVideo_comment_count());
        if (isLive)
            liveSectionFragment.setLiveSeition(data.getSections(), data.getIs_buy() == 1, data.getPrice() == 0);
        GlideLoaderUtil.LoadImage(this, data.getCover(), cover);

        ViewContentSettingUtils.priceSetting(this, price, data.getPrice());
        ViewContentSettingUtils.priceCheck(old_price, data.getV_price());
        if (data.getPrice() == data.getV_price())
            old_price.setVisibility(View.GONE);
        else {
            old_price.setVisibility(View.VISIBLE);
            old_price.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
            old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线（删除线）
            old_price.getPaint().setAntiAlias(true);// 抗锯齿
        }

        ViewContentSettingUtils.priceSetting(this, price2, data.getPrice());

        boolean is_buy = data.getIs_buy() == 1 ? true : false;
        shopping_button.setClickable(is_buy ? false : true);
        shopping_button.setText(is_buy ? "已购买" : "立即购买");
    }
}
