package com.mengka.springboot.hystrix_dashboard_01;

import lombok.extern.slf4j.Slf4j;

/**
 * @author mengka
 * @date 2017/07/11.
 */
@Slf4j
public class Taa {

    public static void main(String[] args){
        JettyServer jettyServer = new JettyServer();
        jettyServer.init();
    }
}
