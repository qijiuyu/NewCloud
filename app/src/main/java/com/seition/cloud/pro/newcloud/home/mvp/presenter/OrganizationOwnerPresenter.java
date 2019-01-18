package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.UploadResponse;
import com.seition.cloud.pro.newcloud.app.bean.organization.Organization;
import com.seition.cloud.pro.newcloud.app.bean.organization.OrganizationStatus;
import com.seition.cloud.pro.newcloud.home.mvp.contract.OrganizationContract;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import okhttp3.MultipartBody;


@ActivityScope
public class OrganizationOwnerPresenter extends BasePresenter<OrganizationContract.Model, OrganizationContract.OwnerView> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;


    @Inject
    public OrganizationOwnerPresenter(OrganizationContract.Model model, OrganizationContract.OwnerView rootView) {
        super(model, rootView);
    }

    public void getMyOrganizationStatus() {

        mModel.getMyOrganizationStatus()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<OrganizationStatus>(mErrorHandler) {
                    @Override
                    public void onNext(OrganizationStatus data) {
                        if (mRootView == null) return;
                        OrganizationStatus organizationStatus = data.getData();
                        mRootView.showOrganizationStatus(organizationStatus);
                        mRootView.hideLoading();
                    }
                });
    }

    public void uploadFiles(List<MultipartBody.Part> filse) {
        mModel.uploadFiles(filse)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<DataBean>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean data) {
                        String logoDataStr = data.getData().toString();
                        JSONArray array = null;

                        try {
                            array = new JSONArray(logoDataStr);
                            double d = Double.parseDouble(array.get(0).toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void uploadFile(MultipartBody.Part file) {
        mModel.uploadFile(file)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {

                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<UploadResponse>(mErrorHandler) {
                    @Override
                    public void onNext(UploadResponse data) {
                        UploadResponse uploadResponse = data.getData();
                        mRootView.showUploadAttachId(uploadResponse.getAttach_id());

                        mRootView.hideLoading();//上拉加载更多
                    }
                });
    }

    /**
     *
     * @param title
     * @param cate_id
     * @param idcard 身份证号码
     * @param phone
     * @param attach_id 机构附件id
     * @param reason 原因
     * @param address
     * @param identity_id 身份证附件id
     */
    public void applyOrganization(String title,String cate_id,String idcard, String phone
            ,String province,String city,String area, String attach_id, String reason, String location, String address, String identity_id) {
        mModel.applyOrganization(title, cate_id, idcard, phone, province, city, area, attach_id, reason,  location, address,  identity_id)
                .subscribeOn(Schedulers.io())
//                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Organization>(mErrorHandler) {
                    @Override
                    public void onNext(Organization data) {
                        mRootView.showMessage(data.getMsg());//上拉加载更多
                        if (data.getCode() == 1)
                            mRootView.killMyself();
                    }
                });
    }

    public void getOrganizationCategory(boolean iscache) {
        mModel.getOrganizationCategory(iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CommonCategory>(mErrorHandler) {
                    @Override
                    public void onNext(CommonCategory data) {
                        ArrayList<CommonCategory> commonCategories = data.getData();
                        mRootView.showCategoryWindows(commonCategories);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

}
