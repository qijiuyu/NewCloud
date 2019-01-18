package com.seition.cloud.pro.newcloud.home.mvp.ui.owner.bind.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.baidu.idl.face.platform.FaceConfig;
import com.baidu.idl.face.platform.FaceSDKManager;
import com.baidu.idl.face.platform.FaceStatusEnum;
import com.baidu.idl.face.platform.IDetectStrategy;
import com.baidu.idl.face.platform.IDetectStrategyCallback;
import com.baidu.idl.face.platform.ILivenessStrategy;
import com.baidu.idl.face.platform.ILivenessStrategyCallback;
import com.baidu.idl.face.platform.ui.FaceSDKResSettings;
import com.baidu.idl.face.platform.ui.utils.CameraUtils;
import com.baidu.idl.face.platform.ui.utils.VolumeUtils;
import com.baidu.idl.face.platform.ui.widget.FaceDetectRoundView;
import com.baidu.idl.face.platform.utils.APIUtils;
import com.baidu.idl.face.platform.utils.Base64Utils;
import com.baidu.idl.face.platform.utils.CameraPreviewUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.UploadResponse;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerBindManageComponent;
import com.seition.cloud.pro.newcloud.home.di.module.BindManageModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.BindManageContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.BindFaceCheckPresenter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import static com.jess.arms.utils.Preconditions.checkNotNull;

@RuntimePermissions
public class BindFaceChedkActivity extends BaseActivity<BindFaceCheckPresenter> implements BindManageContract.FaceCheckView,SurfaceHolder.Callback, Camera.PreviewCallback,  Camera.ErrorCallback,
        VolumeUtils.VolumeCallback, ILivenessStrategyCallback, IDetectStrategyCallback {
    @BindView(R.id.liveness_root_layout)
    View mRootView;
    @BindView(R.id.liveness_surface_layout)
    FrameLayout mFrameLayout;

    protected SurfaceView mSurfaceView;
    protected SurfaceHolder mSurfaceHolder;

    @BindView(R.id.liveness_close)
    ImageView mCloseView;
    @BindView(R.id.liveness_sound)
    ImageView mSoundView;

    @BindView(R.id.liveness_face_round)
    FaceDetectRoundView mFaceDetectRoundView;

    @BindView(R.id.liveness_success_image)
    ImageView mSuccessView;

    @BindView(R.id.liveness_top_tips)
    TextView mTipsTopView;

    @BindView(R.id.liveness_bottom_tips)
    TextView mTipsBottomView;

    @BindView(R.id.face_create_next)
    TextView face_create_next;

    @BindView(R.id.liveness_result_image_layout)
    LinearLayout mImageLayout;


    // 人脸信息
    protected FaceConfig mFaceConfig;
    protected ILivenessStrategy mILivenessStrategy;
    protected IDetectStrategy mIDetectStrategy;
    // 显示Size
    private Rect mPreviewRect = new Rect();
    protected int mDisplayWidth = 0;
    protected int mDisplayHeight = 0;
    protected int mSurfaceWidth = 0;
    protected int mSurfaceHeight = 0;
    protected Drawable mTipsIcon;
    // 状态标识
    protected volatile boolean mIsEnableSound = true;
    protected HashMap<String, String> mBase64ImageMap = new HashMap<String, String>();
    protected boolean mIsCreateSurface = false;
    protected boolean mIsCompletion = false;
    // 相机
    protected Camera mCamera;
    protected Camera.Parameters mCameraParam;
    protected int mCameraId;
    protected int mPreviewWidth;
    protected int mPreviewHight;
    protected int mPreviewDegree;

    // 监听系统音量广播
    protected BroadcastReceiver mVolumeReceiver;

    @OnClick({R.id.liveness_close,R.id.liveness_sound,R.id.face_create_next})
    void doSomething(View view){
        switch (view.getId()){
            case R.id.liveness_close:
                pop();
                break;
            case R.id.liveness_sound:

                mIsEnableSound = !mIsEnableSound;
                mSoundView.setImageResource(mIsEnableSound ?
                        R.mipmap.voice_open : R.mipmap.voice_close);
                if(cameraType == 0){
                    if (mILivenessStrategy != null) {
                        mILivenessStrategy.setLivenessStrategySoundEnable(mIsEnableSound);
                    }
                }else if(cameraType == 1||cameraType == 2){
                    if (mIDetectStrategy != null) {
                        mIDetectStrategy.setDetectStrategySoundEnable(mIsEnableSound);
                    }
                }
                break;
            case R.id.face_create_next:
                operationType = 4;
                cameraType = 0;
                setLoginFaceView();
                face_create_next.setVisibility(View.GONE);
                break;
        }
    }

    private int cameraType; // 0 :活体检测 1： 人脸识别（ 包括登录，验证，创建）
    private int operationType; //1 :登录 ，2 :验证 ，3 ：创建 4 :人脸 5 :体验刷脸

    /*public static BindFaceChedkActivity newInstance(int OperationType) {
        Bundle args = new Bundle();
        args.putInt("OperationType",OperationType);
        BindFaceChedkActivity fragment = new BindFaceChedkActivity();
        fragment.setArguments(args);
        return fragment;
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
        return inflater.inflate(R.layout.fragment_face_check,container,false);
    }*/

    @Override
    public void onResume() {
        super.onResume();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        mVolumeReceiver = VolumeUtils.registerVolumeReceiver(BindFaceChedkActivity.this, this);
        if (mTipsTopView != null) {
            mTipsTopView.setText(R.string.detect_face_in);
        }
        startPreview();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(cameraType == 0){
            if (mILivenessStrategy != null) {
                mILivenessStrategy.reset();
            }
        }else if(cameraType == 1||cameraType == 2){
            if (mIDetectStrategy != null) {
                mIDetectStrategy.reset();
            }
        }

        VolumeUtils.unRegisterVolumeReceiver(BindFaceChedkActivity.this, mVolumeReceiver);
        mVolumeReceiver = null;
        super.onStop();
        stopPreview();

    }

    @Override
    public void onPause() {
        super.onPause();
        stopPreview();
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerBindManageComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .bindManageModule(new BindManageModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.fragment_face_check;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.bind_face);

        BindFaceChedkActivityPermissionsDispatcher.startPreviewWithPermissionCheck(this);
        initData();
        setSurfaceView();
//        mPresenter.getFaceSaveStatus();
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        BindFaceChedkActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        BindFaceChedkFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }*/

    private void initData(){

        FaceSDKResSettings.initializeResId();

        mFaceConfig = FaceSDKManager.getInstance().getFaceConfig();

        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int vol = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        mIsEnableSound = vol > 0 ? mFaceConfig.isSound : false;

        DisplayMetrics dm = new DisplayMetrics();
        Display display = getWindowManager().getDefaultDisplay();
        display.getMetrics(dm);
        mDisplayWidth = dm.widthPixels;
        mDisplayHeight = dm.heightPixels;

        if (mBase64ImageMap != null) {
            mBase64ImageMap.clear();
        }

        operationType = getIntent().getIntExtra("OperationType",0);
        setCameraType();
    }
    private void setCameraType(){
        if( operationType ==1 || operationType ==2 || operationType ==3 )
            cameraType  = 1;
        else
            cameraType  = 0;
    }

    private void setSurfaceView(){
        mSurfaceView = new SurfaceView(BindFaceChedkActivity.this);
        FrameLayout.LayoutParams cameraFL = new FrameLayout.LayoutParams(
                (int) (mDisplayWidth * FaceDetectRoundView.SURFACE_RATIO), (int) (mDisplayHeight * FaceDetectRoundView.SURFACE_RATIO),
                Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

        mSurfaceView.setLayoutParams(cameraFL);
        mFrameLayout.addView(mSurfaceView);
    }


    private void setLoginFaceView(){

        if (mTipsTopView != null) {
            mTipsTopView.setText(R.string.detect_face_in);
        }
        startPreview();
    }

    private Camera open() {
        Camera camera;
        int numCameras = Camera.getNumberOfCameras();
        if (numCameras == 0) {
            return null;
        }

        int index = 0;
        while (index < numCameras) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(index, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                break;
            }
            index++;
        }

        System.out.println("index------>>>"+index);
        if (index < numCameras) {
            camera = Camera.open(index);
            mCameraId = index;
        } else {
            camera = Camera.open(0);
            mCameraId = 0;
        }
        return camera;
    }

    //    @NeedsPermission(Manifest.permission.CAMERA)
    @NeedsPermission({Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS})
    protected void startPreview() {
        if (mSurfaceView != null && mSurfaceView.getHolder() != null) {
            mSurfaceHolder = mSurfaceView.getHolder();
            mSurfaceHolder.addCallback(this);
        }

        if (mCamera == null) {
            try {
                mCamera = open();
            } catch (RuntimeException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (mCamera == null) {
            return;
        }

        if (mCameraParam == null) {
            mCameraParam = mCamera.getParameters();
        }

        mCameraParam.setPictureFormat(PixelFormat.JPEG);
        int degree = displayOrientation(BindFaceChedkActivity.this);
        mCamera.setDisplayOrientation(degree);
        // 设置后无效，camera.setDisplayOrientation方法有效
        mCameraParam.set("rotation", degree);
        mPreviewDegree = degree;

        Point point = CameraPreviewUtils.getBestPreview(mCameraParam,
                new Point(mDisplayWidth, mDisplayHeight));

        mPreviewWidth = point.x;
        mPreviewHight = point.y;
        // Preview 768,432


        if(cameraType == 0){
            if (mILivenessStrategy != null) {
                mILivenessStrategy.setPreviewDegree(degree);
            }
        }else if(cameraType == 1||cameraType == 2){
            if (mIDetectStrategy != null) {
                mIDetectStrategy.setPreviewDegree(degree);
            }
        }


        mPreviewRect.set(0, 0, mPreviewHight, mPreviewWidth);

        mCameraParam.setPreviewSize(mPreviewWidth, mPreviewHight);
        mCamera.setParameters(mCameraParam);

        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.stopPreview();
            mCamera.setErrorCallback(this);
            mCamera.setPreviewCallback(this);
            mCamera.startPreview();
        } catch (RuntimeException e) {
            e.printStackTrace();
            CameraUtils.releaseCamera(mCamera);
            mCamera = null;
        } catch (Exception e) {
            e.printStackTrace();
            CameraUtils.releaseCamera(mCamera);
            mCamera = null;
        }
    }

    private int displayOrientation(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int rotation = windowManager.getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
            default:
                degrees = 0;
                break;
        }
        int result = (0 - degrees + 360) % 360;
        if (APIUtils.hasGingerbread()) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(mCameraId, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                result = (info.orientation + degrees) % 360;
                result = (360 - result) % 360;
            } else {
                result = (info.orientation - degrees + 360) % 360;
            }
        }
        return result;
    }

    protected void stopPreview() {
        if (mCamera != null) {
            try {
                mCamera.setErrorCallback(null);
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
            } catch (RuntimeException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                CameraUtils.releaseCamera(mCamera);
                mCamera = null;
            }
        }
        if (mSurfaceHolder != null) {
            mSurfaceHolder.removeCallback(this);
        }

        if(cameraType == 0){
            if (mILivenessStrategy != null) {
                mILivenessStrategy = null;
            }
        }else if(cameraType == 1||cameraType == 2){
            if (mIDetectStrategy != null) {
                mIDetectStrategy = null;
            }
        }

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        if(materialDialog!=null)
            materialDialog.dismiss();
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

   /* @Override
    public void showFaceSaveStatus(FaceStatus status) {

    }*/

    @Override
    public void onError(int error, Camera camera) {

    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        System.out.println("onPreviewFrame()  -->>type : "+cameraType+"  mIsCompletion  "+mIsCompletion);
        if (mIsCompletion) {
            return;
        }
        if(cameraType == 0){
            if (mILivenessStrategy == null) {
                mILivenessStrategy = FaceSDKManager.getInstance().getLivenessStrategyModule();
                mILivenessStrategy.setPreviewDegree(mPreviewDegree);
                mILivenessStrategy.setLivenessStrategySoundEnable(mIsEnableSound);

                Rect detectRect = FaceDetectRoundView.getPreviewDetectRect(
                        mDisplayWidth, mPreviewHight, mPreviewWidth);
                mILivenessStrategy.setLivenessStrategyConfig(
                        mFaceConfig.getLivenessTypeList(), mPreviewRect, detectRect, this);//ILivenessStrategyCallback
            }
            mILivenessStrategy.livenessStrategy(data);
        }else
        if(cameraType == 1||cameraType == 2){
            if (mIDetectStrategy == null && mFaceDetectRoundView != null && mFaceDetectRoundView.getRound() > 0) {
                mIDetectStrategy = FaceSDKManager.getInstance().getDetectStrategyModule();
                mIDetectStrategy.setPreviewDegree(mPreviewDegree);
                mIDetectStrategy.setDetectStrategySoundEnable(mIsEnableSound);

                Rect detectRect = FaceDetectRoundView.getPreviewDetectRect(mDisplayWidth, mPreviewHight, mPreviewWidth);
                mIDetectStrategy.setDetectStrategyConfig(mPreviewRect, detectRect, this);//// IDetectStrategyCallback
            }
            if (mIDetectStrategy != null) {
                mIDetectStrategy.detectStrategy(data);
            }
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsCreateSurface = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mSurfaceWidth = width;
        mSurfaceHeight = height;
        if (holder.getSurface() == null) {
            return;
        }
        startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsCreateSurface = false;
    }

    @Override
    public void onDetectCompletion(FaceStatusEnum status, String message, HashMap<String, String> base64ImageMap) {
        if (mIsCompletion) {
            return;
        }
        System.out.println("onDetectCompletion()");
        onRefreshView(status, message);
        if (status == FaceStatusEnum.OK) {
            mIsCompletion = true;
            saveImage(base64ImageMap);
        }else if (status == FaceStatusEnum.Error_DetectTimeout ||
                status == FaceStatusEnum.Error_LivenessTimeout ||
                status == FaceStatusEnum.Error_Timeout) {
            stopPreview();
            showDialog( "操作超时");
        }
    }

    private void showDialog(String message){
        new MaterialDialog.Builder(BindFaceChedkActivity.this)
                .content(message)
                .positiveText("再试一次")
                .negativeText("退出")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        setLoginFaceView();
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

    @Override
    public void onLivenessCompletion(FaceStatusEnum status, String message, HashMap<String, String> base64ImageMap) {
        if (mIsCompletion) {
            return;
        }
        onRefreshView(status, message);

        if (status == FaceStatusEnum.OK) {
            System.out.println("onLivenessCompletion()"+operationType);

            mIsCompletion = true;
            if(operationType == 5){
//                setFragmentResult(MyConfig.ResultCodeFaceTest,null);
                setResult(MyConfig.ResultCodeFaceTest,null);
                killMyself();
            }else {
                saveImage(base64ImageMap);
            }
        }
   /*     if (status == FaceStatusEnum.OK && mIsCompletion) {
            // showMessageDialog("活体检测", "检测成功");
            saveImage(base64ImageMap);
        }*/
        else if (status == FaceStatusEnum.Error_DetectTimeout ||
                status == FaceStatusEnum.Error_LivenessTimeout ||
                status == FaceStatusEnum.Error_Timeout) {
            stopPreview();

            showDialog( "操作超时");
        }
    }

    @Override
    public void volumeChanged() {
        try {
            AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            if (am != null) {
                int cv = am.getStreamVolume(AudioManager.STREAM_MUSIC);
                mIsEnableSound = cv > 0;
                mSoundView.setImageResource(mIsEnableSound
                        ? R.mipmap.voice_open : R.mipmap.voice_close);

                if(cameraType == 0){
                    if (mILivenessStrategy != null) {
                        mILivenessStrategy.setLivenessStrategySoundEnable(mIsEnableSound);
                    }
                }else if(cameraType == 1||cameraType == 2){
                    if (mIDetectStrategy != null) {
                        mIDetectStrategy.setDetectStrategySoundEnable(mIsEnableSound);
                    }
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void onRefreshView(FaceStatusEnum status, String message) {
        switch (status) {

            case OK:
                if(cameraType == 0){
                }else if(cameraType == 1||cameraType == 2){
                    onRefreshTipsView(false, message);
                    mTipsBottomView.setText("");
                    mFaceDetectRoundView.processDrawState(false);
                    onRefreshSuccessView(true);
                    break;
                }
            case Liveness_OK:
            case Liveness_Completion:
                onRefreshTipsView(false, message);
                mTipsBottomView.setText("");
                mFaceDetectRoundView.processDrawState(false);
                onRefreshSuccessView(true);
                break;
            case Detect_DataNotReady:
            case Liveness_Eye:
            case Liveness_Mouth:
//            case Liveness_HeadUp:
//            case Liveness_HeadDown:
//            case Liveness_HeadLeft:
//            case Liveness_HeadRight:
//            case Liveness_HeadLeftRight:
                onRefreshTipsView(false, message);
                mTipsBottomView.setText("");
                mFaceDetectRoundView.processDrawState(false);
                onRefreshSuccessView(false);
                break;
            case Detect_PitchOutOfUpMaxRange:
            case Detect_PitchOutOfDownMaxRange:
            case Detect_PitchOutOfLeftMaxRange:
            case Detect_PitchOutOfRightMaxRange:
                onRefreshTipsView(true, message);
                mTipsBottomView.setText(message);
                mFaceDetectRoundView.processDrawState(true);
                onRefreshSuccessView(false);
                break;
            default:
                onRefreshTipsView(false, message);
                mTipsBottomView.setText("");
                mFaceDetectRoundView.processDrawState(true);
                onRefreshSuccessView(false);
        }
    }

    private void onRefreshTipsView(boolean isAlert, String message) {
        if (isAlert) {
            if (mTipsIcon == null) {
                mTipsIcon = getResources().getDrawable(R.mipmap.ic_warning);
                mTipsIcon.setBounds(0, 0, (int) (mTipsIcon.getMinimumWidth() * 0.7f),
                        (int) (mTipsIcon.getMinimumHeight() * 0.7f));
                mTipsTopView.setCompoundDrawablePadding(15);
            }
            mTipsTopView.setBackgroundResource(R.drawable.bg_tips);
            mTipsTopView.setText(R.string.detect_standard);
            mTipsTopView.setCompoundDrawables(mTipsIcon, null, null, null);
        } else {
            mTipsTopView.setBackgroundResource(R.drawable.bg_tips_no);
            mTipsTopView.setCompoundDrawables(null, null, null, null);
            if (!TextUtils.isEmpty(message)) {
                mTipsTopView.setText(message);
            }
        }
    }

    private void onRefreshSuccessView(boolean isShow) {
        if (mSuccessView.getTag() == null) {
            Rect rect = mFaceDetectRoundView.getFaceRoundRect();
            RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) mSuccessView.getLayoutParams();
            rlp.setMargins(
                    rect.centerX() - (mSuccessView.getWidth() / 2),
                    rect.top - (mSuccessView.getHeight() / 2),
                    0,
                    0);
            mSuccessView.setLayoutParams(rlp);
            mSuccessView.setTag("setlayout");
        }
        mSuccessView.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
    }

    private void saveImage(HashMap<String, String> imageMap) {
        Set<Map.Entry<String, String>> sets = imageMap.entrySet();
        Bitmap bmp = null;
        mImageLayout.removeAllViews();
        for (Map.Entry<String, String> entry : sets) {
            bmp = base64ToBitmap(entry.getValue());
            ImageView iv = new ImageView(BindFaceChedkActivity.this);
            iv.setImageBitmap(bmp);
            mImageLayout.addView(iv, new LinearLayout.LayoutParams(300, 300));
        }
        mFaceTask = new FaceTask(imageMap);
        mFaceTask.execute();

    }
    private String attach_id ;
    private int uploadPhotoCount = 0;
    @Override
    public void showUploadAttachId(UploadResponse response) {


        switch (operationType){
            case 1:
                attach_id = response.getAttach_id();
                faceLogin(attach_id);
                break;
            case 2:
                attach_id = response.getAttach_id();
                materialDialog.setContent("正在验证人脸");
                faceVerify(attach_id);
                break;
            case 3:
                attach_id = response.getAttach_id();
                materialDialog.setContent("正在创建人物");
                faceCreate(attach_id);
                break;
            case 4:
                if ("".equals(attach_id) || attach_id == null) {
                    attach_id = response.getAttach_id();
                } else {
                    attach_id += "," + response.getAttach_id();
                }
                uploadPhotoCount++;

                if (uploadPhotoCount == files.size()) {
                    materialDialog.setContent("正在创建人脸");
                    faceAdd(attach_id);
                }
                break;
        }
    }

    private void faceLogin(String attach_id) {
        mPresenter.faceLogin(attach_id);
    }
    private void faceVerify(String attach_id) {
        mPresenter.faceVerify(attach_id);
    }
    private void faceAdd(String attach_id) {
        mPresenter.faceAdd(attach_id);
    }
    private void faceCreate(String attach_id) {
        mPresenter.faceCreate(attach_id);
    }

    @Override
    public void faceLogin(boolean success) {
        if(success) {
            stopPreview();
            deleteCameraPhoto(parentfile);
            setResult(MyConfig.ResultCodeFaceCheck,null);
            killMyself();
//            files.remove(file);
        }
        else {
            deleteCameraPhoto(parentfile);
//            files.remove(file);
            mIsCompletion = false;
            stopPreview();
        }
    }

    @Override
    public void faceVerify(boolean success) {
        if(success) {

            deleteCameraPhoto(parentfile);
//            files.remove(file);
//            setFragmentResult(MyConfig.ResultCodeFaceTest,null);
            setResult(MyConfig.ResultCodeFaceCheck,null);
            killMyself();
        }
        else {
            deleteCameraPhoto(parentfile);
//            files.remove(file);
            mIsCompletion = false;
            stopPreview();
        }
    }

    @Override
    public void faceCreated(boolean success) {
        if(success) {
            face_create_next.setVisibility(View.VISIBLE);
            mTipsTopView.setText("人物创建成功");
            deleteCameraPhoto(parentfile);
//            files.remove(file);
            mIsCompletion = false;
            stopPreview();
//            setFragmentResult(MyConfig.ResultCodeFaceCheck,null);
//            killMyself();
        }
        else {
            deleteCameraPhoto(parentfile);
//            files.remove(file);
            mIsCompletion = false;
            stopPreview();
        }
    }

    @Override
    public void faceAdd(boolean success) {
        if(success) {
            deleteCameraPhoto(parentfile);
        }
        else {
            deleteCameraPhoto(parentfile);
            mIsCompletion = false;
            stopPreview();
        }
    }

    private class FaceTask extends AsyncTask<Void, Integer, Void> {

        HashMap<String, String> imageMap;
        FaceTask(HashMap<String, String> imageMap){
            this.imageMap = imageMap;
        }

        @Override
        protected Void doInBackground(Void... objects) {
            Set<Map.Entry<String, String>> sets = imageMap.entrySet();
            for (Map.Entry<String, String> entry : sets) {
                Bitmap bmp = base64ToBitmap(entry.getValue());
                String name = "face_"+entry.hashCode();
                SaveCameraPhoto(bmp,name);
            }

            handler.sendEmptyMessage(2);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
//            System.out.println("保存进度"+values[0]);
//            if(values[0] == 100)
//                Toast.makeText(FaceCheckNewActivity.this,"保存完成",Toast.LENGTH_SHORT).show();
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            uploadFiles(filesToMultipartBodyParts(files));
//            post_imgs(photoPaths);
        }
    };

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
        materialDialog = new MaterialDialog.Builder(BindFaceChedkActivity.this)
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

    List<File> files = new ArrayList<>();
    //    private static final String faceSavePath = "/storage/emulated/0/Eduline_Face/";
    File parentfile = null;//new File(faceSavePath);

    public void SaveCameraPhoto(Bitmap bmp,String name){

        try {
//        File file1 = File.createTempFile(UUID.randomUUID().toString(), ".jpg");
//        String  sd= "";

            String ss = Environment.getExternalStorageDirectory().getAbsolutePath();
//        String  filePath = faceSavePath+name+".jpg";
            String parentPath = ss+"/Eduline_Face/";
            parentfile = new File(parentPath);
            String  filePath = parentPath+name+".jpg";

            File file = new File(filePath);
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdir();//创建文件夹
            }

            BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(file));
//            System.out.println(bmp.getWidth()+",,_1_,,"+ bmp.getHeight()+"oriention"+oriention);
            bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight());
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, bos);//向缓冲区压缩图片
//            bmp.recycle();

            bos.flush();
            bos.close();
            files.add(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteCameraPhoto(File file){

        if(file.isFile()){
            file.delete();
            return;
        }
        if(file.isDirectory()){
            File[] childFile = file.listFiles();
            if(childFile == null || childFile.length == 0){
                file.delete();
                return;
            }
            for(File f : childFile){
                deleteCameraPhoto(f);
            }
            file.delete();
        }
    }

    protected Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64Utils.decode(base64Data, Base64Utils.NO_WRAP);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private FaceTask mFaceTask;
//    private ArrayList<String > photoPaths = new ArrayList<>();
}