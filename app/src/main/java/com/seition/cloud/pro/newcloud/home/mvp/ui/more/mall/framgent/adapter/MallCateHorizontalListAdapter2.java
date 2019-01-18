package com.seition.cloud.pro.newcloud.home.mvp.ui.more.mall.framgent.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.mall.MallCategory;

import java.util.ArrayList;

public class MallCateHorizontalListAdapter2 extends BaseAdapter {
    public Context context;
    private ArrayList<MallCategory> datas;
    private LayoutInflater inflater;
    private int firstCateSize;

    public MallCateHorizontalListAdapter2(Context context, ArrayList<MallCategory> datas) {
        // TODO Auto-generated constructor stub
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.datas = datas;
        selected = new SparseBooleanArray();
    }

    public void setDatas(ArrayList<MallCategory> datas, int firstCateSize) {
        this.datas = datas;
        this.firstCateSize = firstCateSize * 2;
        notifyDataSetChanged();
    }

    public void setDatas(ArrayList<MallCategory> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int index) {
        // TODO Auto-generated method stub
        return datas.get(index);
    }

    @Override
    public long getItemId(int id) {
        // TODO Auto-generated method stub
        return id;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_mall_cate_tab_second, null);
            viewHolder.groupCategoryTitle = (TextView) convertView.findViewById(R.id.group_category_title);
//			viewHolder.groupCategoryTitle.setWidth(itemwidth);;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//            viewHolder.groupCategoryTitle.setPadding(0, 20, 0, 20);
//
            if (selected.get(position)) {
                viewHolder.groupCategoryTitle.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else {
                viewHolder.groupCategoryTitle.setTextColor(context.getResources().getColor(R.color.black));
            }
        MallCategory c = (MallCategory) getItem(position);
        viewHolder.groupCategoryTitle.setText(c.getTitle());
        return convertView;
    }

    class ViewHolder {
        public TextView groupCategoryTitle;
    }


    //用来装载某个item是否被选中
    SparseBooleanArray selected;
    int old = -1;
//	int parentPosition = -1;

    public void setSelectedItem(int selected) {
//		this.parentPosition = groupPosition;
        if (old != -1) {
            this.selected.put(old, false);
        }
        this.selected.put(selected, true);
        old = selected;
    }
}

