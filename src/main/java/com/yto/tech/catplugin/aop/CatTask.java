package com.yto.tech.catplugin.aop;

import java.lang.annotation.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CatTask {

    /**
     * 方法耗时报警阈值
     * 超过当前值会上报到Cat的problem报表中
     * 默认值5000毫秒
     * @return
     */
    int alertDurationThresholdMillis() default 5000;
}
