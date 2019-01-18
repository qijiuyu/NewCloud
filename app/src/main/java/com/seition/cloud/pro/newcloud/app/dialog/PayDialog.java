package com.seition.cloud.pro.newcloud.app.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.TextView;

import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.money.BalanceInfo;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;

/**
 * Created by addis on 2017/10/24.
 */

public class PayDialog extends Dialog {
    private Context mContext;
    private BuyListener buyListener;
    String price_str = "";
    private RadioButton balance, alpay, wxpay;
    private TextView pay_price;
    private BalanceInfo meMoney;
    /*private Handler numberHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MyConfig.SUCCESS:
                    meMoney = new MMoney((JSONObject) msg.obj);
                    if (balance != null)
                        balance.setText("   (当前账户余额为¥" + meMoney.getLearn() + ")");
                    break;
                case MyConfig.ERROR:

                    break;
                case MyConfig.EMPTY:

                    break;

            }
        }
    };
    */
    public PayDialog(@NonNull Context context, BuyListener listener) {
        super(context, R.style.dialog_style);
        mContext = context;
        this.buyListener = listener;
//        String url = MyConfig.GET_ACCOUNT_INFO + Utils.getTokenString(context);
//        Log.i("info", "number url = " + url);
//        NetDataHelper.getJSON_1(context, numberHandler, url, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
//        window.setWindowAnimations(R.style.bottom_menu_animation); // 添加动画效果
        setContentView(R.layout.dialog_select_pay);
        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth();
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(true);// 点击Dialog外部消失

        alpay = (RadioButton) findViewById(R.id.alpay);
        wxpay = (RadioButton) findViewById(R.id.wxpay);
        balance = (RadioButton) findViewById(R.id.balance);

        pay_price = (TextView) findViewById(R.id.pay_price);

        pay_price.setText(price_str);

        if (meMoney != null)
            balance.setText("   (当前账户余额为¥" + meMoney.getBalance() + ")");

        findViewById(R.id.buy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alpay.isChecked()) buyListener.buy_ls(MyConfig.ALIPAY);
                else if (wxpay.isChecked()) buyListener.buy_ls(MyConfig.WXPAY);
                else buyListener.buy_ls(MyConfig.ICNPAY);
                dismiss();
            }
        });
        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setPrice(String price) {
        price_str = price;
        if (pay_price != null)
            pay_price.setText(price);
    }

    public interface BuyListener {
        void buy_ls(String payStyle);
    }

}
