package com.zju.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lujie on 2017/8/14.
 */
@Entity
@Table(name = "left_menu")
public class LeftMenuDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private Integer parentid;

    private String title;

    private Integer level;

    private String url;

    @Transient
    private List<LeftMenuDO> submenus = new ArrayList<LeftMenuDO>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<LeftMenuDO> getSubmenus() {
        return submenus;
    }

    public void setSubmenus(List<LeftMenuDO> submenus) {
        this.submenus = submenus;
    }

    public boolean hasSubmenus()
    {
        return this.getSubmenus().size() == 0;
    }
}
