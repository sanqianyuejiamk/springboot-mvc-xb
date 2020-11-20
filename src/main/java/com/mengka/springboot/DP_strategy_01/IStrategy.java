package com.mengka.springboot.DP_strategy_01;

/**
 *  策略模式（Strategy Pattern）是定义了一组算法，将每个算法都封装起来，并且使它们之间可以互换。
 *
 * @author mengka
 * @version 2020/11/20
 * @since
 */
public interface IStrategy {

    void algorithm(int[] data);
}
