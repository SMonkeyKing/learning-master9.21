package com.zju.utils.jpa;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by xiaoqian on 2015/5/21.
 */
@MappedSuperclass
abstract public class BaseDomain implements Serializable {

    /**
     * 创建日期
     */
    @Column(name = "date_created")
    private Date dateCreated;

    /**
     * 最后更新日期
     */
    @Column(name = "last_updated")
    private Date lastUpdated;

    @Column(name = "version")
    private Integer version;

    @Column(name = "delete_flag")
    private Boolean deleteFlag = false;

    @Column(name = "delete_date")
    private Date deleteDate;

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        if(deleteFlag)
            this.deleteDate = new Date();
        this.deleteFlag = deleteFlag;
    }

    @PrePersist
    protected void prePersist(){
        dateCreated = new Date();
        lastUpdated = dateCreated;
        version = 1;
    }

    @PreUpdate
    private void preUpdate(){
        lastUpdated = new Date();
        if(version==null){
            version =1;
        }else{
            version++;
        }
    }
}