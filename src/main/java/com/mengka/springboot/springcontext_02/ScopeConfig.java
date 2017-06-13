package com.mengka.springboot.springcontext_02;

import com.mengka.springboot.springcontext_02.component.MengkaScopeClient;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author mengka
 * @date 2017/05/14.
 */
@Configurable
@Component
public class ScopeConfig {

    /**
     * {@link ScopeEnum}
     * SCOPE:"prototype"
     * 每个请求方可以得到自己对应的一个对象实例；
     * 通常，声明为prototype的scope的bean定义类型，都是一些有状态的，比如保存每个顾客信息的对象；
     *
     * @param name
     * @return
     */
    @Bean
    @Scope("prototype")
    public MengkaScopeClient mengkaScopeClient(String name){
        return new MengkaScopeClient(name);
    }
}
