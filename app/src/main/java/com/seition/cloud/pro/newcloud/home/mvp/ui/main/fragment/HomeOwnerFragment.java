package com.seition.cloud.pro.newcloud.home.mvp.ui.main.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.StatusBarUtil;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.user.MessageUserInfo;
import com.seition.cloud.pro.newcloud.app.bean.user.UserAccount;
import com.seition.cloud.pro.newcloud.app.bean.user.UserCount;
import com.seition.cloud.pro.newcloud.app.bean.user.UserMember;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerHomeComponent;
import com.seition.cloud.pro.newcloud.home.di.module.HomeModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.HomeContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.HomeOwnerFragmentPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.coupon.fragment.OwnerCouponMainFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.fragment.CourseOwnerMainFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.download.activity.DownloadActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.LoginActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.main2.fragment.MainFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.fragment.ExamOwnerMainFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.group.activity.GroupOwnerActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.library.fragment.ArticleLibraryOwnerFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.qa.fragment.QuestionaskOwnerFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.organization.fragment.OrganizationOwnerFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.OwnerSettingFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.OwnerUserInfoFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.collect.CollectMainFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.exchange.fragment.OwnerExchangeFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.member.fragment.MemberCenterFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.message.activity.MessageActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.money.activity.OwnerMoneyFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.note.OwnerNoteFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.order.activity.OwnerOrderFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.referrals.OwnerReferralsFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.study.fragment.OwnerStudyFragment;
import com.seition.cloud.pro.newcloud.widget.ObservableScrollView;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class HomeOwnerFragment extends BaseFragment<HomeOwnerFragmentPresenter> implements HomeContract.OwnerFragmentView, ObservableScrollView.ScrollViewListener {
    @BindView(R.id.springview)
    SpringView springView;

    @BindView(R.id.attention_count)
    TextView attention_count;
    @BindView(R.id.fans_count)
    TextView fans_count;
    @BindView(R.id.owner_organization)
    TextView owner_organization;
    @BindView(R.id.owner_more)
    TextView owner_more;
    @BindView(R.id.owner_course_by_teacher)
    TextView owner_course_by_teacher;

    @BindView(R.id.ll_denglu_info)
    LinearLayout ll_denglu_info;
    @BindView(R.id.ll_user_info)
    RelativeLayout ll_user_info;
    @BindView(R.id.iv_msg_tishi)
    ImageView iv_msg_tishi;


    public static HomeOwnerFragment newInstance() {
        Bundle args = new Bundle();
        HomeOwnerFragment fragment = new HomeOwnerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
    }

    private static String oauth_token_secret, oauth_token;

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        loadData();
        if (TextUtils.isEmpty(oauth_token)) {
            ll_denglu_info.setVisibility(View.VISIBLE);
            ll_user_info.setVisibility(View.GONE);
        } else {
            ll_denglu_info.setVisibility(View.GONE);
            ll_user_info.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_owner, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (MyConfig.isOpenAboutSchool) {
            owner_organization.setVisibility(View.VISIBLE);
            owner_more.setVisibility(View.GONE);
        } else {
            owner_organization.setVisibility(View.GONE);
            owner_more.setVisibility(View.VISIBLE);
        }

        StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(), 0, null);
        oauth_token_secret = PreferenceUtil.getInstance(_mActivity).getString("oauth_token_secret", null);
        oauth_token = PreferenceUtil.getInstance(_mActivity).getString("oauth_token", null);
        GlideLoaderUtil.LoadCircleImage(getActivity(), R.mipmap.default_img, user_cover);
        springView.setType(SpringView.Type.FOLLOW);

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }

            @Override
            public void onLoadmore() {
            }
        });
        springView.setHeader(new DefaultHeader(getActivity()));   //参数为：logo图片资源，是否显示文字
//        Sofia.with(getActivity())
//                .statusBarBackground(ContextCompat.getColor(getActivity(), R.color.colorPrimary))
//                .navigationBarBackground(ContextCompat.getDrawable(getActivity(), R.color.colorPrimary))
//                .invasionStatusBar()
//                .fitsStatusBarView(mToolbar);
//        mStatusView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        toolbar_bottom.setBackgroundColor(Color.argb(0, 0x20, 0x69, 0xCF));
        initScrollViewBehaiver();
//        if(!TextUtils.isEmpty(oauth_token) )
//            loadData();
    }

    private void loadData() {
        mPresenter.getUserInfo(true);
        mPresenter.getUserAccount(true);
        mPresenter.getUserCount(true);
        mPresenter.getUserVip("new", true);
    }

    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传Message,通过what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事
     * <p>
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onCreate还没执行
     * setData里却调用了presenter的方法时,是会报空的,因为dagger注入是在onCreated方法中执行的,然后才创建的presenter
     * 如果要做一些初始化操作,可以不必让外部调setData,在initData中初始化就可以了
     *
     * @param data
     */

    @Override
    public void setData(Object data) {

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        springView.onFinishFreshAndLoad();
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

    }

    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.user_cover)
    ImageView user_cover;

    @Override
    public void showUserInfo(MessageUserInfo user) {
        if (user == null) {
            owner_course_by_teacher.setVisibility(View.GONE);
            return;
        }
        user_name.setText(user.getUname());
        GlideLoaderUtil.LoadCircleImage(getActivity(), user.getAvatar_big(), user_cover);
        if (user.isIs_teacher()) {
//            showMessage("我是老师噢");
            owner_course_by_teacher.setVisibility(View.VISIBLE);
        } else owner_course_by_teacher.setVisibility(View.GONE);
    }

    @BindView(R.id.user_balance)
    TextView user_balance;
    @BindView(R.id.user_recttte)
    TextView user_recttte;
    @BindView(R.id.user_integral)
    TextView user_integral;

    @Override
    public void showUserAccount(UserAccount userAccount) {
        user_balance.setText(getResources().getString(R.string.price_symbol) + userAccount.getLearn());
        user_recttte.setText(getResources().getString(R.string.price_symbol) + userAccount.getSplit());
        user_integral.setText(userAccount.getScore() + "");
    }

    @Override
    public void showUserCount(UserCount userCount) {
        attention_count.setText(userCount.getFollow() + getResources().getString(R.string.owner_attention));
        fans_count.setText(userCount.getFans() + getResources().getString(R.string.owner_fans));
        int messageCommentCount = Integer.parseInt(userCount.getNo_read_comment());
        int messagePrivateCount = Integer.parseInt(userCount.getNo_read_message());
        int messageSystemCount = Integer.parseInt(userCount.getNo_read_notify());
        PreferenceUtil.getInstance(_mActivity).saveInt("messageCommentCount", messageCommentCount);
        PreferenceUtil.getInstance(_mActivity).saveInt("messagePrivateCount", messagePrivateCount);
        PreferenceUtil.getInstance(_mActivity).saveInt("messageSystemCount", messageSystemCount);
        if ((messageCommentCount + messagePrivateCount + messageSystemCount) > 0)
            iv_msg_tishi.setVisibility(View.VISIBLE);
        else
            iv_msg_tishi.setVisibility(View.GONE);
    }

    @BindView(R.id.member_type_img)
    ImageView member_type_img;
    @BindView(R.id.open_member)
    TextView open_member;

    private int vipType = 0;
    private long ctime = 0;
    private String vipGrade;

    @Override
    public void showUserMember(UserMember userMember) {
        GlideLoaderUtil.LoadCircleImage(getActivity(), userMember.getCover(), member_type_img);
        vipGrade = userMember.getVip_type_txt();//data.optString("vip_type_txt");

        vipType = Integer.parseInt(userMember.getVip_type().equals("") ? "0" : userMember.getVip_type());//Integer.parseInt(data.optString("vip_type").equals("") ? "0" : data.optString("vip_type"));

        if (vipGrade.equals("") || vipType == 0) {
            member_type_img.setVisibility(View.GONE);
            open_member.setText(R.string.become_vip);
        } else {
            member_type_img.setVisibility(View.VISIBLE);
            open_member.setText(vipGrade);
        }
        ctime = Long.parseLong(userMember.getVip_expire());//data.optLong("vip_expire");
    }

    @BindView(R.id.toolbar_bottom)
    LinearLayout toolbar_bottom;
    @BindView(R.id.title_txt)
    TextView title_txt;

    @BindView(R.id.scrollView)
    ObservableScrollView scrollView;
    @BindView(R.id.owner_top)
    RelativeLayout owner_top;
    int height;

    private void initScrollViewBehaiver() {
        ViewTreeObserver vto = owner_top.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                owner_top.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                height = owner_top.getHeight();
                owner_top.getWidth();
                scrollView.setScrollViewListener(HomeOwnerFragment.this);
            }
        });

    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 70) {
            float scale = (float) y / 70;
            float alpha = (255 * scale);

            toolbar_bottom.setBackgroundColor(Color.argb((int) alpha, 0x20, 0x69, 0xCF));
            if (y == 0) {
                title_txt.setVisibility(View.GONE);
                toolbar_bottom.setBackgroundColor(Color.argb(0, 0x20, 0x69, 0xCF));
            } else
                title_txt.setVisibility(View.VISIBLE);
        } else {
            toolbar_bottom.setBackgroundColor(Color.argb(255, 0x20, 0x69, 0xCF));
            title_txt.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.balance_block, R.id.recttte_block, R.id.integral_block})
    void toMoney(View v) {

        ((MainFragment) getParentFragment()).startBrotherFragment(OwnerMoneyFragment.newInstance(v.getId()));
    }

    @OnClick({R.id.all_orders, R.id.orders_no_payment, R.id.orders_exit, R.id.orders_done, R.id.orders_pending, R.id.orders_refunded})
    void toOrder(View view) {
        ((MainFragment) getParentFragment()).startBrotherFragment(OwnerOrderFragment.newInstance(view.getId(), ""));
    }

    @OnClick({R.id.owner_course, R.id.owner_note, R.id.owner_collect, R.id.owner_qa, R.id.owner_exam, R.id.owner_group,
            R.id.owner_library, R.id.owner_download, R.id.owner_organization, R.id.owner_coupon, R.id.owner_study_recode, R.id.owner_exchange_record,
            R.id.owner_msg, R.id.owner_setting, R.id.user_cover, R.id.owner_unlogin, R.id.member_tag_ll, R.id.owner_referrials, R.id.owner_course_by_teacher})
    void toOwnerBlock(View v) {
        switch (v.getId()) {
            case R.id.owner_course_by_teacher:
                ((MainFragment) getParentFragment()).startBrotherFragment(CourseOwnerMainFragment.newInstance(true));
                break;
            case R.id.owner_referrials:
                ((MainFragment) getParentFragment()).startBrotherFragment(OwnerReferralsFragment.newInstance(""));
                break;
            case R.id.owner_course:
                ((MainFragment) getParentFragment()).startBrotherFragment(CourseOwnerMainFragment.newInstance(false));
                break;
            case R.id.owner_note:
                ((MainFragment) getParentFragment()).startBrotherFragment(OwnerNoteFragment.newInstance());
                break;
            case R.id.owner_collect:
                ((MainFragment) getParentFragment()).startBrotherFragment(CollectMainFragment.newInstance());
                break;
            case R.id.owner_qa:
                ((MainFragment) getParentFragment()).startBrotherFragment(QuestionaskOwnerFragment.newInstance());
                break;
            case R.id.owner_exam:
                ((MainFragment) getParentFragment()).startBrotherFragment(ExamOwnerMainFragment.newInstance());
                break;
            case R.id.owner_group:
                launchActivity(new Intent(getActivity(), GroupOwnerActivity.class));
                break;
            case R.id.owner_library:
                ((MainFragment) getParentFragment()).startBrotherFragment(ArticleLibraryOwnerFragment.newInstance());
                break;
            case R.id.owner_download:
                launchActivity(new Intent(getActivity(), DownloadActivity.class));
                break;
            case R.id.owner_organization:
                ((MainFragment) getParentFragment()).startBrotherFragment(OrganizationOwnerFragment.newInstance());
                break;
            case R.id.owner_coupon:
                ((MainFragment) getParentFragment()).startBrotherFragment(OwnerCouponMainFragment.newInstance());
                break;
            case R.id.owner_study_recode:
                ((MainFragment) getParentFragment()).startBrotherFragment(OwnerStudyFragment.newInstance());
                break;
            case R.id.owner_exchange_record:
                ((MainFragment) getParentFragment()).startBrotherFragment(OwnerExchangeFragment.newInstance());
                break;
            case R.id.owner_msg:
                launchActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.owner_setting:
                ((MainFragment) getParentFragment()).startBrotherFragment(OwnerSettingFragment.newInstance());
                break;
            case R.id.user_cover:
                ((MainFragment) getParentFragment()).startBrotherFragment(OwnerUserInfoFragment.newInstance());
                break;
            case R.id.owner_unlogin:
                launchActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.member_tag_ll:
                ((MainFragment) getParentFragment())
                        .startBrotherFragment(
                                MemberCenterFragment.newInstance(vipType, vipGrade, ctime));
                break;
        }
    }
}
