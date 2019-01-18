package com.seition.cloud.pro.newcloud.app.config;

/**
 * Created by addis on 14/03/2018.
 */

public class MessageConfig {
    //download
    public static final String TYPE_BOOK = "Library-Cache";//文库
    public static final String TYPE_VIDEO = "Video-Cache";//视频
    public static final String FROM_COURSE = "from-course";//来自课程
    public static final String FROM_DOWNLOAD = "from-download";//来自下载
    public final static int UNDOWNLOAD = 0x00000076;//未开始
    public final static int DOWNLOADING = 0x00000075;//下载中
    public final static int DOWNLOADFINISH = 0x00000074;//已下载完成
    public static final int SEITION_ITEM = 0x00000073;//课程章节列表内章节
    public static final int VIDEO_ITEM = 0x00000072;//课程章节列表内课时
    public static final int ANSWER_SHEET_TYPE = 0x00000071;//考试答题卡，题目类型
    public static final int ANSWER_SHEET_QUESTION = 0x00000072;//考试答题卡，题目

    public static final int Data_Error = 0x317;//数据错误
    public static final int Net_Error = 0x318;//网络访问失败

    public static final int Net_Successful = 0x316;//网络访问成功

    public static final int ToastShow = 0x319;//显示信息
    public static final int EXIT = 0x320;//退出
    public static final int ToastShowAndExit = 0x321;//显示信息并退出

    /**
     * 讲师
     */
    public static final String TEACHER_GETINFO = "teacher-getInfo";
    /**
     * 课程
     */
    public static final String COURSE_INFO = "course-info";
    public static final String LIVE_INFO = "live-info";
    public static final String COURSE_SEITION_LIST = "course-SEITION-LIST";
    /**
     * 考试
     */
    public static final String START_EXAMINATION = "exam-start";
    public static final String START_EXAMINATION_TYPE = "exam-start-type";
    public static final String START_EXAMINATION_IS_TEST = "isTest";
    public static final String EXAM_CLASSIFY_LIST_CACHE_NAME = "examClassifyList";
    public static final String EXAM_LIST_CACHE_NAME = "examList";
    public static final String EXAM_COMMIT = "updata_exam_dara";
    public static final String EXAM_SAVE = "save_exam_dara";

    /**
     * 资讯
     */
    public static final String NEWS_CLASSIFY_LIST = "news-classify-list";
    public static final String NEWS_LIST = "news-list";
    /**
     * 文库
     */
    public static final String LIBRARY_LIST_CACHE = "library-list-cache";
    public static final String OWNER_LIBRARY_LIST_CACHE = "owner-library-list-cache";

    /**
     * 登录
     */
    public static final String BACK_IS_EXIT = "back-is-exit";
}
