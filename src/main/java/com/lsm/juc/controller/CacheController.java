package com.lsm.juc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping(value = "/cache")
public class CacheController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static FutureTask<String> ft = null;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getName(@Param(value = "key") String key) throws Exception {
        //先从缓存取
        ReentrantLock reentrantLock=null;
        String value = stringRedisTemplate.opsForValue().get(key);
        if (null == value) {
            //缓存没取到
            Callable<String> callable = new Callable<String>() {
                @Override
                public String call() throws Exception {
                    //模拟数据库查询耗时
                    Thread.sleep(5000);
                    return "从数据库中查出来的值";
                }
            };
            //FutureTask<String> ft = new FutureTask<>(callable);
            ft = new FutureTask<>(callable);
            //f = ft;
            redisTemplate.opsForValue().set(key, ft.get());
            System.out.println("从数据库取数据");
            ft.run();
            return ft.get();
        }
        return value;
        //return ft.get();
    }


}
