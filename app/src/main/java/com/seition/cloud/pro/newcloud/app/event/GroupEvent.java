package com.seition.cloud.pro.newcloud.app.event;

import com.seition.cloud.pro.newcloud.app.bean.group.Group;

/**
 * Created by YoKeyword on 16/6/5.
 */
public class GroupEvent {
    public Group group;

    public GroupEvent(Group group) {
        this.group = group;
    }
}
