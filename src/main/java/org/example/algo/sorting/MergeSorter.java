package org.example.algo.sorting;

import org.example.algo.ArraySorter;
import org.example.algo.Utils;

public class MergeSorter implements ArraySorter {

    public static void main(String[] args) {
//        int[] a1 = {1, 4, 7, 8, 2, 5, 6};
//        merge(a1, 0, 3, 6);
//        Utils.printArray(a1);
//
//        int[] a2 = {1, 4, 7, 9, 2, 5, 8, 10, 14, 15};
//        merge(a2, 0, 3, 9);
//        Utils.printArray(a2);

        MergeSorter sorter = new MergeSorter();
        Utils.checkSorter(sorter);

//        int[] a3 = {4, 2, 3, 5};
//        sorter.sort(a3);
//        Utils.printArray(a3);
    }

    public void sort(int[] arr) {
        doMergeSort(arr, 0, arr.length-1);
    }

    // break down recursively the array into sub-arrays that contains only one item,
    // so can be merged upwards layer by layer
    private static void doMergeSort(int[] arr, int low, int high) {
        if (low >= high) return;              // only one item, so it's sorted

        int mid = low + (high - low) / 2;

        doMergeSort(arr, low, mid);            // the low part is sorted
        doMergeSort(arr, mid + 1, high);  // the high part is sorted

        // here, we get two slices sorted, so can be merged
        merge(arr, low, mid, high);
    }

    /**
     * **merge** makes arr[low, high] as a sorted slice
     * <p>
     * assuming
     * 1. low < mid < high
     * 2. arr[low, mid] and arr[mid+1, high] are **sorted** slices
     *
     * @param arr
     * @param low  the low index in the array
     * @param mid  the mid index in the array
     * @param high the high index in the array
     */
    private static void merge(int[] arr, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];

        int i = low;     // pointer to the start of the low part
        int j = mid + 1; // pointer to the start of the high part
        int k = 0;       // pointer to the start of temp

        // fetch the less one and move one step the corresponding pointer
        while (i <= mid && j <= high) {
            temp[k++] = (arr[i] <= arr[j]) ? arr[i++] : arr[j++];
//            if (arr[i] <= arr[j]) {
//                temp[k] = arr[i];
//                i++;
//            } else {
//                temp[k] = arr[j];
//                j++;
//            }
//            k++;
        }

        // two parts maybe have different sizes
        while (i <= mid) temp[k++] = arr[i++];
        while (j <= high) temp[k++] = arr[j++];

        // copy back
        for (int m = 0; m < temp.length; m++) {
            arr[low + m] = temp[m];
        }
    }

}
