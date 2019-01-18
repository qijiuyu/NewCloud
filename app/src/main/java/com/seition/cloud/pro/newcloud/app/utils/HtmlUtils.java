package com.seition.cloud.pro.newcloud.app.utils;

/**
 * Created by addis on 2018/5/24.
 */
public class HtmlUtils {
    public static String cleanP(String content) {
        if (content == null) return "";
        content = content.trim().replaceAll("</?[p|P][^>]*>", "");
        return content;
    }
}
