package com.mengka.springboot.a_BiFunction_01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

/**
 * @author mengka
 * @version 2020/11/23
 * @since
 */
public class Tbb {

    public static void main(String[] args) throws Exception {
        List<Double> list1 = Arrays.asList(1.0d, 2.1d, 3.3d);
        List<Double> list2 = Arrays.asList(0.1d, 0.2d, 4d);

        List<Double> result2 = listCombiner(list1, list2, (a, b) -> a + b);
        System.out.println("result2 = " + result2);
    }



    private static <T, U, R> List<R> listCombiner(List<T> list1, List<U> list2, BiFunction<T, U, R> combiner) {
        List<R> result = new ArrayList<>();
        for (int i = 0; i < list1.size(); i++) {
            result.add(combiner.apply(list1.get(i), list2.get(i)));
        }
        return result;
    }
}
