package com.zju.app.config;

/**
 * Created by xiaoqian on 2016/8/10.
 */

import javax.validation.constraints.NotNull;

/**
 * qurtz 定时任务配置vo
 */
public class QuartzConfigVo {
    /**
     * 同一时间内只能运行一个任务
     */
    private Boolean singleFlag = true;
    /**
     * 下一个任务与前一个任务的最少时间间隔，上一个任务自动完结除外
     */
    private Long singleJobTime;

    private Boolean runFlag = false;
    private Boolean concurrent = true;
    private String jobName;
    @NotNull
    private String triggerCronExpression ;
    @NotNull
    private String jobClass;
    @NotNull
    private String jobClassRunMethod;

    public Boolean getSingleFlag() {
        return singleFlag;
    }

    public void setSingleFlag(Boolean singleFlag) {
        this.singleFlag = singleFlag;
    }

    public Long getSingleJobTime() {
        return singleJobTime;
    }

    public void setSingleJobTime(Long singleJobTime) {
        this.singleJobTime = singleJobTime;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Boolean getRunFlag() {
        return runFlag;
    }

    public void setRunFlag(Boolean runFlag) {
        this.runFlag = runFlag;
    }

    public Boolean getConcurrent() {
        return concurrent;
    }

    public void setConcurrent(Boolean concurrent) {
        this.concurrent = concurrent;
    }

    public String getTriggerCronExpression() {
        return triggerCronExpression;
    }

    public void setTriggerCronExpression(String triggerCronExpression) {
        this.triggerCronExpression = triggerCronExpression;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public String getJobClassRunMethod() {
        return jobClassRunMethod;
    }

    public void setJobClassRunMethod(String jobClassRunMethod) {
        this.jobClassRunMethod = jobClassRunMethod;
    }

    @Override
    public String toString() {
        return "QuartzConfigVo{" +
                "runFlag=" + runFlag +
                ", concurrent=" + concurrent +
                ", jobName='" + jobName + '\'' +
                ", triggerCronExpression='" + triggerCronExpression + '\'' +
                ", jobClass='" + jobClass + '\'' +
                ", jobClassRunMethod='" + jobClassRunMethod + '\'' +
                '}';
    }
}
