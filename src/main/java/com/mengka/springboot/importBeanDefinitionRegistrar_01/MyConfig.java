package com.mengka.springboot.importBeanDefinitionRegistrar_01;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author mengka
 * @date 2017/06/17.
 */
@Configuration
@Import(MyBeanRegistrar.class)
public class MyConfig {

    @Bean
    public ClientBean clientBean () {
        return new ClientBean();
    }
}
