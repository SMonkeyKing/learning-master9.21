package com.zju.model;

import com.zju.utils.jpa.BaseDomain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lujie on 2017/10/01.
 */
@Entity
@Table(name = "jxhd_push_img")
public class JxhdPushImgDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qid")
    private Integer qid;

    private String image;

    @Column(name = "date_created")
    private Date dateCreated;

    public Integer getQid() {
        return qid;
    }

    public void setQid(Integer qid) {
        this.qid = qid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
