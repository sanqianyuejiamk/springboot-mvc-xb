package com.mengka.springboot.jmx_02.server;

/**
 * @author mengka
 * @date 2017/06/28.
 */
public interface HelloWorldMBean {

    public long getUptime();

    public String getMemory();
}
