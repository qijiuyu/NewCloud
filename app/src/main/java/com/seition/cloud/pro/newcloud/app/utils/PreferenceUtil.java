package com.seition.cloud.pro.newcloud.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.seition.cloud.pro.newcloud.app.bean.user.User;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;


public class PreferenceUtil {
    public final static String TOKEN_SECRET = "oauth_token_secret";
    public final static String TOKEN = "oauth_token";
    public final static String USER_ID = "user_id";
    public final static String USER_NAME = "uname";
    public final static String USER_AVATAR = "user_avatar";
    public final static String ONLY_LOGIN_KEY = "only_login_key";
    public final static String ORDER_NEED_RELOAD = "OdersNeedReload";

    public static String onlyLogibnKey;
    private static String PREFERENCE_NAME;

    private static PreferenceUtil preferenceUtil;

    private SharedPreferences shareditorPreferences;

    private Editor editor;

    private PreferenceUtil(Context context) {
        PREFERENCE_NAME = MyConfig.PREFERENCE_NAME;
        init(context);
    }

    public boolean getWifiPlayVideoConfig() {
        return shareditorPreferences.getBoolean("WifiPlayVideoConfig", false);
    }

    public void setWifiPlayConfig(boolean open) {
        editor.putBoolean("WifiPlayVideoConfig", open).commit();
    }

    public void init(Context context) {
        if (shareditorPreferences == null || editor == null) {
            try {
                shareditorPreferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
                editor = shareditorPreferences.edit();
                onlyLogibnKey = getString(context, ONLY_LOGIN_KEY, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static PreferenceUtil getInstance(Context context) {
        if (preferenceUtil == null) {
            preferenceUtil = new PreferenceUtil(context);
        }
        return preferenceUtil;
    }

    public void saveLong(String key, long l) {
        editor.putLong(key, l);
        editor.commit();
    }

    public long getLong(String key, long defaultlong) {
        return shareditorPreferences.getLong(key, defaultlong);
    }

    public void saveBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key, boolean defaultboolean) {
        return shareditorPreferences.getBoolean(key, defaultboolean);
    }

    public void saveInt(String key, int value) {
        if (editor != null) {
            editor.putInt(key, value);
            editor.commit();
        }
    }

    public int getInt(String key, int defaultInt) {
        return shareditorPreferences.getInt(key, defaultInt);
    }

    public String getString(String key, String defvalue) {
        return shareditorPreferences.getString(key, defvalue);
    }

    public String getString(Context context, String key, String defaultValue) {
        if (shareditorPreferences == null || editor == null) {
            shareditorPreferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
            editor = shareditorPreferences.edit();
        }
        if (shareditorPreferences != null) {
            return shareditorPreferences.getString(key, defaultValue);
        }
        return defaultValue;
    }

    public void saveString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    public void destroy() {
        shareditorPreferences = null;
        editor = null;
        preferenceUtil = null;
    }

    public void saveLoginUser(User user) {
        preferenceUtil.saveString(TOKEN_SECRET, user.getOauth_token_secret());
        preferenceUtil.saveString(TOKEN, user.getOauth_token());
        preferenceUtil.saveString(USER_ID, user.getUid() + "");
        preferenceUtil.saveString(USER_NAME, user.getUname());
        preferenceUtil.saveString(USER_AVATAR, user.getUserface());
        preferenceUtil.saveString(ONLY_LOGIN_KEY, user.getOnly_login_key());
        onlyLogibnKey = user.getOnly_login_key();
    }

    public void clearLoginUser() {
        preferenceUtil.remove(TOKEN_SECRET);
        preferenceUtil.remove(TOKEN);
        preferenceUtil.remove(USER_ID);
        preferenceUtil.remove(USER_NAME);
        preferenceUtil.remove(USER_AVATAR);
        preferenceUtil.remove(ONLY_LOGIN_KEY);
        preferenceUtil.remove(ORDER_NEED_RELOAD);
    }
}
