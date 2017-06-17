package com.mengka.springboot.importBeanDefinitionRegistrar_01;

import com.mengka.springboot.util.TimeUtil;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import java.util.Date;

/**
 *  This Interface 'ImportBeanDefinitionRegistrar' is to be implemented by types that register additional bean definitions when processing @Configuration classes.
 *
 * @author mengka
 * @date 2017/06/17.
 */
public class MyBeanRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(AppBean.class);
        beanDefinition.getPropertyValues().addPropertyValue("str", "value set from registrar["+ TimeUtil.toDate(new Date(),TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
        registry.registerBeanDefinition("appBean", beanDefinition);
    }
}
