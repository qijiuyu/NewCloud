package com.seition.cloud.pro.newcloud.home.mvp.ui.owner.message.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.message.MessageLetter;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerMessageComponent;
import com.seition.cloud.pro.newcloud.home.di.module.MessageModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MessageContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.MessagePresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.message.adapter.MessageChatAdapter;
import com.seition.cloud.pro.newcloud.widget.ListFaceView;
import com.seition.cloud.pro.newcloud.widget.emoji.FaceLayout;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MessageChatActivity extends BaseActivity<MessagePresenter> implements MessageContract.View {
//    @BindView(R.id.springview)
//    SpringView springView;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.et_msg)
    EditText et_msg;
    @BindView(R.id.face_view)
     ListFaceView tFaceView;// 表情框
    @BindView(R.id.faceView)
    FaceLayout faceView;

    @Inject
    MessageChatAdapter adapter;

    /**
     * 把中文字符串转换为十六进制Unicode编码字符串
     */
    public static String stringToUnicode(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            if (ch > 255)
                str += "\\u" + Integer.toHexString(ch);
            else
                str += String.valueOf(s.charAt(i));
        }
        return str;
    }

    @OnClick({R.id.send_btn,R.id.changimg})
    void sendMsg(View view ) {
        switch (view.getId()){
            case R.id.send_btn:
//                try {
////                    ParseEmojiMsgUtil.c
//                    mPresenter.replyMsg(URLEncoder.encode(et_msg.getText().toString(), "UTF-8"));
//                    et_msg.setText("");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
                mPresenter.replyMsg(/*stringToUnicode(*/et_msg.getText().toString().trim()/*)*/);
                et_msg.setText("");
                break;
            case R.id.changimg:
                /*if (tFaceView.getVisibility() == View.GONE) {
                    tFaceView.setVisibility(View.VISIBLE);
//                    ivFace.setImageResource(R.drawable.key_bar);
                } else if (tFaceView.getVisibility() == View.VISIBLE) {
                    tFaceView.setVisibility(View.GONE);
//                    ivFace.setImageResource(R.drawable.face_bar);
                }*/

                if (faceView.getVisibility() == View.GONE) {
                    faceView.setVisibility(View.VISIBLE);
                    Utils.hideSoftInput(et_msg);
//                    ivFace.setImageResource(R.drawable.key_bar);
                } else if (faceView.getVisibility() == View.VISIBLE) {
                    faceView.setVisibility(View.GONE);
                    et_msg.clearFocus();
                    Utils.showSoftInput(et_msg);
//                    ivFace.setImageResource(R.drawable.face_bar);
                }

                break;
        }


    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMessageComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .messageModule(new MessageModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_message_chat; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.chatting_records);
        //初始化表情布局
//        tFaceView.initSmileView(et_msg);
        faceView.setFaceAdapter(mFaceAdapter);
        MessageLetter messageLetter = (MessageLetter) getIntent().getSerializableExtra("messageLetter");
        mPresenter.setMessageLetter(messageLetter);
        initView();
        mPresenter.getChatList();
    }

    private void initView() {
        mPresenter.initAdapterListener();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        springView.setType(SpringView.Type.FOLLOW);
//        springView.setListener(mPresenter);
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

    private FaceLayout.FaceAdapter mFaceAdapter = new FaceLayout.FaceAdapter() {

        @Override
        public void doAction(int paramInt, String paramString) {
            EditText localEditBlogView = et_msg;
            int i = localEditBlogView.getSelectionStart();
            int j = localEditBlogView.getSelectionStart();
            String str1 = "[" + paramString + "]";
            String str2 = localEditBlogView.getText().toString();

            SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder();
            localSpannableStringBuilder.append(str2, 0, i);
            localSpannableStringBuilder.append(str1);
            localSpannableStringBuilder.append(str2, j, str2.length());

            highlightContent(MessageChatActivity.this, localSpannableStringBuilder);
            localEditBlogView.setText(localSpannableStringBuilder, TextView.BufferType.SPANNABLE);
            localEditBlogView.setSelection(i + str1.length());

            Log.v("Tag", localEditBlogView.getText().toString());
        }
    };

    public static void highlightContent(Context paramContext,
                                        Spannable paramSpannable) {
        try {
            Matcher localMatcher = Pattern.compile("\\[(\\S+?)\\]").matcher(
                    paramSpannable);
            while (true) {
                if (!localMatcher.find())
                    return;
                int i = localMatcher.start();
                int j = localMatcher.end();
                String str = localMatcher.group(1);
                Integer localInteger = FaceLayout.facesKeyString.get(str);
                if ((localInteger.intValue() <= 0) || (localInteger == null))
                    continue;
                paramSpannable.setSpan(
                        new ImageSpan(paramContext, localInteger.intValue()),
                        i, j, 33);
            }
        } catch (Exception e) {
            Log.d("TSUtils", e.toString());
        }
    }
}
