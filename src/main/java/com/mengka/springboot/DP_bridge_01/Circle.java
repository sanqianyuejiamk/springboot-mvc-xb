package com.mengka.springboot.DP_bridge_01;

import com.mengka.springboot.DP_bridge_01.draw.DrawAPI;

/**
 *  桥接模式，将一个大类或一系列紧密相关的类拆分为抽象和实现两个独立的层次结构， 从而能在开发时分别使用。
 *
 * @author mengka
 * @version 2020/11/17
 * @since
 */
public class Circle extends Shape {

    private int x, y, radius;

    public Circle(int x, int y, int radius, DrawAPI drawAPI) {
        super(drawAPI);
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public void draw() {
        drawAPI.drawCircle(radius,x,y);
    }
}
