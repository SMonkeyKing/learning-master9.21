package com.zju.model;

import com.zju.utils.jpa.BaseDomain;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

/**
 * Created by lujie on 2017/8/13.
 *
 * 选择题实体
 */
@Entity
@Table(name = "tk_question")
public class QuestionDO extends BaseDomain {

    @Id
    @GenericGenerator(name = "PKUUID", strategy = "uuid2")
    @GeneratedValue(generator = "PKUUID")
    @Column(length = 36)
    private String id;

    private Integer textbook;

    private Integer subject;

    private String content;

    private String answer;

    //题型：选择题-1，填空题-2，计算题-3
    private Integer type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTextbook() {
        return textbook;
    }

    public void setTextbook(Integer textbook) {
        this.textbook = textbook;
    }

    public Integer getSubject() {
        return subject;
    }

    public void setSubject(Integer subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
