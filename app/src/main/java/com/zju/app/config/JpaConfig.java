package com.zju.app.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.Entity;

/**
 * Created by xujingfeng on 2017/8/11.
 */
@Configuration
@EntityScan(basePackages = {"com.zju.model"})
@EnableJpaRepositories(basePackages = {"com.zju.repository"})
public class JpaConfig {
}
