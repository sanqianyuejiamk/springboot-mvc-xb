package com.mengka.springboot.thread_callable_01;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @author mengka
 * @date 2017/06/20.
 */
@Slf4j
public class MengkaCallable implements Callable {
    @Override
    public Object call() throws Exception {
        Thread.sleep(2000);
        return "task2";
    }
}
