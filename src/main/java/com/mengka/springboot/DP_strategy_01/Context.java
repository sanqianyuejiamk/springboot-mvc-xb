package com.mengka.springboot.DP_strategy_01;

/**
 * @author huangyy
 * @version farabbit2.0, 2019/6/18
 * @since farabbit2.0
 */
public class Context {

    private IStrategy strategy;

    public Context(IStrategy strategy){
        this.strategy = strategy;
    }

    public void execute(int[] data){
        strategy.algorithm(data);
    }
}
