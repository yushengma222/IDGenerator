package com.example.idgenerator.service.lock;

import com.example.idgenerator.service.RedisService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yushengma
 * 并发锁实现 -- Redis
 *
 * 超时（setNx、expire非原子）、误删、并发（大于过期时间）
 */
@Component
public class LockByRedis {

    @Autowired
    RedisService redisService;

    private static final String PREFIX_KEY = "prefix_";

    public String getLock(String key, int limit, long outTimeInSeconds) {
        int startNum = RandomUtils.nextInt(0, limit);
        for (int i = 0; i < limit; i++) {
            int curNum = (startNum + i) % limit;
            String concurrentKey = PREFIX_KEY + key + curNum;
            String concurrentValue = RandomStringUtils.randomAlphabetic(10);

            redisService.setNxWithOutTime(concurrentKey, concurrentValue, outTimeInSeconds);
            String actualValue = redisService.get(concurrentKey);

            boolean isEqual = StringUtils.equals(concurrentValue, actualValue);
            if (isEqual) {
                return concurrentKey;
            }
        }
        return null;
    }
}
