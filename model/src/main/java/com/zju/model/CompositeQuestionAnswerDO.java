package com.zju.model;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by lujie on 2017/10/10.
 */
public class CompositeQuestionAnswerDO implements Serializable{
    private Integer paper_id;

    private Integer num;

    public Integer getPaper_id() {
        return paper_id;
    }

    public void setPaper_id(Integer paper_id) {
        this.paper_id = paper_id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
