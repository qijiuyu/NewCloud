package com.bokecc.dwlivedemo_new.popup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bokecc.dwlivedemo_new.R;
import com.bokecc.dwlivedemo_new.base.BasePopupWindow;
import com.bokecc.dwlivedemo_new.base.PopupAnimUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 ${郭鹏飞}.<br/>
 */

public class LiveSourcePcPortraitPopup extends BasePopupWindow {

    public LiveSourcePcPortraitPopup(Context context) {
        super(context);
    }

    public LiveSourcePcPortraitPopup(Context context, int width, int height) {
        super(context, width, height);
    }


    ListView listView;
    MyListAdapter mListAdapter;
    int selectedItem;

    @Override
    protected void onViewCreated() {
        listView = findViewById(R.id.id_lv_source_view);

        mListAdapter = new MyListAdapter();

        listView.setAdapter(mListAdapter);
    }

    public LiveSourcePcPortraitPopup setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
        mListAdapter.notifyDataSetChanged();
//        listView.setSelection(selectedItem);
        return this;
    }

    @Override
    protected int getContentView() {
        return R.layout.live_source_landscape_layout;
    }

    @Override
    protected Animation getEnterAnimation() {
        return PopupAnimUtil.getDefTranslateEnterAnim();
    }

    public LiveSourcePcPortraitPopup setAdapterList(List<String> list) {
        mListAdapter.setList(list);
        mListAdapter.notifyDataSetChanged();
        return this;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        listView.setOnItemClickListener(listener);
    }


    @Override
    protected Animation getExitAnimation() {
        return PopupAnimUtil.getDefTranslateExitAnim();
    }

    private class MyListAdapter extends BaseAdapter {

        List<String> list = new ArrayList<String>();

        public void setList(List list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            TextView tv = new TextView(mContext);
            tv.setText(list.get(i));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            initLandscapeTextView(i, tv);
            return tv;
        }

        private void initLandscapeTextView(int i, TextView tv) {
            tv.setPadding(30, 15 ,10 , 15);

            if (i == selectedItem) {
                tv.setTextColor(Color.argb(255, 255, 102, 51));
                Drawable rightDrawable =  mContext.getResources().getDrawable(R.mipmap.line_btn_select);
                rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                tv.setCompoundDrawables(null, null, rightDrawable, null);
                tv.setCompoundDrawablePadding(10);
            } else {
                tv.setTextColor(Color.argb(255, 255, 255, 255));
                tv.setCompoundDrawables(null, null, null, null);
            }

            tv.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.MATCH_PARENT));
            tv.setGravity(Gravity.CENTER_VERTICAL);
        }
    }
}
