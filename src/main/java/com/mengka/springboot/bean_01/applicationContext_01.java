package com.mengka.springboot.bean_01;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author mengka
 * @date 2017/09/20.
 */
@Slf4j
public class applicationContext_01 {

    public static void main(String[] args){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                new String[]{"aa.xml","mengka-bean.xml"});

        Mengka mengka = (Mengka)applicationContext.getBean("mengka");
        log.info("----------, mengka = "+ JSON.toJSONString(mengka));


    }
}
