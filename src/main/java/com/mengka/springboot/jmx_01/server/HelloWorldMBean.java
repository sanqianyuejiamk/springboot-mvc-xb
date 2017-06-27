package com.mengka.springboot.jmx_01.server;

/**
 *  定义向外部提供的API接口
 *
 * @author mengka
 * @date 2017/06/27.
 */
public interface HelloWorldMBean {

    public String getName();

    public void setName(String name);

    public String printHello();

    public String printHello(String whoName);
}
