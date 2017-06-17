package com.mengka.springboot.importBeanDefinitionRegistrar_01;

import lombok.extern.slf4j.Slf4j;

/**
 * @author mengka
 * @date 2017/06/17.
 */
@Slf4j
public class AppBean {

    private String str;

    public void setStr(String str) {
        this.str = str;
    }

    public void process() {
        log.info("AppBean执行中。");
        System.out.println(str);
    }

}
