package com.seition.cloud.pro.newcloud.app.bean.examination;

/**
 * Created by addis on 2018/9/8.
 */
public class ExamLevel {
    private int levelId;
    private String levelTitle;

    public ExamLevel() {
    }

    public ExamLevel(int levelId, String levelTitle) {
        setLevelId(levelId);
        setLevelTitle(levelTitle);
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public String getLevelTitle() {
        return levelTitle;
    }

    public void setLevelTitle(String levelTitle) {
        this.levelTitle = levelTitle;
    }
}
