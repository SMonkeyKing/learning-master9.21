package com.zju.model;

import com.zju.utils.jpa.BaseDomain;

import javax.persistence.*;

/**
 * Created by lujie on 2017/9/28.
 * 自主训练--单元测试
 */
@Entity
@Table(name = "zzxl_unit_test")
public class UnitTestFromTKDO extends BaseDomain{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "question_id" )
    private Integer questionId;

    @Column(name = "type_id" )
    private Integer typeId;

    @Column(name = "question_content" )
    private String questionContent;

    @Column(name = "question_answer")
    private String questionAnswer;

    //题目类型
    @Column(name = "question_type")
    private Integer questionType;

    //学生答案
    @Column(name = "stu_ans")
    private String stuAns;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public String getStuAns() {
        return stuAns;
    }

    public void setStuAns(String stuAns) {
        this.stuAns = stuAns;
    }
}
