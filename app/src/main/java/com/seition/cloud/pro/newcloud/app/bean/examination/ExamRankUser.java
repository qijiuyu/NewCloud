package com.seition.cloud.pro.newcloud.app.bean.examination;

import com.jess.arms.base.bean.DataBean;

import java.util.ArrayList;

public class ExamRankUser extends DataBean<ExamRankUser> {

    private ArrayList<RankUser> list;
    private RankUser now;
//    private RankAvg avg;

    public class RankUser{
        private String uid;
        private String username;
        private String userface;
        private double score;
        private String rank_nomber;

        public int getAnser_time() {
            return anser_time;
        }

        public void setAnser_time(int anser_time) {
            this.anser_time = anser_time;
        }

        private int anser_time;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUserface() {
            return userface;
        }

        public void setUserface(String userface) {
            this.userface = userface;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public String getRank_nomber() {
            return rank_nomber;
        }

        public void setRank_nomber(String rank_nomber) {
            this.rank_nomber = rank_nomber;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }

    public class RankAvg{
        private int avg;
        private String transcend_rate;

        public int getAvg() {
            return avg;
        }

        public void setAvg(int avg) {
            this.avg = avg;
        }

        public String getTranscend_rate() {
            return transcend_rate;
        }

        public void setTranscend_rate(String transcend_rate) {
            this.transcend_rate = transcend_rate;
        }
    }


    public ArrayList<RankUser> getList() {
        return list;
    }

    public void setList(ArrayList<RankUser> list) {
        this.list = list;
    }

    public RankUser getNow() {
        return now;
    }

    public void setNow(RankUser now) {
        this.now = now;
    }


}
