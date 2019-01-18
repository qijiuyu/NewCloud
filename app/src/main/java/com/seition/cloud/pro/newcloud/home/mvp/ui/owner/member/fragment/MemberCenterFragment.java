package com.seition.cloud.pro.newcloud.home.mvp.ui.owner.member.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.FragmentBean;
import com.seition.cloud.pro.newcloud.app.bean.member.Member;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.app.utils.TimeUtils;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerMemberComponent;
import com.seition.cloud.pro.newcloud.home.di.module.MemberModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MemberContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.MemberActivityPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.MemberTypeRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.MemberUserRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.VPFragmentAdapter;
import com.seition.cloud.pro.newcloud.widget.AutoHeightViewPager;
import com.seition.cloud.pro.newcloud.widget.decoration.SpacesItemDecoration;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MemberCenterFragment extends BaseBackFragment<MemberActivityPresenter> implements MemberContract.MemberView, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.projectPager)
    AutoHeightViewPager viewPager;//AutoHeightViewPager

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.instro)
    TextView instro;
    @BindView(R.id.open_member)
    TextView open_member;


    @BindView(R.id.member_type_grid)
    RecyclerView member_type_grid;

    @BindView(R.id.gv_preview)
    RecyclerView gv_preview;


    private int currentVipType = 0;
    private String vipGrade;
    private long ctime;
    private boolean isVip = false;

    public static MemberCenterFragment newInstance(int vipType, String vipGrade, long ctime) {
        Bundle args = new Bundle();
        args.putInt("vipType", vipType);
        args.putString("vipGrade", vipGrade);
        args.putLong("ctime", ctime);
        MemberCenterFragment fragment = new MemberCenterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private Member currentMmember;
    private ArrayList<Member> members = new ArrayList<>();

    @OnClick({R.id.open_member})
    void toMember(View view) {
        switch (view.getId()) {
            case R.id.open_member:
                if (currentMmember == null)
                    showMessage("未获取到会员类型");
                else {
                    start(MemberRechargeFragment.newInstance(vipGrade, isVip, currentVipType, currentMmember, members));
                }

                break;
        }
    }


    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerMemberComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .memberModule(new MemberModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_member, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(getResources().getString(R.string.member_center));
//        showLoadingDialog();
        initView();
        initData();
        loadData();
    }

    @Override
    public void setData(Object data) {

    }

    private void initData() {
        currentVipType = getArguments().getInt("vipType", 0);
        vipGrade = getArguments().getString("vipGrade");
        ctime = getArguments().getLong("ctime", 0);

        if (currentVipType > 0) {
            isVip = true;
            try {
                instro.setText("到期时间：" + TimeUtils.stampToDate((ctime +""), TimeUtils.Format_TIME3));
            } catch (Exception e) {
                e.printStackTrace();
                instro.setText(vipGrade + "");
            }
            open_member.setText("升级/续费");
        }

        name.setText(PreferenceUtil.getInstance(_mActivity).getString("uname", ""));
    }

    private void loadData() {
        mPresenter.getVipGrades(false);
        mPresenter.getNewMembers(6, false);
    }

    @Inject
    MemberTypeRecyclerAdapter memberAdapter;
    @Inject
    MemberUserRecyclerAdapter memberUserAdapter;

    private void initView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(_mActivity, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        member_type_grid.setLayoutManager(gridLayoutManager);// 布局管理器
        member_type_grid.setAdapter(memberAdapter);
        memberAdapter.setOnItemClickListener(this);
        member_type_grid.addItemDecoration(new SpacesItemDecoration(Utils.dip2px(_mActivity, 10), Utils.dip2px(_mActivity, 6)));

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        gv_preview.setLayoutManager(linearLayoutManager2);// 布局管理器
        gv_preview.setAdapter(memberUserAdapter);
//        memberUserAdapter.setOnItemClickListener(this);
        gv_preview.addItemDecoration(new SpacesItemDecoration(Utils.dip2px(_mActivity, 10), Utils.dip2px(_mActivity, 6)));

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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Member member = (Member) members.get(position);
        if (Integer.parseInt(member.getId()) < Integer.parseInt(currentMmember.getId()))
            member = currentMmember;
        start(MemberRechargeFragment.newInstance(vipGrade, isVip, currentVipType, member, members));
    }

    @Override
    public void setFragment(ArrayList<Member> members) {
//        hideLoadingDialog();
        this.members = members;
        ArrayList<FragmentBean> fragmenList = new ArrayList<FragmentBean>();
        for (Member member : members) {
            fragmenList.add(new FragmentBean(member.getTitle(), MemberCoursesListFragment.newInstance(Integer.parseInt(member.getId()))));
        }
//        fragmenList.add(new FragmentBean(members.get(0).getTitle(),  MemberCoursesListFragment.newInstance(Integer.parseInt(members.get(0).getId()))));
        viewPager.setAdapter(new VPFragmentAdapter(getChildFragmentManager(), fragmenList));
        viewPager.setOffscreenPageLimit(fragmenList.size() - 1);
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabs.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                viewPager.resetHeight(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        if (isVip)
            setCurrentMmember(members);
        else
            currentMmember = members.get(0);
    }

    private void setCurrentMmember(ArrayList<Member> datas) {

        for (int i = 0; i < datas.size(); i++) {
            if (vipGrade.equals(datas.get(i).getTitle()))
                currentMmember = datas.get(i);
        }
    }

}
