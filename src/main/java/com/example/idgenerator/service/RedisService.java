package com.example.idgenerator.service;

/**
 * @author yushengma
 * @version redis, 18/12/10
 */
public interface RedisService {
    void set(String key, String value);
    String get(String key);
    void delete(String key);
    boolean exists(String key);
    boolean setNx(String key, String value);
    String getSet(String key, String value);
    long increment(String key);
    long incrementBy(String key, int num);
    void setWithOutTime(String key, String value, long outTime);
    void setNxWithOutTime(String key, String value, long outTime);
    void setNxWithOutTimeCopy(String key, String value, long outTimeInSeconds);
    String hGet(String space, String key);
    void hSet(String space, String key, String value);
    void hDel(String space, String key);
    boolean hExists(String space, String key);
}
