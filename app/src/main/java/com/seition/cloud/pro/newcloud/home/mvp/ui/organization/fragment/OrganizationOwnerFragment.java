package com.seition.cloud.pro.newcloud.home.mvp.ui.organization.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.organization.OrganizationStatus;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerOrganizationComponent;
import com.seition.cloud.pro.newcloud.home.di.module.OrganizationModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.OrganizationContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.OrganizationOwnerPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.main2.fragment.MainFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.order.activity.OwnerOrderFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class OrganizationOwnerFragment extends BaseBackFragment<OrganizationOwnerPresenter> implements OrganizationContract.OwnerView {
    @BindView(R.id.ll_school)
    LinearLayout ll_school;
    @BindView(R.id.ll_probar)
    LinearLayout ll_probar;
    @BindView(R.id.organization_state)
    TextView organization_state;
    OrganizationStatus organizationStatus;

    @OnClick({R.id.rl_school_home, R.id.rl_school_order, R.id.organization_state})
    void doSomeThing(View view) {
        switch (view.getId()) {
            case R.id.rl_school_home:
                if (organizationStatus.getId() == 0)
                    showMessage("机构数据有误！");
                else
                    start(OrganizationDetailsFragment.newInstance(organizationStatus.getId() + ""));
                break;
            case R.id.rl_school_order:
                if (organizationStatus.getId() == 0)
                    showMessage("机构数据有误！");
                else
                    start(OwnerOrderFragment.newInstance(0, organizationStatus.getId() + ""));
//                    start(OrganizationOrderMainFragment.newInstance(organizationStatus.getId() + ""));
                break;
            case R.id.organization_state:
                start(OrganizationApplyforFragment.newInstance(organizationStatus));
                break;
        }
    }

    public static OrganizationOwnerFragment newInstance() {
        OrganizationOwnerFragment fragment = new OrganizationOwnerFragment();
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
        return inflater.inflate(R.layout.activity_organization_owner, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(getResources().getString(R.string.owner_organization));

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mPresenter.getMyOrganizationStatus();
    }

    @Override
    public void setData(Object data) {
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
    public void showOrganizationStatus(OrganizationStatus organizationStatus) {
        int status = organizationStatus.getStatus();
        this.organizationStatus = organizationStatus;
        switch (status) {
            //-1 :未提交申请或提交申请失败0:已经提交申请,但未审核,1:已通过 2:已被禁用,3:审核不通过
            case -1:
                organization_state.setClickable(true);
                break;
            case 0:
                organization_state.setText("正在审核\n请稍候...");
                organization_state.setTextColor(getResources().getColor(R.color.color_50));
                organization_state.setClickable(false);
                break;
            case 1:
                ll_school.setVisibility(View.VISIBLE);
                organization_state.setClickable(false);
                break;
            case 2:
                organization_state.setText("对不起\n您已被禁用机构功能\n如需帮助请联系客服");
                organization_state.setClickable(false);
                break;
            case 3:
                organization_state.setText("审核失败\n请点击重新提交资料");
                organization_state.setClickable(true);
//                organization_state.setText("正在审核\n请稍候...");
//                organization_state.setTextColor(getResources().getColor(R.color.color_50));
//                organization_state.setClickable(false);
                break;
        }
        ll_probar.setVisibility(View.GONE);
    }

    @Override
    public void showUploadAttachId(String attach_id) {

    }

    @Override
    public void showCategoryWindows(ArrayList<CommonCategory> commonCategories) {
     /*   this.commonCategories = commonCategories;
        categoryPickPopupWindow = new CategoryPickPopupWindow(getActivity(), commonCategories );
        if(commonCategories.size() == 0)
            categoryPickPopupWindow.setDialogNoData();
        categoryPickPopupWindow.setOnDialogItemClickListener(this);
        categoryPickPopupWindow.showPopAsDropDown(mTobBar,0 ,0, Gravity.BOTTOM);*/
    }
}
