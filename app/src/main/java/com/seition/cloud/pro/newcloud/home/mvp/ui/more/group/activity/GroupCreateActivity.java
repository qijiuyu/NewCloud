package com.seition.cloud.pro.newcloud.home.mvp.ui.more.group.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
//import com.lzy.imagepicker.bean.ImageItem;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerGroupComponent;
import com.seition.cloud.pro.newcloud.home.di.module.GroupModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.GroupContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.GroupOperationPresenter;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class GroupCreateActivity extends BaseActivity<GroupOperationPresenter> implements GroupContract.GroupOperationView {

    @BindView(R.id.toolbar_right_text)
    TextView toolbar_right_text;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.group_cover_select)
    TextView group_cover_select;
    @BindView(R.id.group_name)
    EditText group_name;
    @BindView(R.id.group_classify)
    TextView group_classify;

    @BindView(R.id.group_info)
    EditText group_info;
    @BindView(R.id.group_announcement)
    EditText group_announcement;

    @BindView(R.id.group_cover)
    RelativeLayout group_cover;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerGroupComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .groupModule(new GroupModule(this))
                .build()
                .inject(this);
    }

    private String groupOperationType ="0";
    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_group_create; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        toolbar_right_text.setText(R.string.group_operation_save);
        setTitle((groupOperationType.equals("0"))?getResources().getString(R.string.group_create):getResources().getString(R.string.group_edit));
    }
    private String groupName ="",groupCateId ="",groupInfo ="",groupAnnounce ="";
    private int groupId = -1 ,groupCoverId =-1;
    @OnClick({R.id.group_cover_select,R.id.toolbar_right_text})
    void groupOperation(View view){
        switch(view.getId()) {
            case R.id.group_cover_select:
                selectCover();
            break;
            case R.id.toolbar_right_text:
                try {
                    checkParameter();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            break;
        }
    }

    public void selectCover(){
//        ImagePicker imagePicker = ImagePicker.getInstance();
//        imagePicker.setImageLoader(new GlideImageLoader());
//        imagePicker.setMultiMode(true);
//        imagePicker.setShowCamera(true);
//        imagePicker.setSelectLimit(2);
//        imagePicker.setCrop(false);
//        Intent intent = new Intent(this, ImageGridActivity.class);
//        startActivityForResult(intent,100);
    }
//    private List<ImageItem> imageItems;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if(resultCode == ImagePicker.RESULT_CODE_ITEMS){
            imageItems = (ArrayList<ImageItem>)data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);

            if(imageItems!=null &&imageItems.size()>0) {
                final ArrayList<File> files = new ArrayList<>();
                for (int i = 0;i<imageItems.size();i++){
                    files.add(new File(imageItems.get(i).path));
                }
                showSelectGroupCover(imageItems.get(0).path);
                mPresenter.uploadFiles(filesToMultipartBodyParts(files));
            }
        }*/
    }

    void showSelectGroupCover(String path){
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

        // group_creat_get_photo.setImageBitmap(bitmap);
        // group_creat_get_photo.setMaxHeight(350);
        Drawable drawable =     new BitmapDrawable(bitmap);
        group_cover.setBackgroundDrawable(drawable);
//        group_cover.setBackground(drawable);//setImageBitmap(bitmap);
        group_cover_select.setVisibility(View.INVISIBLE);
    }

    public static List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files){
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for(File file:files){
            RequestBody requestBody = RequestBody.create(MediaType.parse("face"),file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file",file.getName(),requestBody);
            parts.add(part);
        }
        return parts;
    }

    private void checkParameter() throws UnsupportedEncodingException {

        groupName = URLEncoder.encode(group_name.getText().toString().trim(),"utf-8");
        groupInfo = URLEncoder.encode(group_info.getText().toString().trim(),"utf-8");
        groupAnnounce = URLEncoder.encode(group_announcement.getText().toString().trim(),"utf-8");


        if ((groupCoverId != -1)&&groupOperationType.equals("1")) {
            group_announcement.setError("小组封面不能为空");
            return;
        }
        if("".equals(groupName)){
            group_name.setError("小组名称不能为空"); return;
        }
//        if("".equals(groupCateId)&&groupOperationType.equals("0")){
//             group_classify.setError("请选择小组分类");return;
//        }
        if("".equals(groupInfo)) {
            group_info.setError("小组简介不能为空"); return;
        }
        if("".equals(groupAnnounce)){
            group_announcement.setError("小组公告不能为空");return;
        }
        if ((groupId == -1)&&groupOperationType.equals("1")) {
            group_announcement.setError("小组id不能为空");
            return;
        }

        if (groupOperationType.equals("0")) {
            mPresenter.createGroup(groupName, groupCoverId, groupCateId, "open", groupInfo, groupAnnounce);
        }
        else
            mPresenter.editGroup(groupId,groupName,groupCoverId,groupCateId,groupInfo,groupAnnounce);

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
        finish();
    }


    @Override
    public void setGroupLogoData(int logoData) {
        groupCoverId = logoData;
    }
}
