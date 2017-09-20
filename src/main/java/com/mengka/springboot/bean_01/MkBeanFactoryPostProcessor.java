package com.mengka.springboot.bean_01;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 *  在容器获取bean的所有信息之后，会执行该方法的接口<hr>
 *  ClassPathResource rs = new ClassPathResource("/aa.xml");
 *   这句执行完了之后，执行 <font color="red">postProcessBeanFactory()</font>
 * @author mengka
 *
 */
public class MkBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(
            ConfigurableListableBeanFactory beanFactory) throws BeansException {


        String[] aaStrings=beanFactory.getBeanDefinitionNames();

        /**
         *  打印出容器里面,装载了的所有的bean的名字
         *
         */
        for(int i=0;i<aaStrings.length;i++){
            System.out.println("我正在努力加载： "+aaStrings[i]);
        }
    }

}