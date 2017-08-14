package com.zju.utils.lang;

import com.alibaba.fastjson.JSON;

/**
 * Created by oracle on 2016/10/27.
 */
public class ObjectConvert {           //去除对象状态
    public static <T> T convert(T obj,Class<T> elememType){
       return JSON.parseObject(JSON.toJSONString(obj),elememType);
    }
    public static String toString(Object object){
        if(object!=null){
            return object+"";
        }
        return "";
    }
}
