package com.seition.cloud.pro.newcloud.app.bean.user;


import com.jess.arms.base.bean.DataBean;


/*  用户信息（登陆 注册 首页）*/
public class User extends DataBean<User> {


    /* 登陆*/
    protected int uid; // 用户标识
    private String login;
    protected String uname;
    protected String email = "";
    protected String phone = ""; // 用户手机号
    protected String sex; // 用户性别，1.男 2.女
    private String background_id;
    protected String location; // 用户所在城市
    protected String is_audit; // 是否通过审核 0.未通过 1.已通过
    protected String is_active; // 是否已激活
    protected String is_init; // 是否初始化用户资料
    protected String ctime; // 注册时间
    private String reg_ip;
    private String browser;
    private String browser_ver;
    private String os;
    private String place;
    protected String identity; // 身份标识(1.用户 2.组织)
    protected String api_key;
    private String domain;
    protected String province; // 省ID，关联省份数据
    protected String city;
    protected String area;
    //    private String lang;
//    private String timezone;
    protected String is_del; // 是否禁用
    protected String first_letter; // 是否禁用
    protected String intro; // 用户简介
    protected String profession; // 用户职业
    protected String last_login_time; // 用户最后一次登录时间
    //    private String last_feed_id;
//    private String last_post_time;
    private String search_key;
    private String invite_code;

    //    protected String my_college;
    protected String mail_activate; // 邮箱激活状态
    protected String login_num;
    protected String phone_activate; // 手机激活状态
    //    protected String interest;
    protected String mhm_id;

    private String userface;
    protected String oauth_token; // 用户oauth_token，移动端身份标识
    protected String oauth_token_secret; // 移动端身份密钥
    protected String only_login_key;//唯一登陆标识

//    private String avatar_original;
//    private String avatar_big;
//    private String avatar_middle;

    public String getOnly_login_key() {
        return only_login_key;
    }

    public void setOnly_login_key(String only_login_key) {
        this.only_login_key = only_login_key;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public String getUserface() {
        return userface;
    }

    public void setUserface(String userface) {
        this.userface = userface;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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


    public String getSearch_key() {
        return search_key;
    }

    public void setSearch_key(String search_key) {
        this.search_key = search_key;
    }


    public String getSpace_link() {
        return space_link;
    }

    public void setSpace_link(String space_link) {
        this.space_link = space_link;
    }

    private String space_link;

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


    public String getOauth_token() {
        return oauth_token;
    }

    public void setOauth_token(String oauth_token) {
        this.oauth_token = oauth_token;
    }

    public String getOauth_token_secret() {
        return oauth_token_secret;
    }

    public void setOauth_token_secret(String oauth_token_secret) {
        this.oauth_token_secret = oauth_token_secret;
    }


    public String getLogin_num() {
        return login_num;
    }

    public void setLogin_num(String login_num) {
        this.login_num = login_num;
    }


    public String getMhm_id() {
        return mhm_id;
    }

    public void setMhm_id(String mhm_id) {
        this.mhm_id = mhm_id;
    }


    /*public String getAvatar_original() {
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
    }*/
}
