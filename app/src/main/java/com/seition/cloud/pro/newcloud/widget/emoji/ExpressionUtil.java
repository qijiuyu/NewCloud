package com.seition.cloud.pro.newcloud.widget.emoji;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionUtil {

    public static String[] faceItem = new String[]{"[biaoqing_01]", "[biaoqing_02]", "[biaoqing_03]", "[biaoqing_04]",
            "[biaoqing_05]", "[biaoqing_06]", "[biaoqing_07]", "[biaoqing_08]", "[biaoqing_09]", "[biaoqing_10]",
            "[biaoqing_11]", "[biaoqing_12]", "[biaoqing_13]", "[biaoqing_14]", "[biaoqing_15]", "[biaoqing_16]",
            "[biaoqing_17]", "[biaoqing_18]", "[biaoqing_19]", "[biaoqing_20]", "[biaoqing_21]", "[biaoqing_22]",
            "[biaoqing_23]", "[biaoqing_24]", "[biaoqing_25]", "[biaoqing_26]", "[biaoqing_27]", "[biaoqing_28]",
            "[biaoqing_29]", "[biaoqing_30]", "[biaoqing_31]", "[biaoqing_32]", "[biaoqing_33]", "[biaoqing_34]",
            "[biaoqing_35]", "[biaoqing_36]", "[biaoqing_37]", "[biaoqing_38]", "[biaoqing_39]", "[biaoqing_40]",
            "[biaoqing_41]", "[biaoqing_42]", "[biaoqing_43]", "[biaoqing_44]", "[biaoqing_45]", "[biaoqing_46]",
            "[biaoqing_47]", "[biaoqing_48]", "[biaoqing_49]", "[biaoqing_50]", "[biaoqing_51]", "[biaoqing_52]",
            "[biaoqing_53]", "[biaoqing_54]", "[biaoqing_55]", "[biaoqing_56]", "[biaoqing_57]", "[biaoqing_58]",
            "[biaoqing_59]", "[biaoqing_60]", "[biaoqing_61]", "[biaoqing_62]", "[biaoqing_63]", "[biaoqing_64]",
            "[biaoqing_65]", "[biaoqing_66]", "[biaoqing_67]", "[biaoqing_68]", "[biaoqing_69]", "[biaoqing_70]",
            "[biaoqing_71]", "[biaoqing_72]", "[biaoqing_73]",};

    /**
     * 对spanableString进行正则判断，如果符合要求，则以表情图片代替
     *
     * @param context
     * @param spannableString
     * @param patten
     * @param start
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws NumberFormatException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static SpannableString dealExpression(Context context, SpannableString spannableString, Pattern patten,
                                                 int start, int textSize) {
        Matcher matcher = patten.matcher(spannableString);
        while (matcher.find()) {
            String key = matcher.group();
            if (matcher.start() < start) {
                continue;
            }
            Integer resId = getFaceId(key);// --------------表情命名id,为face0,face1.....
            // Log.e("dealExpression", rid);
            // Field field = R.drawable.class.getDeclaredField(rid);
            // int resId = Integer.parseInt(field.get(null).toString()); //
            // 通过上面匹配得到的字符串来生成图片资源id
            if (resId != 0) {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
                DisplayMetrics dm = new DisplayMetrics();
                dm = context.getResources().getDisplayMetrics();
                float density = dm.density;
                bitmap = Bitmap.createScaledBitmap(bitmap, (int) (45 * density), (int) (45 * density), true);
                ImageSpan imageSpan = new ImageSpan(bitmap, DynamicDrawableSpan.ALIGN_BOTTOM); // 通过图片资源id来得到bitmap，用一个ImageSpan来包装
                int end = matcher.start() + key.length(); // 计算该图片名字的长度，也就是要替换的字符串的长度
                spannableString.setSpan(imageSpan, matcher.start(), end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE); // 将该图片替换字符串中规定的位置中
                if (end < spannableString.length()) { // 如果整个字符串还未验证完，则继续。。
                    spannableString = dealExpression(context, spannableString, patten, end, textSize);
                }
                break;
            }
        }
        return spannableString;
    }

    public static Integer getFaceId(String key) {
//        for (int i = 0; i < faceItem.length; i++) {
//            if (faceItem[i].equals(key)) {
//                return FaceLayout.faceDisplayList.get(i);
//            }
//        }
        for (int i = 0; i < FaceLayout.biaoqing_str.length; i++) {
            String str = "[" + FaceLayout.biaoqing_str[i] + "]";
            if (key.equals(str)) {
                return FaceLayout.faceDisplayList.get(i);
            }
        }
        return 0;
    }

    /**
     * 得到一个SpanableString对象，通过传入的字符串,并进行正则判断
     *
     * @param context
     * @param str
     * @return
     */
    public static SpannableString getExpressionString(Context context, String str, String zhengze, int textSize) {
        SpannableString spannableString = new SpannableString(str);
        Pattern sinaPatten = Pattern.compile(zhengze, Pattern.CASE_INSENSITIVE); // 通过传入的正则表达式来生成一个pattern
        // try {
        dealExpression(context, spannableString, sinaPatten, 0, textSize + 5);
        // } catch (Exception e) {
        // // Log.e("dealExpression exception", e.getMessage());
        // }
        return dealExpression(context, spannableString, sinaPatten, 0, textSize + 5);
    }
}