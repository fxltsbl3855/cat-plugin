package com.yto.tech.catplugin.aop;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.yto.tech.catplugin.YtoCatConstant;
import com.yto.tech.catplugin.exception.CatRedisDurationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CatRedisAspect {
    private static final Logger logger = LoggerFactory.getLogger(CatRedisAspect.class);

    @Around("@annotation(catRedis)")
    public void around(ProceedingJoinPoint pjp,CatRedis catRedis) {
        logger.debug("redis aop invoked start.....");
        long start = System.currentTimeMillis();

        String methodName = "unknowMethod";
        String className = "unknowClassName";
        try {
            methodName = pjp.getSignature().getName();
            className = pjp.getSignature().getDeclaringType().getCanonicalName();
        }catch (Exception e){
            logger.error("redis aop process name",e);
        }

        Transaction transaction = Cat.newTransaction(YtoCatConstant.TRANSACTION_TYPE_REDIS, className+"."+methodName);
        try {
            pjp.proceed();
            transaction.setStatus(Transaction.SUCCESS);
        } catch (Throwable e) {
            logger.error("aop execute error",e);
            transaction.setStatus(e); 
            Cat.logError(e);
        }finally {
            long duration = System.currentTimeMillis()-start;
            if(duration > catRedis.alertDurationThresholdMillis()){
                Cat.logError("redisDurationAlert",new CatRedisDurationException("Real("+duration+") > Threshold("+catRedis.alertDurationThresholdMillis()+")"));
            }
            logger.info("redis aop invoked end..... time = {}",duration);
            transaction.complete();
        }
    }

}