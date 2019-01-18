package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.bean.group.GroupMember;
import com.seition.cloud.pro.newcloud.widget.CustomShapeImageView;

import java.util.ArrayList;

public class GroupMemberHorizontalListAdapter extends BaseAdapter {

	public Context context;
	private ArrayList<GroupMember> datas;
	private LayoutInflater inflater;

	public GroupMemberHorizontalListAdapter(
                                            ) {
		// TODO Auto-generated constructor stub
//		this.context = context;
//		inflater = LayoutInflater.from(context);

	}

	public void setData(ArrayList<GroupMember> datas){
		if(this.datas!=null&&datas.size()>0)
		this.datas.clear();

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
		context = arg2.getContext();
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.item_group_member_horizontal_list,null);
			viewHolder.image =	(CustomShapeImageView) convertView.findViewById(R.id.group_member_img);
			viewHolder.name =	(TextView) convertView.findViewById(R.id.group_member_name);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		GroupMember groupMember = (GroupMember) getItem(position);
		viewHolder.name.setText( ""+groupMember.getName());
		final String url = groupMember.getAvatar_big();
//		请求带缓存的图片资源
		GlideLoaderUtil.LoadImage(convertView.getContext(),url,viewHolder.image);

		return convertView;
	}
	
	class ViewHolder{
		CustomShapeImageView image;
		TextView name;
	}
}

