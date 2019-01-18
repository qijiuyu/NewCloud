package com.seition.cloud.pro.newcloud.home.mvp.ui.user.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.AreaInfo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;


public class AreaMainFragment extends BaseBackFragment implements AreaFragment.OnFragmentInteractionListener/*<UserReceiveAddressListPresenter> implements UserContract.View*/ {




    private Fragment provinceFragment;
    private Fragment cityFragment;
    private Fragment districtFragment;

    public static AreaMainFragment newInstance() {
        Bundle args = new Bundle();

        AreaMainFragment fragment = new AreaMainFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @OnClick({R.id.toolbar_back  })
    void addAddress(View view)
    {
        switch (view.getId()){
            case R.id.toolbar_back:

                FragmentManager fragmentManager = getChildFragmentManager();
                if (fragmentManager.getBackStackEntryCount()>0){
                    fragmentManager.popBackStack();
                }else{
                    pop();
                }
                break;

        }

    }




    @Override
    public void setupFragmentComponent(AppComponent appComponent) {

      /*  DaggerUserComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .userModule(new UserModule(this))
                .build()
                .inject(this);*/
    }

    View view;
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_area_main, container, false);
        return view;
    }



    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle("选择地区");
        initview();
    }
    FragmentManager  fragmentManager;
    private void initview(){
        provinceFragment = AreaFragment.newInstance(0,0);
       fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content,provinceFragment).commit();
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
    private Map map = new HashMap();
    int nextAreaType;
    AreaInfo areaInfo;
    @Override
    public void onFragmentInteraction(AreaInfo info, int type) {
        if (info==null){
            return;
        }
        nextAreaType = type;
        areaInfo = info;
        fragmentChange();
    }

    private void fragmentChange(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);
        if (provinceFragment != null)
            transaction.hide(provinceFragment);
        if (cityFragment != null)
            transaction.hide(cityFragment);
        if (districtFragment != null)
            transaction.hide(districtFragment);
//        if (classify_fg != null)
//            transaction.hide(classify_fg);
        System.out.println("nextAreaType = "+nextAreaType);
//        int level = areaInfo.getLevel();
        switch (nextAreaType){
            case 1:
                map.put("provId",areaInfo.getArea_id());
                map.put("provName",areaInfo.getTitle());

//                if (areaInfo.isLeaf()){
//                    Intent intent = new Intent();
//
//                    intent.putExtra("addressInfo", (Serializable) map);
//                    setResult(RESULT_OK,intent);
//                    finish();
//                }else{
//                    level =  2;
//                    transaction.hide(provinceFragment);
                if (provinceFragment != null)
                    transaction.hide(provinceFragment);
                if (districtFragment != null)
                    transaction.hide(districtFragment);
//                if (cityFragment == null) {
                cityFragment=AreaFragment.newInstance(Integer.parseInt(areaInfo.getArea_id()),1);
                transaction.replace(R.id.content,cityFragment).addToBackStack(null).commit();
//                } else {
//                    transaction.show(cityFragment);
//                }
//                }
                break;
            case 2:
                map.put("cityId",areaInfo.getArea_id());
                map.put("cityName",areaInfo.getTitle());
//                if (areaInfo.isLeaf()){
//                    Intent intent = new Intent();
//                    intent.putExtra("addressInfo", (Serializable) map);
//                    setResult(RESULT_OK,intent);
//                    finish();
//                }else {
//                    level =  3;
                transaction.hide(provinceFragment);
                transaction.hide(cityFragment);

                transaction.replace(R.id.content, districtFragment = AreaFragment.newInstance(Integer.parseInt(areaInfo.getArea_id()),2)).addToBackStack(null).commit();

//                    if (districtFragment == null) {
//                        transaction.add (R.id.content, districtFragment = AreaFragment.newInstance(Integer.parseInt(areaInfo.getArea_id()),2)).addToBackStack(null).commit();
//                    } else {
//                        transaction.show(districtFragment);
//                    }
//                }
                break;

            case 3:
                map.put("districtId",areaInfo.getArea_id());
                map.put("districtName",areaInfo.getTitle());
                Bundle bundle = new Bundle();
                bundle.putSerializable("addressInfo", (Serializable) map);
//                Intent intent = new Intent();
//                intent.putExtra("addressInfo", (Serializable) map);
                setFragmentResult(RESULT_OK,bundle);
//                setResult(RESULT_OK,intent);
                pop();
                break;
        }

    }


    /*@Override
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
    public void showStateViewState(int state) {
        showMultiViewState(state);
    }
*/

}
