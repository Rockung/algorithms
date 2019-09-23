package org.example.algo.sorting;

import org.example.algo.ArraySorter;
import org.example.algo.Utils;

/**
 * In a max-heap, parent node is always greater than or equal to child nodes
 */
public class HeapSorter implements ArraySorter {

    public static void main(String[] args) {
        int[] arr = {4, 6, 8, 5, 9};

        buildMaxHeap(arr, 1, arr.length);
        buildMaxHeap(arr, 0, arr.length);
        Utils.printArray(arr);

        HeapSorter sorter = new HeapSorter();
        Utils.checkSorter(sorter);
    }

    /**
     * sort the array in ascending order
     * <p>
     * 1. build a max-heap from the array
     * 2. swap the root node with the last node and delete the last node
     * from the heap
     * 3. rebuild a max-heap from the heap, and so on.
     *
     * @param arr
     */
    public void sort(int[] arr) {
        // int last_index = arr.length - 1;
        // int parent_index = (last_index - 1) / 2;
        // int parent_index = arr.length/2 - 1

        // build a max-heap from bottom to top
        // start from the last non-leaf node
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            buildMaxHeap(arr, i, arr.length);
//            heapify(arr, i, arr.length);
        }

        // decrease the array size from the tail
        for (int i = arr.length - 1; i > 0; i--) {
            // swap the values of the root with the tail
            Utils.swapArray(arr, 0, i);

            // rebuild the max heap
            buildMaxHeap(arr, 0, i);
//            heapify(arr, 0, i);
        }
    }

    /**
     * build a max-heap from an array(treated as a binary tree)
     * <p>
     * adjust the subtree of root i into a max-heap
     *
     * @param arr
     * @param i   the index of a non-leaf node
     * @param n   number of items to be adjust, decreasingly
     */
    public static void buildMaxHeap(int[] arr, int i, int n) {
        // save the old root value
        int temp = arr[i];

        // traverse into the left sub-node
        for (int k = i * 2 + 1; k < n; k = k * 2 + 1) {
            // left < right, let k point to the right
            if (k + 1 < n && arr[k] < arr[k + 1]) {
                k++;
            }

            // child > parent
            if (arr[k] > temp) {
                arr[i] = arr[k];

                // trace the location of the old root value should go
                i = k;
            } else {
                // scanning from bottom to top, so bottom nodes are done
                break; // !!!!
            }
        }

        // put the old root value into this node
        arr[i] = temp;
    }


    void heapify(int[] tree, int i, int n) {
        if (i >= n) return;

        int c1 = 2 * i + 1; // left sub-node
        int c2 = c1 + 1;    // right sub-node

        // recursively heapify childrens
        if (c1 < n) heapify(tree, c1, n);
        if (c2 < n) heapify(tree, c2, n);

        // here, child nodes is heapified
        // swap parent with one of children
        int max = i;
        if (c1 < n && tree[c1] > tree[max]) max = c1;
        if (c2 < n && tree[c2] > tree[max]) max = c2;
        if (max != i) Utils.swapArray(tree, max, i);
    }

    void heapify2(int[] tree, int i, int n) {
        if (i >= n) return;

        int c1 = 2 * i + 1; // left sub-node
        int c2 = c1 + 1;    // right sub-node

        int max = i;
        if (c1 < n && tree[c1] > tree[max]) max = c1;
        if (c2 < n && tree[c2] > tree[max]) max = c2;
        if (max != i) {
            // swap parent with one of children
            Utils.swapArray(tree, max, i);

            //
            heapify2(tree, max, n);
        }
    }
}
