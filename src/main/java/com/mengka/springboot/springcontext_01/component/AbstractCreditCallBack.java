package com.mengka.springboot.springcontext_01.component;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mengka.springboot.springcontext_01.model.MengkaDO;
import java.util.concurrent.TimeUnit;

/**
 * @author mengka
 * @date 2017/05/14.
 */
abstract public class AbstractCreditCallBack implements CreditCallBack{

    protected static Cache<String, MengkaDO> cache = CacheBuilder.newBuilder()
            //设置cache的初始大小为10，要合理设置该值
            .initialCapacity(300)
            //设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
            .concurrencyLevel(20)
            //设置cache中的数据在写入之后的存活时间为10hours
            .expireAfterWrite(10, TimeUnit.MINUTES)
            //构建cache实例
            .build();

    static{
        cache.put("044101331",new MengkaDO("044101331","default mengkaDO"));
    }
}
