package com.seition.cloud.pro.newcloud.home.mvp.ui.owner.bind.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.bind.FaceStatus;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerBindManageComponent;
import com.seition.cloud.pro.newcloud.home.di.module.BindManageModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.BindManageContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.BindFaceDetailsPresenter;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.seition.cloud.pro.newcloud.app.config.MyConfig.RequestCodeFace;


public class BindFaceFragment extends BaseBackFragment<BindFaceDetailsPresenter> implements BindManageContract.FaceDetailsView  {
    @BindView(R.id.face_bind_result)
    TextView face_bind_result;

    @OnClick({R.id.face_bind_result,R.id.face_taste_txt})
    void doSomething(View view){
        switch (view.getId()){
            case R.id.face_bind_result:
//                start(BindFaceChedkActivity.newInstance(operationType));
                if(operationType >5)
                    showFaceDialog();
                else
                    startActivity(new Intent(_mActivity,BindFaceChedkActivity.class).putExtra("OperationType", operationType));

                break;
            case R.id.face_taste_txt:
//                startForResult(BindFaceChedkActivity.newInstance(5),RequestCodeFace);
                startActivityForResult(new Intent(_mActivity,BindFaceChedkActivity.class).putExtra("OperationType", 5),RequestCodeFace);
                break;
        }
    }
    private void showFaceDialog() {
        new MaterialDialog.Builder(_mActivity)
                .content("你的人脸已经绑定完成，是否重新上传人脸信息？")
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        operationType = 4 ;
                        startActivity(new Intent(_mActivity,BindFaceChedkActivity.class).putExtra("OperationType", operationType));
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

    public static BindFaceFragment newInstance() {
        Bundle args = new Bundle();
//        args.putSerializable("organ",organ);
        BindFaceFragment fragment = new BindFaceFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerBindManageComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .bindManageModule(new BindManageModule(this))
                .build()
                .inject(this);

    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_bind_face,container,false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.bind_face);

        mPresenter.getFaceSaveStatus();
//        loginPresenter.getFaceSence(false,"login");
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
        pop();
    }

    int operationType = -1 ;
    @Override
    public void showFaceSaveStatus(FaceStatus status) {
        switch (status.getStatus()){
            case 0:
                operationType = 3;
                face_bind_result.setText("创建人物");
                break;
            case 1:
                operationType = 6;
                face_bind_result.setText("已绑定");
                break;
            case 2:
                operationType = 4 ;
                face_bind_result.setText("完善人脸");
                break;

        }
    }

/*    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == MyConfig.ResultCodeFaceTest)
            showMessage( "体验刷脸成功");

    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MyConfig.ResultCodeFaceTest)
            showMessage( "体验刷脸成功");
    }
}
