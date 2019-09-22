package org.example.algo.sorting;

import org.example.algo.ArraySorter;
import org.example.algo.Utils;

public class ShellSorter implements ArraySorter {

    public static void main(String[] args) {
        // init an array
        int arr[] = {72, 6, 57, 88, 60, 42, 83, 73, 48, 85};
        Utils.printArray(arr);

        // apply insertion sort on a series of gaps(4,2,1) of arr
        // a gap array is a series of items jump on the base array at a fixed step
        for (int gap = 4; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                for (int j = i; j > gap-1; j -= gap) {
                    if (arr[j] < arr[j-gap])
                        Utils.swapArray(arr, j, j-gap);
                    else
                        break;
                }
            }
        }

        Utils.printArray(arr);

        Utils.checkSorter(new ShellSorter());
    }

    public void sort(int[] arr) {
        // gaps in Knuth series
        int h = 1;
        while(h < arr.length/3) {
            h = h*3 + 1;
        }

        for (int gap = h; gap > 0; gap = (gap-1)/3) {
            for (int i = gap; i < arr.length; i++) {
                for (int j = i; j > gap-1; j -= gap) {
                    if (arr[j] < arr[j-gap])
                        Utils.swapArray(arr, j, j-gap);
                    else
                        break;
                }
            }
        }
    }

}
