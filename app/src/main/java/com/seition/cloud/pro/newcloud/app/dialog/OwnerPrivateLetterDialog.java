package com.seition.cloud.pro.newcloud.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.utils.Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class OwnerPrivateLetterDialog implements OnClickListener {

	private Context context;
	private Dialog dialog;

	private TextView title_back;
	private TextView title_right;
	private TextView recv_person;
	private EditText et_msg;
	private ImageView smile_img;
//	private FaceLayout_old face_view;
	private String title_str = "发私信";
	private TextView title;
	private int toUid;// 对方的uid

	public OwnerPrivateLetterDialog(Context context) {
		this.context = context;
		dialog = new Dialog(context, R.style.dialog_fullscreen);
		dialog.setContentView(R.layout.dialog_private_chat);
		title = (TextView) dialog.findViewById(R.id.title);
//		title.setText(title_str);
		title_back = (TextView) dialog.findViewById(R.id.title_back);
		title_right = (TextView) dialog.findViewById(R.id.title_right);
//		title_right.setText("发送");
		smile_img = (ImageView) dialog.findViewById(R.id.smile_img);
		et_msg = (EditText) dialog.findViewById(R.id.et_msg);

		recv_person = (TextView) dialog.findViewById(R.id.recv_person);

//		face_view = (FaceLayout_old) dialog.findViewById(R.id.face_view);

		title_back.setOnClickListener(this);
		title_right.setOnClickListener(this);
		smile_img.setOnClickListener(this);
		et_msg.setOnClickListener(this);

//		face_view.setFaceAdapter(mFaceAdapter);
	}

	/*protected AppProgressDialog pd;

	public void showProgressDialog(String message, boolean isCancelable) {
		if (pd == null) {
			pd = new AppProgressDialog(context);
			pd.setCanceledOnTouchOutside(false);
			pd.getWindow().setGravity(Gravity.CENTER);
		}
		if (pd != null && !pd.isShowing()) {
			pd.setCancelable(isCancelable);
			pd.setMessage(message);
			pd.show();
		}
	}

	public AppProgressDialog getAppProgressDialog() {
		AppProgressDialog pdd = pd;
		return pdd;
	}

	public void dimissProgressDialog() {
		if (pd != null && pd.isShowing()) {
			pd.dismiss();
			pd = null;
		}
	}*/


	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.title_back:
			dismiss();
			break;
		case R.id.title_right:
			String content = et_msg.getText().toString().trim();
			onSendButtonClickListener.onSendButtonClick(content,toUid);
//			send();
			break;
		case R.id.smile_img:

//				smile_img.setImageResource(R.mipmap.jianpan);
				Utils.hideSoftInput(et_msg);//hideSoftKeyboard(context, et_msg);

				smile_img.setImageResource(R.mipmap.biaoqing);
//				SociaxUIUtils.showSoftKeyborad(context, et_msg);

			break;
		case R.id.et_msg:

				smile_img.setImageResource(R.mipmap.biaoqing);
//				SociaxUIUtils.showSoftKeyborad(context, et_msg);

			break;
		}
	}

	public interface OnDialogSendButtonClickListener {
		void onSendButtonClick( String content, int uid);

	}
	OnDialogSendButtonClickListener onSendButtonClickListener;
	public void setOnDialogItemClickListener(OnDialogSendButtonClickListener onSendButtonClickListener){
		this.onSendButtonClickListener = onSendButtonClickListener;
	}


	public void show() {
		dialog.show();
	}

	public void hide() {
		dialog.hide();
	}

	public void dismiss() {
		dialog.dismiss();
	}

	public void setToUid(int id) {
		toUid = id;
	}

	public void setRecvName(String name) {
		recv_person.setText(name);
	}

	private void send() {
//		showProgressDialog("正在发送消息",true);
		String contString = et_msg.getText().toString();
		if (contString.length() > 0) {

			try {
				contString = URLEncoder.encode(contString, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			String path = "";//MyConfig.OWNER_SEND_CHAT + Utils.getTokenString(context);
			path += "&to=" + toUid;
			path += "&content=" + contString;

//			sendPrivateChatMsg(path);
		}
	}

	/*private void sendPrivateChatMsg(final String path) {
		try {
			if (!IsNet.isNets(context)) {
				return;
			}
			NetComTools.getInstance(context).getNetJson(path, new JsonDataListener() {
				@Override
				public void OnReceive(JSONObject jsonObject) {
					try {
						if (jsonObject.getInt("code") == 0 && (jsonObject.get("data") != JSONObject.NULL)) {
							if (jsonObject.getBoolean("data")) {

								Toast.makeText(context, "发送成功", Toast.LENGTH_SHORT).show();
								dismiss();
							}
						} else {

							Toast.makeText(context, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void OnError(String error) {
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/*private FaceLayout_old.FaceAdapter mFaceAdapter = new FaceLayout_old.FaceAdapter() {

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
			SociaxUIUtils.highlightContent(context, localSpannableStringBuilder);
			localEditBlogView.setText(localSpannableStringBuilder, TextView.BufferType.SPANNABLE);
			localEditBlogView.setSelection(i + str1.length());

			Log.v("Tag", localEditBlogView.getText().toString());
		}
	};*/
}
