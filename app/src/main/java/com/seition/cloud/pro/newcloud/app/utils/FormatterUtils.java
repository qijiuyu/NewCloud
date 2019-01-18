package com.seition.cloud.pro.newcloud.app.utils;

import java.math.BigDecimal;

/**
 * Created by addis on 2018/4/8.
 */

public class FormatterUtils {

    public static String fileSizeFormatter(long fileSize) {
        if (fileSize < 1024)
            return fileSize + " b";
        else if (fileSize < 1024 * 1024)
            return new BigDecimal(fileSize / 1024d).setScale(2, 4) + " kb";
        else if (fileSize < 1024 * 1024 * 1024)
            return new BigDecimal(fileSize / 1024d / 1024d).setScale(2, 4) + " mb";
        else if (fileSize < 1024 * 1024 * 1024 * 1024)
            return new BigDecimal(fileSize / 1024d / 1024d / 1024d).setScale(2, 4) + " Gb";
        else return new BigDecimal(fileSize / 1024d / 1024d / 1024d / 1024d).setScale(2, 4) + " Tb";
    }

}
