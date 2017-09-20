package com.mengka.springboot.bean_01;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author mengka
 * @date 2017/09/20.
 */
public class MengkaBeanPostProcessor implements BeanPostProcessor {

    private static final Log log = LogFactory.getLog(MengkaBeanPostProcessor.class);

    private static final String before_desc = "----------, {%s}初始化之前, postProcessBeforeInitialization..";

    private static final String after_desc = "----------, {%s}初始化之后, postProcessAfterInitialization..";

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.info(String.format(before_desc,beanName));
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info(String.format(after_desc,beanName));
        return bean;
    }
}
