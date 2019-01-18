package com.seition.cloud.pro.newcloud.home.mvp.ui.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.MApplication;
import com.seition.cloud.pro.newcloud.app.utils.M;
import com.seition.cloud.pro.newcloud.app.utils.download.DBUtils;
import com.seition.cloud.pro.newcloud.home.mvp.ui.main2.fragment.MainFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.activity.ExamTypeListActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.group.activity.GroupActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.library.activity.ArticleLibraryListActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.mall.framgent.MallFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.news.activity.NewsActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.qa.fragment.QuestionaskMainFragment;
import com.yanzhenjie.sofia.StatusView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class HomeMoreFragment extends BaseFragment {
    @BindView(R.id.status_view)
    StatusView mStatusView;

    @OnClick({R.id.rl_group, R.id.rl_news, R.id.rl_mall, R.id.rl_online_test
            , R.id.rl_library, R.id.rl_qa, R.id.rl_test, R.id.rl_test2})
    void toMoudleList(View v) {
        switch (v.getId()) {
            case R.id.rl_group:
                launchActivity(new Intent(getActivity(), GroupActivity.class));
                break;
            case R.id.rl_news:
                launchActivity(new Intent(getActivity(), NewsActivity.class));
                break;
            case R.id.rl_mall:
                ((MainFragment) getParentFragment()).startBrotherFragment(MallFragment.newInstance());//StudyMainFragment OwnerStudyFragment
                break;
            case R.id.rl_online_test:
                launchActivity(new Intent(getActivity(), ExamTypeListActivity.class));
                break;
            case R.id.rl_library:
                launchActivity(new Intent(getActivity(), ArticleLibraryListActivity.class));
                break;
            case R.id.rl_qa:
                ((MainFragment) getParentFragment()).startBrotherFragment(QuestionaskMainFragment.newInstance());
                break;
            case R.id.rl_test:
                getData();
                break;
            case R.id.rl_test2:
//                DBUtils.init(getContext()).cleanData();
//                launchActivity(new Intent(getActivity(), TestAcivity.class));
//                try {
//                    String encode = M.getEncryptData(
//                            MApplication.getCodedLock(), M.getMapString(
//                                    "cotent", 111333
//                                    , "page", 1
//                                    , "content2", new String[]{"asasad", "zxcxc"}
//                                    , "content7", new String[]{"asasad", "zxcxc"}
//                                    , "content8", new String[]{"asasad", "zxcxc"}
//                                    , "content3", "xjhxjxj"
//                                    , "content4", "xjhxjxj"
//                                    , "content5", "xjhxjxj"
//                                    , "content6", "xjhxjxj"
//                            ));
//                    examCommit(encode);
//                    get();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                try {
//                    String str = M.getDecodeData(MApplication.getCodedLock(), "BId4vTXSeq-0c-x8ORCmfhOLeWiSWzUBeBGRM5CryKPr-GcvQWAo82p3tSV4y_dIJfA7YvpSA3gUSUQapCvNbvQv3WN0hzd-Bkr6uiS0rS7GcgVYzxtSBBNrDrjTLj3cCvwTmSZ7aoXve8ZFlAzhhB8CvLSjJ3iz8L63iyVcC3DIII7KzPBgdGk1f40_Yvh1nwGBBjLow6_t6q0PWTsWqtQJTB5Ryh7v3gVCla5E7k5KmW0p2vlATXv3rdbmQ1tikheotf4Bf6dRQVLIOL0qo5qN_vZ27aEVPv-I25sx37uXU6Ep4tCC2V5TncEJ7aoKhtciKT2fiiOi1pDoBStF4ZQBFFntx9UWZdAEuCK4BjPG7Au8KESzVkbIXyF7py5ey5nDZXOot_CR7cHOl_pDME5v96YAkfmB0hoi5FkUOualMY4wO8opXNm9nPejTvScTw9ivoNB_Yo54K0cWinq-DuCvBd9QdI5xD3jKe_NCFMTvwKNMo-FoqLP3NFOCltmo6x7tm8Yje7lUsNjP_KURZWaHxaetgQmQ8_xhWzEi7ov3mdCJBZKxninlD1Y8xi3ckuaTFokzV5M1vmDGZF0qlt_4sOBkIMZ8y2DpQ82auvziAHpqGoqZcf0Io_MKTvs8Xm_851qTHxyCZhuclBQOALygeXE4N5J2CVw6J0dtxGu06C7YUEntLJaw4TJs5Zz-D6JO9efw8Q2ibldKhi_pWj0c56IG2J1tMhFxKcd_stmyXQeu8X13WzjyKj4tmV2U_zzPQGAv0JZPpslmfvoeQnyUSwCSs_vDti4igIDwjnZoCWDwZOnD-gWFmY2d12W4jRaCQ8HrF17woISUYfGXCfcSTiF-FwNZVCJciK2GWZh33yqDKCVUA9AraBMhqerfwjkwdHjgfV0rXdxuLwxDgWi4EK2rZeyyB4oMT9N0HdfD7OHbcFmWhN8X-k_oYqVU07QjGO0NPscymRQPWQPVinM8fC6JiQSbExb41oC9wo=");
//                    String str2 = M.getDecodeData(MApplication.getCodedLock(), "_jLWzKQIhNVe9NWfQZQVcnXoZB2jlcGysU-4zRJO7wxu2XM7D2uhVZ6cIthkvPN9");
//                    Toast.makeText(_mActivity, str, 0).show();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                break;
        }
    }

    private void getData() {

    }


    private static HomeMoreFragment instance;

    public static HomeMoreFragment getInstance() {
        if (instance == null) {
            instance = new HomeMoreFragment();
        }
        return instance;
    }

    public static HomeMoreFragment newInstance() {
        Bundle args = new Bundle();
        HomeMoreFragment fragment = new HomeMoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
//        DaggerHomeComponent //如找不到该类,请编译一下项目
//                .builder()
//                .appComponent(appComponent)
//                .homeModule(new HomeModule(this))
//                .build()
//                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_more, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mStatusView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));

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

    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }


}
