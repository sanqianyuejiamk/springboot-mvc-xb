package com.mengka.springboot.thread_jdk8_01;

/**
 *  jdk8 lambda expressions使用多线程
 *
 * @author mengka
 * @date 2017/06/20.
 */
public class thread_jdk8_01 {

    public static void main(String[] args) {
        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello " + threadName);
        };

        task.run();

        Thread thread = new Thread(task);
        thread.start();

        System.out.println("Done!");
    }
}
