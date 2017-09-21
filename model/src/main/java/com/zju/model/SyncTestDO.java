package com.zju.model;

import com.zju.utils.jpa.BaseDomain;

import javax.persistence.*;

/**
 * Created by lujie on 2017/8/19.
 * 同步测试
 */
@Entity
@Table(name = "tbxa_sync_test")
public class SyncTestDO extends BaseDomain{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private String name;

    private String url;

    @Column(name = "lp_id" )
    private Integer lpId;

    @Column(name = "answer_url")
    private String answerUrl;

    @Column(name = "xzt_answer")
    private String xztAnswer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getLpId() {
        return lpId;
    }

    public void setLpId(Integer lpId) {
        this.lpId = lpId;
    }

    public String getAnswerUrl() {
        return answerUrl;
    }

    public void setAnswerUrl(String answerUrl) {
        this.answerUrl = answerUrl;
    }

    public String getXztAnswer() {
        return xztAnswer;
    }

    public void setXztAnswer(String xztAnswer) {
        this.xztAnswer = xztAnswer;
    }
}
