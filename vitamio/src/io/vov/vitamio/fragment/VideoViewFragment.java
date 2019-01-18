package io.vov.vitamio.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnPageChangeListener;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.FileCallback;
import com.lzy.okhttputils.request.BaseRequest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import io.vov.vitamio.Config;
import io.vov.vitamio.DBVideoBean;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnInfoListener;
import io.vov.vitamio.R;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.listener.MyVideoViewListener;
import io.vov.vitamio.listener.MyVideoViewListener.OnFullScreenListener;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

@SuppressLint("ValidFragment")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class VideoViewFragment extends MBaseFragment implements OnPageChangeListener {
    private static VideoViewFragment self;
    private Context mContext;
    private VideoView mVideoView;
    private ProgressBar pb;
    private FrameLayout video_fl;
    private TextView tv_buy;
    private TextView downloadRateView;
    private TextView loadRateView;
    //    private WebView webview;
    private PDFView pdfView;
    private LinearLayout video;
    private LinearLayout media_ll;
    private ImageView start;
    private TextView media_time;
    private SeekBar seek_bar;
    private TextView txt;
    private TextView quan;
    private TextView page;
    private ScrollView sv;
    private boolean isquanping;
    private MediaController mc;
    private DBVideoBean dvb;
    long[] mHits = new long[2];
    private int playTime;//可播放时长
    private boolean isPlay = false;
    private boolean isBuy = false;
    private int play_time = 0;//已播放时长
    private long time;//已播放时长
    private boolean isVideoFirst = false;
    boolean isPause = false;
    private MyVideoViewListener.OnFullScreenListener onFullScreenListener;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
        /*    time = mVideoView.getCurrentPosition() / 1000;
            if ((dvb != null && !dvb.is_free()) && !"1".equals(is_play_all)) {
                if (!isBuy && time >= playTime) {
                    tv_buy.setVisibility(View.VISIBLE);
                    mVideoView.stopPlayback();
                    isPlay = false;
                } else {
                    play_time++;
                    if (onFullScreenListener != null && dvb != null && dvb.getType() == 1 && time != 0 &&
                            play_time % 8 == 0)
                        onFullScreenListener.addRecode(dvb.getTid(), time);
                    if (isPlay)
                        handler.sendEmptyMessageDelayed(0, 1000);
                }
            }*/

            time = mVideoView.getCurrentPosition() / 1000;
            if (dvb != null && !dvb.is_free()) {
                if (isBuy || dvb.isBuy()) ; //已购买的不操作
                else if (time >= playTime) {
                    tv_buy.setVisibility(View.VISIBLE);
                    pb.setVisibility(View.GONE);
                    mVideoView.stopPlayback();
                    setPlay(false);
                }
            }
            play_time++;
            if (onFullScreenListener != null && dvb != null && dvb.getType() == 1 && time != 0 &&
                    play_time % 8 == 0)
                onFullScreenListener.addRecode(dvb.getTid(), time);
            if (isPlay && (mVideoView.getDuration() / 1000 > time))
                handler.sendEmptyMessageDelayed(0, 1000);
        }
    };

    public VideoViewFragment(Context c) {
        this.mContext = c;
        Vitamio.isInitialized(mContext.getApplicationContext());
    }

    public static VideoViewFragment getInstance(Context c) {
        return new VideoViewFragment(c);
    }

    @Override
    public void onDestroyView() {
//        if (webview != null) webview.destroy();
        super.onDestroyView();
        play_time = 0;
        setPlay(false);
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }

    public void setIsBuy(boolean isBuy) {
        this.isBuy = isBuy;
    }

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getFragmentName() {
        return "VideoViewFragment";
    }

    @Override
    public void initView() {
        mVideoView = (VideoView) findId(R.id.buffer);
//        webview = (WebView) findId(R.id.wv);
        video = (LinearLayout) findId(R.id.video);
        pdfView = (PDFView) findId(R.id.pdfView);
        txt = (TextView) findId(R.id.txt);
        quan = (TextView) findId(R.id.quan);
        page = (TextView) findId(R.id.page);
        sv = (ScrollView) findId(R.id.sv);
        pb = (ProgressBar) findId(R.id.probar);
        video_fl = (FrameLayout) findId(R.id.video_fl);
        tv_buy = (TextView) findId(R.id.tv_buy);
        downloadRateView = (TextView) findId(R.id.download_rate);
        loadRateView = (TextView) findId(R.id.load_rate);

        media_ll = (LinearLayout) findId(R.id.media_ll);
        media_time = (TextView) findId(R.id.media_time);
        start = (ImageView) findId(R.id.start);
        seek_bar = (SeekBar) findId(R.id.seek_bar);

        setMVideoView();
//        initWebView();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initListener() {
        mVideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                checkQuan();
                return false;
            }
        });
        quan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                quan();
            }
        });
        txt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                checkQuan();
            }
        });
        start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMediaPlayer != null)
                    if (mMediaPlayer.isPlaying()) {
                        mediaPause();
                    } else {
                        mMediaPlayer.start();
                        Message msg = new Message();
                        msg.what = 1;
                        msg.arg1 = mMediaPlayer.getDuration();
                        msg.arg2 = mMediaPlayer.getCurrentPosition();
                        mediaTimeHandler.sendMessage(msg);
                    }
            }
        });
        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mMediaPlayer != null && mMediaPlayer.isPlaying())
                    mMediaPlayer.seekTo(seekBar.getProgress());
            }
        });
//        txt.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                checkQuan();
//                return false;
//            }
//        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.video_view_fragment;
    }

    @SuppressLint("NewApi")
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        try {
            if (mVideoView != null) {
                mVideoView.stopPlayback();
                mVideoView.destroyDrawingCache();
            }
            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer.release();
            }
            if (mc != null)
                mc.destroyDrawingCache();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mediaPause() {
        if (mMediaPlayer != null) {
            try {
                mMediaPlayer.pause();
            } catch (Exception e) {
                e.printStackTrace();
            }
            isMediaPlay = false;
            start.setImageResource(R.drawable.ic_media_play);
        }
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
//        setPlay(false);
        mediaPause();
        isPause = true;
        if (mVideoView != null)
            mVideoView.pause();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        setPlay(true);
//        if (mVideoView != null)
//            if (dvb != null && dvb.getType() == 1)
//                mVideoView.start();
//        if (mMediaPlayer != null) mMediaPlayer.start();
    }

    private void checkQuan() {
        Log.i("info", "quan");
        if (onFullScreenListener == null)
            return;
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis() - 500))
            quan();
    }

    int height = 0;
    boolean isFirst = true;

    private void quan() {
        if (isFirst) {
            height = video_fl.getHeight();
            isFirst = false;
        }
        if (isquanping) {
            txt.setHeight(height);
            onFullScreenListener.onFullScreenListener(false);
            quan.setText("全屏");
        } else {
            onFullScreenListener.onFullScreenListener(true);
            quan.setText("取消全屏");
            txt.setHeight(video_fl.getHeight());
        }
        isquanping = !isquanping;
    }

    private void setMVideoView() {
        mc = new MediaController(mContext.getApplicationContext(), true, video_fl);
        mVideoView.setMediaController(mc);
        mc.setAnchorView(mVideoView);
        mVideoView.setOnInfoListener(new MyInfoListener());
        mVideoView.setOnBufferingUpdateListener(new MyBufferingUpdateListener());
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mediaPlayer) {
                mediaPlayer.setPlaybackSpeed(1.0f);
                handler.sendEmptyMessage(0);
            }
        });
        mc.setVisibility(View.GONE);
        isStart = true;
    }

    private boolean isStart;
    private String path;

    public void setIsStart(boolean isStart) {
        this.isStart = isStart;
    }

    public void setOnPreparedListener(MediaPlayer.OnPreparedListener listener) {
        mVideoView.setOnPreparedListener(listener);
    }

    public void setOnFullScreenListener(OnFullScreenListener listener) {
        this.onFullScreenListener = listener;
    }

    private void stop() {
        if (!isStart) return;
        pb.setVisibility(View.GONE);
        tv_buy.setVisibility(View.GONE);
        //视频
//        video_fl.setVisibility(View.GONE);
        video.setVisibility(View.GONE);
        sv.setVisibility(View.GONE);
        mc.setVisibility(View.GONE);
        quan.setVisibility(View.GONE);
        mVideoView.stopPlayback();
        mVideoView.setVideoPath("");
        downloadRateView.setText("");
        loadRateView.setText("");
        downloadRateView.setVisibility(View.GONE);
        loadRateView.setVisibility(View.GONE);
        //音频
        try {
            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        media_ll.setVisibility(View.GONE);
        //文档
        pdfView.setVisibility(View.GONE);
        page.setVisibility(View.GONE);
        //文本
        txt.setVisibility(View.GONE);
        setPlay(false);
    }

    public void setPlay(boolean play) {
        if (play && !isPlay) {
            isPlay = play;
            handler.sendEmptyMessage(0);
        } else isPlay = play;
    }

    public void setData(DBVideoBean dvb) {
        this.dvb = dvb;
        stop();
        if (dvb != null)
            time_handler.sendEmptyMessage(0);
        else Toast.makeText(mContext, "没有资源！", Toast.LENGTH_SHORT).show();
    }

    private boolean isMediaPlay = false;
    @SuppressLint("HandlerLeak")
    private Handler mediaTimeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                isMediaPlay = true;
                seek_bar.setMax(msg.arg1);
                seek_bar.setProgress(msg.arg2);
                start.setImageResource(R.drawable.ic_media_pause);
            } else if (mMediaPlayer != null) {
                try {

                    seek_bar.setProgress(mMediaPlayer.getCurrentPosition());
                    media_time.setText(getDate(mMediaPlayer.getCurrentPosition() / 1000) + " / " + getDate(mMediaPlayer.getDuration() / 1000));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (isMediaPlay) mediaTimeHandler.sendEmptyMessageDelayed(0, 200);
        }
    };

    public String getDate(int time) {
        StringBuilder sb = new StringBuilder();
        //时
//        if (time > 3600) {
//            long h = time / 3600;
//            time %= 3600;
//            if (h < 10) sb.append("0");
//            sb.append(h);
//        } else sb.append("00");
//        sb.append(":");
        if (time > 60) {
            long m = time / 60;
            time %= 60;
            if (m < 10) sb.append("0");
            sb.append(m);
        } else sb.append("00");
        sb.append(":");
        if (time < 10) sb.append("0");
        sb.append(time);
        return sb.toString();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //屏幕切换时，设置全屏
        if (mVideoView != null) {
            mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
        }
        super.onConfigurationChanged(newConfig);
    }

    android.media.MediaPlayer mMediaPlayer;

    private void play() {
//        Log.i("info", "dvb.getType() = " + dvb.getType() + "   , dvb.isExist() = " + dvb.isExist() + "   , dvb.getPath() = " + dvb.getPath() + "   , dvb.getExtensions() = " + dvb.getExtensions() + "   , dvb.getUri() = " + dvb.getUri());
        time = 0;
        switch (dvb.getType()) {
            case 0://视频
            case 1://视频
                pb.setVisibility(View.VISIBLE);
                video.setVisibility(View.VISIBLE);
                downloadRateView.setVisibility(View.VISIBLE);
                loadRateView.setVisibility(View.VISIBLE);
                isVideoFirst = true;
                if (dvb.isExist())
                    mVideoView.setVideoPath(dvb.getPath() + dvb.getExtensions());
                else
                    mVideoView.setVideoPath(dvb.getUri());
                setPlay(true);
                break;
            case 2://音频
                if (!isBuy && !dvb.is_free()) {
                    tv_buy.setVisibility(View.VISIBLE);
                    return;
                }
                media_ll.setVisibility(View.VISIBLE);
//                webview.setVisibility(View.VISIBLE);
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Referer", Config.HEADER);
//                webview.loadUrl(dvb.getUri(), headers);
                mMediaPlayer = new android.media.MediaPlayer();
                try {
                    mMediaPlayer.setDataSource(mContext, Uri.parse(dvb.getUri()), headers);
                    mMediaPlayer.setOnPreparedListener(new android.media.MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(android.media.MediaPlayer mediaPlayer) {
                            if (mediaPlayer == null) return;
                            Message msg = new Message();
                            msg.what = 1;
                            msg.arg1 = mediaPlayer.getDuration();
                            msg.arg2 = mediaPlayer.getCurrentPosition();
                            mediaTimeHandler.sendMessage(msg);
                            mediaPlayer.start();
                        }
                    });
                    mMediaPlayer.setOnCompletionListener(new android.media.MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(android.media.MediaPlayer mediaPlayer) {
                            int i = 0;
                        }
                    });
                    mMediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 3://文本
                if (!isBuy && !dvb.is_free()) {
                    tv_buy.setVisibility(View.VISIBLE);
                    return;
                }
                txt.setVisibility(View.VISIBLE);
                sv.setVisibility(View.VISIBLE);
                setTxt(dvb.getUri());
                break;
            case 4://文档
                if (!isBuy && !dvb.is_free()) {
                    tv_buy.setVisibility(View.VISIBLE);
                    return;
                }
                pdfView.setVisibility(View.VISIBLE);
                quan.setVisibility(View.VISIBLE);
                page.setVisibility(View.VISIBLE);
                String file;
                pdfView.recycle();
                if (dvb.isExist()) {
                    showPdf(new File(dvb.getPath() + dvb.getExtensions()));
                } else if (new File(Environment.getExternalStorageDirectory().toString() + "/CloudCourses/file", dvb.getName() + dvb.getTid() + ".pdf").exists()) {
                    showPdf(new File(Environment.getExternalStorageDirectory().toString() + "/CloudCourses/file", dvb.getName() + dvb.getTid() + ".pdf"));
                } else
                    OkHttpUtils.get(dvb.getUri())//
                            .tag(this)//
                            .execute(new DownloadFileCallBack(Environment.getExternalStorageDirectory().toString() + "/CloudCourses/file", dvb.getName() + dvb.getTid() + ".pdf"));//保存到sd卡
                break;
        }
    }

    private void showPdf(File path) {
        pdfView.fromFile(path)
                //                .pages(0, 0, 0, 0, 0, 0) // 默认全部显示，pages属性可以过滤性显示
                .defaultPage(1)//默认展示第一页
                .onPageChange(this)//监听页面切换
                .load();
    }

    private class DownloadFileCallBack extends FileCallback {


        public DownloadFileCallBack(String destFileDir, String destFileName) {
            super(destFileDir, destFileName);
        }


        @Override
        public void onBefore(BaseRequest request) {
            txt.setVisibility(View.VISIBLE);
            sv.setVisibility(View.VISIBLE);
            txt.setText("\n\n\n文档加载中\n请稍后...");
        }

        @Override
        public void onResponse(boolean isFromCache, File file, Request request, Response response) {
//            show_pdf.setVisibility(View.VISIBLE);
            txt.setVisibility(View.GONE);
            if (file.exists()) {
                showPdf(file);
//                showPdf(file);
            } else
                Toast.makeText(mContext, "文件下载失败,请给我文件存储权限", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
//            System.out.println("downloadProgress -- " + totalSize + "  " + currentSize + "  " + progress + "  " + networkSpeed);
        }


        @Override
        public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
            super.onError(isFromCache, call, response, e);
            txt.setText("加载失败！");
        }
    }

    @Override
    public void onPageChanged(int position, int all) {
        //页数
        page.setText(position + "/" + all);
    }

    private void setTxt(String str) {
        txt.setText(Html.fromHtml(str));
    }

    private Handler time_handler = new Handler() {
        public void handleMessage(Message msg) {
            if (isStart) {
                play();
            } else {
                time_handler.sendEmptyMessageDelayed(0, 500);
//                time_handler.sendMessageDelayed(msg, 500);
            }
        }
    };

    public static int dip2px(float dpValue, Context context) {

        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(float pxValue, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public void mNotFullScreen(RelativeLayout.LayoutParams layoutParams) {
        Window window = getActivity().getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        RelativeLayout.LayoutParams fl_lp = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dip2px(200, getActivity())
        );

        video_fl.setLayoutParams(fl_lp);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mVideoView.requestLayout();
        txt.requestLayout();
        pdfView.requestLayout();
    }

    public int getHeightPixel(Activity activity) {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        return localDisplayMetrics.heightPixels;
    }

    public int getWidthPixel(Activity activity) {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        return localDisplayMetrics.widthPixels;
    }

    public int getStatusBarHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);

        int statusBarHeight = frame.top;
        return statusBarHeight;
    }

    public void mFullScreen(RelativeLayout.LayoutParams layoutParams) {
        Window window = getActivity().getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        int height = getHeightPixel(getActivity());
        int weight = getWidthPixel(getActivity());
        RelativeLayout.LayoutParams fl_lp = new RelativeLayout.LayoutParams(
                getHeightPixel(getActivity()),
                getWidthPixel(getActivity())/* - getStatusBarHeight(getActivity())*/
        );

        video_fl.setLayoutParams(fl_lp);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
    }

    class MyInfoListener implements OnInfoListener {

        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            // TODO Auto-generated method stub
            switch (what) {
                case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                    if (mVideoView.isPlaying()) {
                        mVideoView.pause();
                        pb.setVisibility(View.VISIBLE);
                        downloadRateView.setText("");
                        loadRateView.setText("");
                        downloadRateView.setVisibility(View.VISIBLE);
                        loadRateView.setVisibility(View.VISIBLE);
                        setPlay(true);
                    } else {
                        mVideoView.setBackgroundResource(R.color.transparent);
                    }
                    break;
                case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                    if (isVideoFirst) {
                        isPause = false;
                        isVideoFirst = false;
                    }
                    if (isPause) {
                        isPause = false;
                        mVideoView.pause();
                    } else
                        mVideoView.start();
                    mc.setVisibility(View.GONE);
                    pb.setVisibility(View.GONE);
                    downloadRateView.setVisibility(View.GONE);
                    loadRateView.setVisibility(View.GONE);
//                    isPlay = false;
                    break;
                case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                    downloadRateView.setText("" + extra + "kb/s" + "  ");
//                    setPlay(false);
                    break;
            }
            return true;
        }
    }

    class MyBufferingUpdateListener implements OnBufferingUpdateListener {

        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            // TODO Auto-generated method stub
            pb.setVisibility(View.GONE);
            downloadRateView.setVisibility(View.GONE);
            loadRateView.setVisibility(View.GONE);
            loadRateView.setText(percent + "%");
        }

    }

}
