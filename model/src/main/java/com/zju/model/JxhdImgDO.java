package com.zju.model;

import com.zju.utils.jpa.BaseDomain;

import javax.persistence.*;

/**
 * Created by lujie on 2017/10/01.
 */
@Entity
@Table(name = "jxhd_img")
public class JxhdImgDO extends BaseDomain{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
