package com.seition.cloud.pro.newcloud.home.mvp.ui.owner.message.adapter;

import android.text.SpannableString;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.message.MessageLetterLast;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.widget.emoji.ExpressionUtil;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by addis on 2018/5/28.
 */
public class MessageChatAdapter extends BaseMultiItemQuickAdapter<MessageLetterLast, BaseViewHolder> {
    public static int LEFT_TYPE = 91;
    public static int RIGHT_TYPE = 92;

    public MessageChatAdapter() {
        super(new ArrayList<>());
        addItemType(LEFT_TYPE, R.layout.item_private_message_left);
        addItemType(RIGHT_TYPE, R.layout.item_private_message_right);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, MessageLetterLast bean) {
        viewHolder.setText(R.id.tv_sendtime, bean.getMtime());
        viewHolder.setText(R.id.name, bean.getUser_info().getUname());

//        SpannableString spannableString = ExpressionUtil.getExpressionString(context, faceString, "\\[[a-z]{1,11}_\\d\\d\\]",
//                faceString.length());

//        Spannable spannable = Utils.showChatContent(mContext,(TextView) viewHolder.getView(R.id.tv_chatcontent) , bean.getContent());
//        ((TextView) viewHolder.getView(R.id.tv_chatcontent)).setText(spannable,TextView.BufferType.SPANNABLE);

//        viewHolder.setText(R.id.tv_chatcontent, unicodeToString(bean.getContent()));


        // 表情处理
        String faceString = bean.getContent(); // -------------要转换的文字
        if (faceString == null) faceString = "";
        SpannableString spannableString = ExpressionUtil.getExpressionString(mContext, faceString, "\\[[a-z_]{1,11}]",
                faceString.length());
        viewHolder.setText(R.id.tv_chatcontent, spannableString);// -------暂时取消表情解析
        if (viewHolder.getItemViewType() == LEFT_TYPE)
            GlideLoaderUtil.LoadCircleImage(mContext, bean.getUser_info().getAvatar_small(), viewHolder.getView(R.id.left_userhead));
        else
            GlideLoaderUtil.LoadCircleImage(mContext, bean.getUser_info().getAvatar_small(), viewHolder.getView(R.id.right_userhead));

    }

    /**
     * 把十六进制Unicode编码字符串转换为中文字符串
     */
    public static String unicodeToString(String str) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{2,4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }
        return str;
    }

    public static String unicode2String(String unicode) {

        StringBuffer string = new StringBuffer();

        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {

            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);

            // 追加成string
            string.append((char) data);
        }

        return string.toString();
    }


}
