package com.zju.app.job;

import com.zju.app.business.jxhd.service.JxhdPushQuestionService;
import com.zju.model.CompositeQuestionAnswerDO;
import com.zju.model.JxhdPushDO;
import com.zju.model.JxhdPushQuestionDO;
import com.zju.utils.Lang;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by xujingfeng on 2017/10/10.
 */

@Component
@Transactional
public class DemoJob {

    public final static Logger logger = LoggerFactory.getLogger(DemoJob.class);

    @Autowired
    JxhdPushQuestionService jxhdPushQuestionService;

    @PersistenceContext
    public EntityManager entityManager;
    public void run() throws ParseException {

        //logger.info("demo job is running...");
        List<JxhdPushQuestionDO> jxhdPushQuestionDOS = jxhdPushQuestionService.findAll();

        DateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowStr = simpleFormat.format(new Date());
        for (JxhdPushQuestionDO jxhdPushQuestionDo:jxhdPushQuestionDOS
             ) {
            long minutesDiff = Lang.getDateMinutesDiff(new Date(),jxhdPushQuestionDo.getDateCreated());
            /*String fromDate = simpleFormat.format(jxhdPushQuestionDo.getDateCreated());
            String toDate = simpleFormat.format(nowStr);
            Date date1 = (Date) simpleFormat.parse(fromDate);
            Date date2 =(Date) simpleFormat.parse(toDate);
            long from = simpleFormat.parse(fromDate).getTime();
            long to = simpleFormat.parse(toDate).getTime();
            int minutes = (int) ((to - from)/(1000 * 60));*/
            if(minutesDiff>=10)
            {
                jxhdPushQuestionService.deletePushQuestion(jxhdPushQuestionDo.getId());
            }
        }
        //jxhdPushQuestionService.delete(nowStr);
        /*String sql="select * from jxhd_question  where  " +
                "round((UNIX_TIMESTAMP('"+nowStr+"')-UNIX_TIMESTAMP(date_created))/60)>10";
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(JxhdPushQuestionDO.class));
        List<JxhdPushDO> jxhdPushDOList = query.getResultList();

        for (JxhdPushDO jxhdPushDo:
             jxhdPushDOList) {
            logger.info("jxhdPushDo+++++++"+jxhdPushDo.getQuestion());
            entityManager.remove(jxhdPushDo);
        }*/
        //int reslut = query.executeUpdate();
        //entityManager.remove(query.getResultList());
    }

}

