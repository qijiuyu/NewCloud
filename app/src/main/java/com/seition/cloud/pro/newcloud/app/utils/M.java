package com.seition.cloud.pro.newcloud.app.utils;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by addis on 2018/4/17.
 */
public class M {
    public static String getMapString(Object... parameter) {
        HashMap<Object, Object> map = new HashMap<>();
        for (int i = 0; i < parameter.length / 2; i++)
            map.put(parameter[i * 2], parameter[i * 2 + 1]);
        if (PreferenceUtil.onlyLogibnKey != null && !PreferenceUtil.onlyLogibnKey.isEmpty()) {
            map.put(PreferenceUtil.ONLY_LOGIN_KEY, PreferenceUtil.onlyLogibnKey);
        }
        return new Gson().toJson(map);
    }

    /**
     * 加密
     *
     * @param key
     * @param content
     * @return
     * @throws Exception
     */
    public static String getEncryptData(String key, String content) {
        StringBuilder encryptData = new StringBuilder();
        long time = System.currentTimeMillis() / 1000;
//        String hex = timeToHexTime(time);
//        String token = getToken(time, hex);
        encryptData.append(time);
        if (content != null) {
            encryptData.append("|");
            encryptData.append(content);//加密数据添加
        }
        String ecbString = encryptData.toString();
        try {
            ecbString = SecureUtils.encrypt(encryptData.toString(), key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ecbString;
    }

    /**
     * 解密
     *
     * @param encryptData
     * @return
     */
    public static String getDecodeData(String key, String encryptData) throws Exception {
        String decodeData = "";
        if (encryptData != null) {
            decodeData = SecureUtils.decryptByAes(encryptData, key);
//            decodeData = encryptData.replaceAll("-", "\\+")
//                    .replaceAll("_", "/");
//            decodeData = HexUtils.decrypt(decodeData, key);
        }
        return decodeData;
    }

    public static String timeToHexTime(long time) {
        return Long.toHexString(time);
    }

    public static String getToken(long time, String hexTime) {
        return Utils.MD5(time + hexTime);
    }


}
