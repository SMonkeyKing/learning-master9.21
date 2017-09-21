package com.zju.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

/**
 * Created by lujie on 2017/8/26.
 */
public class DataResult implements Serializable{

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 响应状态码
    private Integer code;

    // 响应消息
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
