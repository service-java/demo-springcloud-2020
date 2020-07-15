package cn.jiiiiiin.data.orm.config;

import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * TODO 修改为可选的配置bean 组件
 *
 * @author jiiiiiin
 */
@Configuration
@EnableCaching
//@ConditionalOnProperty(prefix = "jiiiiiin.data", name = "cache", havingValue = "ehcache")
public class EHCacheConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(EHCacheConfiguration.class);

    @Bean
    public CacheManager cacheManager(){
        logger.info("初始化默认的CacheManager::EhCacheCacheManager");
        return new EhCacheCacheManager(cacheManagerFactory().getObject());
    }

    @Bean
    @ConditionalOnMissingBean(EhCacheManagerFactoryBean.class)
    public EhCacheManagerFactoryBean cacheManagerFactory() {
        logger.info("初始化默认的EhCacheManagerFactoryBean");
        val factoryBean = new EhCacheManagerFactoryBean();
        factoryBean.setConfigLocation(new ClassPathResource("def-ehcache-config.xml"));
        factoryBean.setShared(true);
        return factoryBean;
    }

}
