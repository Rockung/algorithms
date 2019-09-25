package org.example.algo.search;

import org.example.algo.ArraySearcher;
import org.example.algo.Utils;
import org.example.algo.sorting.QuickSorter;

public class InterpolationSearcher implements ArraySearcher {

    public static void main(String[] args) {
        QuickSorter sorter = new QuickSorter();
        int[] arr = Utils.generateRandomArray(10000);
        int key = arr[0];
        sorter.sort(arr);

        BinarySearcher binarySearcher = new BinarySearcher();
        InterpolationSearcher interpolationSearcher = new InterpolationSearcher();

        System.out.println("    search key: " + key);
        System.out.println("  search index: " + interpolationSearcher.search(arr, key));
        System.out.println("  search index: " + binarySearcher.search(arr, key));
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
            int gap = (arr[high] - arr[low]);
            int mid = (gap == 0) ? low : low + (high - low) * (key - arr[low]) / gap;

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

}
