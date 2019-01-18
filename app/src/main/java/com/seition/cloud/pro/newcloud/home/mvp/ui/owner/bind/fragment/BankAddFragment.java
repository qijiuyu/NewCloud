package com.seition.cloud.pro.newcloud.home.mvp.ui.owner.bind.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerBindManageComponent;
import com.seition.cloud.pro.newcloud.home.di.module.BindManageModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.BindManageContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.BindManagePresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.user.fragment.AreaMainFragment;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.seition.cloud.pro.newcloud.app.config.MyConfig.RequestCodeArea;
import static com.seition.cloud.pro.newcloud.app.config.MyConfig.RequestCodeBank;


public class BankAddFragment extends BaseBackFragment<BindManagePresenter> implements BindManageContract.View {
    @BindView(R.id.open_account)
    TextView open_account;
    @BindView(R.id.account_pca)
    TextView account_pca;

    @BindView(R.id.account_address)
    EditText account_address;
    @BindView(R.id.account_number)
    EditText account_number;
    @BindView(R.id.account_name)
    EditText account_name;
    @BindView(R.id.account_phone)
    EditText account_phone;
    @BindView(R.id.confirm_btn)
    TextView confirm_btn;

    public static BankAddFragment newInstance() {
        Bundle args = new Bundle();
//        args.putSerializable("organ",organ);
        BankAddFragment fragment = new BankAddFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick({R.id.open_account,R.id.account_pca,R.id.confirm_btn})
    void doSomething(View view){
        switch (view.getId()){
            case R.id.open_account:
                startForResult(BankListFragment.newInstance(),RequestCodeBank);
                break;
            case R.id.account_pca:
                 startForResult(AreaMainFragment.newInstance(),RequestCodeArea);
                break;
            case R.id.confirm_btn:
                if (!Utils.checkBankCard(account_number.getText().toString().trim())) {
                    showMessage("请输入正确的银行卡号");
                    return ;
                }
                if (!Utils.isPhone(account_phone.getText().toString().trim())) {
                    showMessage("请输入正确的电话号码");
                    return ;
                }
                mPresenter.addBindBank(account_number.getText().toString().trim(),
                                        account_name.getText().toString().trim(),
                                        bankname,
                                        account_address.getText().toString().trim(),
                                        bankAddress,province_id+"",city_id+"",area_id+"",account_phone.getText().toString().trim());
                break;
        }
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
        return inflater.inflate(R.layout.fragment_bank_add,container,false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.add_bank);

        account_address.addTextChangedListener(textWatcher);
        account_number.addTextChangedListener(textWatcher);
        account_name.addTextChangedListener(textWatcher);
        account_phone.addTextChangedListener(textWatcher);
    }
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            check();
        }
    };

    private void check() {
        boolean isOk = isOk();
        confirm_btn.setEnabled(isOk);
        if (isOk)
            confirm_btn.setBackgroundResource(R.color.colorPrimary);
        else
            confirm_btn.setBackgroundResource(R.color.color_a5c3eb);

    }
    String bankname = "";
    String bankAddress = "";

    int province_id;
    int city_id;
    int area_id;
    private boolean isOk() {
        if ("".equals(bankname)) {
//            showDialog("请选择开户银行");
            return false;
        }
        if ("".equals(bankAddress)) {
//            showDialog("请选择开户银行所在地区");
            return false;
        }
        if ("".equals(account_address.getText().toString().trim())) {
//            showDialog("请输入开户行地址");
            return false;
        }

        if ("".equals(account_number.getText().toString().trim())) {

            return false;
        }
        if ("".equals(account_name.getText().toString().trim())) {
//            showDialog("请输入姓名");
            return false;
        }
        if ("".equals(account_phone.getText().toString().trim())) {

            return false;
        }
        return true;
    }
    @Override
    public void setData(Object data) {

    }

    String selectProvince = "", selectCity="", selectArea ="" ;
    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == RequestCodeBank && resultCode == RESULT_OK && data != null) {
            bankname =data.getString("BankName");
            open_account.setText(bankname);
//            getArguments().putString(ARG_TITLE, mTitle);
//            Toast.makeText(_mActivity, R.string.modify_title, Toast.LENGTH_SHORT).show();
        }
        if (requestCode == RequestCodeArea && resultCode == RESULT_OK && data != null) {
            HashMap map = (HashMap)data.getSerializable("addressInfo");
            selectProvince = map.get("provName").toString();
            selectCity = map.get("cityName").toString();
            selectArea = map.get("districtName").toString();

            province_id = Integer.parseInt(map.get("provId").toString());
            city_id = Integer.parseInt(map.get("cityId").toString());
            area_id = Integer.parseInt(map.get("districtId").toString());
            bankAddress = selectProvince+selectCity+selectArea;
            account_pca.setText(bankAddress);
//            getArguments().putString(ARG_TITLE, mTitle);
//            Toast.makeText(_mActivity, R.string.modify_title, Toast.LENGTH_SHORT).show();
        }
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


}
