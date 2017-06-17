package com.mengka.springboot.importBeanDefinitionRegistrar_01;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *  使用importBeanDefinitionRegistrar，注册一些附加的spring bean；
 *
 * @author mengka
 * @date 2017/06/17.
 */
@Slf4j
public class Taa {

    public static void main(String[] args){
        log.info("importBeanDefinitionRegistrar_01 start..");
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        ClientBean bean = context.getBean(ClientBean.class);
        bean.doSomething();
        log.info("importBeanDefinitionRegistrar_01 end..");
    }
}
