package com.mengka.springboot.DP_strategy_01;

/**
 *  策略模式：
 *  1）一种对象行为型模式；
 *  2）将一系列的算法封装到一系列的策略类里面，作为一个抽象策略类的子类；
 *  3）准备一组算法，并将每一个算法封装起来，使得他们可以互换；
 *
 *
 * 》》由客户端自己决定在什么情况下使用什么具体策略角色；
 *
 * 》》完成一项任务，往往可以有多种不同的方式，每一种方式称为一种策略；
 *
 * @author mengka
 * @version 2020/11/20
 * @since
 */
public class Taa {

    public static void main(String... args){
        int[] data1 = new int[] { 7, 14, 2, 4, 5, 6, 15, 1, 3, 22 };
        int[] data2 = new int[] { 7, 14, 2, 4, 5, 6, 15, 1, 3, 22 };

        /**快速排序算法策略**/
        Context qscontext = new Context(new QuickSortStrategy());
        qscontext.execute(data1);

        /**合并排序算法策略**/
        Context mscontext = new Context(new MergeSortStrategy());
        mscontext.execute(data2);
    }
}
