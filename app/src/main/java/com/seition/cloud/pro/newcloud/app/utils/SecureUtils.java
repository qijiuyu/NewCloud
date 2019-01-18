package com.seition.cloud.pro.newcloud.app.utils;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by addis on 2018/5/2.
 */
public class SecureUtils {
    private static String sKey;

    public static String encrypt(String sSrc, String reqKey) throws Exception {
        return encryptByAes(sSrc, reqKey);
    }

    public static String encryptByAes(String sSrc, String reqKey) throws Exception {
        byte[] raw;
        if (reqKey != null) {
            raw = reqKey.getBytes("ASCII");
        } else {
            raw = sKey.getBytes("ASCII");
        }
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());
        return encryptUrlSalf(encrypted);
    }

    public static String decryptByAes(String sSrc, String reqKey) {
        try {
            byte[] raw;
            if (reqKey != null) {
                raw = reqKey.getBytes("ASCII");
            } else {
                raw = sKey.getBytes("ASCII");
            }

            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = decryptUrlSafe(sSrc);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original);
                return originalString;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static byte[] decryptUrlSafe(String key) throws Exception {
        String decodeStr = key.replaceAll("-", "+").replaceAll("_", "/");
        String qualsStr = "";
//        for (int i = 0; i < key.length() % 4; i++) {
//            qualsStr += "=";
//        }
        return Base64.decode(decodeStr + qualsStr, Base64.DEFAULT);
    }

    public static String encryptUrlSalf(byte[] key) {
        String str = Base64.encodeToString(key, Base64.DEFAULT);
        str = str.replaceAll("\\+", "-")
                .replaceAll("/", "_")
                .replaceAll("\\r", "")
                .replaceAll("\\n", "")
                .replaceAll("=+$", "");
        return str;
    }
}
