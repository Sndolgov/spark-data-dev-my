package starter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.unsafe_sparkdata.aspect.LazyCollectionAspectHandler;
import com.unsafe_sparkdata.util.FirstLevelCacheService;
import com.unsafe_sparkdata.util.LazySparkList;

@Configuration
public class SparkDataStarterConf {

    @Bean
    @Scope("prototype")
    public LazySparkList lazySparkList() {
        return new LazySparkList();
    }

    @Bean
    public FirstLevelCacheService firstLevelCacheService() {
        return new FirstLevelCacheService();
    }

    @Bean
    public LazyCollectionAspectHandler lazyCollectionAspectHandler() {
        return new LazyCollectionAspectHandler();
    }
}
