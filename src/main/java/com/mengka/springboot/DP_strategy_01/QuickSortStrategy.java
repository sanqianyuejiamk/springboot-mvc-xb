package com.mengka.springboot.DP_strategy_01;

import com.google.gson.Gson;

/**
 * @author mengka
 * @version 2020/11/20
 * @since
 */
public class QuickSortStrategy implements IStrategy {
    @Override
    public void algorithm(int[] data) {
        System.out.println("快速排序");

        int low = 0;
        int high = data.length - 1;

        long start1 = System.nanoTime();

        // 快速排序
        quicksort(data, low, high);

        long endTime1 = System.nanoTime();
        System.out.println(new Gson().toJson(data));
        System.out.println("time = " + (endTime1 - start1) / 1000.0 + "us");

    }

    /**
     * 快速排序
     *
     * @param bb
     * @param low
     * @param high
     */
    public static void quicksort(int[] bb, int low, int high) {
        int w = 0;
        if (low < high) {
            w = split(bb, low, high, w);
            quicksort(bb, low, w - 1);
            quicksort(bb, w + 1, high);
        }
    }

    /**
     * 对bb[low..high]区间内的元素进行一次快速排序
     *
     * @param bb
     * @param low
     * @param high
     * @param w
     *            bb[low]在经过一次快速排序后的位置
     */
    public static int split(int[] bb, int low, int high, int w) {
        int i = low;
        int x = bb[low];
        for (int j = low + 1; j <= high; j++) {
            if (bb[j] <= x) {
                i = i + 1;// 最后的位置向右移动一位
                if (i != j) {
                    int temp = bb[i];
                    bb[i] = bb[j];
                    bb[j] = temp;
                }
            }
        }
        /**
         * 将bb[low]交换到它对应的位置上
         */
        int temp = bb[low];
        bb[low] = bb[i];
        bb[i] = temp;
        w = i;
        return w;
    }
}
