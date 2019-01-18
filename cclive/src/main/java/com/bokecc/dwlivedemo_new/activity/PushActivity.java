package com.bokecc.dwlivedemo_new.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bokecc.dwlivedemo_new.R;
import com.bokecc.dwlivedemo_new.R2;
import com.bokecc.dwlivedemo_new.adapter.ChatAdapter;
import com.bokecc.dwlivedemo_new.adapter.EmojiAdapter;
import com.bokecc.dwlivedemo_new.adapter.PrivateChatAdapter;
import com.bokecc.dwlivedemo_new.adapter.PrivateUserAdapter;
import com.bokecc.dwlivedemo_new.anim.OnAnimEndListener;
import com.bokecc.dwlivedemo_new.base.BaseActivity;
import com.bokecc.dwlivedemo_new.contract.PushContract;
import com.bokecc.dwlivedemo_new.global.Config;
import com.bokecc.dwlivedemo_new.module.ChatEntity;
import com.bokecc.dwlivedemo_new.module.PrivateUser;
import com.bokecc.dwlivedemo_new.popup.CommonPopup;
import com.bokecc.dwlivedemo_new.popup.LoadingPopup;
import com.bokecc.dwlivedemo_new.presenter.PushPresenter;
import com.bokecc.dwlivedemo_new.recycle.BaseOnItemTouch;
import com.bokecc.dwlivedemo_new.recycle.OnClickListener;
import com.bokecc.dwlivedemo_new.util.EmojiUtil;
import com.bokecc.dwlivedemo_new.util.SoftKeyBoardState;
import com.bokecc.sdk.mobile.push.chat.model.ChatUser;
import com.bokecc.sdk.mobile.push.core.DWPushConfig;
import com.bokecc.sdk.mobile.push.core.DWPushSession;
import com.bokecc.sdk.mobile.push.view.DWTextureView;

import java.net.URLDecoder;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class PushActivity extends BaseActivity implements PushContract.View {

    View mRoot;
    @BindView(R2.id.id_push_gl_surface)
    DWTextureView mTextureView;
    @BindView(R2.id.id_push_pusher_name)
    TextView mUserName;
    @BindView(R2.id.id_push_online_count)
    TextView mRoomCount;
    @BindView(R2.id.id_push_beauty)
    ImageView mBeauty;
    @BindView(R2.id.id_push_voice)
    ImageView mVoice;
    @BindView(R2.id.id_push_oper_layout)
    RelativeLayout mOperLayout;
    @BindView(R2.id.id_private_chat_user_layout)
    LinearLayout mPrivateChatUserLayout;
    @BindView(R2.id.id_push_chat_layout)
    RelativeLayout mChatLayout;
    @BindView(R2.id.id_push_chat_input)
    EditText mInput;
    @BindView(R2.id.id_push_chat_emoji)
    ImageView mEmoji;
    @BindView(R2.id.id_push_emoji_grid)
    GridView mEmojiGrid;
    @BindView(R2.id.id_push_chat_list)
    RecyclerView mChatList;
    @BindView(R2.id.id_push_private_chat)
    ImageView mPrivateIcon;
    @BindView(R2.id.id_private_chat_msg_layout)
    LinearLayout mPrivateChatMsgLayout;
    @BindView(R2.id.id_private_chat_user_list)
    RecyclerView mPrivateChatUserList;
    @BindView(R2.id.id_private_chat_msg_mask)
    FrameLayout mPrivateChatMsgMask;
    @BindView(R2.id.id_private_chat_title)
    TextView mPrivateChatUserName;
    @BindView(R2.id.id_private_chat_list)
    RecyclerView mPrivateChatMsgList;
    @BindView(R2.id.id_push_mask_layer)
    FrameLayout mMaskLayer;

    private int mLayoutId;
    private PushPresenter mPushPresenter;

    // 软键盘
    private InputMethodManager mImm;
    // 软键盘是否显示
    private boolean isSoftInput = false;
    // emoji是否需要显示 emoji是否显示
    private boolean isEmoji = false, isEmojiShow = false;
    // 聊天是否显示
    private boolean isChat = false;
    // 是否是私聊
    private boolean isPrivate = false;
    // 是否显示私聊用户列表
    private boolean isPrivateChatUser = false;
    // 是否显示私聊列表
    private boolean isPrivateChatMsg = false;
    private String mCurPrivateUserId = "";
    // 公聊数据适配器
    private ChatAdapter mChatAdapter;
    // 私聊用户列表适配器
    private PrivateUserAdapter mPrivateUserAdapter;
    // 私聊信息列表
    private PrivateChatAdapter mPrivateChatAdapter;
    private ChatUser mTo; // 私聊对象
    private ArrayList<ChatEntity> mPrivateChats; // 存放所有的私聊信息

    // 是否开启美颜 是否静音
    private boolean isBeauty = true, isMute = false;
    // 软键盘监听
    private SoftKeyBoardState mSoftKeyBoardState;

    private CommonPopup mExitPopup;
    private LoadingPopup mLoadingPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPushPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPushPresenter.onPause();
        mMaskLayer.setVisibility(View.GONE);
        if (isSoftInput) {
            mImm.hideSoftInputFromWindow(mInput.getWindowToken(), 0);
        }
        hideChatLayot();
        hideEmoji();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPushPresenter.onDestory();
        if (mSoftKeyBoardState != null) {
            mSoftKeyBoardState.release();
        }
    }

    @Override
    protected void beforeSetContentView() {
        Bundle bundle = getIntent().getExtras();
        boolean isVertical = bundle.getBoolean(Config.PUSH_ORIENTATION);
        if (isVertical) {
            mLayoutId = R.layout.activity_push;
        } else {
            mLayoutId = R.layout.activity_push_land;
        }
    }

    @Override
    protected int getLayoutId() {
        return mLayoutId;
    }

    @Override
    public void onBindPresenter() {
        mPushPresenter = new PushPresenter(this, this);
    }

    @Override
    protected void onViewCreated() {
        mRoot = getWindow().getDecorView().findViewById(android.R.id.content);
        Bundle bundle = getIntent().getExtras();
        boolean isVertical = bundle.getBoolean(Config.PUSH_ORIENTATION);
        isBeauty = bundle.getBoolean(Config.PUSH_BEAUTY);
        int cameraPos = bundle.getInt(Config.PUSH_CAMERA);
        int resolutionPos = bundle.getInt(Config.PUSH_RESOLUTION);
        int bitrateValue = bundle.getInt(Config.PUSH_BITRATE);
        int fpsValue = bundle.getInt(Config.PUSH_FPS);
        int serverPos = bundle.getInt(Config.PUSH_SERVER);
        // 保持屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        DWPushConfig pushConfig = new DWPushConfig.DWPushConfigBuilder().
                fps(fpsValue).
                bitrate(bitrateValue).
                orientation(isVertical ? DWPushConfig.PORTRAIT : DWPushConfig.LANDSCAPE).
                cameraType(cameraPos == 0 ? DWPushConfig.CAMERA_FRONT : DWPushConfig.CAMERA_BACK).
                videoResolution(resolutionPos == 0 ? DWPushConfig.RESOLUTION_LOW :
                        resolutionPos == 1 ? DWPushConfig.RESOLUTION_SD : DWPushConfig.RESOLUTION_HD).
                beauty(isBeauty).
                rtmpNodeIndex(serverPos).
                build();
        if (pushConfig.orientation == DWPushConfig.PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        mPushPresenter.setTextureView(mTextureView);
        mPushPresenter.start(pushConfig);
        mPushPresenter.loopForChatCount();

        mInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideEmoji();
                return false;
            }
        });
        mUserName.setText(URLDecoder.decode(DWPushSession.getInstance().getUserName()));
        mRoomCount.setText(String.format("%s人", 0));
        EmojiAdapter emojiAdapter = new EmojiAdapter(this);
        emojiAdapter.bindData(EmojiUtil.imgs);
        mEmojiGrid.setAdapter(emojiAdapter);
        mEmojiGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == EmojiUtil.imgs.length - 1) {
                    mPushPresenter.deleteInputOne(mInput);
                } else {
                    mPushPresenter.addEmoji(mInput, position);
                }
            }
        });

        mPrivateChats = new ArrayList<>(); // 初始化私聊数据集合

        mChatList.setLayoutManager(new LinearLayoutManager(this));
        mChatAdapter = new ChatAdapter(this);
        mChatList.setAdapter(mChatAdapter);
        mChatList.addOnItemTouchListener(new BaseOnItemTouch(mChatList, new OnClickListener() {
            @Override
            public void onClick(RecyclerView.ViewHolder viewHolder) {
                int position = mChatList.getChildAdapterPosition(viewHolder.itemView);
                ChatEntity chatEntity = mChatAdapter.getChatEntities().get(position);
                click2PrivateChat(chatEntity, false);
            }
        }));

        mPrivateChatUserList.setLayoutManager(new LinearLayoutManager(this));
        mPrivateUserAdapter = new PrivateUserAdapter(this);
        mPrivateChatUserList.setAdapter(mPrivateUserAdapter);
        mPrivateChatUserList.addOnItemTouchListener(new BaseOnItemTouch(mPrivateChatUserList, new OnClickListener() {
            @Override
            public void onClick(RecyclerView.ViewHolder viewHolder) {
                // 隐藏用户列表
                mMaskLayer.setVisibility(View.GONE);
                mPrivateChatUserLayout.setVisibility(View.GONE);
                isPrivateChatUser = false;
                int position = mPrivateChatUserList.getChildAdapterPosition(viewHolder.itemView);
                PrivateUser privateUser = mPrivateUserAdapter.getPrivateUsers().get(position);
                privateUser.setRead(true);
                mPrivateUserAdapter.notifyDataSetChanged();
                if (isAllPrivateChatRead()) {
                    mPrivateIcon.setImageResource(R.drawable.push_private_msg);
                }
                ChatEntity chatEntity = new ChatEntity();
                chatEntity.setUserId(privateUser.getId());
                chatEntity.setUserName(privateUser.getName());
                chatEntity.setUserAvatar(privateUser.getAvatar());
                click2PrivateChat(chatEntity, true);
            }
        }));

        mPrivateChatMsgList.setLayoutManager(new LinearLayoutManager(this));
        mPrivateChatAdapter = new PrivateChatAdapter(this);
        mPrivateChatMsgList.setAdapter(mPrivateChatAdapter);
        mPrivateChatMsgList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideEmoji();
                if (isSoftInput) {
                    mImm.hideSoftInputFromWindow(mInput.getWindowToken(), 0);
                }
                return true;
            }
        });

        initLoadingPopup();
        initClosePopup();
        onSoftInputChange();

    }

    /**
     * 点击发起私聊
     */
    private void click2PrivateChat(ChatEntity chatEntity, boolean flag) {
        if (flag) { // 私聊用户列表点击发起私聊
            goPrivateChat(chatEntity);
            mCurPrivateUserId = chatEntity.getUserId();
        } else {
            if (!chatEntity.isPublisher()) { // 如果当前被点击的用户不是主播，则进行私聊
                goPrivateChat(chatEntity);
                mCurPrivateUserId = chatEntity.getUserId();
            }
        }
    }

    /**
     * 跳转私聊
     */
    private void goPrivateChat(ChatEntity chatEntity) {
        isPrivate = true;
        mTo = null;
        mTo = new ChatUser();
        mTo.setUserId(chatEntity.getUserId());
        mTo.setUserName(chatEntity.getUserName());
        ArrayList<ChatEntity> toChatEntitys = new ArrayList<>();
        for (ChatEntity entity : mPrivateChats) {
            // 从私聊列表里面读取到 当前发起私聊的俩个用户聊天列表
            if (entity.getUserId().equals(chatEntity.getUserId()) ||
                    entity.getReceiveUserId().equals(chatEntity.getUserId())) {
                toChatEntitys.add(entity);
            }
        }
        mPrivateChatAdapter.setDatas(toChatEntitys);
        showPrivateChatMsgList(chatEntity.getUserName());
    }

    /**
     * 判断是否所有私聊信息全部读完
     */
    private boolean isAllPrivateChatRead() {
        int i = 0;
        for (; i < mPrivateUserAdapter.getPrivateUsers().size(); i++) {
            if (!mPrivateUserAdapter.getPrivateUsers().get(i).isRead()) {
                break;
            }
        }
        return i >= mPrivateUserAdapter.getPrivateUsers().size();
    }

    private void initLoadingPopup() {
        mLoadingPopup = new LoadingPopup(this);
        mLoadingPopup.setKeyBackCancel(false);
        mLoadingPopup.setOutsideCancel(false);
    }

    private void initClosePopup() {
        mExitPopup = new CommonPopup(this);
        mExitPopup.setOutsideCancel(true);
        mExitPopup.setKeyBackCancel(true);
        mExitPopup.setTip("您确认结束直播吗?");
        mExitPopup.setOKClickListener(new CommonPopup.OnOKClickListener() {
            @Override
            public void onClick() {
                mPushPresenter.stop();
                finish();
            }
        });
    }

    private void onSoftInputChange() {
        mImm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        mSoftKeyBoardState = new SoftKeyBoardState(
                mRoot, false);
        mSoftKeyBoardState.setOnSoftKeyBoardStateChangeListener(new SoftKeyBoardState.OnSoftKeyBoardStateChangeListener() {
            @Override
            public void onChange(boolean isShow) {
                isSoftInput = isShow;
                if (!isSoftInput) { // 软键盘隐藏
                    if (isEmoji) {
                        mEmojiGrid.setVisibility(View.VISIBLE);// 避免闪烁
                        isEmojiShow = true; // 修改emoji显示标记
                        isEmoji = false; // 重置
                    } else {
                        hideChatLayot(); // 隐藏聊天操作区域
                    }
                    if (isPrivateChatMsg && !isEmojiShow) { // 私聊软键盘隐藏时，显示公聊列表
                        mChatList.setVisibility(View.VISIBLE);
                    }
                } else {
                    hideEmoji();
                    if (isPrivateChatMsg) { // 私聊进行消息输入的时候隐藏公聊列表
                        mChatList.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @OnClick(R2.id.id_push_close)
    void close() { // 关闭
        mExitPopup.show(mRoot);
    }

    @OnClick(R2.id.id_push_beauty)
    void toggleBeauty() { // 美颜切换
        if (isBeauty) {
            mBeauty.setImageResource(R.drawable.push_beauty_close);
        } else {
            mBeauty.setImageResource(R.drawable.push_beauty_open);
        }
        isBeauty = !isBeauty;
        mPushPresenter.toggleBeauty(isBeauty);
    }

    @OnClick(R2.id.id_push_voice)
    void toggleVoice() { // 静音切换
        if (isMute) {
            mVoice.setImageResource(R.drawable.push_voice_open);
        } else {
            mVoice.setImageResource(R.drawable.push_voice_close);
        }
        isMute = !isMute;
        mPushPresenter.toggleVolume(isMute);
    }

    @OnClick(R2.id.id_push_camera)
    void swapCamera() { // 切换相机
        mPushPresenter.swapCamera();
    }

    @OnClick(R2.id.id_push_private_chat)
    void openPrivateChatUserList() { // 显示私聊用户列表
        showPrivateChatUserList();
    }

    @OnClick(R2.id.id_private_chat_user_close)
    void closePrivateChatUserList() { // 关闭私聊用户列表
        hidePrivateChatUserList();
    }

    @OnClick(R2.id.id_push_chat)
    void chat() { // 发起公聊
        isPrivate = false; // 修改私聊标记
        showChatLayout();
    }

    @OnClick(R2.id.id_private_chat_close)
    void closePrivate() { // 关闭私聊
        hidePrivateChatMsgList();
    }

    @OnClick(R2.id.id_push_chat_emoji)
    void emoji() {
        showEmoji();
    }

    @OnClick(R2.id.id_push_chat_send)
    void sendMsg() { // 发送聊天
        if (isPrivate) {
            mPushPresenter.sendChatMsg(mInput.getText().toString(), mTo);
        } else {
            mPushPresenter.sendChatMsg(mInput.getText().toString(), null);
        }
    }

    @OnClick(R2.id.id_private_chat_back)
    void backChatUser() { // 返回私聊用户列表
        if (isSoftInput) {
            mImm.hideSoftInputFromWindow(mInput.getWindowToken(), 0);
        }
        hidePrivateChatMsgList();
        showPrivateChatUserList();
    }

    @OnClick(R2.id.id_push_mask_layer)
    void dismissAll() {
        mMaskLayer.setVisibility(View.GONE);
        if (isSoftInput) {
            mImm.hideSoftInputFromWindow(mInput.getWindowToken(), 0);
        }
        hideChatLayot();
        hideEmoji();
        hidePrivateChatUserList();
        hidePrivateChatMsgList();
    }

    /**
     * 显示私聊用户列表
     */
    private void showPrivateChatUserList() {
        mOperLayout.setVisibility(View.GONE); // 隐藏推流操作
        mChatLayout.setVisibility(View.GONE); // 隐藏聊天操作
        mMaskLayer.setVisibility(View.VISIBLE);
        mPrivateChatUserLayout.setVisibility(View.VISIBLE); // 显示用户列表
        isPrivateChatUser = true;
    }

    /**
     * 隐藏私聊用户
     */
    private void hidePrivateChatUserList() {
        if (isPrivateChatUser) {
            mMaskLayer.setVisibility(View.GONE);
            mOperLayout.setVisibility(View.VISIBLE);
            mChatLayout.setVisibility(View.INVISIBLE);
            mPrivateChatUserLayout.setVisibility(View.GONE);
            isPrivateChatUser = false;
        }
    }

    public void showChatLayout() {
        isChat = true;
        mMaskLayer.setVisibility(View.VISIBLE);
        mOperLayout.setVisibility(View.INVISIBLE);
        mChatLayout.setVisibility(View.VISIBLE);
        mInput.setFocusableInTouchMode(true);
        mInput.requestFocus();
        mImm.showSoftInput(mInput, 0);
    }

    public void hideChatLayot() {
        if (isChat) {
            AlphaAnimation animation = new AlphaAnimation(0f, 1f);
            animation.setDuration(300L);
            mOperLayout.startAnimation(animation);
            mOperLayout.setVisibility(View.VISIBLE);
            mInput.setFocusableInTouchMode(false);
            mInput.clearFocus();
            mMaskLayer.setVisibility(View.GONE);
            mChatLayout.setVisibility(View.INVISIBLE);
            isChat = false;
        }
    }

    /**
     * 显示emoji
     */
    public void showEmoji() {
        if (isEmojiShow)
            return;
        if (isSoftInput) {
            isEmoji = true; // 需要显示emoji
            mInput.clearFocus();
            mImm.hideSoftInputFromWindow(mInput.getWindowToken(), 0);
        } else {
            mEmojiGrid.setVisibility(View.VISIBLE);// 避免闪烁
            isEmojiShow = true; // 修改emoji显示标记
        }
        if (!isSoftInput) {
            mChatList.setVisibility(View.GONE);
        }
        mEmoji.setImageResource(R.drawable.push_chat_emoji);
    }

    /**
     * 隐藏emoji
     */
    public void hideEmoji() {
        if (isEmojiShow) { // 如果emoji显示
            mEmojiGrid.setVisibility(View.GONE);
            isEmojiShow = false; // 修改emoji显示标记
            mEmoji.setImageResource(R.drawable.push_chat_emoji_normal);
            if (!isSoftInput) {
                mChatList.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 显示私聊信息列表
     */
    public void showPrivateChatMsgList(final String username) {
        mOperLayout.setVisibility(View.INVISIBLE);
        mChatLayout.setVisibility(View.VISIBLE);
        mInput.setFocusableInTouchMode(true);
        TranslateAnimation animation = new TranslateAnimation(1f, 1f, 0f, 1f);
        animation.setDuration(300L);
        animation.setAnimationListener(new OnAnimEndListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                mMaskLayer.setVisibility(View.VISIBLE);
            }
        });
        mPrivateChatMsgLayout.startAnimation(animation);
        mPrivateChatMsgMask.setBackgroundColor(Color.parseColor("#FAFAFA"));
        mPrivateChatUserName.setText(username);
        mPrivateChatMsgLayout.setVisibility(View.VISIBLE);
        if (mPrivateChatAdapter.getItemCount() - 1 > 0) {
            mPrivateChatMsgList.scrollToPosition(mPrivateChatAdapter.getItemCount() - 1);// 进行定位
        }
        isPrivateChatMsg = true;
    }

    /**
     * 隐藏私聊信息列表
     */
    public void hidePrivateChatMsgList() {
        if (isPrivateChatMsg) {
            hideEmoji();
            if (isSoftInput) {
                mImm.hideSoftInputFromWindow(mInput.getWindowToken(), 0);
            }
            mChatList.setVisibility(View.VISIBLE);
            mInput.setFocusableInTouchMode(false);
            mInput.clearFocus();
            mMaskLayer.setVisibility(View.GONE);
            mPrivateChatMsgMask.setBackgroundColor(Color.parseColor("#00000000"));
            mChatLayout.setVisibility(View.INVISIBLE);
            mOperLayout.setVisibility(View.VISIBLE);
            mPrivateChatMsgLayout.setVisibility(View.GONE);
            isPrivateChatMsg = false;
        }
    }

    @Override
    public void showLoading() {
        mRoot.post(new Runnable() {
            @Override
            public void run() {
                mLoadingPopup.show(mRoot);
            }
        });
    }

    @Override
    public void dismissLoading() {
        mRoot.post(new Runnable() {
            @Override
            public void run() {
                mLoadingPopup.dismiss();
            }
        });
    }

    @Override
    public void updateRoomCount(int count) {
        mRoomCount.setText(String.format("%s人", count));
    }

    @Override
    public void clearChatInput() {
        mInput.setText("");
    }

    @Override
    public void updatePrivateChat(ChatEntity chatEntity) {
        if (isPrivateChatMsg && (chatEntity.isPublisher() ||
                chatEntity.getUserId().equals(mCurPrivateUserId))) { // 如果当前界面是私聊信息界面直接在该界面进行数据更新
            mPrivateChatAdapter.add(chatEntity);
            mPrivateChatMsgList.smoothScrollToPosition(mPrivateChatAdapter.getItemCount() - 1);// 进行定位
        }
        PrivateUser privateUser = new PrivateUser();
        if (chatEntity.isPublisher()) {
            privateUser.setId(chatEntity.getReceiveUserId());
            privateUser.setName(chatEntity.getReceivedUserName());
            privateUser.setAvatar(chatEntity.getReceiveUserAvatar());
        } else {
            privateUser.setId(chatEntity.getUserId());
            privateUser.setName(chatEntity.getUserName());
            privateUser.setAvatar(chatEntity.getUserAvatar());
        }
        privateUser.setMsg(chatEntity.getMsg());
        privateUser.setTime(chatEntity.getTime());
        privateUser.setRead(isPrivateChatMsg && (chatEntity.isPublisher() ||
                chatEntity.getUserId().equals(mCurPrivateUserId)));
        mPrivateUserAdapter.add(privateUser);
        if (!isAllPrivateChatRead()) {
            mPrivateIcon.setImageResource(R.drawable.push_private_msg_new);
        }
        mPrivateChats.add(chatEntity);
    }

    @Override
    public void updateChat(ChatEntity chatEntity) {
        mChatAdapter.add(chatEntity);
        mChatList.smoothScrollToPosition(mChatAdapter.getItemCount() - 1);// 进行定位
    }

    @Override
    public void onBackPressed() {
        if (isEmojiShow) {
            hideEmoji();
            hideChatLayot();
            return;
        }
        if (isPrivateChatMsg) {
            hidePrivateChatMsgList();
            showPrivateChatUserList();
            return;
        }
        if (isPrivateChatUser) {
            hidePrivateChatUserList();
            return;
        }
        mExitPopup.show(mRoot);
    }
}
