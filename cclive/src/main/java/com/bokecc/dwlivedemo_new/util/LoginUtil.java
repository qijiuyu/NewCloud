package com.bokecc.dwlivedemo_new.util;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.bokecc.dwlivedemo_new.view.LoginLineLayout;

/**
 * Created by liufh on 2016/11/28.
 */

public class LoginUtil {

    public static boolean isLoginButtonEnabled(EditText... editTexts) {
        for (int i=0; i<editTexts.length; i++) {
            if ("".equals(editTexts[i].getEditableText().toString().trim())) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNewLoginButtonEnabled(LoginLineLayout... views) {
        for (int i=0; i<views.length; i++) {
            if ("".equals(views[i].getText().trim())) {
                return false;
            }
        }
        return true;
    }

    public static void toast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
