package com.seition.cloud.pro.newcloud.home.mvp.ui.organization.fragment;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.addis.umeng.UmengUtil;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.FragmentBean;
import com.seition.cloud.pro.newcloud.app.bean.organization.Organization;
import com.seition.cloud.pro.newcloud.app.config.Share;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerOrganizationComponent;
import com.seition.cloud.pro.newcloud.home.di.module.OrganizationModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.OrganizationContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.OrganizationDetailsPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.LoginActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.VPFragmentAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class OrganizationDetailsFragment extends BaseBackFragment<OrganizationDetailsPresenter> implements OrganizationContract.DetailsView {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.projectPager)
    ViewPager viewPager;
    @BindView(R.id.organization_attention)
    LinearLayout organization_attention;
    @BindView(R.id.organization_attention_img)
    ImageView organization_attention_img;
    @BindView(R.id.organization_attention_txt)
    TextView organization_attention_txt;

    @BindView(R.id.organization_cover)
    ImageView organization_cover;
    @BindView(R.id.organization_name)
    TextView organization_name;
    @BindView(R.id.organization_count)
    TextView organization_count;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.toolbar_right_text)
    TextView toolbar_right_text;

    @OnClick({R.id.organization_attention, R.id.toolbar_right_text})
    void organizationAttention(View view) {

        switch (view.getId()) {
            case R.id.organization_attention:

                if (TextUtils.isEmpty(PreferenceUtil.getInstance(_mActivity).getString("oauth_token", null))) {
                    launchActivity(new Intent(_mActivity, LoginActivity.class));
                } else {
                    switch (attentionData) {
                        case 0:
                            mPresenter.doOrganizationFollow(user_id);
                            break;
                        case 1:
                            mPresenter.cancelOrganizationFollow(user_id);
                            break;
                    }
                }

                break;
            case R.id.toolbar_right_text:
//                mPresenter.getShareUrl();
                if (organization == null) {
                    showMessage("正在获取机构详情");
                    return;
                }
                UmengUtil.shareUrl(_mActivity, Share.getSchoolHome(organization.getSchool_id() + "", organization.getDoadmin()),
                        organization.getTitle(), organization.getIdcard(), organization.getCover());
                break;
        }


    }

    Organization organization;
    //    private Organization organization;
    private String school_id = "";
    OrganizationHomeFragment organizationHomeFragment;
    OrganizationCourseFragment organizationCourseFragment;
    OrganizationCommentFragment organizationTeacherFragment;

    private int attentionData;
    private String user_id = "";

    public static OrganizationDetailsFragment newInstance(String school_id) {
        Bundle args = new Bundle();
        args.putString("school_id", school_id);
        OrganizationDetailsFragment fragment = new OrganizationDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerOrganizationComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .organizationModule(new OrganizationModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_organization_details, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.organization_details);
//        toolbar_right_text.setBackgroundResource(R.drawable.ic_offline_share);
        initView();
//        setFragmenList();
    }

    @Override
    public void setData(Object data) {

    }

    private void initView() {
//        organization = (Organization)getArguments().getSerializable("organ");
        school_id = getArguments().getString("school_id");
        mPresenter.getOrganizationDetails(school_id, true);

    }


    public void setOrganizationDetails(Organization organization) {
        toolbar_title.setText(organization.getTitle());
        GlideLoaderUtil.LoadRoundImage1(_mActivity, organization.getCover(), organization_cover);
        organization_name.setText(organization.getTitle());
        organization_count.setText(organization.getCount().getVideo_count() + " 课程" + " | " + organization.getCount().getTeacher_count() + " 讲师");
        user_id = organization.getUid() + "";
    }

    @Override
    public void setAttention(int attentionData) {
        this.attentionData = attentionData;
        switch (attentionData) {
            case 0:
                organization_attention_img.setImageResource(R.drawable.ic_focus);
                organization_attention_txt.setText(R.string.organization_follow_no);
                break;
            case 1:
                organization_attention_img.setImageResource(R.drawable.ic_unfocus);
                organization_attention_txt.setText(R.string.organization_follow_yes);
                break;
        }
    }

    @Override
    public void share(com.seition.cloud.pro.newcloud.app.bean.Share data) {

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

    }


    @Override
    public void setFragmenList(Organization organization) {
        this.organization = organization;
        setOrganizationDetails(organization);
        setAttention(organization.getFollow_state().getFollowing());

        ArrayList<FragmentBean> fragmenList = new ArrayList<FragmentBean>();
        fragmenList.add(new FragmentBean("简介", organizationHomeFragment = OrganizationHomeFragment.newInstance(organization)));
        fragmenList.add(new FragmentBean("课程", organizationCourseFragment = OrganizationCourseFragment.newInstance(organization)));
        fragmenList.add(new FragmentBean("讲师", organizationTeacherFragment = OrganizationCommentFragment.newInstance(organization)));

        viewPager.setAdapter(new VPFragmentAdapter(getChildFragmentManager(), fragmenList));
        viewPager.setOffscreenPageLimit(fragmenList.size() - 1);

        tabs.setupWithViewPager(viewPager);
    }

}
