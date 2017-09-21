package com.zju.model;

import com.zju.utils.jpa.BaseDomain;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;

/**
 * Created by xujingfeng on 2017/8/11.
 *
 * 教学课件实体
 */
@Entity
@Table(name = "jxkj_course_ware")
public class CourseWareDO extends BaseDomain {

    /*@Id
    @GenericGenerator(name = "PKUUID", strategy = "uuid2")
    @GeneratedValue(generator = "PKUUID")
    @Column(length = 36)*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private String name;

    private String url;

    //资源所属类别，对应left_menu中的id
    private Integer typeid;

    @Column(name = "upload_name")
    private String uploadName;

    @Transient
    private MultipartFile file;

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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public String getUploadName() {
        return uploadName;
    }

    public void setUploadName(String uploadName) {
        this.uploadName = uploadName;
    }
}
