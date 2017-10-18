package com.mengka.springboot.activemq_03;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author huangyy
 * @date 2017/09/30.
 */
@Slf4j
public class Taa {

    public static void main(String[] args){
        log.info("----------------, activemq_03 TaaProducer test....");

        String serviceConfigXMLs[] = new String[]{"activemq_03/activemq-context.xml"};
        ApplicationContext context = new ClassPathXmlApplicationContext(serviceConfigXMLs);

        log.info("----------------, activemq_03 TaaProducer end....");
    }
}
