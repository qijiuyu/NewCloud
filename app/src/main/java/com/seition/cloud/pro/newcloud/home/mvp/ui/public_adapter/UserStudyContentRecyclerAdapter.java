package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.study.StudyRecordContent;
import com.seition.cloud.pro.newcloud.app.utils.TimeUtils;

import java.util.ArrayList;

public class UserStudyContentRecyclerAdapter  extends BaseAdapter/*BaseQuickAdapter<StudyRecordContent, BaseViewHolder>*/ {

    private ArrayList<StudyRecordContent> list;

    public UserStudyContentRecyclerAdapter(ArrayList<StudyRecordContent> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list == null)
            list = new ArrayList<>();
        return list.size();
    }

    @Override
    public StudyRecordContent getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        VHolder vh = null;
        if (view == null) {
            vh = new VHolder();
            view = View.inflate(view.getContext(), R.layout.item_study_content, null);
            vh.course_title = (TextView) view.findViewById(R.id.courses_title);
            vh.cb = (ImageView) view.findViewById(R.id.cb);
            vh.video_title = (TextView) view.findViewById(R.id.video_title);
            vh.video_time = (TextView) view.findViewById(R.id.video_time);
            view.setTag(vh);
        } else
            vh = (VHolder) view.getTag();
        final StudyRecordContent srb = getItem(position);
        try {
            vh.course_title.setText(srb.getVideo_info().getVideo_title());
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*if (findViewById(R.id.delete_record) != null)
            if (((TextView) findViewById(R.id.delete_record)).getText().toString().isEmpty()) {
                vh.cb.setVisibility(View.GONE);
            } else {
                vh.cb.setVisibility(View.VISIBLE);
                if (srb.isSelect())
                    vh.cb.setBackgroundResource(R.mipmap.ic_choose);
                else
                    vh.cb.setBackgroundResource(R.mipmap.ic_unchoose);
            }*/
        vh.video_title.setText(srb.getVideo_section().getTitle());
        vh.video_time.setText("上次播放记录时长:" + TimeUtils.timeLongToString(Long.parseLong(srb.getTime())));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*TextView right = (TextView) findViewById(R.id.delete_record);
                if (right == null ? delete.getText().toString().indexOf("取消") > -1 : right.getText().toString().equals("取消")) {
                    srb.setSelect(!srb.isSelect());
                    showDelete();
                    notifyDataSetChanged();
                } else {
                    Intent intent = new Intent(mContext, CoursesDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", srb.getVideo_info());
                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(intent);
                }*/
            }
        });
        return view;
    }

    class VHolder {
        private ImageView cb;
        private TextView course_title;
        private TextView video_title;
        private TextView video_time;
    }
/*
    public UserStudyContentRecyclerAdapter() {
        super(R.layout.item_study_content);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, StudyRecordContent bean) {
//        viewHolder.setText(R.id.name, bean.getName());
//        viewHolder.setText(R.id.intro, bean.getIntro());
//        viewHolder.setText(R.id.member_count, "成员:" + bean.getMemberCount());
//        viewHolder.setText(R.id.thread_count, "帖子:" + bean.getThreadCount());
//        GlideLoaderUtil.LoadImage(viewHolder.itemView.getContext(),bean.getLogoUrl(),(ImageView) viewHolder.getView(R.id.group_img));
    }*/



}
