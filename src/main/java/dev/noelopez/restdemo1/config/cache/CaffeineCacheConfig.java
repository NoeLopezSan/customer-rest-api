package dev.noelopez.restdemo1.config.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CaffeineCacheConfig {
    private Logger logger = LoggerFactory.getLogger(CaffeineCacheConfig.class);
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.registerCustomCache("customers",
                buildCache(100,200, 2));
        cacheManager.registerCustomCache("customersSearch",
                buildCache(50,100, 1));
        return cacheManager;
    }

    private Cache<Object, Object> buildCache(int initialCapacity, int maximumSize, int durationInHours) {
        return Caffeine.newBuilder()
                .initialCapacity(initialCapacity)
                .maximumSize(maximumSize)
                .expireAfterAccess(durationInHours, TimeUnit.HOURS)
                .evictionListener((Object key, Object value, RemovalCause cause) ->
                        logger.info(String.format("Key %s was evicted (%s)%n", key, cause)))
                .removalListener((Object key, Object value, RemovalCause cause) ->
                        logger.info(String.format("Key %s was removed (%s)%n", key, cause)))
                .scheduler(Scheduler.systemScheduler())
                .recordStats()
                .build();
    }
//
//    @Bean
//    Caffeine caffeineSpec() {
//        return Caffeine.newBuilder()
//                .initialCapacity(10)
//                .maximumSize(100)
//                .expireAfterAccess(5, TimeUnit.HOURS);
//    }
}
