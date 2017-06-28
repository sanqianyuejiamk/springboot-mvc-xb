package com.mengka.springboot.jmx_02.server;

import lombok.extern.slf4j.Slf4j;

/**
 * @author mengka
 * @date 2017/06/28.
 */
@Slf4j
public class HelloWorld implements HelloWorldMBean {

    private final long startTime;

    private Runtime runtime = Runtime.getRuntime();

    public HelloWorld() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public long getUptime() {
        return System.currentTimeMillis() - startTime;
    }

    @Override
    public String getMemory() {
        float freeMemory = (float) runtime.freeMemory();
        float totalMemory = (float) runtime.totalMemory();
        String memory = "idle scale : " + (freeMemory / totalMemory) + "%; freeMemory="
                + (freeMemory) / 1024 + " KB; totalMemory=" + (totalMemory) / 1024 + " KB";
        return memory;
    }
}
