package com.mengka.springboot.DP_factory_01;

import com.mengka.springboot.DP_factory_01.keyboard.Keyboard;

/**
 * @author mengka
 * @version 2020/12/3
 * @since
 */
public class Taa {

    //客户端代码。实例化不同的工厂子类，可以通过不同的创建方法创建不同的产品
    public static void main(String... args){
        IFactory dellFactory = new DellFactory();
        IFactory HPFactory = new HPFactory();
        //创建戴尔键盘
        Keyboard dellKeyboard = dellFactory.createKeyboard();
        //...
    }
}
