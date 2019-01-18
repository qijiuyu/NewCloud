package com.seition.cloud.pro.newcloud.app.bean.group;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class GroupTheme implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
	private String gid;
	private String uid;
	private String name;
	private String title;
	private String cid;
	private String viewcount;
	private String replaycount;
	private String dist;
	private String top;
	private String lock;
	private String addtime;
	private String replytime;
	private String mtime;
	private String status;
	private String isrecom;
	private String is_del;
	private int is_collect;
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCid0() {
		return cid0;
	}

	public void setCid0(String cid0) {
		this.cid0 = cid0;
	}

	public int getIs_admin() {
		return is_admin;
	}

	public void setIs_admin(int is_admin) {
		this.is_admin = is_admin;
	}

	private String cid0;
	private int is_admin;
	private JSONArray attach;

	public JSONArray getAttach() {
		return attach;
	}

	public void setAttach(JSONArray attach) {
		this.attach = attach;
	}
	public GroupTheme() {}

	public GroupTheme(JSONObject object) {
		try {
			if(object.has("id"))
				setId(object.getString("id"));
			if(object.has("title"))
				setTitle(object.getString("title"));
			if(object.has("name"))
				setName(object.getString("name"));
			if(object.has("viewcount"))
				setViewcount(object.getString("viewcount"));
			if(object.has("replycount"))
				setReplaycount(object.getString("replycount"));
			if(object.has("addtime"))
				setAddtime(object.getString("addtime"));
			if(object.has("attach"))
				setAttach(object.getJSONArray("attach"));
			if(object.has("uid"))
				setUid(object.getString("uid"));
			if(object.has("replytime"))
				setReplytime(object.getString("replytime"));
			if(object.has("status"))
				setStatus(object.getString("status"));
			if(object.has("top"))
				setTop(object.getString("top"));
			if(object.has("dist"))
				setDist(object.getString("dist"));
			if(object.has("cid"))
				setCid(object.getString("cid"));
			if(object.has("gid"))
				setGid(object.getString("gid"));
			if(object.has("is_del"))
				setIs_del(object.getString("is_del"));
			if(object.has("isrecom"))
				setIsrecom(object.getString("isrecom"));
			if(object.has("lock"))
				setLock(object.getString("lock"));
			if(object.has("mtime"))
				setMtime(object.getString("mtime"));
			if(object.has("is_collect"))
				setIs_collect(object.getInt("is_collect"));
			if(object.has("is_admin"))
				setIs_admin(object.getInt("is_admin"));
			if(object.has("cid0"))
				setCid0(object.getString("cid0"));
			if(object.has("content"))
				setContent(object.getString("content"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public int getIs_collect() {
		return is_collect;
	}

	public void setIs_collect(int is_collect) {
		this.is_collect = is_collect;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getViewcount() {
		return viewcount;
	}

	public void setViewcount(String viewcount) {
		this.viewcount = viewcount;
	}

	public String getDist() {
		return dist;
	}

	public void setDist(String dist) {
		this.dist = dist;
	}

	public String getTop() {
		return top;
	}

	public void setTop(String top) {
		this.top = top;
	}

	public String getLock() {
		return lock;
	}

	public void setLock(String lock) {
		this.lock = lock;
	}

	public String getReplytime() {
		return replytime;
	}

	public void setReplytime(String replytime) {
		this.replytime = replytime;
	}

	public String getMtime() {
		return mtime;
	}

	public void setMtime(String mtime) {
		this.mtime = mtime;
	}

	public String getIsrecom() {
		return isrecom;
	}

	public void setIsrecom(String isrecom) {
		this.isrecom = isrecom;
	}

	public String getIs_del() {
		return is_del;
	}

	public void setIs_del(String is_del) {
		this.is_del = is_del;
	}

	public String getReplaycount() {
		return replaycount;
	}
	public void setReplaycount(String replaycount) {
		this.replaycount = replaycount;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}

	
	
}
