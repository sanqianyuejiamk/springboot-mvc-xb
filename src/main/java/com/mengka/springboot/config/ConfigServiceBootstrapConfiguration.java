//package com.mengka.springboot.config;
//
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//import org.springframework.core.env.ConfigurableEnvironment;
//import org.springframework.retry.annotation.EnableRetry;
//import org.springframework.retry.annotation.Retryable;
//import org.springframework.retry.interceptor.RetryInterceptorBuilder;
//import org.springframework.retry.interceptor.RetryOperationsInterceptor;
//
///**
// * @author mengka
// * @date 2017/07/11.
// */
//@Configuration
//@EnableConfigurationProperties
//public class ConfigServiceBootstrapConfiguration {
//
//    @Autowired
//    private ConfigurableEnvironment environment;
//
//    @Bean
//    public ConfigClientProperties configClientProperties() {
//        ConfigClientProperties client = new ConfigClientProperties(this.environment);
//        return client;
//    }
//
//    @Bean
//    @ConditionalOnMissingBean(ConfigServicePropertySourceLocator.class)
//    @ConditionalOnProperty(value = "spring.cloud.config.enabled", matchIfMissing = true)
//    public ConfigServicePropertySourceLocator configServicePropertySource(ConfigClientProperties properties) {
//        ConfigServicePropertySourceLocator locator = new ConfigServicePropertySourceLocator(
//                properties);
//        return locator;
//    }
//
//    @ConditionalOnProperty(value = "spring.cloud.config.failFast", matchIfMissing=false)
//    @ConditionalOnClass({ Retryable.class, Aspect.class, AopAutoConfiguration.class })
//    @Configuration
//    @EnableRetry(proxyTargetClass = true)
//    @Import(AopAutoConfiguration.class)
//    @EnableConfigurationProperties(RetryProperties.class)
//    protected static class RetryConfiguration {
//
//        @Bean
//        @ConditionalOnMissingBean(name = "configServerRetryInterceptor")
//        public RetryOperationsInterceptor configServerRetryInterceptor(
//                RetryProperties properties) {
//            return RetryInterceptorBuilder
//                    .stateless()
//                    .backOffOptions(properties.getInitialInterval(),
//                            properties.getMultiplier(), properties.getMaxInterval())
//                    .maxAttempts(properties.getMaxAttempts()).build();
//        }
//    }
//}
