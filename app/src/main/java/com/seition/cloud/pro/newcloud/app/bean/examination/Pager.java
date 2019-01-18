/**
 * Copyright 2017 bejson.com
 */
package com.seition.cloud.pro.newcloud.app.bean.examination;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.jess.arms.base.bean.MBaseBean;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;

import java.util.ArrayList;

/**
 * Auto-generated: 2017-11-14 10:39:31
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Pager extends MBaseBean implements MultiItemEntity {

    private int exams_question_id;//	试题ID
    private int exams_question_type_id;//试题类型ID
    private int exams_subject_id;
    private int exams_point_id;//试题考点ID
    private int exams_module_id;
    private int level;
    private int is_del;
    private int is_collect;//当前用户是否已经收藏试题	1 已收藏 0 未收藏
    private int is_do;//	当前用户是否已经做过该试题	1 已经做过 0 未做过
    private String analyze;//试题解析内容
    private String content;//	试题内容
    private String question_subject;//	试题所属专业
    private String question_subject_fullpath;//试题所属专业分类
    private String exams_module_title;
    private String exams_point_title;//考点名称
    private String level_title;
    private String exams_question_type_title;//试题类型名称
    private long create_time;
    private long update_time;
    private Type_info type_info;
    private ArrayList<AnswerOptionsItem> answer_options;//	试题选项
    private ArrayList<String> answer_true_option;//	正确的答案
    private boolean isNow;//是否为当前选中试题
    private boolean is_right;

    public boolean isRight() {
        return is_right;
    }

    public void setRight(boolean is_right) {
        this.is_right = is_right;
    }

    public boolean isNow() {
        return isNow;
    }

    public void setNow(boolean now) {
        isNow = now;
    }

    public void setExams_question_id(int exams_question_id) {
        this.exams_question_id = exams_question_id;
    }

    public int getExams_question_id() {
        return exams_question_id;
    }

    public void setExams_question_type_id(int exams_question_type_id) {
        this.exams_question_type_id = exams_question_type_id;
    }

    public int getExams_question_type_id() {
        return exams_question_type_id;
    }

    public void setExams_subject_id(int exams_subject_id) {
        this.exams_subject_id = exams_subject_id;
    }

    public int getExams_subject_id() {
        return exams_subject_id;
    }

    public void setExams_point_id(int exams_point_id) {
        this.exams_point_id = exams_point_id;
    }

    public int getExams_point_id() {
        return exams_point_id;
    }

    public void setExams_module_id(int exams_module_id) {
        this.exams_module_id = exams_module_id;
    }

    public int getExams_module_id() {
        return exams_module_id;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setAnswer_options(ArrayList<AnswerOptionsItem> answer_options) {
        this.answer_options = answer_options;
    }

    public ArrayList<AnswerOptionsItem> getAnswer_options() {
        return answer_options;
    }

    public void setAnswer_true_option(ArrayList<String> answer_true_option) {
        this.answer_true_option = answer_true_option;
    }

    public ArrayList<String> getAnswer_true_option() {
        return answer_true_option;
    }

    public void setAnalyze(String analyze) {
        this.analyze = analyze;
    }

    public String getAnalyze() {
        return analyze;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setIs_del(int is_del) {
        this.is_del = is_del;
    }

    public int getIs_del() {
        return is_del;
    }

    public void setQuestion_subject(String question_subject) {
        this.question_subject = question_subject;
    }

    public String getQuestion_subject() {
        return question_subject;
    }

    public void setQuestion_subject_fullpath(String question_subject_fullpath) {
        this.question_subject_fullpath = question_subject_fullpath;
    }

    public String getQuestion_subject_fullpath() {
        return question_subject_fullpath;
    }

    public void setExams_module_title(String exams_module_title) {
        this.exams_module_title = exams_module_title;
    }

    public String getExams_module_title() {
        return exams_module_title;
    }

    public void setExams_point_title(String exams_point_title) {
        this.exams_point_title = exams_point_title;
    }

    public String getExams_point_title() {
        return exams_point_title;
    }

    public void setLevel_title(String level_title) {
        this.level_title = level_title;
    }

    public String getLevel_title() {
        return level_title;
    }

    public void setType_info(Type_info type_info) {
        this.type_info = type_info;
    }

    public Type_info getType_info() {
        return type_info;
    }

    public void setExams_question_type_title(String exams_question_type_title) {
        this.exams_question_type_title = exams_question_type_title;
    }

    public String getExams_question_type_title() {
        return exams_question_type_title;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_do(int is_do) {
        this.is_do = is_do;
    }

    public int getIs_do() {
        return is_do;
    }

    @Override
    public int getItemType() {
        return MessageConfig.ANSWER_SHEET_QUESTION;
    }


//    public Pager(JSONObject item) {
//        if (item.has("exams_question_id")) setExams_question_id(item.optInt("exams_question_id"));
//        if (item.has("exams_question_type_id"))
//            setExams_question_type_id(item.optInt("exams_question_type_id"));
//        if (item.has("exams_subject_id")) setExams_subject_id(item.optInt("exams_subject_id"));
//        if (item.has("exams_point_id")) setExams_point_id(item.optInt("exams_point_id"));
//        if (item.has("exams_module_id")) setExams_module_id(item.optInt("exams_module_id"));
//        if (item.has("is_collect")) setIs_collect(item.optInt("is_collect"));
//        if (item.has("is_do")) setIs_do(item.optInt("is_do"));
//        if (item.has("level")) setLevel(item.optInt("level"));
//        if (item.has("is_del")) setIs_del(item.optInt("is_del"));
//        if (item.has("create_time")) setCreate_time(item.optLong("create_time"));
//        if (item.has("update_time")) setUpdate_time(item.optLong("update_time"));
//        if (item.has("content")) setContent(item.optString("content"));
//        if (item.has("analyze")) setAnalyze(item.optString("analyze"));
//        if (item.has("question_subject")) setQuestion_subject(item.optString("question_subject"));
//        if (item.has("question_subject_fullpath"))
//            setQuestion_subject_fullpath(item.optString("question_subject_fullpath"));
//        if (item.has("exams_module_title"))
//            setExams_module_title(item.optString("exams_module_title"));
//        if (item.has("exams_point_title"))
//            setExams_point_title(item.optString("exams_point_title"));
//        if (item.has("exams_question_type_title"))
//            setExams_question_type_title(item.optString("exams_question_type_title"));
//        if (item.has("create_time")) setCreate_time(item.optLong("create_time"));
//        if (item.has("update_time")) setUpdate_time(item.optLong("update_time"));
//        if (item.has("type_info")) setType_info(new Type_info(item.optJSONObject("type_info")));
//        if (item.has("answer_true_option")) {
//            JSONArray array = item.optJSONArray("answer_true_option");
//            ArrayList<String> list = new ArrayList<>();
//            for (int i = 0; i < array.length(); i++)
//                list.add(array.optString(i));
//            setAnswer_true_option(list);
//        }
//        if (item.has("answer_options")) {
//            ArrayList<AnswerOptionsItem> list = new ArrayList<>();
//            switch (getType_info().getQuestion_type_key()) {
//                case ExamConfig.ESSAYS:
//                    list.add(new AnswerOptionsItem("", ""));
//                    break;
//                case ExamConfig.COMPLETION:
//                    for (int i = 0; i < getAnswer_true_option().size(); i++)
//                        list.add(new AnswerOptionsItem(i + 1 + "、", ""));
//                    break;
//                default:
//                    JSONArray answer_options = item.optJSONArray("answer_options");
//                    for (int i = 0; i < answer_options.length(); i++) {
//                        list.add(new AnswerOptionsItem(answer_options.optJSONObject(i)));
//                    }
////                    try {
////                        JsonParser parser = new JsonParser();
////                        String str = item.optString("answer_options");
////                        JsonObject jsonObj = parser.parse(str).getAsJsonObject();
////                        Iterator i$ = jsonObj.entrySet().iterator();
////                        while (i$.hasNext()) {
////                            Map.Entry entry = (Map.Entry) i$.next();
////                            list.add(new AnswerOptionsItem(entry.getKey().toString(), entry.getValue().toString()));
////                        }
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }
//            }
//            setAnswer_options(list);
//        }
//    }

}