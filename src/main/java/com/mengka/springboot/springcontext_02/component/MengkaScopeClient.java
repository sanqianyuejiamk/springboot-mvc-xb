package com.mengka.springboot.springcontext_02.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author mengka
 * @date 2017/05/14.
 */
@Slf4j
public class MengkaScopeClient extends Client{

    /**
     *  scope:prototype，每次都生成一个新的对象实例
     */
    private final String name;

    @Autowired
    private MengkaComponent mengkaComponent;

    public MengkaScopeClient(String name){
        this.name = name;
    }

    public String getName() {
        mengkaComponent.foo();
        return this.name;
    }
}
