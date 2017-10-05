package com.zju.app.interceptor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by xujingfeng on 2017/10/4.
 * <p>
 * 登录配置类
 */
@ConfigurationProperties(prefix = "login")
@Component
public class LoginConfigVO {

    private String username;

    private String password;

    //登录端点
    private String loginInEntry;

    //排除不过滤的url
    private String[] excludes;

    public String getLoginInEntry() {
        return loginInEntry;
    }

    public void setLoginInEntry(String loginInEntry) {
        this.loginInEntry = loginInEntry;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getExcludes() {
        return excludes;
    }

    public void setExcludes(String[] excludes) {
        this.excludes = excludes;
    }
}
