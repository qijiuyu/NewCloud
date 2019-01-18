package com.seition.cloud.pro.newcloud.home.mvp.ui.owner.order.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.PayResponse;
import com.seition.cloud.pro.newcloud.app.bean.FragmentBean;
import com.seition.cloud.pro.newcloud.app.bean.money.BalanceDetails;
import com.seition.cloud.pro.newcloud.app.bean.order.Order;
import com.seition.cloud.pro.newcloud.app.bean.order.OrderRefund;
import com.seition.cloud.pro.newcloud.app.listener.RecyclerItemClickListener;
import com.seition.cloud.pro.newcloud.app.popupwindow.BasePopWindow;
import com.seition.cloud.pro.newcloud.app.popupwindow.ListPopWindow;
import com.seition.cloud.pro.newcloud.app.utils.GlideImageLoader;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.app.config.Service;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerOrderComponent;
import com.seition.cloud.pro.newcloud.home.di.module.OrderModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.OrderContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.OrderPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.WebActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.album.PickerSelectAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.view.ShowAgreement;
import com.seition.cloud.pro.newcloud.widget.decoration.SpacesItemDecoration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.seition.cloud.pro.newcloud.app.config.MyConfig.RequestCodeOrderReimburseBack;
import static com.seition.cloud.pro.newcloud.app.config.MyConfig.RequestCodeOrderReimburseCamera;
import static com.seition.cloud.pro.newcloud.app.config.MyConfig.RequestCodeOrderReimburseMuti;

/**
 * Created by addis on 2017/3/8.
 */

public class OrderReimburseFragment extends BaseBackFragment<OrderPresenter> implements OrderContract.View, ListPopWindow.OnDialogItemClickListener {

    @BindView(R.id.protocol)
    TextView protocol;

    @BindView(R.id.cover)
    ImageView cover;
    @BindView(R.id.refund_title)
    TextView refund_title;
    @BindView(R.id.refund_price)
    TextView refund_price;

    @BindView(R.id.refund_money)
    TextView refund_money;
    @BindView(R.id.refund_way)
    TextView refund_way;

    @BindView(R.id.refund_reason_rl)
    RelativeLayout refund_reason_rl;
    @BindView(R.id.refund_reason_txt)
    TextView refund_reason_txt;
    @BindView(R.id.refund_declare)
    EditText refund_declare;
    @BindView(R.id.refund_submit)
    Button refund_submit;
    @BindView(R.id.ok)
    CheckBox ok;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.theme_publish_grid_wow)
    RecyclerView recyclerView;
    PickerSelectAdapter adapter;

    private Order order;
    private int order_id;
    private int order_type;
    private String resonId = "";

    public static OrderReimburseFragment newInstance(Order order) {
        Bundle args = new Bundle();
        args.putSerializable("order", order);
        OrderReimburseFragment fragment = new OrderReimburseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerOrderComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .orderModule(new OrderModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reimburse, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle("申请退款");
        initMaterialDialog();
        initView();
        ((AppCompatActivity) _mActivity).setSupportActionBar(toolbar);
    }

    @Override
    public void setData(Object data) {

    }


    @OnClick({R.id.protocol, R.id.refund_reason_rl, R.id.camera, R.id.album, R.id.refund_submit})
    void onClicked(View view) {
        switch (view.getId()) {
            case R.id.protocol:
                ShowAgreement.showAgreement(_mActivity, getLayoutInflater(), ok, "refund");
//                launchActivity(new Intent(_mActivity, WebActivity.class)
//                        .putExtra("url", Service.DOMAIN_NAME_DAFENGCHE_SHARE + "app=api&mod=Public&act=single&key=tuikuan")
//                        .putExtra("title", getString(R.string.procotol_name))
//                );
                break;
            case R.id.refund_reason_rl:
                listPopWindow.showPopAsDropDown(refund_reason_rl, 0, 0, Gravity.BOTTOM);
                break;
            case R.id.camera:
                openCamear();
                break;
            case R.id.album:
                if (files.size() > 0)
                    openPhotos();
                else
                    openPhoto();
                break;
            case R.id.refund_submit:
                refund_note = refund_declare.getText().toString().trim();
                if (!ok.isChecked()) {
                    showMessage(getString(R.string.procotol5));
                    return;
                }

                if ("".equals(resonId)) {
                    showMessage("请选择退款原因！");
                    return;
                }
                if ("".equals(refund_note)) {
                    showMessage("请填写退款说明！");
                    return;
                }
                postReimburse();
                break;
        }
    }


    private void initView() {
        order = (Order) getArguments().getSerializable("order");
        order_id = order.getId();

        if (order.getOrder_type() == 3)
            order_type = 2;
        else if (order.getOrder_type() == 4)
            order_type = 0;
        else if (order.getOrder_type() == 5)
            order_type = 3;

        if (order_type == 0 || order_type == 2 || order.getOrder_type() == 5)
            loadData(order_type, order_id);
        else
            showMessage("该类商品无法退款");

        if (order.getOrder_type() == 5) {
            refund_title.setText(order.getVideo_name());
            refund_price.setText("¥ " + order.getPrice() + "");
//            course_name.setText(order.getLine_class().getCourse_name());
            GlideLoaderUtil.LoadImage(_mActivity, order.getCover(), cover);
        } else {
            String title = order.getVideo_name();
            if (order.getCourse_hour_id() > 0)
                title += "——" + order.getCourse_hour_title();
            refund_title.setText(title);
            refund_price.setText("¥ " + order.getPrice() + "");
//            course_name.setText(order.getSource_info().getVideo_title());
            GlideLoaderUtil.LoadImage(_mActivity, order.getCover(), cover);
        }

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));// 布局管理器
//        recyclerView.setAdapter(adapter);
        //添加ItemDecoration，item之间的间隔
        int leftRight = Utils.dip2px(getActivity(), 5);
        int topBottom = Utils.dip2px(getActivity(), 5);

        recyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));


        adapter = new PickerSelectAdapter(_mActivity);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(_mActivity,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (adapter.getItemViewType(position) == PickerSelectAdapter.TYPE_ADD) {
                            openPhotos();
                        } else {
                            Intent intentPreview = new Intent(_mActivity, ImagePreviewDelActivity.class);
                            intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                            intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                            intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                            startActivityForResult(intentPreview, RequestCodeOrderReimburseBack);
                        }
                    }
                }));
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.GONE);
    }

    ListPopWindow listPopWindow;

    private void initPopup(String day) {
        listPopWindow = new ListPopWindow(_mActivity, null, 1);
        listPopWindow.addItemDatas("讲师不专业");
        listPopWindow.addItemDatas("课程不是想学的");
        listPopWindow.addItemDatas(day + "天无理由退款");
        listPopWindow.addItemDatas("其他原因");
        listPopWindow.setOnDialogItemClickListener(this);
    }

    private MaterialDialog materialDialog;

    public void initMaterialDialog() {
        materialDialog = new MaterialDialog.Builder(_mActivity)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .canceledOnTouchOutside(false).build();
    }

    private void loadData(int order_type, int order_id) {
        if (materialDialog != null)
            materialDialog.setContent("正在获取课程信息");
        mPresenter.refundOrderInfo(order_type, order_id);
    }


    @Override
    public void showFragment(ArrayList<FragmentBean> fragmenList) {

    }

    @Override
    public void setDatas(ArrayList<Order> orders, boolean pull) {

    }

    @Override
    public void showDialog(BalanceDetails balanceDetails) {

    }

    @Override
    public void showPayResult(PayResponse data) {

    }

    @Override
    public void showRefundOrderInfo(OrderRefund info) {
        initPopup(info.getRefundConfig());
        //   原支付途径（支付宝）
        refund_way.setText("原支付途径（" + info.getPay_type() + "）");
        refund_money.setText("¥ " + info.getPrice());
        refund_price.setText("¥ " + info.getPrice());
        refund_submit.setBackgroundResource(R.drawable.shape_frame_theme);
        refund_submit.setEnabled(true);
    }

    @Override
    public void reload() {

    }

    int i = 0;

    @Override
    public void showUploadAttachId(String attach_id) {
        i++;
        if (i == files.size()) {
            i = 0;
            files.clear();
        }
        if ("".equals(voucher))
            voucher += attach_id;
        else
            voucher += "," + attach_id;
        if (i == files.size()) postReimburse();

    }

    @Override
    public void notificationListData() {

    }


    @Override
    public void showLoading() {
        materialDialog.show();
    }

    @Override
    public void hideLoading() {
        if (materialDialog != null)
            materialDialog.dismiss();
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

    private String refund_note = "";
    private String voucher = "";//上传图片成功后返回的id组合而成的字符串

    private void postReimburse() {
        if (files.size() > 0 && voucher.equals(""))
            showDialog();
        else {
            materialDialog.setContent("正在申请退款....");
            mPresenter.orderRefund(order_type, order_id, resonId, refund_note, voucher);
        }
    }

    private void showDialog() {
        new MaterialDialog.Builder(_mActivity)
                .content("你选择的文件未上传，需要上传吗?")
                .positiveText("确定")
                .negativeText("直接退款")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                        uploadFiles(filesToMultipartBodyParts(files));
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                        materialDialog.setContent("正在申请退款....");
                        mPresenter.orderRefund(order_type, order_id, resonId, refund_note, voucher);
                    }
                })
                .show();
    }

    public void openCamear() {
        Intent intent = new Intent(_mActivity, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, RequestCodeOrderReimburseCamera);

    }

    public void openPhoto() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setMultiMode(true);
        imagePicker.setShowCamera(true);
        imagePicker.setSelectLimit(9);
        imagePicker.setCrop(false);
        Intent intent = new Intent(_mActivity, ImageGridActivity.class);
        startActivityForResult(intent, RequestCodeOrderReimburseMuti);

    }

    /* 如果需要进入选择的时候显示已经选中的图片，
     * 详情请查看ImagePickerActivity
     * */
    public void openPhotos() {
        ImagePicker.getInstance().setSelectLimit(PickerSelectAdapter.MAX);//- imageItems.size()
        Intent intent1 = new Intent(_mActivity, ImageGridActivity.class);
        intent1.putParcelableArrayListExtra(ImageGridActivity.EXTRAS_IMAGES, imageItems);
        startActivityForResult(intent1, RequestCodeOrderReimburseMuti);
    }


    @Override
    public void onDialogItemClick(BasePopWindow popWindow, String type, int position) {
        resonId = position + 1 + "";
        refund_reason_txt.setText(type);
    }

    private ArrayList<ImageItem> imageItems = new ArrayList<>();
    ArrayList<File> files = new ArrayList<>();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (requestCode == RequestCodeOrderReimburseCamera) {
                ArrayList<ImageItem> items = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
//                if (imageItems.size() > 0)
//                    addImageItem(items);
//                else
                imageItems.addAll(items);
            } else if (requestCode == RequestCodeOrderReimburseMuti) {

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
            }
            if (imageItems != null && imageItems.size() > 0) {
                files.clear();
                ArrayList<String> attachPathList = new ArrayList<>();
                for (int i = 0; i < imageItems.size(); i++) {
                    files.add(new File(imageItems.get(i).path));
                    attachPathList.add(imageItems.get(i).path);
                }
                recyclerView.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged(imageItems);

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

    private void addImageItem(ArrayList<ImageItem> items) {
        for (int i = 0; i < items.size(); i++) {
            compareImageItem(items.get(i));
        }
    }

    private void compareImageItem(ImageItem item) {
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < imageItems.size(); i++) {
            names.add(imageItems.get(i).path);
        }
        if (!names.contains(item.path))
            imageItems.add(item);

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

    public void uploadFiles(List<MultipartBody.Part> files) {
        materialDialog.setContent("正在上传图片");
        showLoading();
        LogUtils.debugInfo("上传的图片 files.size()" + files.size());
        for (MultipartBody.Part file : files)
            mPresenter.uploadFile(file);
    }

}
