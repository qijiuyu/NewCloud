package com.seition.cloud.pro.newcloud.home.mvp.ui.more.news.adapter;

import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.examination.MExamBean;
import com.seition.cloud.pro.newcloud.app.bean.news.NewsItemBean;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by addis on 2018/3/26.
 */

public class NewsListAdapter extends BaseQuickAdapter<NewsItemBean, BaseViewHolder> {

    public NewsListAdapter() {
        super(R.layout.item_news_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsItemBean item) {
        helper.setText(R.id.item_title, item.getTitle());
        helper.setText(R.id.item_content, delHTMLTag(item.getText()));
        helper.setText(R.id.item_time, item.getDateline() + "");
        helper.setText(R.id.item_read, "阅读：" + item.getReadcount());
        GlideLoaderUtil.LoadImage(mContext, item.getImage(), helper.getView(R.id.cover));
    }


    /**
     * 去掉所有html标签返回文字
     *
     * @param htmlStr
     * @return
     */
    public static String delHTMLTag(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式


        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); //过滤script标签


        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); //过滤style标签


        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); //过滤html标签


        return htmlStr.trim(); //返回文本字符串
    }
}
