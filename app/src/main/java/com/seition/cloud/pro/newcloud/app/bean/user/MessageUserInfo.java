package com.seition.cloud.pro.newcloud.app.bean.user;

import com.jess.arms.base.bean.DataBean;

import java.util.ArrayList;

public class MessageUserInfo extends DataBean<MessageUserInfo> {
    private int uid; // 用户标识
    private String login;
    private String uname;
    private String email = "";
    private String phone = ""; // 用户手机号
    private String sex; // 用户性别，1.男 2.女
    private String background_id;
    private String location; // 用户所在城市
    private String is_audit; // 是否通过审核 0.未通过 1.已通过
    private String is_active; // 是否已激活
    private String is_init; // 是否初始化用户资料
    private String ctime; // 注册时间
    private String reg_ip;
    private String browser;
    private String browser_ver;
    private String os;
    private String place;
    private String identity; // 身份标识(1.用户 2.组织)
    private String api_key;
    private String domain;
    private String province; // 省ID，关联省份数据
    private String city;
    private String area;
    private String lang;
    private String timezone;
    private String is_del; // 是否禁用
    private String first_letter; // 是否禁用
    private String intro; // 用户简介
    private String profession; // 用户职业
    private String last_login_time; // 用户最后一次登录时间
    private String last_feed_id;
    private String last_post_time;
    private String search_key;
    private String my_college;
    private String mail_activate; // 邮箱激活状态
    private String login_num;
    private String phone_activate; // 手机激活状态
    private String interest;
    private String mhm_id;
    private String user_face; // 用户头像地址
    private String login_salt;
    private String avatar_original;
    private String avatar_big;
    private String avatar_middle;
    private String avatar_small;
    private String avatar_tiny;
    private String avatar_url;
    private String space_link;
    private String space_link_no;
    private String space_url;
    private boolean is_teacher;

    private ArrayList<UserGroup> api_user_group;
    private ArrayList<UserGroup> user_group;

    public boolean isIs_teacher() {
        return is_teacher;
    }

    public void setIs_teacher(boolean is_teacher) {
        this.is_teacher = is_teacher;
    }

    public String getSpace_link_no() {
        return space_link_no;
    }

    public void setSpace_link_no(String space_link_no) {
        this.space_link_no = space_link_no;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin_salt() {
        return login_salt;
    }

    public void setLogin_salt(String login_salt) {
        this.login_salt = login_salt;
    }

    public String getBackground_id() {
        return background_id;
    }

    public void setBackground_id(String background_id) {
        this.background_id = background_id;
    }

    public String getIs_audit() {
        return is_audit;
    }

    public void setIs_audit(String is_audit) {
        this.is_audit = is_audit;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getIs_init() {
        return is_init;
    }

    public void setIs_init(String is_init) {
        this.is_init = is_init;
    }

    public String getReg_ip() {
        return reg_ip;
    }

    public void setReg_ip(String reg_ip) {
        this.reg_ip = reg_ip;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getBrowser_ver() {
        return browser_ver;
    }

    public void setBrowser_ver(String browser_ver) {
        this.browser_ver = browser_ver;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getLast_feed_id() {
        return last_feed_id;
    }

    public void setLast_feed_id(String last_feed_id) {
        this.last_feed_id = last_feed_id;
    }

    public String getLast_post_time() {
        return last_post_time;
    }

    public void setLast_post_time(String last_post_time) {
        this.last_post_time = last_post_time;
    }

    public String getSearch_key() {
        return search_key;
    }

    public void setSearch_key(String search_key) {
        this.search_key = search_key;
    }

    public String getAvatar_original() {
        return avatar_original;
    }

    public void setAvatar_original(String avatar_original) {
        this.avatar_original = avatar_original;
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

    public String getAvatar_tiny() {
        return avatar_tiny;
    }

    public void setAvatar_tiny(String avatar_tiny) {
        this.avatar_tiny = avatar_tiny;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getSpace_url() {
        return space_url;
    }

    public void setSpace_url(String space_url) {
        this.space_url = space_url;
    }

    public String getSpace_link() {
        return space_link;
    }

    public void setSpace_link(String space_link) {
        this.space_link = space_link;
    }


    public int getUid() {
        return uid;
    }

    public void setUid(int mId) {
        this.uid = mId;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String mEmail) {
        this.email = mEmail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String mPhone) {
        this.phone = mPhone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String mSex) {
        this.sex = mSex;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String mLocation) {
        this.location = mLocation;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String mCtime) {
        this.ctime = mCtime;
    }


    public String getIntro() {
        return intro;
    }

    public void setIntro(String mIntro) {
        this.intro = mIntro;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String mProfession) {
        this.profession = mProfession;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getIs_del() {
        return is_del;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }

    public String getFirst_letter() {
        return first_letter;
    }

    public void setFirst_letter(String first_letter) {
        this.first_letter = first_letter;
    }

    public String getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String getPhone_activate() {
        return phone_activate;
    }

    public void setPhone_activate(String phone_activate) {
        this.phone_activate = phone_activate;
    }

    public String getMail_activate() {
        return mail_activate;
    }

    public void setMail_activate(String mail_activate) {
        this.mail_activate = mail_activate;
    }

    public String getUser_face() {
        return user_face;
    }

    public void setUser_face(String user_face) {
        this.user_face = user_face;
    }


    public String getMy_college() {
        return my_college;
    }

    public void setMy_college(String my_college) {
        this.my_college = my_college;
    }

    public String getLogin_num() {
        return login_num;
    }

    public void setLogin_num(String login_num) {
        this.login_num = login_num;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getMhm_id() {
        return mhm_id;
    }

    public void setMhm_id(String mhm_id) {
        this.mhm_id = mhm_id;
    }


    public ArrayList<UserGroup> getApi_user_group() {
        return api_user_group;
    }

    public void setApi_user_group(ArrayList<UserGroup> api_user_group) {
        this.api_user_group = api_user_group;
    }

    public ArrayList<UserGroup> getUser_group() {
        return user_group;
    }

    public void setUser_group(ArrayList<UserGroup> user_group) {
        this.user_group = user_group;
    }
}
