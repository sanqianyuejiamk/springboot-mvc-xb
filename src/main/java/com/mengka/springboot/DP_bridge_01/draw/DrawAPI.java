package com.mengka.springboot.DP_bridge_01.draw;

/**
 * @author mengka
 * @version 2020/11/17
 * @since
 */
public interface DrawAPI {

    /**
     *  画一个圆圈
     *
     * @param radius 半径
     * @param x
     * @param y
     */
    public void drawCircle(int radius, int x, int y);
}
