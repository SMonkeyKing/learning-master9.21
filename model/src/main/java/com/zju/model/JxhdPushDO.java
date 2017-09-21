package com.zju.model;

import javax.persistence.*;

/**
 * Created by lujie on 2017/8/25.
 */
@Entity
@Table(name = "jxhd_push")
public class JxhdPushDO{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "paper_id")
    private Integer paperId;

    //如果是试卷，则为文件的url，http://localhost/....
    @Column(name = "paper_url")
    private String paperUrl;

    //如果是题目，则为题目的html
    private String question;

    @Column(name = "xzt_answer")
    private String xztAnswer;

    private String answer;

    //题目的类型 1-选择题 2-填空题 3-计算题
    private Integer type;

    @Column(name = "paper_name")
    private String paperName;

    @Column(name = "upload_name")
    private String uploadName;

    //1-试卷 2-题目
    private  Integer category;

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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getXztAnswer() {
        return xztAnswer;
    }

    public void setXztAnswer(String xztAnswer) {
        this.xztAnswer = xztAnswer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getPaperUrl() {
        return paperUrl;
    }

    public void setPaperUrl(String paperUrl) {
        this.paperUrl = paperUrl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public String getUploadName() {
        return uploadName;
    }

    public void setUploadName(String uploadName) {
        this.uploadName = uploadName;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }
}
