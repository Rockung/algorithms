package org.example.algo.search;

import org.example.algo.ArraySearcher;
import org.example.algo.Utils;
import org.example.algo.sorting.QuickSorter;

public class BinarySearcher implements ArraySearcher {

    public static void main(String[] args) {
        // ========== 0  1  2  3   4   5   6   7   8====//
        int[] arr = { 3, 5, 9, 10, 13, 24, 57, 89, 100 };

        BinarySearcher searcher = new BinarySearcher();
        System.out.println(searcher.search_r(arr, 13));

        QuickSorter sorter = new QuickSorter();
        int[] arr2 = Utils.generateRandomArray(10000);
        int key = arr2[0];
        sorter.sort(arr2);

        System.out.println("    search key: " + key);
        System.out.println("  search index: " + searcher.search(arr2, key));
        System.out.println("search_r index: " + searcher.search_r(arr2, key));
        System.out.println("      search 0: " + searcher.search(arr2, 0));
        System.out.println("    search_r 0: " + searcher.search_r(arr2, 0));
    }

    /**
     * Search a key in a sorted array
     *
     * @param arr the array that is already sorted
     * @param key the key
     * @return the index of the **key** in the array, -1 if not found
     */
    public int search(int[] arr, int key) {
        int low = 0;
        int high = arr.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (key == arr[mid]) {
                return mid;
            } else if (key < arr[mid]) {
                high = mid -1;
            } else {
                low = mid + 1;
            }
        }

        return -1;
    }

    /**
     * Search a key in a sorted array(recursion version)
     *
     * @param arr the array that is already sorted
     * @param key the key
     * @return the index of the **key** in the array, -1 if not found
     */
    public int search_r(int[] arr, int key) {
        return search_r(arr, key, 0, arr.length - 1);
    }

    /**
     *  Search a key in a slice of an sorted array
     *
     * @param arr
     * @param key
     * @param low the low index
     * @param high the high index
     * @return
     */
    private int search_r(int[] arr, int key, int low, int high) {
        if (low > high) return -1;   // !!! not low >= high

        int mid = low + (high - low) / 2;

        if (key == arr[mid]) {
            return mid;
        } else if (key < arr[mid]) {
            return search_r(arr, key, low, mid - 1);
        } else {
            return search_r(arr, key, mid + 1, high);
        }
    }

}
