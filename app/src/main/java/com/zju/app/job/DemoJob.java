package com.zju.app.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by xujingfeng on 2017/10/10.
 */
@Component
public class DemoJob {

    public final static Logger logger = LoggerFactory.getLogger(DemoJob.class);

    public void run(){
        logger.info("demo job is running...");
    }

}
