package com.zju.model;

import com.zju.utils.jpa.BaseDomain;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

/**
 * Created by lujie on 2017/8/13.
 *
 * 选择题实体
 */
@Entity
@Table(name = "tk_question")
public class QuestionDO extends BaseDomain {


    /*@Id
    @GenericGenerator(name = "PKUUID", strategy = "uuid2")
    @GeneratedValue(generator = "PKUUID")
    @Column(length = 36)*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private Integer textbook;

    private Integer subject;

    private String content;

    private String answer;

    //题型：选择题-1，填空题-2，计算题-3
    private Integer type;

    @Column(name = "judge_answer" )
    private String judgeAnswer;

    private Integer typeid;

    //答题人数
    @Column(name = "ans_num" )
    private Integer ansNum;

    //正确人数
    @Column(name = "correct_num" )
    private Integer correctNum;

    //正确率
    @Column(name = "correct_rate" )
    private String correctRate;

    //是否已加入单元测试
    @Column(name = "unit_flag" )
    private Integer unitFlag;


    //是否已加入同步测试
    @Column(name = "sync_flag" )
    private Integer syncFlag;

    /*//是否已加入单元测试
    @Column(name = "unit_flag" )
    private Boolean unitFlag = false;


    //是否已加入同步测试
    @Column(name = "sync_flag" )
    private Boolean syncFlag = false;*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTextbook() {
        return textbook;
    }

    public void setTextbook(Integer textbook) {
        this.textbook = textbook;
    }

    public Integer getSubject() {
        return subject;
    }

    public void setSubject(Integer subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getJudgeAnswer() {
        return judgeAnswer;
    }

    public void setJudgeAnswer(String judgeAnswer) {
        this.judgeAnswer = judgeAnswer;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public Integer getAnsNum() {
        return ansNum;
    }

    public void setAnsNum(Integer ansNum) {
        this.ansNum = ansNum;
    }

    public String getCorrectRate() {
        return correctRate;
    }

    public void setCorrectRate(String correctRate) {
        this.correctRate = correctRate;
    }

    public Integer getCorrectNum() {
        return correctNum;
    }

    public void setCorrectNum(Integer correctNum) {
        this.correctNum = correctNum;
    }

    public Integer getUnitFlag() {
        return unitFlag;
    }

    public void setUnitFlag(Integer unitFlag) {
        this.unitFlag = unitFlag;
    }

    public Integer getSyncFlag() {
        return syncFlag;
    }

    public void setSyncFlag(Integer syncFlag) {
        this.syncFlag = syncFlag;
    }

    /*public Boolean getUnitFlag() {
        return unitFlag;
    }

    public void setUnitFlag(Boolean unitFlag) {
        this.unitFlag = unitFlag;
    }

    public Boolean getSyncFlag() {
        return syncFlag;
    }

    public void setSyncFlag(Boolean syncFlag) {
        this.syncFlag = syncFlag;
    }*/
}
