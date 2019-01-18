package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.library.LibraryCategoryBean;

import java.util.ArrayList;

public class ArticleLibraryCategoryListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context mContext;

    private ArrayList<LibraryCategoryBean> listDatas;
    private boolean isOff;
    int bcolor;

    public ArticleLibraryCategoryListAdapter(Context context, ArrayList<LibraryCategoryBean> listDatas, boolean isOff, int bcolor) {
        this.isOff = isOff;
        this.mContext = context;
        this.listDatas = listDatas;
        this.bcolor = bcolor;
        mInflater = LayoutInflater.from(context);
    }

    public void setListDatas(ArrayList<LibraryCategoryBean> listDatas) {
        this.listDatas = listDatas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        int count = 0;
        if (listDatas == null)
            count = 0;
        else
            count = listDatas.size();
        return count;
    }

    @Override
    public LibraryCategoryBean getItem(int position) {
        if (listDatas == null || position >= listDatas.size()) return null;
        return listDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setSelect(int position) {
        if (listDatas.size() != 0) {
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.item_category_list_dialog_item, null);//search_category_list_item  mall_category_list_item
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.mall_category_name);
            viewHolder.vi = (View) convertView.findViewById(R.id.xxxx);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LibraryCategoryBean common = (LibraryCategoryBean) getItem(position);
        viewHolder.name.setText("" + common.getTitle());
        return convertView;
    }

    private class ViewHolder {
        TextView name;
        View vi;
    }

}
