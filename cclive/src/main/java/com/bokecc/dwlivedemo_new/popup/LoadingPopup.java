package com.bokecc.dwlivedemo_new.popup;

import android.content.Context;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.bokecc.dwlivedemo_new.R;
import com.bokecc.dwlivedemo_new.base.BasePopupWindow;
import com.bokecc.dwlivedemo_new.base.PopupAnimUtil;
import com.bumptech.glide.Glide;

/**
 * 作者 ${郭鹏飞}.<br/>
 */

public class LoadingPopup extends BasePopupWindow {

    public LoadingPopup(Context context) {
        super(context);
    }

    @Override
    protected void onViewCreated() {
        ImageView loadingIcon = findViewById(R.id.id_loading_img);
        Glide.with(mContext).load(R.drawable.loading)
//                .asGif()
                .into(loadingIcon);
    }

    @Override
    protected int getContentView() {
        return R.layout.loading_layout;
    }

    @Override
    protected Animation getEnterAnimation() {
        return PopupAnimUtil.getDefScaleEnterAnim();
    }

    @Override
    protected Animation getExitAnimation() {
        return PopupAnimUtil.getDefScaleExitAnim();
    }
}
