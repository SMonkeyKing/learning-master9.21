package com.zju.model;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by lujie on 2017/9/5.
 * 互动模块选择题答案统计
 */

public class JudgeAnswerDO implements Serializable{

    private Integer paper_id;
    private Integer answerA;
    private Integer answerB;
    private Integer answerC;
    private Integer answerD;
    //答对人数
    private Integer correct;
    //正确率
    private double rate;

    public Integer getPaper_id() {
        return paper_id;
    }

    public void setPaper_id(Integer paper_id) {
        this.paper_id = paper_id;
    }

    public Integer getAnswerA() {
        return answerA;
    }

    public void setAnswerA(Integer answerA) {
        this.answerA = answerA;
    }

    public Integer getAnswerB() {
        return answerB;
    }

    public void setAnswerB(Integer answerB) {
        this.answerB = answerB;
    }

    public Integer getAnswerC() {
        return answerC;
    }

    public void setAnswerC(Integer answerC) {
        this.answerC = answerC;
    }

    public Integer getAnswerD() {
        return answerD;
    }

    public void setAnswerD(Integer answerD) {
        this.answerD = answerD;
    }

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
