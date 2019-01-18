package com.seition.cloud.pro.newcloud.home.mvp.ui.owner.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.note.Note;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerUserComponent;
import com.seition.cloud.pro.newcloud.home.di.module.UserModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.UserContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.UserNoteListPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.NoteRecyclerAdapter;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


//public class FeedBackFragment extends BaseActivity<LoginPresenter> implements LoginContract.View {
public class OwnerNoteFragment extends BaseBackFragment<UserNoteListPresenter>  implements  UserContract.View ,BaseQuickAdapter.OnItemClickListener{
    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @Inject
    NoteRecyclerAdapter adapter;

    public static OwnerNoteFragment newInstance() {
        Bundle args = new Bundle();
//        args.putSerializable("organ",organ);
        OwnerNoteFragment fragment = new OwnerNoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerUserComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .userModule(new UserModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_owner_note,container,false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(getResources().getString(R.string.owner_note));
        initView();
    }

    @Override
    public void setData(Object data) {

    }

    private void initView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));// 布局管理器
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        springView.setType(SpringView.Type.FOLLOW);

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                springView.setEnableFooter(false);
                loadData(true);
            }

            @Override
            public void onLoadmore() {
                loadData(false);
            }
        });
        springView.setHeader(new DefaultHeader(_mActivity));   //参数为：logo图片资源，是否显示文字
        springView.setFooter(new DefaultFooter(_mActivity));
        springView.setEnableFooter(false);
        loadData(true);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Note note = (Note)adapter.getItem(position);
        start(OwnerNoteCommentFragment.newInstance(note));
    }

    private void loadData(boolean pul){
        mPresenter.getMyNotes(pul);
    }

    @Override
    public void showStateViewState(int state) {
            showMultiViewState(state);
    }

    @Override
    public void showSpingViewFooterEnable(boolean enabled) {
        springView.setEnableFooter(enabled);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        springView.onFinishFreshAndLoad();
    }

    @Override
    public void showMessage(String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {
        pop();
    }



}
