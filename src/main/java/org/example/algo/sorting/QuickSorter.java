package org.example.algo.sorting;

import org.example.algo.ArraySorter;
import org.example.algo.Utils;

public class QuickSorter implements ArraySorter {

    public static void main(String[] args) {
        // init an array
        int arr[] = {72, 6, 57, 88, 60, 42, 83, 73, 48, 85};
        Utils.printArray(arr);

        // quick sorting
        QuickSorter sorter = new QuickSorter();
        sorter.sort(arr);

        // print the sorted array
        Utils.printArray(arr);

        Utils.checkSorter(new QuickSorter());
    }

    public void sort(int[] arr) {
        int low = 0;
        int high = arr.length - 1;
        doQuickSort(arr, low, high);
    }

    /**
     * Partition the array and recursively sort those arrays
     *
     * @param arr the array to be sorted
     * @param low the first index
     * @param high the last index
     */
    private static void doQuickSort(int[] arr, int low, int high) {
        if (low < high) {
            // partition the slice **arr[low, high]** of **arr** into two parts,
            // return the index **mid** between the partitions
            int mid = doPartition(arr, low, high);

            // apply doQuickSort on left partition
            doQuickSort(arr, low, mid-1);

            // apply doQuickSort on right partition
            doQuickSort(arr, mid+1, high);
        }
    }

    /***
     *  Partition the slice arr[low, high] into left slice | pivot | right slice
     *
     *  Use the first item arr[low] as the pivot, move the items less than the
     *  pivot to the left and the items equal or greater than the pivot to the
     *  right
     *
     * @param arr the array
     * @param low the start index in the array
     * @param high the end index in the array
     * @return the middle index
     */
    private static int doPartition(int[] arr, int low, int high) {
        // assign the right pointer i and the left pointer j
        int i = low;
        int j = high;

        // assign the first number as the pivot
        int p = arr[low];

        // loop to do partition operation: fill holes
        while(i < j) {
            // move the right pointer **j** towards the middle, find the first item
            // less than the pivot
            while(arr[j] >= p && i < j) j--;

            // fill the item into the hole and move the left pointer **i** one step
            // towards the middle(i++)
            if (i < j) {
                arr[i] = arr[j];
                i++;
            }

            // move the left pointer **i** towards the middle, find the first item
            // greater than or equal to the pivot
            while(arr[i] < p && i < j) i++;

            // fill the item into the hole and move the right pointer **j** one step
            // towards the middle(j--)
            if (i < j) {
                arr[j] = arr[i];
                j--;
            }
        }

        // fill the base into the hole
        arr[i] = p; // arr[j] = p;

        // return the hole index
        return i; // return j;
    }
}
