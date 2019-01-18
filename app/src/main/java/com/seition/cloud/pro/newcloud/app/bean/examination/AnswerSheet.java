package com.seition.cloud.pro.newcloud.app.bean.examination;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;

import java.util.ArrayList;

/**
 * Created by addis on 2018/6/11.
 */
public class AnswerSheet extends AbstractExpandableItem<Pager> implements MultiItemEntity {
    private String title;

    public AnswerSheet(String title, ArrayList<Pager> pagers) {
        this.title = title;
        if (pagers != null)
            for (int i = 0; i < pagers.size(); i++)
                addSubItem(pagers.get(i));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getItemType() {
        return MessageConfig.ANSWER_SHEET_TYPE;
    }

    @Override
    public int getLevel() {
        return 0;
    }
}
