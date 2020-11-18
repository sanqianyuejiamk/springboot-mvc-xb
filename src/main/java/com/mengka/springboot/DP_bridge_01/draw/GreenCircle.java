package com.mengka.springboot.DP_bridge_01.draw;

/**
 * @author mengka
 * @version 2020/11/17
 * @since
 */
public class GreenCircle implements DrawAPI {

    @Override
    public void drawCircle(int radius, int x, int y) {
        System.out.println("Drawing Circle[ color: green, radius: "
                + radius +", x: " +x+", "+ y +"]");
    }
}
