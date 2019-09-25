package org.example.algo.search;

import org.example.algo.ArraySearcher;
import org.example.algo.Utils;
import org.example.algo.sorting.QuickSorter;

import java.util.Arrays;

/**
 * Fibonacci search uses fibonacci series to adjust the middle position such
 * as that doing in binary/interpolation search.
 *
 * Fibonacci series:
 *
 *    fib(1) = fib(2) = 1
 *    fib(k) = fib(k-1) + fib(k-2)
 *
 * current loop with k
 *    |-------------------------------------------------------|--------------|
 *    low                                                   high           fib(k)
 *
 * assign middle position
 *    mid = low + fib(k-1) - 1
 *    |-----------------------------------|-------------------|--------------|
 *    low                               mid                 high           fib(k)
 *    |--------- fib(k-1)-----------------|---------   fib(k-2)-------------|
 *
 *  next loop with k-1 (switching to low or high part)
 *    |            k = k-1                |             k = k - 2           |
 *
 */
public class FibonacciSearcher implements ArraySearcher {

    public static void main(String[] args) {
        QuickSorter sorter = new QuickSorter();
        int[] arr = Utils.generateRandomArray(10000);
        int key = arr[0];
        sorter.sort(arr);

        BinarySearcher binarySearcher = new BinarySearcher();
        FibonacciSearcher fibonacciSearcher = new FibonacciSearcher();

        System.out.println("    search key: " + key);
        int index = 0;
        index = binarySearcher.search(arr, key);
        System.out.println("  binary index: " + index + " value: " + arr[index]);
        index = fibonacciSearcher.search(arr, key);
        System.out.println("  myself index: " + index + " value: " + arr[index]);
        index = fibonacciSearcher.search_no_copy(arr, key);
        System.out.println("  nocopy index: " + index + " value: " + arr[index]);
    }

    public int search(int[] arr, int key) {
        int low = 0;
        int high = arr.length - 1;

        // create the fibonacci array to cover the arr size
        int[] fib = createFibArray(arr.length);
        int k = fib.length - 1;

        // copy and fill with the last value
        int[] temp = Arrays.copyOf(arr, fib[k]);
        for (int i = high + 1; i < temp.length; i++) {
            temp[i] = arr[high];
        }

        while (low <= high) {
            int mid = low + fib[k - 1] - 1;
            int mid_val = temp[mid];

            if (key < mid_val) {       // low part
                high = mid - 1;
                k--;
            } else if (key > mid_val){ // high part
                low = mid + 1;
                k -= 2;
            } else {                   // equal
                return (mid <= high) ? mid : high;
            }
        }

        return -1;
    }

    public int search_no_copy(int[] arr, int key) {
        int low = 0;
        int high = arr.length - 1;

        // create the fibonacci array to cover the arr size
        int[] fib = createFibArray(arr.length);
        int k = fib.length -1;

        int bound = high;
        while (low <= high) {
            int mid = low + fib[k - 1] - 1;
            int mid_val = (mid > bound) ? arr[bound] : arr[mid];

            if (key < mid_val) {        // low part
                high = mid - 1;
                k--;
            } else if (key > mid_val) { // high part
                low = mid + 1;
                k -= 2;
            } else {                    // equal
                return (mid <= high) ? mid : high;
            }
        }

        return -1;
    }

    /**
     * Dynamically allocate a fibonicci array
     *
     * @param size the array size
     * @return
     */
    private int[] createFibArray(int size) {
        int fibSize = computeFibSize(size);

        int fib[] = new int[fibSize];
        fib[0] = 1;
        fib[1] = 1;
        for (int i = 2; i < fibSize; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }
        return fib;
    }

    /**
     * Compute the index of first fibonicci greater or equal to the size
     * in the fibonicci series(from 1).
     *
     * @param size the size of an array
     * @return
     */
    private int computeFibSize(int size) {
        int a = 1;
        int b = 1;
        int c = b + a;
        int k = 0;
        while(c < size) {
            a = b;
            b = c;
            c = b + a;
            k++;
        }

        return k + 3;
    }
}
