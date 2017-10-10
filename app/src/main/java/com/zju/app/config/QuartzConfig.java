package com.zju.app.config;

import org.quartz.Trigger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoqian on 2016/2/24.
 */
@Configuration
@ConditionalOnProperty(name = "quartz.enabled")
@ConfigurationProperties(prefix = "jobs")
@Lazy(false)
public class QuartzConfig implements ApplicationContextAware, InitializingBean {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

    private int jobconfigIndex = 0;

    /**
     * 调度工厂
     */
    @Bean
    public SchedulerFactoryBean jobFactory() {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        List<Trigger> triggerList = new ArrayList<Trigger>();

        for (int i = 0; i < joblist.size(); i++) {
            jobconfigIndex = i;
            triggerList.add(triggerFactoryBeans().getObject());
        }

        Trigger[] triggers = new Trigger[triggerList.size()];
        factoryBean.setTriggers(triggerList.toArray(triggers));
        return factoryBean;
    }


    /**
     * autowired by @ConfigurationProperties
     */
    List<QuartzConfigVo> joblist = new ArrayList<QuartzConfigVo>();

    public List<QuartzConfigVo> getJoblist() {
        return joblist;
    }

    public void setJoblist(List<QuartzConfigVo> joblist) {
        this.joblist = joblist;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<QuartzConfigVo> new_jobList = new ArrayList<QuartzConfigVo>();
        for (QuartzConfigVo job : joblist) {
            if (job.getRunFlag()) {
                new_jobList.add(job);
            }
        }
        this.joblist = new_jobList;
    }

    @Bean()
    @Scope("prototype")
    public CronTriggerFactoryBean triggerFactoryBeans() {

        QuartzConfigVo quartzConfigVo = joblist.get(jobconfigIndex);
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setName(quartzConfigVo.getJobName());

        trigger.setBeanName("trigger_" + quartzConfigVo.getJobName());
        MethodInvokingJobDetailFactoryBean bean = jobDetailFactoryBean();

        trigger.setJobDetail(bean.getObject());
        trigger.getJobDataMap().put(QuartzConfigVo.class.getName(),
                quartzConfigVo);
        trigger.setCronExpression(quartzConfigVo.getTriggerCronExpression());
        return trigger;

    }

    @Bean(name = "jobDetailFactoryBean")
    @Scope("prototype")
    MethodInvokingJobDetailFactoryBean jobDetailFactoryBean() {

        QuartzConfigVo quartzConfigVo = joblist.get(jobconfigIndex);
        MethodInvokingJobDetailFactoryBean detail = new MethodInvokingJobDetailFactoryBean();
        detail.setConcurrent(quartzConfigVo.getConcurrent());
        detail.setBeanName("detail_" + quartzConfigVo.getJobName());
        detail.setName("detail_" + quartzConfigVo.getJobName());
        detail.setTargetBeanName("detail_target_" + quartzConfigVo.getJobName());
        try {
            detail.setTargetObject(applicationContext.getBean(Class
                    .forName(quartzConfigVo.getJobClass())));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        detail.setTargetMethod(quartzConfigVo.getJobClassRunMethod());
        return detail;
    }

}
