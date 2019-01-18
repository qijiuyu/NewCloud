package com.seition.cloud.pro.newcloud.home.mvp.ui.owner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.ChangeFaceResponse;
import com.seition.cloud.pro.newcloud.app.bean.user.MessageUserInfo;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerHomeComponent;
import com.seition.cloud.pro.newcloud.home.di.module.HomeModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.HomeContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.HomeUserInfoFragmentPresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.seition.cloud.pro.newcloud.app.config.MyConfig.RequestCodeImagePicker;


public class OwnerUserInfoFragment extends BaseBackFragment<HomeUserInfoFragmentPresenter> implements HomeContract.SetInfFragmentView {

    @BindView(R.id.user_photo)
    ImageView user_photo;
    @BindView(R.id.toolbar_right_text)
    TextView toolbar_right_text;
    @BindView(R.id.nick_name)
    EditText nick_name;
    @BindView(R.id.sex_readio)
    RadioGroup sex_group;
    @BindView(R.id.radio_male)
    RadioButton radio_male;
    @BindView(R.id.radio_female)
    RadioButton radio_female;
    @BindView(R.id.personal_signature)
    EditText personal_signature;



    @OnClick({R.id.toolbar_right_text, R.id.user_photo})
    void toSearch(View view) {
        switch (view.getId()) {
            case R.id.toolbar_right_text:
                String userName = nick_name.getText().toString().trim();
                String intro = personal_signature.getText().toString().trim();
                int radioButtonId = sex_group.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) _mActivity.findViewById(radioButtonId);
                String s = rb.getText().toString();
                mPresenter.setUserInfo(userName,s.equals("男")?1:2,intro);
                break;
            case R.id.user_photo:
                selectCover(RequestCodeImagePicker);
                break;
        }

    }

    public static OwnerUserInfoFragment newInstance() {
        Bundle args = new Bundle();
//        args.putSerializable("organ",organ);
        OwnerUserInfoFragment fragment = new OwnerUserInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_owner_user_info, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle("个人资料");
        toolbar_right_text.setText("保存");

        mPresenter.getUserInfo(true);
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

    public void selectCover1(int requestCode) {

        Intent intent = new Intent(_mActivity, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 是否是直接打开相机
        startActivityForResult(intent, requestCode);
    }
    public void selectCover(int requestCode) {

        Intent intent = new Intent(_mActivity, ImageGridActivity.class);
        startActivityForResult(intent, requestCode);
    }

    private List<ImageItem> imageItems;
    ArrayList<File> files = new ArrayList<>();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            imageItems = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (imageItems != null && imageItems.size() > 0) {
                files.clear();
                for (int i = 0; i < imageItems.size(); i++) {
                    files.add(new File(imageItems.get(i).path));
                }
                if (requestCode == RequestCodeImagePicker) {
                    showUpImageDialog(files);
//                    uploadFiles(filesToMultipartBodyParts(files));
                }

            }
        }
    }

    private void showUpImageDialog(ArrayList<File> files){
        new MaterialDialog.Builder(_mActivity)
                .content("是否修改你的头像")
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        uploadFiles(filesToMultipartBodyParts(files));
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

    MaterialDialog materialDialog;

    public void uploadFiles(List<MultipartBody.Part> files) {
        materialDialog = new MaterialDialog.Builder(_mActivity)
//                .title("正在上传图片")
                .content("正在上传图片")
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .canceledOnTouchOutside(false)
                .show();
        LogUtils.debugInfo("上传的图片 files.size()" + files.size());
        for (MultipartBody.Part file : files)
            mPresenter.setUserFace(file);

    }

    public static List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("face"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }

    void showSelectGroupCover(View view, String path) {
        if (path.equals(""))
            return;
        // 缩放图片, width, height 按相同比例缩放图片
        BitmapFactory.Options options = new BitmapFactory.Options();
        // options 设为true时，构造出的bitmap没有图片，只有一些长宽等配置信息，但比较快，设为false时，才有图片
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        int scale = (int) (options.outWidth / (float) 300);
        if (scale <= 0)
            scale = 1;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(path, options);
        Drawable drawable = new BitmapDrawable(bitmap);
        view.setBackground(drawable);
    }

    @Override
    public void showUserInfo(MessageUserInfo user) {
        String photo = user.getAvatar_big();
        GlideLoaderUtil.LoadCircleImage(_mActivity,photo,user_photo);
        nick_name.setText(user.getUname());
        if("男".equals(user.getSex()))
            radio_male.setChecked(true);
        else
            radio_female.setChecked(true);
        personal_signature.setText(user.getIntro());
    }

    @Override
    public void showSetUserFace(ChangeFaceResponse response) {
        materialDialog.dismiss();
        if (response!=null){
            PreferenceUtil preferenceUtil = PreferenceUtil.getInstance(_mActivity);

            preferenceUtil.saveString("user_avatar", response.getBig());
            showMessage("头像修改成功");
            GlideLoaderUtil.LoadCircleImage(_mActivity, imageItems.get(0).path,user_photo);
        }
        else
            showMessage("头像修改失败");
    }


}
