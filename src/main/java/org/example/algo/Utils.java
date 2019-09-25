package org.example.algo;

import java.util.Arrays;
import java.util.Random;

public final class Utils {
    public static void  printArray(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    public static void swapArray(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Check a sorting algorithm against Arrays.sort
     *
     * @param sorter
     */
    public static void checkSorter(ArraySorter sorter) {
        int[] arr = generateRandomArray(10000);
        int[] arr2 = new int[arr.length];
        System.arraycopy(arr, 0, arr2, 0, arr.length);

        Arrays.sort(arr);
        sorter.sort(arr2);

        boolean same = true;
        for (int i = 0; i < arr2.length; i++) {
            if (arr[i] != arr2[i]) {
                same = false;
                break;
            }
        }

        System.out.println("Sorting is " + (same ? "right" : "wrong"));
    }

    public static int[] generateRandomArray(int size) {
        Random r = new Random();

        int[] arr = new int[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = r.nextInt(size);
        }

        return arr;
    }
}
