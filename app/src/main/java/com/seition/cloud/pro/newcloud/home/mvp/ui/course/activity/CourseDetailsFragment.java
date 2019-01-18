package com.seition.cloud.pro.newcloud.home.mvp.ui.course.activity;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jess.arms.base.BaseBackFragment;
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
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.LoginActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.bind.fragment.BindFaceChedkActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.VPFragmentAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_fragment.CommentFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_fragment.DetailsFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.vov.vitamio.fragment.VideoViewFragment;
import io.vov.vitamio.listener.MyVideoViewListener;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.seition.cloud.pro.newcloud.app.config.MyConfig.RequestCodeFace;

@RuntimePermissions
public class CourseDetailsFragment extends BaseBackFragment<CoursePresenter> implements CourseContract.View {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.projectPager)
    ViewPager viewPager;
    @BindView(R.id.toolbar_right_text)
    TextView toolbar_right_text;
    @BindView(R.id.price2)
    TextView price2;

    @BindView(R.id.details_layout)
    LinearLayout details_layout;
    @BindView(R.id.details_title)
    TextView details_title;
    @BindView(R.id.students_number)
    TextView students_number;
    @BindView(R.id.price)
    TextView price;

    @BindView(R.id.old_price)
    TextView old_price;
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

    private CourseSeitionVideo courseSeitionVideo;
    private CourseDetailsFragment instance;
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
        if (TextUtils.isEmpty(PreferenceUtil.getInstance(_mActivity).getString("oauth_token", null)))
            launchActivity(new Intent(_mActivity, LoginActivity.class).putExtra("SkipToHome", false));
        else
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

    public static CourseDetailsFragment newInstance(String courseId, boolean isLive, String sid, String from) {
        Bundle args = new Bundle();
        args.putString("courseId", courseId);
        args.putBoolean("isLive", isLive);
        args.putString("sid", sid);
        args.putString("from", from);
//        if (instance == null) instance = new CourseDetailsFragment();
        CourseDetailsFragment instance = new CourseDetailsFragment();
        instance.setArguments(args);
        return instance;
    }


    @Override
    public void toDownload(CourseCacheBean course) {
        if (from != null && from.equals(MessageConfig.FROM_DOWNLOAD))
            killMyself();
        else
            launchActivity(new Intent(_mActivity
                    , CourseDownloadActivity.class)
                    .putExtra("course", course)
                    .putExtra("from", MessageConfig.FROM_COURSE)
            );
    }


    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerCourseComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .courseModule(new CourseModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_course_details_new, container, false);
    }

//    @BindView(R.id.status_view)
//    StatusView mStatusView;

    @Override
    public void initData(Bundle savedInstanceState) {
//        mStatusView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent));
//        SystemBarHelper.setHeightAndPadding(this, mToolbar);

        courseId = getArguments().getString("courseId");
        toolbar_right_text.setBackgroundResource(R.drawable.ic_more_operate);
        from = getArguments().getString("from");
        isLive = getArguments().getBoolean("isLive", false);
        sid = getArguments().getString("sid");
        mPresenter.getFaceSence();
        if (isLive)
            mTvPlayImmediately.setText(R.string.live_details);//setTitle(R.string.live_details);
        else
            mTvPlayImmediately.setText(R.string.course_details);//setTitle(R.string.course_details);
        initToolbar();
        setVideoViewFragment();
//        mPresenter.getCourseDetails((courseId = getArguments().getString("courseId")), isLive, sid);//测试默认课程Id 11  Swift语言学习教程
        setFragmenList();
        initMorePopwindow();
        if (!isLive)
            mPresenter.getVideoFreeTime(courseId);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mPresenter.getCourseDetails(courseId, isLive, sid);
        mPresenter.getNotLoginWatchFreeVideo();
        if (courseSectionFragment != null)
            courseSectionFragment.getCourseSeitionList();
    }

    @Override
    public void setData(Object data) {

    }

    private void initToolbar() {
        // 1.appbarLayout
        mAppbar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> showHideFab(verticalOffset));
        mAppbar.addOnOffsetChangedListener(new AppBarStateChangeEvent() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, AppBarStateChangeEvent.State state, int verticalOffset) {
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
        ((AppCompatActivity) _mActivity).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity) _mActivity).getSupportActionBar();
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
                    pop();
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
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (vvf == null)
            vvf = VideoViewFragment.getInstance(_mActivity);
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
                    details_layout.setVisibility(View.GONE);
                    vvf.mFullScreen((RelativeLayout.LayoutParams) video.getLayoutParams());
                } else {
                    // 不是全屏
                    isPortrait = true;
                    tabs.setVisibility(View.VISIBLE);
                    viewPager.setVisibility(View.VISIBLE);
                    ll_bottom.setVisibility(View.VISIBLE);
                    details_layout.setVisibility(View.VISIBLE);
                    vvf.mNotFullScreen((RelativeLayout.LayoutParams) video.getLayoutParams());
                }

            }

            @Override
            public void addRecode(String tid, long time) {
                if (!TextUtils.isEmpty(PreferenceUtil.getInstance(_mActivity).getString("oauth_token", null)))
                    mPresenter.addStudyRecord(courseId, tid, time);
            }
        });
    }

    @Override
    public boolean onBackPressedSupport() {
        if (!isPortrait) {
            backOnclick();
            return true;
        } else
            pop();
        return true;
    }


    private void backOnclick() {
        isPortrait = true;
        tabs.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.VISIBLE);
        ll_bottom.setVisibility(View.VISIBLE);
        details_layout.setVisibility(View.VISIBLE);
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
        morePopup = new CourseDetailMorePopup(_mActivity, mPresenter, isLive);
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
        viewPager.setAdapter(new VPFragmentAdapter(getChildFragmentManager(), fragmenList));
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
        pop();
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        CourseDetailsFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void playVideo(CourseSeitionVideo video) {
        courseSeitionVideo = video;
        if (courseSeitionVideo.getVideo_type() == 5) startTest();
        else
            playVideo();
    }

    public void startTest() {
        if (TextUtils.isEmpty(PreferenceUtil.getInstance(getActivity()).getString("oauth_token", null)))
            launchActivity(new Intent(getActivity(), LoginActivity.class).putExtra("SkipToHome", false));
        else
            mPresenter.getExamInfoAndStartExam(courseSeitionVideo.getEid(), 2);
    }

    private void startVideo() {
        vvf.setIsBuy((courseOnline.getIs_buy() == 1 || courseSeitionVideo.getIs_buy() == 1) ? true : false);
        vvf.setData(courseSeitionVideo.getDBVideoBean());
    }

    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.READ_SMS
            , Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS})
    public void playVideo() {
        CourseDetailsFragmentPermissionsDispatcher.playVideoWithPermissionCheck(this);
//        if(courseSeitionVideo==null)
//            return;
        cover.setVisibility(View.GONE);
        this.video_ll.setVisibility(View.VISIBLE);
        details_layout.setVisibility(View.VISIBLE);
//         锁定AppBarLayout
        AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) mAppbar.getChildAt(0).getLayoutParams();
        layoutParams.setScrollFlags(0);
        mAppbar.getChildAt(0).setLayoutParams(layoutParams);


        if (!NetUtils.isWifiConnected(_mActivity)) {
            if (NetUtils.isNets(_mActivity))
                showDialog();//弹框
//           else
//               showMessage("网络连接失败");//没有网络
        } else {
//            setVideoViewFragment();
            startVideo();
        }
    }

    private void showDialog() {
        new MaterialDialog.Builder(_mActivity)
                .content("你现在使用的是运营商网络，继续观看可能产生超额流量费")
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
//                        setVideoViewFragment();
                        startVideo();
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
            UmengUtil.shareUrl(_mActivity, data.getShare_url(), courseOnline.getVideo_title(), getShareInfo(), courseOnline.getCover());
    }

    @Override
    public void start(CourseOnline course) {
        vvf.onPause();
        start(BuyFragment.newInstance(course));
    }

    @Override
    public void start(CourseOnline course, CourseSeitionVideo item, Section section) {
        if (TextUtils.isEmpty(PreferenceUtil.getInstance(getActivity()).getString("oauth_token", null)))
            launchActivity(new Intent(getActivity(), LoginActivity.class).putExtra("SkipToHome", false));
        else {
            vvf.onPause();
            start(BuyFragment.newInstance(course, item, section));
        }
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
        new MaterialDialog.Builder(_mActivity)
                .content(operationType == 2 ? "登录需要扫脸验证，是否开始扫脸验证？" : "登录需要完善个人人脸信息，是否立即去完善？")
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        startActivityForResult(new Intent(_mActivity, BindFaceChedkActivity.class).putExtra("OperationType", operationType), RequestCodeFace);
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
        if (PreferenceUtil.getInstance(_mActivity).getInt(MyConfig.Config_MarketStatus, 0) == 0)
            count = data.getVideo_order_count();
        else
            count = data.getVideo_order_count_mark();
        students_number.setText("在学" + count + "人");

        commentFragment.setComment_star(data.getVideo_comment_count());
        GlideLoaderUtil.LoadImage(_mActivity, data.getCover(), cover);
        ViewContentSettingUtils.priceSetting(_mActivity, price, data.getPrice());
        ViewContentSettingUtils.priceCheck(old_price, data.getV_price());
        if (data.getPrice() == data.getV_price())
            old_price.setVisibility(View.GONE);
        else {
            old_price.setVisibility(View.VISIBLE);
            old_price.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
            old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线（删除线）
            old_price.getPaint().setAntiAlias(true);// 抗锯齿
        }

        ViewContentSettingUtils.priceSetting(_mActivity, price2, data.getPrice());

        boolean is_buy = data.getIs_buy() == 1 ? true : false;
        if (isLive)
            liveSectionFragment.setLiveSeition(data.getSections(), is_buy, data.getPrice() == 0);
        else
            courseSectionFragment.setBuyOrFree(is_buy, data.getPrice() == 0);
        shopping_button.setClickable(!is_buy);
        shopping_button.setText(is_buy ? "已购买" : "立即购买");
        if (commentFragment != null) commentFragment.setBuy(is_buy);
//        if(data.getType().equals("1"))
//            setIsCollect(data.getIscollect().equals("1")?true:false);
//        else if(data.getType().equals("2"))
//            setIsCollect(data.getIs_collect().equals("1")?true:false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MyConfig.ResultCodeFaceCheck)
            playVideo(mPresenter.getVideo());
    }
}
