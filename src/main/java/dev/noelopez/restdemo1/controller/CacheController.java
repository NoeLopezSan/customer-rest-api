package dev.noelopez.restdemo1.controller;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import dev.noelopez.restdemo1.exception.EntityNotFoundException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ServerWebInputException;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@RestController
@RequestMapping("admin/cache")
@Validated
public class CacheController {
    private CacheManager cacheManager;

    public CacheController(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<cacheInfo> getCacheInfo() {
        return cacheManager.getCacheNames()
            .stream()
            .map(this::getCacheInfo)
            .toList();
    }

    @GetMapping(path = "/evict/{cachename}", produces = TEXT_PLAIN_VALUE)
    public String evictCache(@NotBlank @Size(min = 4, max = 40) @PathVariable String cachename) {
        var cache = cacheManager.getCacheNames()
                .stream()
                .filter(name -> name.equals(cachename))
                .findFirst()
                .orElseThrow(() -> new ServerWebInputException("Cache was not found."));

        cacheManager.getCache(cachename).clear();
        return String.format("Cache region %s has been cleared.",cachename);
    }

    @GetMapping(path = "/evict", produces = TEXT_PLAIN_VALUE)
    public String evictAllCaches() {
        cacheManager.getCacheNames()
                .forEach(name -> cacheManager.getCache(name).clear());

        return "All Cache regions have been cleared.";
    }

    private cacheInfo getCacheInfo(String cacheName) {
        Cache<Object, Object> nativeCache = (Cache)cacheManager.getCache(cacheName).getNativeCache();
        Set<Object> keys = nativeCache.asMap().keySet();
        CacheStats stats = nativeCache.stats();
        return new cacheInfo(cacheName, keys.size(), keys, stats.toString());
    }
    private record cacheInfo(String name, int size, Set<Object> keys, String stats) {}
}
