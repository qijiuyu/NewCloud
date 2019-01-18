package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.examination.TestClassify;

import java.util.ArrayList;

public class ExamCategoryListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context mContext;

    private ArrayList<TestClassify> listDatas;
    //    private BeanContent select_cc;
    private boolean isOff;
    int bcolor;

    public ExamCategoryListAdapter(Context context, ArrayList<TestClassify> listDatas, boolean isOff, int bcolor) {
//		System.out.println("CategoryListAdapter -- CategoryListAdapter()");
        this.isOff = isOff;
        this.mContext = context;

        this.listDatas = listDatas;
        this.bcolor = bcolor;
        mInflater = LayoutInflater.from(context);
    }

    public void setListDatas(ArrayList<TestClassify> listDatas) {
        this.listDatas = listDatas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
//		System.out.println("CategoryListAdapter -- getCount()");
//        if (isOff && categoryType == 2 && listDatas.size() > 0 && ((CommonCategory) listDatas.get(0)).getTitle().equals("全部"))
//            return listDatas.size() - 1;
//        else
        int count = 0;
        if (listDatas == null)
            count = 0;
        else
            count = listDatas.size();
        return count;
    }

    @Override
    public TestClassify getItem(int position) {
//		System.out.println("CategoryListAdapter -- getItem()");
//        if (isOff && categoryType == 2 && listDatas.size() > 0 && ((CommonCategory) listDatas.get(0)).getTitle().equals("全部"))
//            return listDatas.get(position + 1);
//        else
        return listDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setSelect(int position) {
//        if (select_cc != null)
//            select_cc.setSelect(false);
        if (listDatas.size() != 0) {
//            listDatas.get(position).setSelect(true);
//			System.out.println("CategoryListAdapter -- setSelect()" + listDatas.get(0).isSelect());
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
        TestClassify common = (TestClassify) getItem(position);
        viewHolder.name.setText("" + common.getTitle());
        return convertView;
    }

    private class ViewHolder {

        TextView name;
        View vi;
    }

}
