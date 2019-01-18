package com.seition.cloud.pro.newcloud.app.utils.download;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.seition.cloud.pro.newcloud.app.bean.download.CourseCacheBean;
import com.seition.cloud.pro.newcloud.app.bean.download.CourseSeitionCacheBean;
import com.seition.cloud.pro.newcloud.app.bean.download.DownloadBean;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;
import com.seition.cloud.pro.newcloud.app.gen.CourseCacheBeanDao;
import com.seition.cloud.pro.newcloud.app.gen.CourseSeitionCacheBeanDao;
import com.seition.cloud.pro.newcloud.app.gen.DaoMaster;
import com.seition.cloud.pro.newcloud.app.gen.DaoSession;
import com.seition.cloud.pro.newcloud.app.gen.DownloadBeanDao;
import com.seition.cloud.pro.newcloud.app.utils.FileSizeUtil;
import com.seition.cloud.pro.newcloud.home.api.Cache;

import java.io.File;
import java.util.List;

public class DBUtils {
    private final String TAG = "HttpDbUtil";
    public static DBUtils instance;
    private Context context;
    private DownloadBeanDao downloadDao;
    private CourseCacheBeanDao courseDao;
    private CourseSeitionCacheBeanDao seitionDao;
    private SQLiteDatabase sqLiteDatabase;
    private final long UPDATE_TIME = 1000 * 3;//3S

    public DBUtils(Context context) {
        this.context = context;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, Cache.DB_NAME, null);
        sqLiteDatabase = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(sqLiteDatabase);
        DaoSession daoSession = daoMaster.newSession();
        downloadDao = daoSession.getDownloadBeanDao();
        courseDao = daoSession.getCourseCacheBeanDao();
        seitionDao = daoSession.getCourseSeitionCacheBeanDao();
    }

    public void delectCache() {
        deleteDirWihtFile(new File(Cache.Cache_Video), false);
        deleteDirWihtFile(new File(Cache.Cache_File), false);
    }

    /**
     * @param dir
     * @param isDelectMyself 是否删除自己
     */
    public void deleteDirWihtFile(File dir, boolean isDelectMyself) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        try {
            for (File file : dir.listFiles()) {
                if (file.isFile())
                    file.delete(); // 删除所有文件
                else if (file.isDirectory())
                    deleteDirWihtFile(file, true); // 递规的方式删除文件夹
            }
            if (isDelectMyself)
                dir.delete();// 删除目录本身
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String calculatingCacheSize() {
        return FileSizeUtil.getAutoFileOrFilesSize(Cache.Cache_Local);
    }

    public static DBUtils init(Context context) {
        if (instance == null) {
            instance = new DBUtils(context.getApplicationContext());
        }
        return instance;
    }

    public void cleanData() {
        downloadDao.deleteAll();
    }

    /**
     * 查找文库下载列表
     *
     * @return
     */
    public List<DownloadBean> queryDownloadBookList() {
        List<DownloadBean> data = downloadDao.
                queryBuilder().where(DownloadBeanDao.Properties.FileType.eq(MessageConfig.TYPE_BOOK))
                .orderAsc(DownloadBeanDao.Properties.FileType).list();
        return data;
    }

    public List<DownloadBean> queryDownloadList() {
        List<DownloadBean> data = downloadDao.
                queryBuilder().build().list();
        return data;
    }

    public List<CourseCacheBean> queryCourseList() {
        List<CourseCacheBean> data = courseDao.
                queryBuilder().build().list();
        return data;
    }

    /**
     * 根据cacheKey查数据
     *
     * @param info
     * @return
     */
    public List<DownloadBean> queryCacheKey(DownloadBean info) {
        List<DownloadBean> data = downloadDao.
                queryBuilder().where(DownloadBeanDao.Properties.CacheId.eq(info.getCacheId()))
                .orderAsc(DownloadBeanDao.Properties.CacheId)
                .list();
        return data;
    }

    /**
     * 根据cacheKey查数据
     *
     * @param key
     * @return
     */
    public DownloadBean queryCacheKey(long key) {
        return downloadDao.load(new Long(key));
    }

    /**
     * 根据cacheKey查数据并转换为DownloadInfo
     *
     * @param bean
     * @return
     */
    public DownloadBean queryCacheKeyOne(DownloadBean bean) {
        List<DownloadBean> beans = queryCacheKey(bean);
        if (beans.size() == 0) return bean;
        else return beans.get(0);
    }

    /**
     * 插入一条下载记录
     *
     * @param bean
     * @return key 行号
     */
    public DownloadBean insert(DownloadBean bean) {
        if (bean.getKey() != null && bean.getKey() != 0) {
            return bean;
        }
        List<DownloadBean> beans = queryCacheKey(bean);
        if (beans.size() == 0) {
            return queryCacheKey(downloadDao.insert(bean));
        } else return beans.get(0);
    }

    /**
     * 更新通用方法，无延时
     *
     * @param bean
     */
    public void update(DownloadBean bean) {
        downloadDao.update(bean);
    }

    /**
     * 删除下载记录
     *
     * @param bean
     */
    public void delete(DownloadBean bean) {
        downloadDao.delete(bean);
    }

    /**
     * 更新通用方法，无延时
     *
     * @param bean
     */
    public void update(CourseCacheBean bean) {
        courseDao.update(bean);
    }

    /**
     * 删除下载记录
     *
     * @param bean
     */
    public void delete(CourseCacheBean bean) {
        courseDao.delete(bean);
    }

    /**
     * 更新通用方法，无延时
     *
     * @param bean
     */
    public void update(CourseSeitionCacheBean bean) {
        seitionDao.update(bean);
    }

    /**
     * 删除下载记录
     *
     * @param bean
     */
    public void delete(CourseSeitionCacheBean bean) {
        seitionDao.delete(bean);
    }

    /**
     * 根据cacheKey查数据
     *
     * @return
     */
    public List<CourseCacheBean> queryCacheKey(String vid) {
        List<CourseCacheBean> data = courseDao.
                queryBuilder().where(CourseCacheBeanDao.Properties.CourseId.eq(vid))
                .orderAsc(CourseCacheBeanDao.Properties.CourseId)
                .list();
        return data;
    }

    public CourseCacheBean insert(CourseCacheBean course) {
        if (course.getKey() != null && course.getKey() != 0) {
            update(course);
            return course;
        }
        List<CourseCacheBean> beans = queryCacheKey(course.getCourseId());
        if (beans.size() == 0) {
            return courseDao.load(courseDao.insert(course));
        } else return beans.get(0);
    }

    public CourseCacheBean getCourseData(String courseId) {
        List<CourseCacheBean> data = courseDao.
                queryBuilder().where(CourseCacheBeanDao.Properties.CourseId.eq(courseId))
                .orderAsc(CourseCacheBeanDao.Properties.CourseId)
                .list();
        if (data == null || data.size() == 0) return null;
        return data.get(0);
    }

    /**
     * 根据课程id查找章节列表
     *
     * @param courseId
     * @return
     */
    public List<CourseSeitionCacheBean> querySeitionByCourse(int courseId) {
        List<CourseSeitionCacheBean> data = seitionDao.
                queryBuilder().where(CourseSeitionCacheBeanDao.Properties.Vid.gt(courseId))
                .orderAsc(CourseSeitionCacheBeanDao.Properties.Vid).list();
        return data;
    }

    /**
     * 保存下载的课程数据信息
     *
     * @param course
     */
    public void saveCourse(CourseCacheBean course) {
        CourseCacheBean tampCourse = getCourseData(course.getCourseId());
        if (tampCourse == null)
            insert(course);
        else update(tampCourse.updata(course));
    }

    /**
     * 根据课程id查找视频
     *
     * @param seitionId
     * @return
     */
    public List<DownloadBean> queryDownloadVideoBySeition(int seitionId) {
        List<DownloadBean> data = downloadDao.
                queryBuilder().where(DownloadBeanDao.Properties.PId.gt(seitionId))
                .orderAsc(DownloadBeanDao.Properties.PId).list();
        return data;
    }
}
