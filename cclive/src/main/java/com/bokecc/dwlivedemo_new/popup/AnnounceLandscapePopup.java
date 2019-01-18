package com.bokecc.dwlivedemo_new.popup;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bokecc.dwlivedemo_new.R;
import com.bokecc.dwlivedemo_new.base.BasePopupWindow;
import com.bokecc.dwlivedemo_new.base.PopupAnimUtil;

/**
 * 作者 ${郭鹏飞}.<br/>
 */

public class AnnounceLandscapePopup extends BasePopupWindow {

    private TextView mTip;
    private Button mOk, mCancel;

    private boolean isOk = false;

    public AnnounceLandscapePopup(Context context) {
        super(context);
    }

    public AnnounceLandscapePopup(Context context, int width, int height) {
        super(context, width, height);
    }


    ImageView ivAnnouncePopupClose;
    TextView tvAnnounceContent;
    @Override
    protected void onViewCreated() {
        ivAnnouncePopupClose = findViewById(R.id.announce_popup_close);
        ivAnnouncePopupClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        tvAnnounceContent = findViewById(R.id.tv_announce_content);
    }

    public boolean isShown() {
        return ivAnnouncePopupClose.isShown();
    }

    @Override
    protected int getContentView() {
        return R.layout.announce_landscape_layout;
    }

    @Override
    protected Animation getEnterAnimation() {
        return PopupAnimUtil.getDefTranslateEnterAnim();
    }

    @Override
    protected Animation getExitAnimation() {
        return PopupAnimUtil.getDefTranslateExitAnim();
    }

    public void setAnnounceContent(String content) {
        tvAnnounceContent.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动
        tvAnnounceContent.scrollTo(0, 0);
        if (TextUtils.isEmpty(content)) {
            tvAnnounceContent.setText("暂无公告");
        } else {
            tvAnnounceContent.setText(Html.fromHtml(content));
        }

    }

}
