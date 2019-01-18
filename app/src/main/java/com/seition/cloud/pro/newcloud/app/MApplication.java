package com.seition.cloud.pro.newcloud.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.baidu.idl.face.platform.FaceConfig;
import com.baidu.idl.face.platform.FaceEnvironment;
import com.baidu.idl.face.platform.FaceSDKManager;
import com.baidu.idl.face.platform.LivenessTypeEnum;
import com.jess.arms.base.BaseApplication;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.okhttputils.OkHttpUtils;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.face.APIService;
import com.seition.cloud.pro.newcloud.app.face.FaceAiConfig;
import com.seition.cloud.pro.newcloud.app.face.exception.FaceError;
import com.seition.cloud.pro.newcloud.app.face.model.AccessToken;
import com.seition.cloud.pro.newcloud.app.face.utils.OnResultListener;
import com.seition.cloud.pro.newcloud.app.gen.DaoMaster;
import com.seition.cloud.pro.newcloud.app.gen.DaoSession;
import com.seition.cloud.pro.newcloud.app.gen.GreenDaoContext;
import com.seition.cloud.pro.newcloud.app.utils.GlideImageLoader;
import com.seition.cloud.pro.newcloud.app.utils.MediaLoader;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.home.api.Cache;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by addis on 2018/3/28.
 */

public class MApplication extends BaseApplication {

    private static MApplication instance;
    private static WeakReference<Context> contexts;
    public static MApplication getInstance() {
        if (instance == null) {
            instance = new MApplication();
        }
        return instance;
    }

    private static DaoSession daoSession;

    private static String mCodedLock = "2506957b1ea89b71";

    public static String getCodedLock() {
        return PreferenceUtil.getInstance(instance).getString(MyConfig.Config_McryptKey,mCodedLock);
    }

    public static void setCodedLock(String codedLock) {
        mCodedLock = codedLock;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        setupDatabase();
        initDownload();
        initUMeng();
        initImagePicker();
        OkHttpUtils.init(this);
        InitFaceAiConfig();
        initVH();
        initCCxbk();
    }

    public void initImagePicker(){
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setMultiMode(true);
        imagePicker.setShowCamera(true);
        imagePicker.setSelectLimit(1);
        imagePicker.setCrop(false);

        Album.initialize(AlbumConfig.newBuilder(this)
                .setAlbumLoader(new MediaLoader())
                .setLocale(Locale.getDefault())
                .build()
        );
    }

    public static Context getContext() {
        return contexts == null ? null : contexts.get();
    }

    private void initCCxbk() {
        if (contexts == null) {
            contexts = new WeakReference<Context>(this);
        }

//        AudioManager audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
//        audioManager.setMode(AudioManager.MODE_NORMAL);
//        audioManager.setSpeakerphoneOn(true);
//        MyBroadcastReceiver.getInstance().initial(audioManager);
//        CCInteractSDK.init(this, false);
    }

    public void initVH() {
//        VhallSDK.init(this, "8938dbb1a7af148ae2ff5ee0f734b5b0", "a48b30b197667458ccefdce53af66d09");
//        VhallSDK.setLogEnable(true);
    }

    public void initUMeng() {

        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(true);
        try {
            Class<?> aClass = Class.forName("com.umeng.commonsdk.UMConfigure");
            Field[] fs = aClass.getDeclaredFields();
            for (Field f : fs) {
//                Log.e("xxxxxx", "ff=" + f.getName() + "   " + f.getType().getName());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        UMConfigure.init(this, "5a12384aa40fa3551f0001d1"
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");

        PlatformConfig.setWeixin("wxbbb961a0b0bf577a", "7ea0101aeabd53bc32859370cde278cc");
        //注意代码这里配置的回调地址需要和微博开放平台授权回调页保持一致
        PlatformConfig.setSinaWeibo("3997129963", "da07bcf6c9f30281e684f8abfd0b4fca", "http://sns.eduline.com/sina2/callback");
        // 新浪微博 appkey appsecret
        PlatformConfig.setQQZone("101400042", "a85c2fcd67839693d5c0bf13bec84779");
    }

    /**
     * 配置数据库
     */
    public void setupDatabase() {
        //创建数据库"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(new GreenDaoContext(this), Cache.Cache_DB_NAME, null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    private void initDownload() {
        // just for open the log in this demo project.
        FileDownloadLog.NEED_LOG = true;

        /**
         * just for cache Application's Context, and ':filedownloader' progress will NOT be launched
         * by below code, so please do not worry about performance.
         * @see FileDownloader#init(Context)
         */
        FileDownloader.setupOnApplicationOnCreate(this)
                .connectionCreator(new FileDownloadUrlConnection
                        .Creator(new FileDownloadUrlConnection.Configuration()
                        .connectTimeout(15_000) // set connection timeout.
                        .readTimeout(15_000) // set read timeout.
                ))
                .commit();
    }

    private void InitFaceAiConfig() {
        FaceSDKManager.getInstance().initialize(this, FaceAiConfig.licenseID, FaceAiConfig.licenseFileName);
        FaceConfig config = FaceSDKManager.getInstance().getFaceConfig();
        // SDK初始化已经设置完默认参数（推荐参数），您也根据实际需求进行数值调整
        // 设置活体动作，通过设置list LivenessTypeEnum.Eye，LivenessTypeEnum.Mouth，LivenessTypeEnum.HeadUp，
        // LivenessTypeEnum.HeadDown，LivenessTypeEnum.HeadLeft, LivenessTypeEnum.HeadRight,
        // LivenessTypeEnum.HeadLeftOrRight
        List<LivenessTypeEnum> livenessList = new ArrayList<>();
        livenessList.add(LivenessTypeEnum.Eye);
        livenessList.add(LivenessTypeEnum.Mouth);
        livenessList.add(LivenessTypeEnum.HeadLeftOrRight);
        // livenessList.add(LivenessTypeEnum.HeadUp);
        // livenessList.add(LivenessTypeEnum.HeadDown);
        // livenessList.add(LivenessTypeEnum.HeadLeft);
        // livenessList.add(LivenessTypeEnum.HeadRight);
        config.setLivenessTypeList(livenessList);
//		 设置 活体动作是否随机 boolean
        // config.setLivenessRandom(true);
        // 模糊度范围 (0-1) 推荐小于0.7
        config.setBlurnessValue(FaceEnvironment.VALUE_BLURNESS);
        // 光照范围 (0-1) 推荐大于40
        config.setBrightnessValue(FaceEnvironment.VALUE_BRIGHTNESS);
        // 裁剪人脸大小
        config.setCropFaceValue(FaceEnvironment.VALUE_CROP_FACE_SIZE);
        // 人脸yaw,pitch,row 角度，范围（-45，45），推荐-15-15
        config.setHeadPitchValue(FaceEnvironment.VALUE_HEAD_PITCH);
        config.setHeadRollValue(FaceEnvironment.VALUE_HEAD_ROLL);
        config.setHeadYawValue(FaceEnvironment.VALUE_HEAD_YAW);
        // 最小检测人脸（在图片人脸能够被检测到最小值）80-200， 越小越耗性能，推荐120-200
        config.setMinFaceSize(FaceEnvironment.VALUE_MIN_FACE_SIZE);
        // 人脸置信度（0-1）推荐大于0.6
        config.setNotFaceValue(FaceEnvironment.VALUE_NOT_FACE_THRESHOLD);
        // 人脸遮挡范围 （0-1） 推荐小于0.5
        config.setOcclusionValue(FaceEnvironment.VALUE_OCCLUSION);
        // 是否进行质量检测
        config.setCheckFaceQuality(true);
        // 人脸检测使用线程数
        config.setFaceDecodeNumberOfThreads(2);
        // 是否开启提示音
        config.setSound(true);

        FaceSDKManager.getInstance().setFaceConfig(config);


        APIService.getInstance().init(this);
        APIService.getInstance().setGroupId(FaceAiConfig.groupID);
        // 用ak，sk获取token, 调用在线api，如：注册、识别等。为了ak、sk安全，建议放您的服务器，
        APIService.getInstance().initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
//                Log.i("wtf", "AccessToken->" + result.getAccessToken());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
//						Toast.makeText(ChuYouApp.this, "启动成功", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onError(FaceError error) {
                Log.e("xx", "AccessTokenError:" + error);
                error.printStackTrace();

            }
        }, this, FaceAiConfig.apiKey, FaceAiConfig.secretKey);
    }

    private Handler handler = new Handler(Looper.getMainLooper());

    public static DaoSession getDaoInstant() {
        return daoSession;
    }
}
