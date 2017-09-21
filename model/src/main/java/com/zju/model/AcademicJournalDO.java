package com.zju.model;

import com.zju.utils.jpa.BaseDomain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by lujie on 2017/8/16.
 * 学科期刊
 */
@Entity
@Table(name = "sczy_academic_journal")
public class AcademicJournalDO extends BaseDomain{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private String name;

    private String url;

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
}
