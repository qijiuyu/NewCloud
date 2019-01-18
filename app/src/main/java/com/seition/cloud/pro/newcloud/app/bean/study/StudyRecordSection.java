package com.seition.cloud.pro.newcloud.app.bean.study;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class StudyRecordSection extends SectionEntity<StudyRecordContent> {
//    private String time = null;
    public StudyRecordSection(boolean isHeader, String time, boolean isMroe) {
        super(isHeader, time);

    }

    public StudyRecordSection(StudyRecordContent t) {
        super(t);
    }

//    public String getTime() {
//        return time;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }
}
