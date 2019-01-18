package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;

import java.util.ArrayList;
import java.util.List;

public class CategoryGridAdapter extends BaseAdapter {
    private List<CommonCategory> gridData;
    private Context context;
    private LayoutInflater inflater;

    public CategoryGridAdapter() {

    }
    public void setDatas(ArrayList<CommonCategory> gridData) {
        this.gridData = gridData;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return gridData == null ? 0 : gridData.size() > 9 ? 10 : gridData.size();
    }

    @Override
    public CommonCategory getItem(int arg0) {
        // TODO Auto-generated method stub
        return gridData.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        context = arg2.getContext();
        if (null == convertView) {
            inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_category_grid_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.cateCover);
            viewHolder.name = (TextView) convertView.findViewById(R.id.cateName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position == 9) {
            viewHolder.name.setText("更多");
            viewHolder.image.setImageResource(R.mipmap.home_more);
            convertView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
//                    if (context instanceof HomeActivity)
//                        ((HomeActivity) context).selectClassify();
                }
            });
        } else {
            final CommonCategory content = (CommonCategory) getItem(position);
            viewHolder.name.setText("" + content.getTitle());
            //请求带缓存的图片资源
            GlideLoaderUtil.LoadCircleImage(context,content.getIcon(),viewHolder.image);
            convertView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
//                    Intent intent = new Intent(context, CommonSearchNewActivity.class);
//                    intent.putExtra("TOSEARCHTYPE", "1");
//                    intent.putExtra("Id", content.getId());
//                    intent.putExtra("Name", content.getTitle());
//                    context.startActivity(intent);
                }
            });
        }


        return convertView;
    }

    private class ViewHolder {
        ImageView image;
        TextView name;
    }
}
