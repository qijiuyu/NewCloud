package com.seition.cloud.pro.newcloud.app.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.seition.cloud.pro.newcloud.widget.emoji.CenterImageSpan;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	private static Builder ad;

	public static String getCurrentTime(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		String currentTime = sdf.format(date);
		return currentTime;
	}

	public static String getCurrentTime() {
		return getCurrentTime("yyyy-MM-dd  HH:mm:ss");
	}

	private static String lastTxt = "";

	private static Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.obj == null || msg.obj.toString().equals(lastTxt)) {
				lastTxt = "";
			}
		};
	};

	public static void showToast(Context context, String txt) {
		if (txt == null || txt.equals(lastTxt)) {
			return;
		}
		lastTxt = txt;
		Message msg = new Message();
		msg.obj = txt;
		handler.sendMessageDelayed(msg, 5000);
		Toast.makeText(context, txt, Toast.LENGTH_SHORT).show();
	}

	// public static String getTokenString(Context context) {
	// String token = null;
	// PreferenceUtil preferenceUtil = PreferenceUtil.getInstance(context);
	// token = "&oauth_token=" + preferenceUtil.getString("oauth_token", "");
	// token += "&oauth_token_secret="
	// + preferenceUtil.getString("oauth_token_secret", "");
	// return token;
	// }
	/*public static String getTokenString(Context context) {
		PreferenceUtil preferenceUtil = PreferenceUtil.getInstance(context);
		String tokenValue = preferenceUtil.getString("oauth_token", "");
		if (!TextUtils.isEmpty(tokenValue)) {
			String token = "&oauth_token=" + tokenValue + "&oauth_token_secret="
					+ preferenceUtil.getString("oauth_token_secret", "");
			return token;
		} else {
			return "";
		}
	}

	public static int getUid(Context context) {
		PreferenceUtil preferenceUtil = PreferenceUtil.getInstance(context);
		return preferenceUtil.getInt("uid", -1);
	}*/

	public static String getUTF8String(String src) {
		String txt = null;
		try {
			txt = new String(URLEncoder.encode(src, "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return txt;
	}

	@SuppressLint("SimpleDateFormat")
	public static String parseUnixTime(String unixTime, String format) {
		long time = Long.parseLong(unixTime);
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(new Date(time * 1000));
	}

	// 裁剪图像
	public static void crop(Uri uri, int requestCode, Activity activity) {
		// 裁剪图片意图
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("scale", true);// 去黑边
		intent.putExtra("scaleUpIfNeeded", true);// 去黑边
		// 裁剪框的比例，1：1
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// 裁剪后输出图片的尺寸大小
		intent.putExtra("outputX", 300); // 图片太大会crash activity
		intent.putExtra("outputY", 300);
		// 图片格式
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true);// 取消人脸识别
		intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
		activity.startActivityForResult(intent, requestCode);
	}

	// 压缩图片进行上传
	public static void compressBmpToFile(Bitmap bmp, File file) {
		if (bmp == null)
			return;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int options = 100;
		bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
		while (baos.toByteArray().length / 1024 > 100) {
			baos.reset();
			options -= 10;
			bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
		}

		try {
			FileOutputStream fos = new FileOutputStream(
					Environment.getExternalStorageDirectory() + "/chuyouyun_head/head.png");
			fos.write(baos.toByteArray());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean isDialog = false;

	public static void newdialog(final Context c) {
		if (isDialog) {
			return;
		}
		Utils.isDialog = true;
		ad = new Builder(c).setTitle("提示!").setIcon(android.R.drawable.ic_dialog_info)
				.setMessage("检测到你还没开启网络，请开启").setNegativeButton("取消", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Utils.isDialog = false;
					}
				}).setPositiveButton("开启", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Utils.isDialog = false;
						c.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
						// 进入无线网络配置界面
						c.startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS)); // 进入手机中的wifi网络设置界面
					}
				});
		ad.setCancelable(false);
		ad.show();
	}
	
	public static boolean isPhone(String str) {
		if (null == str || "".equals(str) || str.length()<8)
		{
			return false;
		}
		
		Pattern pattern = Pattern.compile("^(0[0-9][0-9]|13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])[0-9]{8}$");
		//国际号码  00-4位国家号码—4位区号-8为电话号码
        if(str.substring(0,2).endsWith("00")){
			pattern=Pattern.compile("^(00)[0-9]{11,22}");
		}
        //国内    0（长途）—11为号码(固话 3位区号—8位号码,移动11位)
        else if (str.substring(0, 1).equals("0"))
		{
			pattern = Pattern.compile("^(0[0-9][0-9])[0-9]{8,9}$");
		}
		
		return pattern.matcher(str).matches();
		
	}


	//判断email格式是否正确
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
//		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}

	public static boolean isFixedPhone(String str) {
        String regx = "([0-9]{3,4}-)?[0-9]{7,8}";
        Pattern p = Pattern.compile(regx);
        Matcher m = p.matcher(str);
        if(m.matches()){
            return true;
        }else{
            return false;
        }
		
	}


	/**
	 * 匹配Luhn算法：可用于检测银行卡卡号
	 *
	 * @param cardNo
	 * @return
	 */

	public static boolean matchLuhn(String cardNo) {
		int[] cardNoArr = new int[cardNo.length()];
		if (cardNoArr.length > 15 && cardNoArr.length < 23)
			return true;
		if (true)
			return false;
		for (int i = 0; i < cardNo.length(); i++) {
			cardNoArr[i] = Integer.valueOf(String.valueOf(cardNo.charAt(i)));
		}

		for (int i = cardNoArr.length - 2; i >= 0; i -= 2) {
			cardNoArr[i] <<= 1;
			cardNoArr[i] = cardNoArr[i] / 10 + cardNoArr[i] % 10;
		}

		int sum = 0;

		for (int i = 0; i < cardNoArr.length; i++) {
			sum += cardNoArr[i];
		}
		return sum % 10 == 0;
	}


	/**
	 * 校验银行卡卡号
	 * @param cardId
	 * @return
	 */
	public static boolean checkBankCard(String cardId) {
		char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
		if(bit == 'N'){
			return false;
		}
		return cardId.charAt(cardId.length() - 1) == bit;
	}

	/**
	 * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
	 * @param nonCheckCodeCardId
	 * @return
	 */
	public static char getBankCardCheckCode(String nonCheckCodeCardId){
		if(nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
				|| !nonCheckCodeCardId.matches("\\d+")) {
			//如果传的不是数据返回N
			return 'N';
		}
		char[] chs = nonCheckCodeCardId.trim().toCharArray();
		int luhmSum = 0;
		for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if(j % 2 == 0) {
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
	}

	
	/**
	 * 得到设备屏幕的宽度
	 */
	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 得到设备屏幕的高度
	 */
	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	/**
	 * 得到设备的密度
	 */
	public static float getScreenDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}

	/**
	 * 把密度转换为像素
	 */
//	public static int dip2px(Context context, float px) {
//		final float scale = getScreenDensity(context);
//		return (int) (px * scale + 0.5);
//	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * sp转换成px
	 */
	private int sp2px(Context context,float spValue){
		float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue*fontScale+0.5f);
	}
	/**
	 * px转换成sp
	 */
	private int px2sp(Context context,float pxValue){
		float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue/fontScale+0.5f);
	}

	public static String replace(String str) {
		while (str.contains("&nbsp;")) {
			str = str.replace("&nbsp;", " ");
		}
		return str;
	}

	/*
	* Get count from a String
	* */
	public static int getChineseCount(String title){
		int count = 0;
		String regEx = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(title);
		while (m.find()) {
			for (int i = 0; i <= m.groupCount(); i++) {
				count = count + 1;
			}
		}
		return count;
	}


	public static void savePreferences(Context context, String key, String value) {
		SharedPreferences prference = context.getSharedPreferences("dafengche",Context.MODE_WORLD_READABLE);
		prference.edit().clear();
		prference.edit().putString(key, value).commit();
	}

	public static String getPreferences(Context context, String key) {
		SharedPreferences preferences = context.getSharedPreferences("dafengche",Context.MODE_WORLD_READABLE);
		String value = preferences.getString(key, "");
		return value;
	}

	public static String delPreferences(Context context, String key) {
		SharedPreferences prference = context.getSharedPreferences("dafengche",Context.MODE_WORLD_READABLE);
		prference.edit().remove(key).commit();
//		prference.edit().commit();
		return null;
	}

	public static void clearPreferences(Context context) {
		SharedPreferences prference = context.getSharedPreferences("dafengche",Context.MODE_WORLD_READABLE);
		prference.edit().clear().commit();
	}



	public static String MD5(String sourceStr) {
		try {
			// 获得MD5摘要算法的 MessageDigest对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(sourceStr.getBytes());
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			StringBuffer buf = new StringBuffer();
			for (int i = 0; i < md.length; i++) {
				int tmp = md[i];
				if (tmp < 0)
					tmp += 256;
				if (tmp < 16)
					buf.append("0");
				buf.append(Integer.toHexString(tmp));
			}
			// 16位加密
//			 return buf.toString().substring(8, 24);
			// 32位加密
			return buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String  getAouthToken(Context context){
		return  PreferenceUtil.getInstance(context).getString("oauth_token", null)+":"+PreferenceUtil.getInstance(context).getString("oauth_token_secret","");

	}

	/**
	 * 隐藏软键盘
	 */
	public static void hideSoftInput(View view) {
		if (view == null || view.getContext() == null) return;
		InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
	/**
	 * 隐藏软键盘
	 */
	public static void showSoftInput(View view) {
		if (view == null || view.getContext() == null) return;
		InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, 0);
	}


	/**
	 * Spannable内的表情高亮显示
	 *
	 * @param paramContext
	 * @param paramSpannable
	 */
	public static Spannable showContentFaceView(Context paramContext,
												Spannable paramSpannable) {
		try {
			Matcher localMatcher = Pattern.compile("\\[(\\S+?)\\]").matcher(
					paramSpannable);
			while (true) {
				if (!localMatcher.find())
					return paramSpannable;
				int faceStart = localMatcher.start();
				int faceStop = localMatcher.end();
				String str = localMatcher.group(1);

				Integer localInteger = getResId(paramContext, str, "drawable");
				if ((localInteger.intValue() <= 0) || (localInteger == null))
					continue;
				// 修改输入框表情
				BitmapDrawable drawable = (BitmapDrawable) paramContext
						.getResources().getDrawable(localInteger.intValue());
				Bitmap bitmap = scale2Bitmap(0.6f, drawable.getBitmap());
				if (bitmap != null) {
//					paramSpannable.setSpan(new ImageSpan(paramContext, ImageUtil.makeGifTransparent(paramContext, localInteger)),
//							faceStart, faceStop,
//							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					paramSpannable.setSpan(new CenterImageSpan(paramContext, bitmap),
							faceStart, faceStop, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
			}
		} catch (Exception e) {
			Log.d("TSUtils", e.toString());
		}

		return paramSpannable;
	}

	/**
	 * 根据资源的名字获取它的ID
	 *
	 * @param name    要获取的资源的名字
	 * @param defType 资源的类型，如drawable, string 。。。
	 * @return 资源的id
	 */
	public static int getResId(Context context, String name, String defType) {
		String packageName = context.getApplicationInfo().packageName;
		return context.getResources().getIdentifier(name, defType, packageName);

	}

	/**
	 * 把bitmap同比例放大
	 *
	 * @param scale  需要放大的倍数
	 * @param bitmap 源bitmap
	 * @return
	 */
	private static Bitmap scale2Bitmap(float scale, Bitmap bitmap) {
		Bitmap bitmap2;
		if (bitmap != null) {
			Matrix matrix = new Matrix();
			matrix.postScale(scale, scale); // 长和宽放大缩小的比例
			bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
					bitmap.getHeight(), matrix, true);
			return bitmap2;
		}
		return null;
	}

	public static final String PATTERN_URL = "(((http|https|Http|Https)://|www.|Www.|WWW.)*" +
			"(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*" +
			"(/[a-zA-Z0-9\\&%_\\./-~-]*)?)";

	/**
	 * 基本功能：过滤所有以"<"开头以">"结尾的标签
	 *
	 * @param str
	 * @return String
	 */
	public static String filterHtml(String str) {
		if (str == null) {
			return "";
		}
		String regxpForHtml = "<([^>]*)>"; // 过滤所有以<开头以>结尾的标签
		Pattern pattern = Pattern.compile(regxpForHtml);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * added on 5.28.2017 leiyan
	 * 聊天内容 匹配表情、url（http 开头）
	 * @param context
	 * @param textView
	 * @param content
	 * @return
	 */
	public static SpannableStringBuilder showChatContent(final Context context, final TextView textView, String content) {
		if (TextUtils.isEmpty(content)){
			return  new SpannableStringBuilder("");
		}
		String patternStr = ""+PATTERN_URL+"|(\\[(.+?)\\])";
		Pattern pattern = Pattern .compile(patternStr);
		content = filterHtml(content);
		Matcher matcher = pattern.matcher(content);
		List<String> list = new LinkedList<String>();
		Map<String,String> clickUname = new HashMap<>();
		while (matcher.find()) {
			String str = matcher.group();
			list.add(str);
		}

		SpannableString spanStr = new SpannableString(content);
		SpannableStringBuilder ssb = new SpannableStringBuilder(spanStr);
		int nextStart = 0;
		if (list.size() > 0) {
			try {
				boolean hasShowFaceView = false;// 用来标记是否执行过表情全部替换
				for (int i = 0; i < list.size(); i++) {
					final String name = list.get(i);
					// 起点，如果是http需要设置起点name=访问网络+
					final int start = content.indexOf(name, nextStart);
					nextStart = start + name.length();
					if (name.contains("[")) {
						// 如果带有[并且不是网站链接，则为表情，
						if (hasShowFaceView) {
							// 如果还没有执行过表情全替换hasShowFaceView，则执行替换表情,否则跳过
							continue;
						}

						Matcher localMatcher = Pattern.compile("\\[(\\S+?)\\]")
								.matcher(ssb);
						while (true) {
							if (!localMatcher.find())// 没找到表情
								break;
							int faceStart = localMatcher.start();
							int faceStop = localMatcher.end();
							String str = localMatcher.group(1);
							// 获取表情
							Integer localInteger = getResId(context, str, "drawable");
							if ((localInteger.intValue() <= 0)
									|| (localInteger == null))
								continue;
							// 设置表情
							// 修改输入框表情
							BitmapDrawable drawable = (BitmapDrawable) context
									.getResources().getDrawable(localInteger.intValue());
							Bitmap bitmap = scale2Bitmap(0.6f, drawable.getBitmap());
							if (bitmap != null) {
								ssb.setSpan(new CenterImageSpan(context, bitmap),
										faceStart, faceStop, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
							}
						}
						// 最后标记一下已经替换全部表情
						hasShowFaceView = true;
					} else {
						// 其他内容 设置点击Span，注意获取clickbale的时候需要判断是不是网络链接，at那人对应的uid不为空就传uid
						ssb.setSpan(new ClickableSpan() {
										@Override
										public void onClick(View widget) {
											Class<?> clazz = null;
											try {
												clazz = Class.forName("com.thinksns.sociax.t4.unit.UnitSociax");
												// 调用 UnitSociax 类中的 startNetActivity 方法
												Method method = clazz.getMethod("startNetActivity", String.class);
												method.invoke(clazz.getConstructors()[0].newInstance(context), name);
											} catch (ClassNotFoundException e) {
												e.printStackTrace();
											} catch (NoSuchMethodException e) {
												e.printStackTrace();
											} catch (IllegalAccessException e) {
												e.printStackTrace();
											} catch (InstantiationException e) {
												e.printStackTrace();
											} catch (InvocationTargetException e) {
												e.printStackTrace();
											}
										}
										@Override
										public void updateDrawState(TextPaint ds) {
											ds.setColor(textView.getCurrentTextColor());
											ds.setUnderlineText(true);
										}
									},
								start, start + name.length(), 0);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		AbsoluteSizeSpan span1 = new AbsoluteSizeSpan(16, true);
		ssb.setSpan(span1, 0, spanStr.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		textView.setMovementMethod(LinkMovementMethod.getInstance());
		return ssb;
	}
}
