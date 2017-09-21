package com.zju.model;

import com.zju.utils.jpa.BaseDomain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by lujie on 2017/8/26.
 * 教学互动——学生回答
 */
@Entity
@Table(name = "jxhd_student_answer")
public class JxhdStudentAnswerDO extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "paper_id")
    private Integer paperId;

    private String userno;

    private String username;

    private String answer;

    @Column(name = "correct_answer")
    private String correctAnswer;

    private Integer correct;

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
}
