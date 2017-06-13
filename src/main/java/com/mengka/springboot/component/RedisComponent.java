package com.mengka.springboot.component;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA
 * User: huangyy
 * Date: 2017/2/16
 * Time: 19:00
 */
@Component
public class RedisComponent {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    private ValueOperations<Object,Object> operations;

    @PostConstruct
    public void init(){
        operations = redisTemplate.opsForValue();
    }

    public void setString(String key, String value) {
        operations.set(key, value);
    }

    public void setString(String key, String value, Long expire) {
        operations.set(key, value, expire, TimeUnit.SECONDS);
    }

    public String getString(String key) {
        if(StringUtils.isBlank(key)){
            return null;
        }
        Object obj = operations.get(key);
        if(obj==null){
            return null;
        }
        return String.valueOf(obj);
    }

    public void set(Object key, Object value) {
        operations.set(key, value);
    }

    public void set(Object key, Object value, Long expire) {
        operations.set(key, value, expire, TimeUnit.SECONDS);
    }

    public Object get(Object key) {
        return operations.get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

}
