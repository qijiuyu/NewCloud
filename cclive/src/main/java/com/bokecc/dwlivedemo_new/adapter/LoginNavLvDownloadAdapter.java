package com.bokecc.dwlivedemo_new.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bokecc.dwlivedemo_new.R;

/**
 * Created by liufh on 2016/11/25.
 */

public class LoginNavLvDownloadAdapter extends BaseAdapter {
    private int selectIndex;
    private String[] listArray;
    public void setSelectItem(int selectIndex) {
        this.selectIndex = selectIndex;
    }

    Context context;

    public LoginNavLvDownloadAdapter(Context context, String[] listArray) {
        this.listArray = listArray;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listArray.length;
    }

    @Override
    public Object getItem(int i) {
        return listArray[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View mView = LayoutInflater.from(context).inflate(R.layout.lv_line, viewGroup, false);
        TextView tv = (TextView) mView.findViewById(R.id.tv_longin_lv_line);
        tv.setText(listArray[i]);
        if (i == selectIndex) {
            tv.setTextColor(Color.rgb(255, 255, 255));
        }
        return mView;
    }
}
