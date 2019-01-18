package com.seition.cloud.pro.newcloud.home.mvp.ui.offline.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.addis.umeng.UmengUtil;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.FragmentBean;
import com.seition.cloud.pro.newcloud.app.bean.Share;
import com.seition.cloud.pro.newcloud.app.bean.offline.CourseOffline;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.dialog.OwnerPrivateLetterDialog;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.app.utils.TimeUtils;
import com.seition.cloud.pro.newcloud.app.utils.ViewContentSettingUtils;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerOfflineComponent;
import com.seition.cloud.pro.newcloud.home.di.module.OfflineModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.OfflineContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.OfflineDetailsPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.buy.activity.BuyFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.LoginActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.VPFragmentAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_fragment.CommentFragment;
import com.seition.cloud.pro.newcloud.widget.AutoHeightViewPage;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class OfflineDetailsFragment extends BaseBackFragment<OfflineDetailsPresenter> implements OfflineContract.OfflineDetailsView, OwnerPrivateLetterDialog.OnDialogSendButtonClickListener {
    @BindView(R.id.cover)
    ImageView cover;
    @BindView(R.id.offline_title)
    TextView offlineTitle;
    @BindView(R.id.teacher_name)
    TextView teacherName;
    @BindView(R.id.student_count)
    TextView studentCount;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.tprice)
    TextView tPrice;
    @BindView(R.id.price)
    TextView price;

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.projectPager)
    AutoHeightViewPage viewPager;
    //    @BindView(R.id.projectPager)
//    ViewPager viewPager;
    @BindView(R.id.zixun)
    TextView zixun;
    @BindView(R.id.yuyue)
    TextView yuyue;

    private String id = "";

    private boolean is_collect = false;

    @BindView(R.id.toolbar_right_text2)
    TextView toolbar_right_text2;
    @BindView(R.id.toolbar_right_text)
    TextView toolbar_right_text;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    OwnerPrivateLetterDialog dialog;

    String sid = "";

    @OnClick({R.id.toolbar_right_text, R.id.toolbar_right_text2, R.id.zixun, R.id.yuyue})
    void doSomething(View view) {
        switch (view.getId()) {
            case R.id.toolbar_right_text:
                if (TextUtils.isEmpty(PreferenceUtil.getInstance(_mActivity).getString("oauth_token", null))) {
                    launchActivity(new Intent(_mActivity, LoginActivity.class));
                } else {
                    mPresenter.collectOffine(is_collect, id);
                }
                break;
            case R.id.toolbar_right_text2:
                mPresenter.getShareUrl("3", course.getCourse_id(), sid);
                break;
            case R.id.zixun:
                if (TextUtils.isEmpty(PreferenceUtil.getInstance(_mActivity).getString("oauth_token", null))) {
                    launchActivity(new Intent(_mActivity, LoginActivity.class));
                } else {
                    dialog = new OwnerPrivateLetterDialog(_mActivity);
                    dialog.setToUid(Integer.parseInt(course.getTeacher_uid()));
                    dialog.setRecvName(course.getTeacher_name());
                    dialog.setOnDialogItemClickListener(this);
                    dialog.show();
                }

                break;
            case R.id.yuyue:
                if (TextUtils.isEmpty(PreferenceUtil.getInstance(_mActivity).getString("oauth_token", null))) {
                    launchActivity(new Intent(_mActivity, LoginActivity.class));
                } else {
                    start(BuyFragment.newInstance(course));
                }
                break;
        }
    }

    CourseOffline course;

    @Override
    public void showDetails(CourseOffline courses) {
        this.course = courses;

        is_collect = courses.getIs_collect().equals("1") ? true : false;
        if (TextUtils.isEmpty(PreferenceUtil.getInstance(_mActivity).getString("oauth_token", null))) {
            toolbar_right_text.setBackgroundResource(R.drawable.ic_offline_collect_no);
        } else {
            if (is_collect) {
                toolbar_right_text.setBackgroundResource(R.drawable.ic_offline_collect);
            } else {
                toolbar_right_text.setBackgroundResource(R.drawable.ic_offline_collect_no);
            }
        }

        offlineTitle.setText(courses.getCourse_name());
//        toolbar_title.setText(courses.getCourse_name());
        teacherName.setText("主讲人：" + (MyConfig.isOpenAboutSchool && "".equals(courses.getTeacher_name()) ? courses.getSchool_info().getTitle() : courses.getTeacher_name()));

        int count = 0;
        if (PreferenceUtil.getInstance(_mActivity).getInt(MyConfig.Config_MarketStatus, 0) == 0)
            count = Integer.parseInt(courses.getCourse_order_count());
        else
            count = Integer.parseInt(courses.getCourse_order_count_mark());
        studentCount.setText("已报名：" + count);
        time.setText("上课时间：" + TimeUtils.stampToDate((courses.getListingtime()), TimeUtils.Format_TIME3) + " ~ " + TimeUtils.stampToDate((courses.getUctime()), TimeUtils.Format_TIME3));

        if (MyConfig.isOpenAboutSchool) {
            address.setText("上课地点：" + courses.getSchool_info().getLocation()/*+" "+courses.getSchool_info().getAddress()*/);
            address.setVisibility(View.VISIBLE);
        } else address.setVisibility(View.GONE);

        tPrice.setText("原价：¥" + courses.getT_price());
        //设置价格
        ViewContentSettingUtils.priceSetting(_mActivity, price, Double.parseDouble(courses.getPrice()));
        GlideLoaderUtil.LoadImage(_mActivity, courses.getImageurl(), cover);

        if (Integer.parseInt(courses.getIs_buy()) == 0) {
            yuyue.setClickable(true);
            yuyue.setText("预约课程");
            if (commentFragment != null) commentFragment.setBuy(false);
        } else if (Integer.parseInt(courses.getIs_buy()) == 1) {
            yuyue.setClickable(false);
            yuyue.setText("已购买");
            if (commentFragment != null) commentFragment.setBuy(true);
        }
        introFragment.setUi(course.getCourse_intro());

    }

    @Override
    public void setIscollect(boolean iscollect) {
        is_collect = iscollect;
        if (iscollect) {
            toolbar_right_text.setBackgroundResource(R.drawable.ic_offline_collect);
        } else {
            toolbar_right_text.setBackgroundResource(R.drawable.ic_offline_collect_no);
        }
    }

    @Override
    public void share(Share data) {
        String shareUrl = data.getShare_url();
        UmengUtil.shareUrl(_mActivity, data.getShare_url(), course.getCourse_name(), course.getCourse_intro(), course.getImageurl());
    }

    @Override
    public void send(boolean successful) {
        showMessage("发送成功");
        dialog.dismiss();
    }

    public static OfflineDetailsFragment newInstance(String offlineId) {
        Bundle args = new Bundle();
        args.putString("OFFLINEID", offlineId);
        OfflineDetailsFragment fragment = new OfflineDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerOfflineComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .offlineModule(new OfflineModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_offline, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle("线下课详情");
        toolbar_right_text.setBackgroundResource(R.drawable.ic_offline_collect_no);
        toolbar_right_text2.setBackgroundResource(R.drawable.ic_offline_share);
        id = getArguments().getString("OFFLINEID");
        sid = getArguments().getString("sid");
        setFragmenList();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        try {
            mPresenter.getCourseOfflineDetails(id, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setData(Object data) {

    }

    OfflineIntroFragment introFragment;
    CommentFragment commentFragment;

    public void setFragmenList() {
        ArrayList<FragmentBean> fragmenList = new ArrayList<FragmentBean>();
        fragmenList.add(new FragmentBean("详情", introFragment = OfflineIntroFragment.newInstance()));//course.getCourse_intro()
        fragmenList.add(new FragmentBean("点评", commentFragment = CommentFragment.newInstance(id, CommentFragment.Comment.Offline)));
        viewPager.setAdapter(new VPFragmentAdapter(getChildFragmentManager(), fragmenList));
        viewPager.setOffscreenPageLimit(fragmenList.size());
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabs.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
//                viewPager.resetHeight(position);
                viewPager.requestLayout();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
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
        pop();
    }

    @Override
    public void onSendButtonClick(String content, int uid) {
//        Toast.makeText(getActivity(),content,Toast.LENGTH_SHORT).show();

        if (!TextUtils.isEmpty(content))
            mPresenter.sendMessage(content, uid);
        else
            showMessage("请输入要发送的消息内容！");
    }
}
