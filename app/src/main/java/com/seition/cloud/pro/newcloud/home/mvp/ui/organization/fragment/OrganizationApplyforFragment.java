package com.seition.cloud.pro.newcloud.home.mvp.ui.organization.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.organization.OrganizationStatus;
import com.seition.cloud.pro.newcloud.app.listener.RecyclerItemClickListener;
import com.seition.cloud.pro.newcloud.app.popupwindow.CategoryPickPopupWindow;
import com.seition.cloud.pro.newcloud.app.utils.GlideImageLoader;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerOrganizationComponent;
import com.seition.cloud.pro.newcloud.home.di.module.OrganizationModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.OrganizationContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.OrganizationOwnerPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.album.PickerSelectAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.user.fragment.AreaMainFragment;
import com.seition.cloud.pro.newcloud.widget.CustomShapeImageView;
import com.seition.cloud.pro.newcloud.widget.decoration.SpacesItemDecoration;
import com.yanzhenjie.album.AlbumFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.seition.cloud.pro.newcloud.app.config.MyConfig.RequestCodeArea;
import static com.seition.cloud.pro.newcloud.app.config.MyConfig.RequestCodeImagePicker_Cover;
import static com.seition.cloud.pro.newcloud.app.config.MyConfig.RequestCodeImagePicker_Logo;
import static com.seition.cloud.pro.newcloud.app.config.MyConfig.RequestCodeImagePicker_Muti_Id;
import static com.seition.cloud.pro.newcloud.app.config.MyConfig.RequestCodeImagePicker_Muti_Organ;
import static com.seition.cloud.pro.newcloud.app.config.MyConfig.RequestCodeOrderReimburseBack;


public class OrganizationApplyforFragment extends BaseBackFragment<OrganizationOwnerPresenter> implements OrganizationContract.OwnerView, CategoryPickPopupWindow.OnDialogItemClickListener {
    @BindView(R.id.o_school)
    TextView o_school;
    @BindView(R.id.o_category_rl)
    RelativeLayout o_category_rl;
    @BindView(R.id.o_cate_name)
    TextView o_cate_name;
    @BindView(R.id.o_name)
    EditText o_name;
    @BindView(R.id.o_identity_number)
    EditText o_identity_number;

    @BindView(R.id.o_phone)
    EditText o_phone;
    @BindView(R.id.o_address)
    EditText o_address;
    @BindView(R.id.o_location)
    TextView o_location;
    @BindView(R.id.o_reson)
    EditText o_reson;
    @BindView(R.id.o_instro)
    EditText o_instro;
    @BindView(R.id.o_logo)
    CustomShapeImageView o_logo;
    @BindView(R.id.o_cover)
    ImageView o_cover;
    @BindView(R.id.o_attach)
    TextView o_attach;

    @BindView(R.id.o_attach_list)
    RecyclerView organization_recycler;

    @BindView(R.id.identity_attach_list)
    RecyclerView identity_recycler;


    @BindView(R.id.submit_first)
    TextView submit_first;
    @BindView(R.id.submit_second)
    TextView submit_second;
    @BindView(R.id.submit_third)
    TextView submit_third;
    @BindView(R.id.submit_four)
    TextView submit_four;
    @BindView(R.id.submit_five)
    TextView submit_five;


    @BindView(R.id.apply_first_step)
    LinearLayout apply_first_step;
    @BindView(R.id.apply_second_step)
    LinearLayout apply_second_step;
    @BindView(R.id.apply_three_step)
    LinearLayout apply_three_step;
    @BindView(R.id.apply_four_step)
    LinearLayout apply_four_step;
    @BindView(R.id.apply_five_step)
    LinearLayout apply_five_step;

    @BindView(R.id.dismiss_the_reason_ll)
    LinearLayout dismiss_the_reason_ll;
    @BindView(R.id.dismiss_the_reason)
    TextView dismiss_the_reason;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    int type = 0;
    HashMap<String, String> logoMap = new HashMap<>();
    private final static String NewLogo = "NewLogo";
    private final static String OldLogo = "OldLogor";

    HashMap<String, String> coverMap = new HashMap<>();
    private final static String NewCover = "NewCover";
    private final static String OldCover = "OldCover";

    HashMap<String, ArrayList<String>> attachMap = new HashMap<>();
    private final static String NewAttach = "NewAttach";
    private final static String OldAttach = "OldAttach";

    HashMap<String, ArrayList<String>> identityMap = new HashMap<>();
    private final static String NewIdentity = "NewIdentity";
    private final static String OldIdentity = "OldIdentity";

    private final static int RightIn = R.anim.slide_in_right;
    private final static int RightOut = R.anim.slide_out_right;
    private final static int LeftIn = R.anim.slide_in_left;
    private final static int LeftOut = R.anim.slide_out_left;

    private ArrayList<AlbumFile> mAlbumFiles;

    OrganizationStatus organizationStatus;
    private String schoolName = "", attach = "", name = "", phone = "", location = "", address = "", reson = ""/*, info = ""*/;
    private String cateId = "", idcard = "", identityId = "", logoId = "", coverId = "";

    CategoryPickPopupWindow categoryPickPopupWindow;
    ArrayList<CommonCategory> commonCategories = new ArrayList<>();

    @OnClick({R.id.toolbar_back, R.id.o_category_rl, R.id.o_logo, R.id.o_cover, R.id.o_attach, R.id.identity_attach, R.id.submit_first, R.id.submit_second, R.id.submit_third, R.id.submit_four, R.id.submit_five, R.id.o_location})
    void openDialog(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                backClick();
                break;
            case R.id.o_category_rl:
                if (commonCategories.size() == 0)
                    mPresenter.getOrganizationCategory(true);
                else
                    categoryPickPopupWindow.showPopAsDropDown(o_category_rl, 0, 0, Gravity.BOTTOM);
                break;
            case R.id.o_logo:
//                selectImage();
                selectCover(1, RequestCodeImagePicker_Logo);
                break;
            case R.id.o_cover:
                selectCover(1, RequestCodeImagePicker_Cover);
                break;
            case R.id.o_attach:
                if (files.size() > 0)
                    openPhotos(RequestCodeImagePicker_Muti_Organ);
                else
                    selectCover(9, RequestCodeImagePicker_Muti_Organ);
//                listPopWindow.showPopAsDropDown(suspension_lay,0 ,0, Gravity.BOTTOM);
                break;
            case R.id.identity_attach:
                if (files.size() > 0)
                    openPhotos(RequestCodeImagePicker_Muti_Id);
                else
                    selectCover(9, RequestCodeImagePicker_Muti_Id);
//                listPopWindow.showPopAsDropDown(suspension_lay,0 ,0, Gravity.BOTTOM);
                break;
            case R.id.submit_first:
                name = o_name.getText().toString().trim();
                idcard = o_identity_number.getText().toString().trim();
                phone = o_phone.getText().toString().trim();
                address = o_address.getText().toString().trim();
                reson = o_reson.getText().toString().trim();

                if ("".equals(cateId)) {
                    showMessage("请选择机构分类");
                    return;
                }
                if ("".equals(name)) {
                    showMessage("机构名称不能为空");
                    return;
                }
                if ("".equals(idcard)) {
                    showMessage("法人身份证不能为空");
                    return;
                }
                if ("".equals(phone)) {
                    showMessage("请输入正确的电话号码");
                    return;
                }
                if ("".equals(location)) {
                    showMessage("机构所在地区不能为空");
                    return;
                }
                if ("".equals(reson)) {
                    showMessage("申请理由不能为空");
                    return;
                }

                apply_first_step.setVisibility(View.GONE);

//                apply_four_step.setVisibility(View.VISIBLE);
                setViewVisibility(apply_four_step, RightIn, View.VISIBLE);
                type = 3;
                SetTitle(3);
                break;
            case R.id.submit_second:
                if ("".equals(logoMap.get(OldLogo)) && "".equals(logoMap.get(NewLogo)))
                    showMessage("请选择机构头像");
                else if ("".equals(logoMap.get(OldLogo)) && !"".equals(logoMap.get(NewLogo)))
                    uploadFiles(filesToMultipartBodyParts(files));
                else if (!"".equals(logoMap.get(OldLogo)) && "".equals(logoMap.get(NewLogo))) {
                    apply_second_step.setVisibility(View.GONE);
                    setViewVisibility(apply_three_step, RightIn, View.VISIBLE);
                    type = 2;
                    SetTitle(2);

                } else if (!"".equals(logoMap.get(OldLogo)) && !"".equals(logoMap.get(NewLogo))) {
                    uploadFiles(filesToMultipartBodyParts(files));
                }

                break;
            case R.id.submit_third:
                if ("".equals(coverMap.get(OldCover)) && "".equals(coverMap.get(NewCover)))
                    showMessage("请选择机构封面");
                else if ("".equals(coverMap.get(OldCover)) && !"".equals(coverMap.get(NewCover)))
                    uploadFiles(filesToMultipartBodyParts(files));
                else if (!"".equals(coverMap.get(OldCover)) && "".equals(coverMap.get(NewCover))) {
                    apply_three_step.setVisibility(View.GONE);
                    setViewVisibility(apply_four_step, RightIn, View.VISIBLE);
                    type = 3;
                    SetTitle(3);
                } else if (!"".equals(coverMap.get(OldCover)) && !"".equals(coverMap.get(NewCover))) {
                    uploadFiles(filesToMultipartBodyParts(files));
                }
                break;
            case R.id.submit_four:
                if (attachMap.get(OldAttach).size() == 0 && attachMap.get(NewAttach).size() == 0)
                    showMessage("请选择要上传的机构附件");
                else if (attachMap.get(OldAttach).size() == 0 && attachMap.get(NewAttach).size() > 0)
                    uploadFiles(filesToMultipartBodyParts(files));
                else if (attachMap.get(OldAttach).size() > 0 && attachMap.get(NewAttach).size() == 0) {
                    apply_four_step.setVisibility(View.GONE);
                    setViewVisibility(apply_five_step, RightIn, View.VISIBLE);
                    type = 4;
                    SetTitle(4);
//                    mPresenter.applyOrganization(name, /*logoId, coverId,*/ cateId, /*info, */idcard, phone, attach, reson, address,identityId);
                } else if (attachMap.get(OldAttach).size() > 0 && attachMap.get(NewAttach).size() > 0) {
                    uploadFiles(filesToMultipartBodyParts(files));
                }

                break;

            case R.id.submit_five:
                if (identityMap.get(OldIdentity).size() == 0 && identityMap.get(NewIdentity).size() == 0)
                    showMessage("请选择要上传的身份证附件");
                else if (identityMap.get(OldIdentity).size() == 0 && identityMap.get(NewIdentity).size() > 0)
                    uploadFiles(filesToMultipartBodyParts(files));
                else if (identityMap.get(OldIdentity).size() > 0 && identityMap.get(NewIdentity).size() == 0) {
                    mPresenter.applyOrganization(name, cateId, idcard, phone, provId
                            , cityId, districtId, attach, reson, location, address, identityId);
                } else if (identityMap.get(OldIdentity).size() > 0 && attachMap.get(NewAttach).size() > 0) {
                    uploadFiles(filesToMultipartBodyParts(files));
                }

                break;
            case R.id.o_location:
                startForResult(AreaMainFragment.newInstance(), RequestCodeArea);
                break;

        }
    }

    String provId;
    String cityId;
    String districtId;

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == RequestCodeArea && resultCode == RESULT_OK && data != null) {
            HashMap map = (HashMap) data.getSerializable("addressInfo");

            location = (map.get("provName").toString().trim() + " " + map.get("cityName").toString().trim() + " " + map.get("districtName").toString()).trim();
            provId = map.get("provId").toString();
            cityId = map.get("cityId").toString();
            districtId = map.get("districtId").toString();
            o_location.setText((map.get("provName").toString() + map.get("cityName").toString() + map.get("districtName").toString()).trim());

        }
    }


    private void setViewVisibility(View view, int animId, int isVisibal) {
        Animation anim = AnimationUtils.loadAnimation(_mActivity, animId);
        LinearInterpolator lir = new LinearInterpolator();
        anim.setInterpolator(lir);
        view.startAnimation(anim);
        view.setVisibility(isVisibal);
    }


    private void SetTitle(int type) {
        switch (type) {
            case 0:
                toolbar_title.setText("填写机构资料");
                break;
            case 1:
                toolbar_title.setText("上传机构头像");
                break;
            case 2:
                toolbar_title.setText("上传机构封面");
                break;
            case 3:
                toolbar_title.setText("上传机构附件");
                break;
            case 4:
                toolbar_title.setText("上传身份证附件");
                break;
        }
    }

    int i = 0;

    @Override
    public void showUploadAttachId(String attach_id) {
        i++;
        if (i == files.size()) {
            materialDialog.dismiss();
            imageItems.clear();
            i = 0;
        }
        if (type == 1) {
            logoId = attach_id;
            logoMap.put(OldLogo, logoMap.get(NewLogo));
            logoMap.put(NewLogo, "");
            submit_second.setText("下一步");
        }
        if (type == 2) {
            coverMap.put(OldCover, coverMap.get(NewCover));
            coverMap.put(NewCover, "");
            coverId = attach_id;
            submit_third.setText("下一步");
        }
        if (type == 3) {
            if ("".equals(attach))
                attach = attach_id;
            else
                attach += "," + attach_id;
            if (i == 0) {
                attachMap.put(OldAttach, attachMap.get(NewAttach));
                attachMap.put(NewAttach, new ArrayList<>());
                submit_four.setText("下一步");
            }


        }
        if (type == 4) {
            if ("".equals(identityId))
                identityId = attach_id;
            else
                identityId += "," + attach_id;
            if (i == 0) {
                identityMap.put(OldIdentity, identityMap.get(NewIdentity));
                identityMap.put(NewIdentity, new ArrayList<>());
                submit_five.setText("提交申请");
            }

        }

    }

    @Override
    public void showCategoryWindows(ArrayList<CommonCategory> commonCategories) {
        this.commonCategories = commonCategories;
        categoryPickPopupWindow = new CategoryPickPopupWindow(_mActivity, commonCategories);
        if (commonCategories.size() == 0)
            categoryPickPopupWindow.setDialogEmptyViewVisibility(true);
        else
            categoryPickPopupWindow.setDialogEmptyViewVisibility(false);
        categoryPickPopupWindow.setOnDialogItemClickListener(this);
        categoryPickPopupWindow.showPopAsDropDown(o_category_rl, 0, 0, Gravity.BOTTOM);
    }

    @Override
    public void onWindowItemClick(Object p) {
        o_cate_name.setText(((CommonCategory) p).getTitle());
        cateId = ((CommonCategory) p).getId();
    }

    public void selectCover(int limit, int requestCode) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setMultiMode(true);
        imagePicker.setShowCamera(true);
        imagePicker.setSelectLimit(limit);
        imagePicker.setCrop(false);
        Intent intent = new Intent(_mActivity, ImageGridActivity.class);
        startActivityForResult(intent, requestCode);
    }


    public static OrganizationApplyforFragment newInstance(OrganizationStatus organizationStatus) {
        OrganizationApplyforFragment fragment = new OrganizationApplyforFragment();
        fragment.setOrganizationStatus(organizationStatus);
        return fragment;
    }

    public void setOrganizationStatus(OrganizationStatus organizationStatus) {
        this.organizationStatus = organizationStatus;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerOrganizationComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .organizationModule(new OrganizationModule(this))
                .build()
                .inject(this);
    }


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_organization_applyfor, container, false);
    }

    private void backClick() {
        switch (type) {
            case 0:
                pop();
                break;
            case 1:
                type = 0;
                setViewVisibility(apply_first_step, LeftIn, View.VISIBLE);
                apply_second_step.setVisibility(View.GONE);
                apply_three_step.setVisibility(View.GONE);
                apply_four_step.setVisibility(View.GONE);

                break;

            case 2:
                type = 1;

                apply_first_step.setVisibility(View.GONE);
                setViewVisibility(apply_second_step, LeftIn, View.VISIBLE);
                apply_three_step.setVisibility(View.GONE);
                apply_four_step.setVisibility(View.GONE);
                break;
            case 3:
                type = 0;
                apply_first_step.setVisibility(View.VISIBLE);
                apply_second_step.setVisibility(View.GONE);
                setViewVisibility(apply_first_step, LeftIn, View.VISIBLE);
                apply_four_step.setVisibility(View.GONE);
                break;
            case 4:
                type = 3;
                apply_four_step.setVisibility(View.VISIBLE);
                apply_second_step.setVisibility(View.GONE);
                setViewVisibility(apply_four_step, LeftIn, View.VISIBLE);
                apply_five_step.setVisibility(View.GONE);
                break;
        }
        SetTitle(type);
    }

    @Override
    public boolean onBackPressedSupport() {
        backClick();
        return true;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle("填写机构资料");
        o_school.setText(PreferenceUtil.getInstance(_mActivity).getString("uname", ""));
        initView();
        organization_recycler.setLayoutManager(new GridLayoutManager(getContext(), 3));// 布局管理器
        identity_recycler.setLayoutManager(new GridLayoutManager(getContext(), 3));// 布局管理器
        //添加ItemDecoration，item之间的间隔
        int leftRight = Utils.dip2px(getActivity(), 5);
        int topBottom = Utils.dip2px(getActivity(), 5);

        organization_recycler.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));
        identity_recycler.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));

        adapter = new PickerSelectAdapter(_mActivity);
        idadapter = new PickerSelectAdapter(_mActivity);
        organization_recycler.addOnItemTouchListener(new RecyclerItemClickListener(_mActivity,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (adapter.getItemViewType(position) == PickerSelectAdapter.TYPE_ADD) {
                            openPhotos(RequestCodeImagePicker_Muti_Organ);

                        } else {
                            Intent intentPreview = new Intent(_mActivity, ImagePreviewDelActivity.class);
                            intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                            intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                            intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                            startActivityForResult(intentPreview, RequestCodeOrderReimburseBack);
                        }
                    }
                }));
        identity_recycler.addOnItemTouchListener(new RecyclerItemClickListener(_mActivity,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (idadapter.getItemViewType(position) == PickerSelectAdapter.TYPE_ADD) {
                            openPhotos(RequestCodeImagePicker_Muti_Id);
                        } else {
                            Intent intentPreview = new Intent(_mActivity, ImagePreviewDelActivity.class);
                            intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                            intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                            intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                            startActivityForResult(intentPreview, RequestCodeOrderReimburseBack);
                        }
                    }
                }));
        organization_recycler.setAdapter(adapter);
        identity_recycler.setAdapter(idadapter);
        showLastCommit();
    }

    //显示上次申请的数据
    public void showLastCommit() {
        if (organizationStatus == null) return;
        dismiss_the_reason_ll.setVisibility(View.VISIBLE);
        dismiss_the_reason.setText(organizationStatus.getRejectInfo());
//        o_name.setText(organizationStatus.getTitle());
//        o_identity_number.setText(organizationStatus.getIdentity_id());
//        o_phone.setText(organizationStatus.getPhone());
//        o_address.setText(organizationStatus.getAddress());
//        o_reson.setText(organizationStatus.getReason());
//        o_instro.setText(organizationStatus.getAbout_us());
//        o_cate_name.setText(organizationStatus.getSchool_category()+"");//???
//        o_location.setText(organizationStatus.getLocation());//???
    }

    /* 如果需要进入选择的时候显示已经选中的图片，
     * 详情请查看ImagePickerActivity
     * */
    public void openPhotos(int requestCode) {
        ImagePicker.getInstance().setSelectLimit(PickerSelectAdapter.MAX);//- imageItems.size()
        Intent intent1 = new Intent(_mActivity, ImageGridActivity.class);
        intent1.putParcelableArrayListExtra(ImageGridActivity.EXTRAS_IMAGES, imageItems);
        startActivityForResult(intent1, requestCode);
    }

    private ArrayList<ImageItem> imageItems = new ArrayList<>();
    PickerSelectAdapter adapter;
    ArrayList<File> files = new ArrayList<>();

    PickerSelectAdapter idadapter;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            ArrayList<ImageItem> items = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (items.size() == 1 && items.get(0).size == 0) {
                imageItems.addAll(items);
            } else {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).size == 0)
                        items.remove(i);
                }
                imageItems.clear();
                imageItems.addAll(items);
            }

            if (imageItems != null && imageItems.size() > 0) {
                files.clear();
                ArrayList<String> attachPathList = new ArrayList<>();
                for (int i = 0; i < imageItems.size(); i++) {
                    files.add(new File(imageItems.get(i).path));
                    attachPathList.add(imageItems.get(i).path);
                }
                if (requestCode == RequestCodeImagePicker_Muti_Organ) {
                    submit_four.setText("上传附件");
                    attachMap.put(NewAttach, attachPathList);
                    adapter.notifyDataSetChanged(imageItems);

                } else if (requestCode == RequestCodeImagePicker_Logo) {
                    logoMap.put(NewLogo, imageItems.get(0).path);
                    submit_second.setText("上传图片");
                    showSelectGroupCover(o_logo, imageItems.get(0).path);
                } else if (requestCode == RequestCodeImagePicker_Cover) {
                    coverMap.put(NewCover, imageItems.get(0).path);
                    submit_third.setText("上传图片");
                    showSelectGroupCover(o_cover, imageItems.get(0).path);
                } else if (requestCode == RequestCodeImagePicker_Muti_Id) {

                    submit_five.setText("上传附件");
                    identityMap.put(NewIdentity, attachPathList);
                    idadapter.notifyDataSetChanged(imageItems);

                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == RequestCodeOrderReimburseBack) {
                imageItems = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (imageItems != null) {
                    files.clear();
                    ArrayList<String> attachPathList = new ArrayList<>();
                    for (int i = 0; i < imageItems.size(); i++) {
                        files.add(new File(imageItems.get(i).path));
                        attachPathList.add(imageItems.get(i).path);
                    }

                    adapter.notifyDataSetChanged(imageItems);
                }
            }
        }
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

    public static List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("face"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
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
            mPresenter.uploadFile(file);

    }

    @Override
    public void setData(Object data) {

    }

    private void initView() {
        logoMap.put(OldLogo, "");
        logoMap.put(NewLogo, "");

        coverMap.put(OldCover, "");
        coverMap.put(NewCover, "");

        attachMap.put(OldAttach, new ArrayList<>());
        attachMap.put(NewAttach, new ArrayList<>());

        identityMap.put(OldIdentity, new ArrayList<>());
        identityMap.put(NewIdentity, new ArrayList<>());
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

    @Override
    public void showOrganizationStatus(OrganizationStatus organizationStatus) {
        this.organizationStatus = organizationStatus;
    }
}
