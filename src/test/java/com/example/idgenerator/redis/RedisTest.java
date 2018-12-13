package com.example.idgenerator.redis;

import com.example.idgenerator.IdgeneratorApplication;
import com.example.idgenerator.service.RedisService;
import com.example.idgenerator.service.constants.Common;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IdgeneratorApplication.class)
public class RedisTest {

    @Autowired
    RedisService redisService;

    @Test
    public void set() {
        redisService.set(Common.ID_REDIS, "200000000");
    }
}
