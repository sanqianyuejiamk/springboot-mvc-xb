package com.mengka.springboot.rxJava_03;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;

/**
 * @author mengka
 * @date 2017/08/17.
 */
@Slf4j
public class map_04 {

    public static void main(String[] args) {
        Observable.just("Hello, world!")
                .map(s -> s + " -Dan")
                .subscribe(s -> System.out.println(s));


        Observable.just("Hello, world!")
                .map(s -> s + " -Dan")
                .map(s -> s.hashCode())
                .map(i -> Integer.toString(i))
                .subscribe(s -> System.out.println(s));
    }
}
