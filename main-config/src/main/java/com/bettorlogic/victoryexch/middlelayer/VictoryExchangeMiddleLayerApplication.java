package com.bettorlogic.victoryexch.middlelayer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableCaching
@EnableSwagger2
@SpringBootApplication(scanBasePackages = VictoryExchangeMiddleLayerApplication.BASE_PACKAGES)
public class VictoryExchangeMiddleLayerApplication {

    static final String BASE_PACKAGES = "com.bettorlogic.victoryexch.middlelayer.*";
    private static final String CACHE_XML_FILE_PATH = "BmExchangeCacheConfig.xml";

    public static void main(String[] args) {
        SpringApplication.run(VictoryExchangeMiddleLayerApplication.class, args);
    }

    @Bean
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheCacheManager().getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheCacheManager() {
        EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
        cmfb.setConfigLocation(new ClassPathResource(VictoryExchangeMiddleLayerApplication.CACHE_XML_FILE_PATH));
        cmfb.setShared(true);
        return cmfb;
    }

    private interface SpringProfiles {
        String DEV = "dev";
        String QA = "qa";
        String DEFAULT = "default";
        String UAT = "uat";
        String PROD = "prod";
    }

    private interface PropertySources {
        String DEV_PROPERTIES = "classpath:bm-exchange-config-dev.properties";
        String REST_URL_PROPERTIES =
                "classpath:sports-book-middle-layer-url-config.properties";
        String CACHE_CONFIG_PROPERTIES = "classpath:bm-exchange-cache-config.properties";
        String QA_PROPERTIES = "classpath:bm-exchange-config-qa.properties";
        String UAT_PROPERTIES = "classpath:bm-exchange-config-uat.properties";
        String PROD_PROPERTIES = "classpath:bm-exchange-config-prod.properties";
    }

    /*
     * Dev is used as default profile
     */
    @Configuration
    @Profile({SpringProfiles.DEV, SpringProfiles.DEFAULT})
    @PropertySource({PropertySources.DEV_PROPERTIES, PropertySources.REST_URL_PROPERTIES, PropertySources.CACHE_CONFIG_PROPERTIES})
    static class DevProfile {
    }

    @Configuration
    @Profile(SpringProfiles.QA)
    @PropertySource({PropertySources.QA_PROPERTIES, PropertySources.REST_URL_PROPERTIES, PropertySources.CACHE_CONFIG_PROPERTIES})
    static class QaProfile {
    }

    @Configuration
    @Profile(SpringProfiles.UAT)
    @PropertySource({PropertySources.UAT_PROPERTIES, PropertySources.REST_URL_PROPERTIES, PropertySources.CACHE_CONFIG_PROPERTIES})
    static class UatProfile {
    }

    @Configuration
    @Profile(SpringProfiles.PROD)
    @PropertySource({PropertySources.PROD_PROPERTIES, PropertySources.REST_URL_PROPERTIES, PropertySources.CACHE_CONFIG_PROPERTIES})
    static class ProdProfile {
    }

}

