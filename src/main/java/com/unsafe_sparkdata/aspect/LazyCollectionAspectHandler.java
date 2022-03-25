package com.unsafe_sparkdata.aspect;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

import com.unsafe_sparkdata.SparkLazyList;
import com.unsafe_sparkdata.util.FirstLevelCacheService;

@Aspect
public class LazyCollectionAspectHandler {

    @Autowired
    private FirstLevelCacheService cacheService;
    @Autowired
    private ConfigurableApplicationContext context;

    @Before("execution(* com.unsafe_sparkdata.SparkLazyList.*(..)) "
            + "&& execution(* java.util.List.*(..))" )
//            + "|| execution(* com.unsafe_sparkdata.SparkLazyList.toString())")
    public void setLazyCollections(JoinPoint jp){
        SparkLazyList lazyList = (SparkLazyList) jp.getTarget();
        if (!lazyList.initialized()){
            List<?> content = cacheService.readDataFor(lazyList, context);
            lazyList.setContent(content);
        }
    }
}
