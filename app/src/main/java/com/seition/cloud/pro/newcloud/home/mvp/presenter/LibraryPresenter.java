package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.download.DownloadBean;
import com.seition.cloud.pro.newcloud.app.bean.library.Arr_Library;
import com.seition.cloud.pro.newcloud.app.bean.library.LibraryCategoryBean;
import com.seition.cloud.pro.newcloud.app.bean.library.LibraryCategorys;
import com.seition.cloud.pro.newcloud.app.bean.library.LibraryItemBean;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;
import com.seition.cloud.pro.newcloud.app.popupwindow.ArticleLibraryCategoryPickPopupWindow;
import com.seition.cloud.pro.newcloud.app.popupwindow.BasePopWindow;
import com.seition.cloud.pro.newcloud.app.popupwindow.ListPopWindow;
import com.seition.cloud.pro.newcloud.app.utils.AdapterViewUtils;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.home.mvp.contract.LibraryContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.download.adapter.DownloadAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.LoginActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.library.adapter.LibraryListAdapter;
import com.seition.cloud.pro.newcloud.widget.DownProgressView;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class LibraryPresenter extends BasePresenter<LibraryContract.Model, LibraryContract.View>
        implements BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener
        , ListPopWindow.OnDialogItemClickListener, ArticleLibraryCategoryPickPopupWindow.OnDialogItemClickListener {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    RecyclerView recycle_view;

    private int count = 10;
    private int page = 1;
    private boolean isLibraryListCache = true;
    LibraryCategoryBean categoryBean = null;
    private boolean isOwner = false;
    @Inject
    LibraryListAdapter adapter;
    String order = "";

    @Inject
    public LibraryPresenter(LibraryContract.Model model, LibraryContract.View rootView) {
        super(model, rootView);
    }

    public void setRecycle_view(RecyclerView recycle_view) {
        this.recycle_view = recycle_view;
    }

    @Override
    public void onLoadmore() {
        super.onLoadmore();
        page++;
        if (isOwner)
            getOwnerLibraryList(false);
        else
            getLibraryList(false);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        page = 1;
        if (isOwner)
            getOwnerLibraryList(true);
        else
            getLibraryList(true);
    }

    boolean isFirst = true;

    public void getLibraryList(boolean pull) {
        mRootView.showLoading();

        boolean isEvictCache = true;//是否驱逐缓存,为ture即不使用缓存,每次下拉刷新即需要最新数据,则不使用缓存

        if (pull) {//默认在第一次下拉刷新时使用缓存
//            page = 1;
            if (isFirst) {
                isFirst = false;
                isEvictCache = false;
            } else
                isEvictCache = true;
        } else {
            isEvictCache = true;
//            page++;
        }
        isOwner = false;
        mModel.getLibraryList(page, count, categoryBean == null ? "" : categoryBean.getDoc_category_id(),order
                , MessageConfig.LIBRARY_LIST_CACHE, isEvictCache)
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
                .subscribe(new ErrorHandleSubscriber<Arr_Library>(mErrorHandler) {
                    @Override
                    public void onNext(Arr_Library data) {
                       /* if (pull) {
                            mRootView.setData(data.getData());
                        } else {
                            mRootView.addData(data.getData());
                        }
                        mRootView.hideLoading();*/

                        ArrayList<LibraryItemBean> datas = data.getData();
                        if (pull) {
                            adapter.setNewData(datas);
                            if (datas.size() > 0) {
                                if (datas.size() < count) {
                                    if (adapter.getFooterViewsCount() == 0)
                                        adapter.addFooterView(AdapterViewUtils.getNoDataViwe(mApplication));
                                    mRootView.showSpingViewFooterEnable(false);//springView不可上拉
                                } else {
                                    adapter.removeAllFooterView();
                                    mRootView.showSpingViewFooterEnable(true);//springView可上拉
                                }
                            } else
                                adapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));
                        } else {
                            adapter.addData(datas);
                            if (datas.size() < count) {
                                if (adapter.getFooterViewsCount() == 0)
                                    adapter.addFooterView(AdapterViewUtils.getNoDataViwe(mApplication));
                                mRootView.showSpingViewFooterEnable(false);
                            } else {
                                mRootView.showSpingViewFooterEnable(true);
                            }
                        }

                    }
                });
    }

    public void getCommonCategory() {
        mModel.getCommonCategory()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    //错误
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<LibraryCategorys>(mErrorHandler) {
                    @Override
                    public void onNext(LibraryCategorys data) {
                        ArrayList<LibraryCategoryBean> categories = data.getData();
                        mRootView.showCategoryWindows(categories);
                    }
                });
    }

    public void getOwnerLibraryList(boolean pull) {
        mRootView.showLoading();
        isOwner = true;
        mModel.getOwnerLibraryList(page, count, MessageConfig.OWNER_LIBRARY_LIST_CACHE, isLibraryListCache)
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
                .subscribe(new ErrorHandleSubscriber<Arr_Library>(mErrorHandler) {
                    @Override
                    public void onNext(Arr_Library data) {
                        /*if (pull) {
                            mRootView.setData(data.getData());
                        } else {
                            mRootView.addData(data.getData());
                        }*/

                        ArrayList<LibraryItemBean> datas = data.getData();
                        if (pull) {
                            adapter.setNewData(datas);
                            if (datas.size() > 0) {
                                if (datas.size() < count) {
                                    if (adapter.getFooterViewsCount() == 0)
                                        adapter.addFooterView(AdapterViewUtils.getNoDataViwe(mApplication));
                                    mRootView.showSpingViewFooterEnable(false);//springView不可上拉
                                } else {
                                    adapter.removeAllFooterView();
                                    mRootView.showSpingViewFooterEnable(true);//springView可上拉
                                }
                            } else
                                adapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));
                        } else {
                            adapter.addData(datas);
                            if (datas.size() < count) {
                                if (adapter.getFooterViewsCount() == 0)
                                    adapter.addFooterView(AdapterViewUtils.getNoDataViwe(mApplication));
                                mRootView.showSpingViewFooterEnable(false);
                            } else {
                                mRootView.showSpingViewFooterEnable(true);
                            }
                        }

                    }
                });
    }


    public void exchangeLibrary(int doc_id, int position) {
        mRootView.showLoading();
        isOwner = true;
        mModel.exchangeLibrary(doc_id)
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
                        if (data.getCode() == 1) {
                            mRootView.showRightText(position);
                        } else {
                            mRootView.showMessage(data.getMsg());
                        }

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

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.download) {
            LibraryItemBean item = (LibraryItemBean) adapter.getItem(position);
            if (item.isBuy()) {
                //已兑换，执行下载操作
                if (recycle_view != null) {
                    DownProgressView progressView = (DownProgressView) adapter.getViewByPosition(recycle_view, position, R.id.download_progress);
                    mRootView.showMessage("开始下载:" + item.getTitle());
                    progressView.start();
                }
            } else {
                if (TextUtils.isEmpty(PreferenceUtil.getInstance(mApplication).getString("oauth_token", null)))
                    mRootView.launchActivity(new Intent(mApplication, LoginActivity.class).putExtra("SkipToHome", false));
                else
                    exchangeLibrary(item.getDoc_id(), position);
            }
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //打开文库
        LibraryItemBean libraryItemBean = ((LibraryListAdapter) adapter).getItem(position);
        DownloadBean bean = libraryItemBean.getDownloadbean();
        if (bean == null) return;
        File path = new File(bean.getFileSavePath());
        if (path.exists())
            mRootView.launchActivity(getFileIntent(path, bean.getExtension()));
    }

    public Intent getFileIntent(File path, String type) {
        Intent intent = new Intent(Intent.ACTION_VIEW);//Intent.ACTION_VIEW = "android.intent.action.VIEW"
        intent.addCategory(Intent.CATEGORY_DEFAULT);//Intent.CATEGORY_DEFAULT = "android.intent.category.DEFAULT"
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(path);
        intent.setDataAndType(uri, "application/" + type);
        return intent;
    }

    @Override
    public void onDialogItemClick(BasePopWindow popWindow, String type, int position) {
        //综合/热门/精华
        switch (position) {
            case 0:
                order = "";
                break;
            case 1:
                order = "hot";
                break;
            case 2:
                order = "1";
                break;
        }
        onRefresh();
    }

    @Override
    public void onWindowItemClick(Object p) {
        LibraryCategoryBean category = (LibraryCategoryBean) p;
        categoryBean = category;
        onRefresh();
    }
}