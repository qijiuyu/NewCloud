package com.seition.cloud.pro.newcloud.app.bean.group;

import java.io.Serializable;
import java.util.ArrayList;

public class GroupMember implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String gid;
	private String uid;
	private String name;
	private String reason;
	private String status;
	private String level;
	private String ctime;
	private String mtime;
	private String uname;
	private int is_admin;
	private String space_url;
	private GroupMemberFollowState  follow_state;
	private ArrayList<GroupMemberProfile> profile;
	private String avatar_big;
	private String avatar_middle;
	private String avatar_small;
	private String sex;
	private String intro;
	private GroupMemberProfileCountInfo count_info;


	public int getIs_admin() {
		return is_admin;
	}

	public void setIs_admin(int is_admin) {
		this.is_admin = is_admin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getSpace_url() {
		return space_url;
	}

	public void setSpace_url(String space_url) {
		this.space_url = space_url;
	}

	public GroupMemberFollowState getFollow_state() {
		return follow_state;
	}

	public void setFollow_state(GroupMemberFollowState follow_state) {
		this.follow_state = follow_state;
	}

	public ArrayList<GroupMemberProfile> getProfile() {
		return profile;
	}

	public void setProfile(ArrayList<GroupMemberProfile> profile) {
		this.profile = profile;
	}

	public String getAvatar_big() {
		return avatar_big;
	}

	public void setAvatar_big(String avatar_big) {
		this.avatar_big = avatar_big;
	}

	public String getAvatar_middle() {
		return avatar_middle;
	}

	public void setAvatar_middle(String avatar_middle) {
		this.avatar_middle = avatar_middle;
	}

	public String getAvatar_small() {
		return avatar_small;
	}

	public void setAvatar_small(String avatar_small) {
		this.avatar_small = avatar_small;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public GroupMemberProfileCountInfo getCount_info() {
		return count_info;
	}

	public void setCount_info(GroupMemberProfileCountInfo count_info) {
		this.count_info = count_info;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getMtime() {
		return mtime;
	}

	public void setMtime(String mtime) {
		this.mtime = mtime;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
