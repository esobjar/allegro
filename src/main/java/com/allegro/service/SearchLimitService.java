package com.allegro.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Service
public class SearchLimitService {

	private static final Logger logger = LoggerFactory.getLogger(SearchLimitService.class);
	
	@Value("${allegro.searchLimitPerMinute}")	
	private int searchLimitPerMinute;
	
    private LoadingCache<String, AtomicInteger> numberOfSearchesCache;
 
    public SearchLimitService() {
        super();
        numberOfSearchesCache = CacheBuilder.newBuilder().
          expireAfterAccess(1, TimeUnit.MINUTES).build(new CacheLoader<String, AtomicInteger>() {
            public AtomicInteger load(String key) {
                return new AtomicInteger();
            }
        });
    }
    
    public void increaseNumberOfSearch(String key) {
        int attempts = 0;
        try {
            attempts = numberOfSearchesCache.get(key).incrementAndGet();
        } catch (ExecutionException e) {
            attempts = 0;
        }
        logger.info(attempts + " search/es: " +  " in " + key);
    }
 
    public boolean isSearchLimitExceeded(String key) {
        try {
            return numberOfSearchesCache.get(key).get() >= searchLimitPerMinute;
        } catch (ExecutionException e) {
            return false;
        }
    }

}
