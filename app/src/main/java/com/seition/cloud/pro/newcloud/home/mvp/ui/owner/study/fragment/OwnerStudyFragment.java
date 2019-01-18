package com.seition.cloud.pro.newcloud.home.mvp.ui.owner.study.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.widget.statue.SimpleMultiStateView;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.study.StudyRecordSection;
import com.seition.cloud.pro.newcloud.app.utils.AdapterViewUtils;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerUserComponent;
import com.seition.cloud.pro.newcloud.home.di.module.UserModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.UserContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.UserStudyListPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.SectionAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class OwnerStudyFragment extends BaseBackFragment<UserStudyListPresenter> implements UserContract.StudyView, BaseQuickAdapter.OnItemClickListener, BaseSectionQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.toolbar_right_text)
    TextView toolbarRightText;
    @BindView(R.id.multistateview)
    SimpleMultiStateView multistateview;

    @BindView(R.id.record_bottom)
    LinearLayout record_bottom;
    Unbinder unbinder;

    @BindView(R.id.check_all)
    TextView check_all;

    @BindView(R.id.delete)
    TextView delete;

    public static OwnerStudyFragment newInstance() {
        OwnerStudyFragment fragment = new OwnerStudyFragment();
        return fragment;
    }

    boolean isDelete = false;

    @OnClick({R.id.check_all, R.id.delete, R.id.toolbar_right_text})
    void doStudy(View view) {
        switch (view.getId()) {
            case R.id.check_all:
                checkAll(check_all.getText().toString().equals("全选"));
                break;
            case R.id.delete:
                delete();
                break;
            case R.id.toolbar_right_text:
                right();
                break;
        }
    }

    public static final int LEFT = 0x51;
    public static final int TOP = 0x52;
    public static final int RIGHT = 0x53;
    public static final int BOTTOM = 0x54;

    //
    public static void setTVDrawable(TextView v, Context c, int id, int position) {
        if (id == 0) {
            v.setCompoundDrawables(null, null, null, null);
            return;
        }
        Drawable d = c.getResources().getDrawable(id);
        d.setBounds(0, 0, d.getMinimumWidth(), d.getMinimumHeight());
        switch (position) {
            case LEFT:
                v.setCompoundDrawables(d, null, null, null);
                break;
            case TOP:
                v.setCompoundDrawables(null, d, null, null);
                break;
            case RIGHT:
                v.setCompoundDrawables(null, null, d, null);
                break;
            case BOTTOM:
                v.setCompoundDrawables(null, null, null, d);
                break;
        }
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

    View view;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_study_record, container, false);
        return view;
    }


    SectionAdapter sectionAdapter;
    ArrayList<StudyRecordSection> studyRecordSections = new ArrayList<>();

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle("我的学习记录");
//        toolbarRightText.setBackgroundResource(R.drawable.ic_delete_white);

        setTVDrawable(toolbarRightText, _mActivity, R.drawable.ic_delete_white, RIGHT);
        toolbarRightText.setText("");

        sectionAdapter = new SectionAdapter(R.layout.item_study_content, R.layout.item_study_recode, studyRecordSections);
        sectionAdapter.setOnItemChildClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));// 布局管理器
        recyclerView.setAdapter(sectionAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        sectionAdapter.setOnItemClickListener(this);

        springView.setType(SpringView.Type.FOLLOW);

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
//                mPresenter.getGroupList(1,10,cateid,true,true);
                loadData();
            }

            @Override
            public void onLoadmore() {

            }
        });
//        springView.setHeader(new DefaultHeader(getActivity()));   //参数为：logo图片资源，是否显示文字
//        springView.callFresh();
//        springView.setFooter(new DefaultFooter(getActivity()));
        loadData();
    }

    @Override
    public SpringView.DragHander getLoadMoreFooterView() {
        return null;
    }

    private void loadData() {
        mPresenter.getStudyRecord(true);
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


    @Override
    public void showLoading() {
        springView.onFinishFreshAndLoad();
    }

    @Override
    public void hideLoading() {
        springView.onFinishFreshAndLoad();

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
        if (isDelete) {
            boolean imageClick = ((StudyRecordSection) studyRecordSections.get(position)).t.isSelect();
            ((StudyRecordSection) studyRecordSections.get(position)).t.setSelect(!imageClick);
            showDelete();
            sectionAdapter.notifyDataSetChanged();
        }
    }

    private void checkAll(boolean isAll) {
        for (int i = 0; i < studyRecordSections.size(); i++) {
            if (studyRecordSections.get(i).t != null)
                studyRecordSections.get(i).t.setSelect(isAll);
        }
        sectionAdapter.notifyDataSetChanged();
        showDelete();
    }

    private void showDelete() {
        deleteStatistics();
        if (del.size() == 0)
            delete.setText("取消删除");
        else
            delete.setText("删除已选(" + del.size() + ")");
        if ((del.size() + headerCount) == studyRecordSections.size())
            check_all.setText("取消全选");
        else
            check_all.setText("全选");
    }

    ArrayList<Integer> del = new ArrayList<>();
    int headerCount = 0;

    private void deleteStatistics() {
        del = new ArrayList<>();
        for (int i = 0; i < studyRecordSections.size(); i++) {
            if (studyRecordSections.get(i).t != null && studyRecordSections.get(i).t.isSelect() && !studyRecordSections.get(i).isHeader)
                del.add(studyRecordSections.get(i).t.getRecord_id());
        }
    }

    private void delete() {
        if (delete.getText().toString().equals("取消删除")) {
            right();
        } else {
            mPresenter.deleteStudyRecord(getDeleteId());
        }
    }

    private void right() {

        if (toolbarRightText.getText().toString().isEmpty()) {
            setTVDrawable(toolbarRightText, _mActivity, 0, RIGHT);
            isDelete = true;
            toolbarRightText.setText("取消");
            sectionAdapter.setDelete(true);
            record_bottom.setVisibility(View.VISIBLE);
            checkAll(false);
        } else {
            setTVDrawable(toolbarRightText, _mActivity, R.drawable.ic_delete_white, RIGHT);
            toolbarRightText.setText("");
            isDelete = false;
            sectionAdapter.setDelete(false);
            record_bottom.setVisibility(View.GONE);
            delete.setText("");
        }

    }

    private String getDeleteId() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < del.size(); i++) {
            if (i != 0)
                sb.append(",");
            sb.append(del.get(i));
        }
        Log.i("info", "sb = " + sb.toString());
        return sb.toString();
    }

    @Override
    public void showStateViewState(int state) {
        showMultiViewState(state);
    }

    @Override
    public void showSpingViewFooterEnable(boolean enabled) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setData(ArrayList<StudyRecordSection> studyRecordSections) {
        this.studyRecordSections.clear();
        this.studyRecordSections = studyRecordSections;

        for (int i = 0; i < studyRecordSections.size(); i++) {
            if (studyRecordSections.get(i).isHeader)
                headerCount++;
        }
        sectionAdapter.setNewData(studyRecordSections);
        if (studyRecordSections.size() == 0)
            sectionAdapter.setEmptyView(AdapterViewUtils.getEmptyViwe(_mActivity));
    }

    @Override
    public void deleteSrudyRecord(boolean successful) {
        if (successful) {
            del.clear();
            showDelete();
            loadData();
        } else
            showMessage("删除学习记录失败");
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
