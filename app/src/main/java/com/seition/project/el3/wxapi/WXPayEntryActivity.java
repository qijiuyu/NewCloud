package com.seition.project.el3.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, MyConfig.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
//		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

//            int WxPayTag = MyConfig.WxPayTag;
//            Intent intent = null;
//            switch (WxPayTag) {
//                case 1:
//					intent=new Intent(WXPayEntryActivity.this,OwnerMemberCenterActivity.class);
//            ((MApplication) getApplicationContext()).finishLastActivity();
//                    break;
//            }
//            MyConfig.WxPayTag = 0;
//            finish();
//			startActivity(intent);
//			finish();

        }

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

			if(resp.errCode==-2){
				Toast.makeText(this,"取消付款！", Toast.LENGTH_LONG).show();
			}
			if(resp.errCode==-1){
				Toast.makeText(this,"支付错误！", Toast.LENGTH_LONG).show();
			}
			if(resp.errCode==0){
				Toast.makeText(this,"支付成功！", Toast.LENGTH_LONG).show();
                sendBroadcast(new Intent(MyConfig.WXPAY_SUCCESS));
            }
		}
		finish();
    }
}