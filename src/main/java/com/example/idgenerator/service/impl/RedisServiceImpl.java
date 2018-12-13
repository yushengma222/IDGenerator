package com.example.idgenerator.service.impl;

import com.example.idgenerator.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author yushengma
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    private static final StringRedisSerializer serializer = new StringRedisSerializer();

    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue()
                .set(key, value);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public boolean exists(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    @Override
    public boolean setNx(String key, String value) {
        return stringRedisTemplate.opsForValue().setIfAbsent(key, value);
    }

    @Override
    public String getSet(String key, String value) {
        return stringRedisTemplate.opsForValue().getAndSet(key, value);
    }

    @Override
    public long increment(String key) {
        return stringRedisTemplate.opsForValue().increment(key, 1);
    }

    @Override
    public long incrementBy(String key, int num) {
        return stringRedisTemplate.opsForValue().increment(key, num);
    }

    @Override
    public void setWithOutTime(String key, String value, long outTime) {
        stringRedisTemplate.opsForValue().set(key, value, outTime, TimeUnit.SECONDS);
    }

    @Override
    public void setNxWithOutTime(String key, String value, long outTime) {
        stringRedisTemplate.execute((RedisCallback) connection -> {
            connection.set(serializer.serialize(key), serializer.serialize(value), Expiration.seconds(outTime), RedisStringCommands.SetOption.SET_IF_ABSENT);
            return null;
        });
    }

    @Override
    public void setNxWithOutTimeCopy(String key, String value, long outTimeInSeconds) {
        stringRedisTemplate.opsForValue().setIfAbsent(key, value, outTimeInSeconds, TimeUnit.SECONDS);
    }

    @Override
    public String hGet(String space, String key) {
        return (String) stringRedisTemplate.opsForHash().get(space, key);
    }

    @Override
    public void hSet(String space, String key, String value) {
        stringRedisTemplate.opsForHash().putIfAbsent(space, key, value);
    }

    @Override
    public void hDel(String space, String key) {
        stringRedisTemplate.opsForHash().delete(space, key);
    }

    @Override
    public boolean hExists(String space, String key) {
        return stringRedisTemplate.opsForHash().hasKey(space, key);
    }
}
