package com.mengka.springboot.DP_strategy_01;

import com.google.gson.Gson;

/**
 * @author mengka
 * @version 2020/11/20
 * @since
 */
public class MergeSortStrategy implements IStrategy {

    private static int[] bb = new int[10];

    @Override
    public void algorithm(int[] data) {
        System.out.println("合并排序");

        int low = 0;
        int high = data.length - 1;

        long start1 = System.nanoTime();

        // 快速排序
        mergeSort(data, low, high);

        long endTime1 = System.nanoTime();
        System.out.println(new Gson().toJson(data));
        System.out.println("time = " + (endTime1 - start1) / 1000.0 + "us");
    }

    /**
     * 合并排序
     * <ul>
     * <li>时间复杂度: <font color="red">O(n*log n)</font>；</li>
     * <li>著名的排序算法；</li>
     * </ul>
     *
     * @param aa
     * @param low
     * @param high
     */
    public static void mergeSort(int[] aa, int low, int high) {
        if (low < high) {
            int mid = (low + high) / 2;
            mergeSort(aa, low, mid);
            mergeSort(aa, mid + 1, high);
            merge(aa, low, mid, high);
        }
    }

    /**
     * 合并两个有序的数组<br>
     * aa[low..mid]<br>
     * aa[(mid+1)..high]<br>
     *
     * @param aa
     * @param low
     * @param mid
     * @param high
     */
    public static void merge(int[] aa, int low, int mid, int high) {
        int s = low;
        int t = mid + 1;
        int k = low;
        while (s <= mid && t <= high) {
            if (aa[s] <= aa[t]) {
                bb[k] = aa[s];
                s = s + 1;
            } else {
                bb[k] = aa[t];
                t = t + 1;
            }
            k = k + 1;
        }

        if (s == mid + 1) {
            System.arraycopy(aa, t, bb, k, high - t + 1);
        } else {
            System.arraycopy(aa, s, bb, k, mid - s + 1);
        }
        System.arraycopy(bb, low, aa, low, high - low + 1);
    }
}
