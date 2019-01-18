package com.bokecc.dwlivedemo_new.popup;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewTreeObserver;
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

public class AnnouncePcPortaritPopup extends BasePopupWindow {

    private TextView mTip;
    private Button mOk, mCancel;

    private boolean isOk = false;

    public AnnouncePcPortaritPopup(Context context) {
        super(context);
    }

    public AnnouncePcPortaritPopup(Context context, int width, int height) {
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
        return R.layout.announce_pc_portrait_layout;
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
        tvAnnounceContent.scrollTo(0, 0);
        tvAnnounceContent.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动
        if (TextUtils.isEmpty(content)) {
            tvAnnounceContent.setText("暂无公告");
        } else {
            tvAnnounceContent.setText(Html.fromHtml(content));
        }

        tvAnnounceContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {

                if(tvAnnounceContent.getLineCount() > 3){
                    tvAnnounceContent.setScrollbarFadingEnabled(false);
                } else {
                    tvAnnounceContent.setScrollbarFadingEnabled(true);
                }

                tvAnnounceContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

}
