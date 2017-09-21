package com.zju.model;

import com.zju.utils.jpa.BaseDomain;

import javax.persistence.*;

/**
 * Created by lujie on 2017/8/17.
 * 暂时没用
 */
@Entity
@Table(name = "stjj_paper")
public class PaperDO extends BaseDomain{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private String xzt_list;

    private String tkt_list;

    private String jst_list;

    private String question_list;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getXzt_list() {
        return xzt_list;
    }

    public void setXzt_list(String xzt_list) {
        this.xzt_list = xzt_list;
    }

    public String getTkt_list() {
        return tkt_list;
    }

    public void setTkt_list(String tkt_list) {
        this.tkt_list = tkt_list;
    }

    public String getJst_list() {
        return jst_list;
    }

    public void setJst_list(String jst_list) {
        this.jst_list = jst_list;
    }

    public String getQuestion_list() {
        return question_list;
    }

    public void setQuestion_list(String question_list) {
        this.question_list = question_list;
    }
}
