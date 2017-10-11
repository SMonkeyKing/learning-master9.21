package com.zju.model;

import javax.persistence.*;

/**
 * Created by lujie on 2017/10/6.
 */
@Entity
@Table(name = "paper_test_question")
public class PaperTestQuestionDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private Integer paperid;

    private Integer questionid;

    private String answer;

    @Column(name = "stu_ans")
    private String stuAns;

    @Column(name = "correct_num")
    private Integer correctNum=0;

    @Column(name = "correct_rate")
    private String correctRate;

    private Integer doNum=0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPaperid() {
        return paperid;
    }

    public void setPaperid(Integer paperid) {
        this.paperid = paperid;
    }

    public Integer getQuestionid() {
        return questionid;
    }

    public void setQuestionid(Integer questionid) {
        this.questionid = questionid;
    }

    public Integer getCorrectNum() {
        return correctNum;
    }

    public void setCorrectNum(Integer correctNum) {
        this.correctNum = correctNum;
    }

    public String getCorrectRate() {
        return correctRate;
    }

    public void setCorrectRate(String correctRate) {
        this.correctRate = correctRate;
    }

    public Integer getDoNum() {
        return doNum;
    }

    public void setDoNum(Integer doNum) {
        this.doNum = doNum;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getStuAns() {
        return stuAns;
    }

    public void setStuAns(String stuAns) {
        this.stuAns = stuAns;
    }
}
