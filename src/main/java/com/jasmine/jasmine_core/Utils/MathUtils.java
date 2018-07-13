package com.jasmine.jasmine_core.Utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MathUtils {
    public static double Median(List<Integer> list) {
        Collections.sort(list);
        return list.size() % 2 == 0 ? ((double) list.get(list.size() / 2) + (double) list.get(list.size() / 2 - 1)) / 2 : (double) list.get(list.size() / 2);
    }

    public static double Median(int[] array) {
        Arrays.sort(array);
        return array.length % 2 == 0 ? ((double) array[array.length / 2] + (double) array[array.length / 2 - 1]) / 2 : (double) array[array.length / 2];
    }

    public static int Median(int a, int b, int c) {
        return ((a - b) * (b - c) > -1 ? b : ((a - b) * (a - c) < 1 ? a : c));
    }

    public static double Median(double a, double b, double c) {
        return ((a - b) * (b - c) > -1 ? b : ((a - b) * (a - c) < 1 ? a : c));
    }
}
