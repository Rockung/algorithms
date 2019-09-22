package org.example.algo.sorting;

import org.example.algo.ArraySorter;
import org.example.algo.Utils;

public class InsertionSorter implements ArraySorter {

    public static void main(String[] args) {
        InsertionSorter sorter = new InsertionSorter();
        Utils.checkSorter(sorter);
    }

    public void sort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            // assume the slice before index i is sorted
            // bubble backwards to its right position
            for (int j = i; j > 0; j--) {
                if (arr[j] < arr[j-1])
                    Utils.swapArray(arr, j, j-1);
                else
                    break;
            }
        }
    }

}
