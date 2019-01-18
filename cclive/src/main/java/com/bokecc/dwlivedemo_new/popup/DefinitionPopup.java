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
import com.bokecc.sdk.mobile.live.pojo.QualityInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 ${郭鹏飞}.<br/>
 */

public class DefinitionPopup extends BasePopupWindow {

    public DefinitionPopup(Context context) {
        super(context);
    }

    public DefinitionPopup(Context context, int width, int height) {
        super(context, width, height);
    }

    ListView listView;
    MyListAdapter mListAdapter;
    int selectedItem;

    @Override
    protected void onViewCreated() {
        listView = findViewById(R.id.id_lv_definition_view);

        mListAdapter = new MyListAdapter();

        listView.setAdapter(mListAdapter);
    }

    public DefinitionPopup setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
        mListAdapter.notifyDataSetChanged();
        return this;
    }

    @Override
    protected int getContentView() {
        return R.layout.definition_layout;
    }

    @Override
    protected Animation getEnterAnimation() {
        return PopupAnimUtil.getDefTranslateEnterAnim();
    }

    public DefinitionPopup setAdapterList(List<QualityInfo> list) {
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

        List<QualityInfo> list = new ArrayList<QualityInfo>();

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
            tv.setText(list.get(i).getDesc());
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);

            initPortraitTextView(i, tv);

            return tv;
        }

        private void initPortraitTextView (int i, TextView tv) {
            tv.setPadding(15, 15 ,10 ,10);
            if (i == selectedItem) {
                tv.setTextColor(Color.argb(255, 255, 102, 51));
            } else {
                tv.setTextColor(Color.argb(255, 255, 255, 255));
            }

            tv.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));
            tv.setGravity(Gravity.CENTER);
        }

        private void initLandscapeTextView(int i, TextView tv) {
            tv.setPadding(60, 15, 10, 20);
            tv.setTextColor(Color.argb(255, 255, 255, 255));
            if (i == selectedItem) {
                Drawable rightDrawable =  mContext.getResources().getDrawable(R.mipmap.line_btn_select);
                rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                tv.setCompoundDrawables(null, null, rightDrawable, null);

            } else {
                tv.setCompoundDrawables(null, null, null, null);
            }

            tv.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.MATCH_PARENT));
            tv.setGravity(Gravity.CENTER_VERTICAL);
        }
    }
}
