package org.example.algo.sorting;

import org.example.algo.ArraySorter;
import org.example.algo.Utils;

public class RadixSorter implements ArraySorter {
    private final static int RADIX = 10;

    public static void main(String[] args) {
        int[] arr = {53, 3, 542, 748, 14, 214};

        RadixSorter sorter = new RadixSorter();
        sorter.sort(arr);
        Utils.printArray(arr);

        Utils.checkSorter(sorter);
    }

    public void sort(int[] arr) {
        // create a bucket for each digits: 0,1,2,...,9
        int[][] bucket = new int[RADIX][arr.length];
        // number of items in each bucket
        int[] itemCounts = new int[RADIX];

        int maxLen = getMaxLen(arr);
        for (int i = 0, n = 1; i < maxLen; i++, n *= RADIX) {
            // put items into the corresponding bucket
            for (int j = 0; j < arr.length; j++) {
                int digit = arr[j] / n % RADIX; // digit on the position

                bucket[digit][itemCounts[digit]] = arr[j];
                itemCounts[digit]++;
            }

            // put the items in the buckets back into the array orderly
            int index = 0;
            for (int j = 0; j < itemCounts.length; j++) {
                int cnt = itemCounts[j];
                if (cnt != 0) {
                    for (int k = 0; k < cnt; k++) {
                        arr[index++] = bucket[j][k]; // jth bucket, kth item
                    }
                    itemCounts[j] = 0;  // clear the bucket
                }
            }
        }
    }

    // get the max len of the number converting to a string
    private int getMaxLen(int[] arr) {
        int max = arr[0];

        for (int i = 1; i < arr.length; i++) {
            if (max < arr[i]) max = arr[i];
        }

        return Integer.toString(max, RADIX).length();
    }
}
