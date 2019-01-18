package com.seition.cloud.pro.newcloud.home.mvp.ui.search.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;

import butterknife.BindView;
import butterknife.OnClick;


public class SearchMainFragment extends BaseBackFragment/*<SearchPresenter> implements SearchContract.View*/ {

    //    @BindView(R.id.topBar)
//    TopBar mTobBar;
    @BindView(R.id.search_edit_text)
    EditText search_edit_text;

    public static final int SEARCH_COURSES = 1;
    public static final int SEARCH_LIVE = 2;
    public static final int SEARCH_OFFLINE_COURSES = 3;
    public static final int SEARCH_VIP_COURSES = 4;
    public static final int SEARCH_HISTORY = 5;

//    public static void startRecyclerViewActivity(Activity activity, int type) {
//        Intent intent = new Intent(activity, SearchMainFragment.class);
//        intent.putExtra("type", type);
//        activity.startActivity(intent);
//    }

    @OnClick(R.id.search)
    void search(View view) {
        String keyword = search_edit_text.getText().toString().trim();
        searchCoursesFragment.setKeyword(keyword);
        if (!"".equals(keyword))
            searchCoursesFragment.loadData(true);
        else {
            ArmsUtils.snackbarText("请输入搜索的关键词");
        }
    }

    public static SearchMainFragment newInstance(int type, String vipid) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putString("vipid", vipid);
        SearchMainFragment fragment = new SearchMainFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
  /*      DaggerSearchComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .searchModule(new SearchModule(this))
                .build()
                .inject(this);*/
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_search, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
        type = getArguments().getInt("type");
        vipid = getArguments().getString("vipid");
        setDefaultFragment();
    }

    @Override
    public void setData(Object data) {

    }

    private void initView() {


    }

    private int type;
    private String vipid = "";
    protected SearchVideoCoursesFragment searchCoursesFragment;

    private void setDefaultFragment() {
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        searchCoursesFragment = SearchVideoCoursesFragment.newInstance(type, "");
        transaction.replace(R.id.id_content, searchCoursesFragment);
        transaction.commit();
    }


   /* @Override
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
    public void showCategoryWindows(ArrayList<CommonCategory> commonCategories) {

    }*/
}
