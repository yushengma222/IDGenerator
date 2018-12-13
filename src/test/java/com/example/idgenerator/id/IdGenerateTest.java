package com.example.idgenerator.id;

import com.example.idgenerator.IdgeneratorApplication;
import com.example.idgenerator.service.IdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.Inet4Address;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(value = SpringRunner.class)
@SpringBootTest(classes = IdgeneratorApplication.class)
public class IdGenerateTest {

    @Autowired
    @Qualifier("idByRedis")
    IdGenerator idGenerator;

    @Autowired
    @Qualifier("idBySnowFlake")
    IdGenerator idGeneratorSnow;

    @Autowired
    @Qualifier("idBySnowRedis")
    IdGenerator idGeneratorSnowRedis;

    @Test
    public void getId() throws Exception {
        long id = idGenerator.getOrderId();
        System.out.println("generate id is: " + id);
        System.out.println("hostAddress: " + Inet4Address.getLocalHost().getHostAddress());
        System.out.println("long: " + new Date().getTime());
        System.out.println("hostname: " + SystemUtils.getHostName());
        System.out.println("hostname: " + StringUtils.toCodePoints(SystemUtils.getHostName()).length);
        int[] ints = StringUtils.toCodePoints(SystemUtils.getHostName());
        for (int i : ints) {
            System.out.println("hostname: " + i);
        }
    }

    @Test
    public void getIdWithThreads() {
        int count = 10;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        ExecutorService es = Executors.newFixedThreadPool(count);
        for (int i = 0; i < count; i++) {
            es.submit(new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                        System.out.println("thread: " + Thread.currentThread() + ", id: " + idGenerator.getOrderId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
                }
            });
        }
        try {
            es.shutdown();
            System.out.println("begin to get id.........");
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getIdBySnowFlakeWithThreads() {
        int count = 100;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        ExecutorService es = Executors.newFixedThreadPool(10);
        Set<Long> ids = new HashSet<>();
        for (int i = 0; i < count; i++) {
            es.submit(new Thread() {
                @Override
                public void run() {
                    try {
                        long id = idGeneratorSnow.getOrderId();
                        ids.add(id);
                        System.out.println("time: " + System.currentTimeMillis() + ", thread: " + Thread.currentThread() + ", id: " + id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
                }
            });
        }
        try {
            es.shutdown();
            System.out.println("begin to get id.........");
            countDownLatch.await();
            System.out.println("count: " + count + ", setNum: " + ids.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getIdBySnowRedisWithThreads() {
        int count = 100;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        ExecutorService es = Executors.newFixedThreadPool(10);
        Set<Long> ids = new HashSet<>();
        for (int i = 0; i < count; i++) {
            es.submit(new Thread() {
                @Override
                public void run() {
                    try {
                        long id = idGeneratorSnowRedis.getOrderId();
                        ids.add(id);
                        System.out.println("time: " + System.currentTimeMillis() + ", thread: " + Thread.currentThread() + ", id: " + id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
                }
            });
        }
        try {
            es.shutdown();
            System.out.println("begin to get id.........");
            countDownLatch.await();
            System.out.println("count: " + count + ", setNum: " + ids.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
