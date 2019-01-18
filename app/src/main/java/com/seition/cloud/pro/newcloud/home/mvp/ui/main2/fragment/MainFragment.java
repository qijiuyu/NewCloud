package com.seition.cloud.pro.newcloud.home.mvp.ui.main2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.LoginActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.main.fragment.HomeCourseFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.main.fragment.HomeFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.main.fragment.HomeMoreFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.main.fragment.HomeOwnerFragment;
import com.seition.cloud.pro.newcloud.widget.BottomBar;
import com.seition.cloud.pro.newcloud.widget.BottomBarTab;

import me.yokeyword.fragmentation.SupportFragment;


public class MainFragment extends SupportFragment {
    private static final int REQ_MSG = 10;

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOUR = 3;

    private SupportFragment[] mFragments = new SupportFragment[4];

    private BottomBar mBottomBar;


    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(), 0, null);
        SupportFragment firstFragment = findChildFragment(HomeFragment.class);
        if (firstFragment == null) {
            mFragments[FIRST] = HomeFragment.newInstance();
            mFragments[SECOND] = HomeCourseFragment.newInstance();
            mFragments[THIRD] = HomeMoreFragment.newInstance();
            mFragments[FOUR] = HomeOwnerFragment.newInstance();

            loadMultipleRootFragment(R.id.content, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOUR]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findChildFragment(HomeCourseFragment.class);
            mFragments[THIRD] = findChildFragment(HomeMoreFragment.class);
            mFragments[FOUR] = findChildFragment(HomeOwnerFragment.class);
        }
    }

    private void initView(View view) {
        mBottomBar = (BottomBar) view.findViewById(R.id.bottomBar);

        mBottomBar.addItem(new BottomBarTab(_mActivity, R.drawable.selector_home_bottom_drawable_home, getResources().getString(R.string.home)))
                .addItem(new BottomBarTab(_mActivity, R.drawable.selector_home_bottom_drawable_course, getResources().getString(R.string.course)))
                .addItem(new BottomBarTab(_mActivity, R.drawable.selector_home_bottom_drawable_more, getResources().getString(R.string.find)))
                .addItem(new BottomBarTab(_mActivity, R.drawable.selector_home_bottom_drawable_owner, getResources().getString(R.string.owner)));

        // 模拟未读消息
//        mBottomBar.getItem(FIRST).setUnreadCount(9);

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                String oauth_token = PreferenceUtil.getInstance(_mActivity).getString("oauth_token", null);
                if(position==3 && TextUtils.isEmpty(oauth_token) )
                    startActivity(new Intent(_mActivity, LoginActivity.class));
                else {
                    showHideFragment(mFragments[position], mFragments[prePosition]);
                }
//                BottomBarTab tab = mBottomBar.getItem(FIRST);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                // 在FirstPagerFragment,FirstHomeFragment中接收, 因为是嵌套的Fragment
                // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
//                EventBusActivityScope.getDefault(_mActivity).post(new TabSelectedEvent(position));
            }
        });
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REQ_MSG && resultCode == RESULT_OK) {

        }
    }

    /**
     * start other BrotherFragment
     */
    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }


    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;


    /**
     * 处理回退事件
     * @return
     */

    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(_mActivity, "再按一次退出程序", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

}
