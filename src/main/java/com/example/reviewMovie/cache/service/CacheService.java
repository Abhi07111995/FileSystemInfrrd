package com.example.reviewMovie.cache.service;

import java.util.Map;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.example.reviewMovie.util.CacheUtil;



@Component
public class CacheService {

	@Autowired
	private CacheUtil cacheUtil;

	@Autowired
	private Environment environment;

	private static Logger LOGGER = LoggerFactory.getLogger(CacheService.class);

	public Boolean setCacheData(Map<String, Object> cacheData) {
		String key = (String) cacheData.get("key");
		int expTime = (int) cacheData.get("expTime");
		Object obj = cacheData.get("cacheData");
		Integer dataBase = Integer.valueOf(environment.getProperty("redis.default.database"));
		try {
			dataBase = (int) cacheData.get("database");
		} catch (NullPointerException e) {
			LOGGER.info("No database selected, Selecting default database");
			dataBase = Integer.valueOf(environment.getProperty("redis.default.database"));
		}
		cacheUtil.setObjectInCache(key, obj, expTime, dataBase);
		return true;
	}

	public <T> T getCacheData(String key, Class<T> t) throws Exception {
		T result = cacheUtil.getObjectFromCache(key, t);
		if (result == null) {
			throw new Exception("Key not found");
		}
		return result;
	}

	public Object getCacheData(String key, int database) throws Exception {
		Object result = cacheUtil.getObjectFromCache(key, database, Object.class);
		if (result == null) {
			throw new Exception("Key not found");
		}
		return result;
	}

	public boolean deleteCacheKey(String key) {
		return cacheUtil.deleteObjectFromCache(key);
	}

	public Boolean deleteCacheKey(String key, int database) {
		return cacheUtil.deleteObjectFromCache(key, database);
	}

	public Boolean hardDeleteCacheKey(String key, int database) {
		return cacheUtil.hardDeleteObjectFromCache(key, database);
	}

	public Boolean setCacheIfNotExists(String key, Object obj, int database, String propertyName) {
		return cacheUtil.setObjectIfNotExist(key, obj, database, propertyName);
	}

}
