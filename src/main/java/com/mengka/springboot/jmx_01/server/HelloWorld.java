package com.mengka.springboot.jmx_01.server;

import com.mengka.springboot.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;

/**
 *  被管理的类，需要实现相应的MBean接口
 *
 * @author mengka
 * @date 2017/06/27.
 */
@Slf4j
public class HelloWorld implements HelloWorldMBean {

    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String printHello() {
        String result = "hello world..["+ TimeUtil.toDate(new Date(),TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS);
        log.info(result);
        return result;
    }

    @Override
    public String printHello(String whoName) {
        String result = "hello "+whoName+"..["+ TimeUtil.toDate(new Date(),TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS);
        log.info(result);
        return result;
    }
}
