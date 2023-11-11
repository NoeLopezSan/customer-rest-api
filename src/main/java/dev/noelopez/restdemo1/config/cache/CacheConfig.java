//package dev.noelopez.restdemo1.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Caching;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.concurrent.ConcurrentMapCache;
//import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//
//import javax.management.timer.Timer;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Configuration
//@EnableCaching
//@EnableScheduling
//public class CacheConfig {
//    private Logger logger = LoggerFactory.getLogger(CacheConfig.class);
//    @Bean
//    public CacheManager cacheManager() {
//        return new ConcurrentMapCacheManager("customers","customersSearch");
//    }
//
//    @Scheduled(fixedRate = Timer.ONE_HOUR)
//    @Caching(evict = {
//            @CacheEvict(cacheNames="customers", allEntries=true),
//            @CacheEvict(cacheNames="customersSearch", allEntries=true)
//    })
//    public void clearAllCaches() {
//        logger.info("Clearing all caches entries.");
//    }
//    @Scheduled(fixedRate = Timer.ONE_HOUR)
//    public void clearAllCachesWithManager() {
//        CacheManager manager = cacheManager();
//        printCachesKeys(manager);
//        manager.getCacheNames().stream()
//                        .forEach(name -> manager.getCache(name).clear());
//
//        logger.info("Clearing all caches entries");
//    }
//
//    private void printCachesKeys(CacheManager manager) {
//        manager.getCacheNames().stream()
//            .forEach(name -> printCacheKeys(manager, name));
//    }
//    private void printCacheKeys(CacheManager manager, String name) {
//        ConcurrentHashMap cacheMap = (ConcurrentHashMap) manager.getCache(name).getNativeCache();
//        logger.info(String.format("Cache Name %s contains %s entries ",name, cacheMap.size()));
//        logger.info(String.format("keys : %s", cacheMap.keySet()));
//    }
//}
