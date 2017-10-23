package com.zju.model;

import com.zju.utils.jpa.BaseDomain;

import javax.persistence.*;

/**
 * Created by lujie on 2017/8/26.
 */

public class JxhdStudentAnswerPhoneDO {
    private Integer id;

    private Integer paperId;

    private String userno;

    private String username;

    //如果是拍照上传，传过来的answer是一张图片，correctAnswer为空
    private String answer;

    private String correctAnswer;

    private Integer correct;

    private Integer questionType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public String getUserno() {
        return userno;
    }

    public void setUserno(String userno) {
        this.userno = userno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }
}
