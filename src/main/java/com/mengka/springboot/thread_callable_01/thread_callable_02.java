package com.mengka.springboot.thread_callable_01;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author mengka
 * @date 2017/06/20.
 */
@Slf4j
public class thread_callable_02 {

    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(
                callable("task1", 2),
                callable("task2", 1),
                callable("task3", 3));

        /**
         *  invokeAny():
         *    1) which works slightly;
         */
        String result = executor.invokeAny(callables);
        System.out.println(result);
    }

    static Callable<String> callable(String result, long sleepSeconds) {
        return () -> {
            TimeUnit.SECONDS.sleep(sleepSeconds);
            return result;
        };
    }
}
