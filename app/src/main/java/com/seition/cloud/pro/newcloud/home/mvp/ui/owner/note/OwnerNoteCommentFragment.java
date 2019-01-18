package com.seition.cloud.pro.newcloud.home.mvp.ui.owner.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.note.Note;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerUserComponent;
import com.seition.cloud.pro.newcloud.home.di.module.UserModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.UserContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.UserNoteListPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.NoteCommentRecyclerAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class OwnerNoteCommentFragment extends BaseBackFragment<UserNoteListPresenter> implements UserContract.View, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.edittext)
    EditText mEdit;
    @BindView(R.id.send_btn)
    Button send_btn;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.comment_time)
    TextView time;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.contents)
    TextView contents;


    @BindView(R.id.user_photo)
    ImageView photo;
    @BindView(R.id.zan_count)
    TextView zan_count;
    @BindView(R.id.comment_count)
    TextView comment_count;

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @Inject
    NoteCommentRecyclerAdapter adapter;
    Note note;

    public static OwnerNoteCommentFragment newInstance(Note note) {
        Bundle args = new Bundle();
        args.putSerializable("note", note);
        OwnerNoteCommentFragment fragment = new OwnerNoteCommentFragment();
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
        return inflater.inflate(R.layout.fragment_note_comment, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(getResources().getString(R.string.owner_note_comment));
        initView();

        note = (Note) getArguments().getSerializable("note");
//        name.setText(note.getData());
        zan_count.setText(note.getNote_help_count() + ""/* + "点赞"*/);
        time.setText(note.getStrtime());
        comment_count.setText(note.getNote_comment_count() + ""/* + "人评论"*/);
        title.setText(note.getNote_title());
        contents.setText(note.getNote_description());
        // 采用缓存加载图片
        GlideLoaderUtil.LoadCircleImage(_mActivity, note.getUserface(), photo);

        loadData();
    }

    @OnClick({R.id.send_btn})
    void noteComment(View view) {
        if ("".equals(mEdit.getText().toString().trim())) {
            showMessage("请输入你要评论的内容");
            return;
        }
        mPresenter.addNoteComments(note.getId(), mEdit.getText().toString().trim(), note.getId());
    }

    @Override
    public void setData(Object data) {

    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));// 布局管理器
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void loadData() {
        mPresenter.getMyNoteComments(note.getId(), 1, false);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void showStateViewState(int state) {
        showMultiViewState(state);
    }

    @Override
    public void showSpingViewFooterEnable(boolean enabled) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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

    }


}
