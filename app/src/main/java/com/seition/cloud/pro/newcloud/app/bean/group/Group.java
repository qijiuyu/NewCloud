package com.seition.cloud.pro.newcloud.app.bean.group;

import java.io.Serializable;

public class Group implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id; //小组ID编号
	private String uid;
	private String name;
	private String intro;
	private String logo;
	private String announce;

	private String cid0; //小组分类ID编号
	private String cid1;
	private String membercount;
	private String threadcount;
	private String type;
	private String need_invite;
	private String need_verify;
	private String actor_level;
	private String brower_level;

	private String openWeibo;
	private String openBlog;
	private String openUploadFile;
	private String whoUploadFile;
	private String whoDownloadFile;
	private String openAlbum;
	private String whoCreateAlbum;
	private String whoUploadPic;
	private String anno;
	private String ipshow;
	private String invitepriv;
	private String createalbumpriv;
	private String uploadpicpriv;
	private String ctime;
	private String mtime;
	private String status;
	private String isrecom;
	private String is_del;
	private int is_join;//是否已经加入该小组 	1:已经加入 0:未加入
	private int is_admin;  	//是否有管理权限	1:是 0:否
	private String logourl;

	/*
	* 以下字段小组信息接口才会返回
	*/
	private String cname0;
	private String cname1;
	private String type_name;
	private String path;

	public String getMembercount() {
		return membercount;
	}

	public void setMembercount(String membercount) {
		this.membercount = membercount;
	}

	public String getThreadcount() {
		return threadcount;
	}

	public void setThreadcount(String threadcount) {
		this.threadcount = threadcount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNeed_invite() {
		return need_invite;
	}

	public void setNeed_invite(String need_invite) {
		this.need_invite = need_invite;
	}

	public String getNeed_verify() {
		return need_verify;
	}

	public void setNeed_verify(String need_verify) {
		this.need_verify = need_verify;
	}

	public String getActor_level() {
		return actor_level;
	}

	public void setActor_level(String actor_level) {
		this.actor_level = actor_level;
	}

	public String getBrower_level() {
		return brower_level;
	}

	public void setBrower_level(String brower_level) {
		this.brower_level = brower_level;
	}

	public String getOpenWeibo() {
		return openWeibo;
	}

	public void setOpenWeibo(String openWeibo) {
		this.openWeibo = openWeibo;
	}

	public String getOpenBlog() {
		return openBlog;
	}

	public void setOpenBlog(String openBlog) {
		this.openBlog = openBlog;
	}

	public String getOpenUploadFile() {
		return openUploadFile;
	}

	public void setOpenUploadFile(String openUploadFile) {
		this.openUploadFile = openUploadFile;
	}

	public String getWhoUploadFile() {
		return whoUploadFile;
	}

	public void setWhoUploadFile(String whoUploadFile) {
		this.whoUploadFile = whoUploadFile;
	}

	public String getWhoDownloadFile() {
		return whoDownloadFile;
	}

	public void setWhoDownloadFile(String whoDownloadFile) {
		this.whoDownloadFile = whoDownloadFile;
	}

	public String getOpenAlbum() {
		return openAlbum;
	}

	public void setOpenAlbum(String openAlbum) {
		this.openAlbum = openAlbum;
	}

	public String getWhoCreateAlbum() {
		return whoCreateAlbum;
	}

	public void setWhoCreateAlbum(String whoCreateAlbum) {
		this.whoCreateAlbum = whoCreateAlbum;
	}

	public String getWhoUploadPic() {
		return whoUploadPic;
	}

	public void setWhoUploadPic(String whoUploadPic) {
		this.whoUploadPic = whoUploadPic;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public String getIpshow() {
		return ipshow;
	}

	public void setIpshow(String ipshow) {
		this.ipshow = ipshow;
	}

	public String getInvitepriv() {
		return invitepriv;
	}

	public void setInvitepriv(String invitepriv) {
		this.invitepriv = invitepriv;
	}

	public String getCreatealbumpriv() {
		return createalbumpriv;
	}

	public void setCreatealbumpriv(String createalbumpriv) {
		this.createalbumpriv = createalbumpriv;
	}

	public String getUploadpicpriv() {
		return uploadpicpriv;
	}

	public void setUploadpicpriv(String uploadpicpriv) {
		this.uploadpicpriv = uploadpicpriv;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
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

	public int getIs_join() {
		return is_join;
	}

	public void setIs_join(int is_join) {
		this.is_join = is_join;
	}

	public int getIs_admin() {
		return is_admin;
	}

	public void setIs_admin(int is_admin) {
		this.is_admin = is_admin;
	}

	public String getCname0() {
		return cname0;
	}

	public void setCname0(String cname0) {
		this.cname0 = cname0;
	}

	public String getCname1() {
		return cname1;
	}

	public void setCname1(String cname1) {
		this.cname1 = cname1;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getAnnounce() {
		return announce;
	}

	public void setAnnounce(String announce) {
		this.announce = announce;
	}

	public String getCid1() {
		return cid1;
	}

	public void setCid1(String cid1) {
		this.cid1 = cid1;
	}

	public String getLogourl() {
		return logourl;
	}

	public void setLogourl(String logourl) {
		this.logourl = logourl;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCid0() {
		return cid0;
	}
	public void setCid0(String cid0) {
		this.cid0 = cid0;
	}
	public String getLogoUrl() {
		return logourl;
	}
	public void setLogoUrl(String group_logo_url) {
		this.logourl = group_logo_url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getMemberCount() {
		return membercount;
	}
	public void setMemberCount(String memberCount) {
		this.membercount = memberCount;
	}
	public String getThreadCount() {
		return threadcount;
	}
	public void setThreadCount(String threadCount) {
		this.threadcount = threadCount;
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
