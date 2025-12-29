package org.demo_st.oauth2.caching;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("products");
    }
}

//cache wroks same like reddis
//but reddis useful for distributed system or we have mnay servers and each server has own cache
//if we want to update cache of entire is difficult
//so by usind reddis . it maintains a ache where all serves can access from it and if any update
//in one server cahe then it updates reddis server cache . so user who is access the cache from other
//can alos get an updated cache