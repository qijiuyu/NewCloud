package com.seition.cloud.pro.newcloud.home.mvp.ui.user.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.ReceiveGoodsAddress;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerUserComponent;
import com.seition.cloud.pro.newcloud.home.di.module.UserModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.UserContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.UserReceiveAddressListPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.ReceiveAddressRecyclerAdapter;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.seition.cloud.pro.newcloud.app.config.MyConfig.RequestCodeArea;


public class ReceiveGoodsAddFragment extends BaseBackFragment<UserReceiveAddressListPresenter> implements UserContract.View, TextWatcher {

    @BindView(R.id.toolbar_right_text)
    TextView toolbar_right_text;
    @Inject
    ReceiveAddressRecyclerAdapter adapter;
    @BindView(R.id.receive_people_input)
    EditText receive_people_input;
    @BindView(R.id.receive_phone_input)
    EditText receive_phone_input;
    @BindView(R.id.receive_address_input)
    EditText receive_address_input;
    @BindView(R.id.receive_area_input)
    TextView receive_area;
    @BindView(R.id.agree_cb)
    CheckBox agree_cb;

    int type = 0;
    ReceiveGoodsAddress receiveGoodsAddress;

    public static ReceiveGoodsAddFragment newInstance(int type, ReceiveGoodsAddress address) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putSerializable("ReceiveGoodsAddress", address);
        ReceiveGoodsAddFragment fragment = new ReceiveGoodsAddFragment();
        fragment.setArguments(args);
        return fragment;
    }

    String selectProvince = "", selectCity = "", selectArea = "";
    String location = "";
    String inputAddress = "", inputName = "", inputPhone = "";
    boolean is_default;

    @OnClick({R.id.toolbar_right_text, R.id.receive_area_input})
    void addAddress(View view) {
        switch (view.getId()) {
            case R.id.toolbar_right_text:

                inputName = receive_people_input.getText().toString().trim();
                inputPhone = receive_phone_input.getText().toString().trim();
                inputAddress = receive_address_input.getText().toString().trim();
                is_default = agree_cb.isChecked();


                if (type == 1) {
                    System.out.println(inputName != receiveGoodsAddress.getName());
                    if (inputName.equals(receiveGoodsAddress.getName()) && inputPhone.equals(receiveGoodsAddress.getPhone()) && inputAddress.equals(receiveGoodsAddress.getAddress())) {
                        showMessage("您输入的信息和之前的一样");
                    } else {
                        checkPargram();
                        mPresenter.changeReceiveAddress(/*selectProvince*/provId, /*selectCity*/cityId, /*selectArea*/districtId, location, inputAddress, inputName, inputPhone, is_default ? "1" : "0", receiveGoodsAddress.getAddress_id());
                    }

                } else {

                    checkPargram();
                    mPresenter.addReceiveAddress(/*selectProvince*/provId, /*selectCity*/cityId, /*selectArea*/districtId, location, inputAddress, inputName, inputPhone, is_default ? "1" : "0");
                }
                break;
            case R.id.receive_area_input:
                startForResult(AreaMainFragment.newInstance(), RequestCodeArea);

                break;
        }

    }


    private void checkPargram() {
        if ("".equals(inputName)) {
            showMessage("请输入收货人姓名");
            return;
        }
        if (!Utils.isPhone(inputPhone)) {
            showMessage("请输入正确的电话号码");
            return;
        }
        if ("".equals(inputAddress)) {
            showMessage("请输入详细的地址");
            return;
        }
        if ("".equals(selectProvince) || "".equals(selectCity) || "".equals(selectArea)) {
            showMessage("请选择您所在的省市区");
            return;
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
        view = inflater.inflate(R.layout.fragment_add_receive_goods_address, container, false);
        return view;
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        type = getArguments().getInt("type");
        receiveGoodsAddress = (ReceiveGoodsAddress) getArguments().getSerializable("ReceiveGoodsAddress");
        initview();
    }

    private void initview() {
        if (type == 1) {
            setTitle("修改收货地址");
            toolbar_right_text.setVisibility(View.GONE);
            receive_people_input.setText(receiveGoodsAddress.getName());
            receive_phone_input.setText(receiveGoodsAddress.getPhone());
            receive_area.setText(receiveGoodsAddress.getProvince() + receiveGoodsAddress.getCity() + receiveGoodsAddress.getArea());
            receive_address_input.setText(receiveGoodsAddress.getAddress());
            agree_cb.setChecked(receiveGoodsAddress.getIs_default().equals("1") ? true : false);
        } else {
            setTitle("新增收货地址");
            toolbar_right_text.setVisibility(View.VISIBLE);
        }

        toolbar_right_text.setText("保存");

        receive_people_input.addTextChangedListener(this);
        receive_phone_input.addTextChangedListener(this);
        receive_address_input.addTextChangedListener(this);
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


    String provId, cityId, districtId;

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == RequestCodeArea && resultCode == RESULT_OK && data != null) {
            HashMap map = (HashMap) data.getSerializable("addressInfo");
            selectProvince = map.get("provName").toString();
            selectCity = map.get("cityName").toString();
            selectArea = map.get("districtName").toString();
            provId = map.get("provId").toString();
            cityId = map.get("cityId").toString();
            districtId = map.get("districtId").toString();
//            bankAddress = (map.get("provName").toString() + map.get("cityName").toString() + map.get("districtName").toString()).trim();
            receive_area.setText(selectProvince + selectCity + selectArea);
//            getArguments().putString(ARG_TITLE, mTitle);
//            Toast.makeText(_mActivity, R.string.modify_title, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showStateViewState(int state) {
        showMultiViewState(state);
    }

    @Override
    public void showSpingViewFooterEnable(boolean enabled) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!"".equals(s.toString()))
            toolbar_right_text.setVisibility(View.VISIBLE);
        else
            toolbar_right_text.setVisibility(View.GONE);
    }
}
