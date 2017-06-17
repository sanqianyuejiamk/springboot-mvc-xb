package com.mengka.springboot.importBeanDefinitionRegistrar_01;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author mengka
 * @date 2017/06/17.
 */
public class ClientBean {

    @Autowired
    private AppBean appBean;

    public void doSomething () {
        appBean.process();
    }
}
