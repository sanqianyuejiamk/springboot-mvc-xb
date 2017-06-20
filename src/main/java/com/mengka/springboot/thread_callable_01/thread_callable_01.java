package com.mengka.springboot.thread_callable_01;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author mengka
 * @date 2017/06/20.
 */
@Slf4j
public class thread_callable_01 {

    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newWorkStealingPool();
        List<Callable<String>> callables = Arrays.asList(
                () -> "task1",
                new MengkaCallable(),
                () -> "task3");

        /**
         *  invokeAll():
         *    1) 支持批量提交多个callables；
         *    2）批量返回，a list of futures；
         */
        executor.invokeAll(callables)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                })
                .forEach(System.out::println);
    }
}
