package com.yto.tech.catplugin.aop;

import com.dianping.cat.Cat;
import com.dianping.cat.CatConstants;
import com.dianping.cat.message.Transaction;
import com.yto.tech.catplugin.YtoCatConstant;
import com.yto.tech.catplugin.exception.CatTaskDurationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CatTaskAspect {
    private static final Logger logger = LoggerFactory.getLogger(CatTaskAspect.class);

    @Around("@annotation(catTask)")
    public void around(ProceedingJoinPoint pjp,CatTask catTask) {
        logger.debug("task aop invoked start.....");
        long start = System.currentTimeMillis();

        String methodName = "unknowMethod";
        String className = "unknowClassName";
        try {
            methodName = pjp.getSignature().getName();
            className = pjp.getSignature().getDeclaringType().getCanonicalName();
        }catch (Exception e){
            logger.error("task aop process name",e);
        }

        Transaction transaction = Cat.newTransaction(YtoCatConstant.TRANSACTION_TYPE_TASK, className+"."+methodName);
        try {
            pjp.proceed();
            transaction.setStatus(Transaction.SUCCESS);
        } catch (Throwable e) {
            logger.error("aop execute error",e);
            transaction.setStatus(e); 
            Cat.logError(e);
        }finally {
            long duration = System.currentTimeMillis()-start;
            if(duration > catTask.alertDurationThresholdMillis()){
                Cat.logError("taskDurationAlert",new CatTaskDurationException("Real("+duration+") > Threshold("+catTask.alertDurationThresholdMillis()+")"));
            }
            logger.info("task aop invoked end..... time = {}",duration);
            transaction.complete();
        }
    }

}