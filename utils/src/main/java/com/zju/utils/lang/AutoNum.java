package com.zju.utils.lang;

/**
 * Created by 31714 on 2016/10/31.
 */
public class AutoNum {

    public static AutoNum create(){
        return new AutoNum();
    }

    public String getNum(){
        double n = Math.random();
        n *= 100;
        int m = (int)n;
        String k;
        if (m<10){
            k = "0"+m;
        } else {
            k = ""+m;
        }
        return k;
    }
}
