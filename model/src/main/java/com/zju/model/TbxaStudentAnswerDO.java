package com.zju.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by lujie on 2017/8/19.
 */
@Entity
@Table(name = "tbxa_student_answer")
public class TbxaStudentAnswerDO implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "test_id")
    private Integer testId;

    private String xzt_answer;

    private String tkjs_answer;

    @Column(name = "correct_num")
    private Integer correctNum;

    @Column(name = "correct_rate")
    private String correctRate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public String getXzt_answer() {
        return xzt_answer;
    }

    public void setXzt_answer(String xzt_answer) {
        this.xzt_answer = xzt_answer;
    }

    public String getTkjs_answer() {
        return tkjs_answer;
    }

    public void setTkjs_answer(String tkjs_answer) {
        this.tkjs_answer = tkjs_answer;
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
}
