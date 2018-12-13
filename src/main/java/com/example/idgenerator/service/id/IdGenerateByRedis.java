package com.example.idgenerator.service.id;

import com.example.idgenerator.service.IdGenerator;
import com.example.idgenerator.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.idgenerator.service.constants.Common.ID_REDIS;

/**
 * @author yushengma
 */
@Service("idByRedis")
public class IdGenerateByRedis implements IdGenerator {

    @Autowired
    RedisService redisService;

    @Override
    public long getOrderId() throws Exception {
        if (redisService.exists(ID_REDIS)) {
            return redisService.increment(ID_REDIS);
        } else {
            throw new Exception("please init id_generate_redis, set first Id");
        }
    }
}
