package com.mengka.springboot.DP_bridge_01;

import com.mengka.springboot.DP_bridge_01.draw.DrawAPI;

/**
 *  桥接（Bridge）是用于把抽象化与实现化解耦，使得二者可以独立变化。
 *
 *  这种类型的设计模式属于结构型模式，它通过提供抽象化和实现化之间的桥接结构，来实现二者的解耦。
 *
 * @author xiafeng
 * @version farabbit2.0, 2020/11/17
 * @since farabbit2.0
 */
public abstract class Shape {

    protected DrawAPI drawAPI;

    protected Shape(DrawAPI drawAPI){
        this.drawAPI = drawAPI;
    }

    public abstract void draw();
}
