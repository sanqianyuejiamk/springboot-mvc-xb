package com.mengka.springboot.DP_bridge_01;

import com.mengka.springboot.DP_bridge_01.draw.GreenCircle;
import com.mengka.springboot.DP_bridge_01.draw.RedCircle;

/**
 *  桥接模式（Bridge Pattern）
 *   演示桥接模式（Bridge Pattern）的用法，使用相同的抽象类方法但是不同的桥接实现类，来画出不同颜色的圆。
 *
 *   https://www.runoob.com/wp-content/uploads/2014/08/20201015-bridge.svg
 *
 *  意图：将抽象部分与实现部分分离（解耦），使它们都可以独立的变化.
 *
 *
 *  文档：https://www.runoob.com/design-pattern/bridge-pattern.html
 *
 * @author mengka
 * @version 2020/11/17
 * @since
 */
public class Taa {

    public static void main(String... args){
        Shape redCircle = new Circle(100,100, 10, new RedCircle());
        Shape greenCircle = new Circle(100,100, 10, new GreenCircle());

        redCircle.draw();
        greenCircle.draw();
    }
}
