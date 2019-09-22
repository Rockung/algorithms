package org.example.algo.sorting;

import org.example.algo.ArraySorter;
import org.example.algo.Utils;

public class BubleSorter implements ArraySorter {

    public static void main(String[] args) {
        BubleSorter sorter = new BubleSorter();
        Utils.checkSorter(sorter);
    }

    public void sort(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            // the most end part of arr is sorted
            for (int j = 0; j < i; j++) {
                // buble the value to the end of arr
                if (arr[j] > arr[j+1])
                    Utils.swapArray(arr, j, j + 1);
            }
        }
    }

}
