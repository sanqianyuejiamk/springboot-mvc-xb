package com.mengka.springboot.DP_factory_01.keyboard;

/**
 *  具体产品实现：定义一个将被相应具体工厂创建的产品对象。
 *
 * @author mengka
 * @version 2020/12/3
 * @since
 */
public class DellKeyboard implements Keyboard {
    @Override
    public void print() {
        //...dell...dell;
    }
}
