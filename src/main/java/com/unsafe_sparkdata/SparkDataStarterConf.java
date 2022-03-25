package com.unsafe_sparkdata;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.unsafe_sparkdata.aspect.LazyCollectionAspectHandler;
import com.unsafe_sparkdata.util.FirstLevelCacheService;

/**
 * @author Evgeny Borisov
 */
@Configuration
public class SparkDataStarterConf {
    @Bean
    public FirstLevelCacheService firstLevelCacheService(){
        return new FirstLevelCacheService();
    }

    @Bean
    public LazyCollectionAspectHandler lazyCollectionAspect(){
        return new LazyCollectionAspectHandler();
    }

    @Bean
    @Scope("prototype")
    public SparkLazyList sparkLazyList(){
        return new SparkLazyList();
    }


}
