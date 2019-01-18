package com.seition.cloud.pro.newcloud.app.bean.group;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class GroupMemberFollowState implements Serializable {

	private static final long serialVersionUID = 1L;
	private int following;
	private int follower;

	public GroupMemberFollowState(JSONObject object) {
		try {
			setFollowing(object.getInt("following"));
			setFollower(object.getInt("follower"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public int getFollowing() {
		return following;
	}

	public void setFollowing(int following) {
		this.following = following;
	}

	public int getFollower() {
		return follower;
	}

	public void setFollower(int follower) {
		this.follower = follower;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
