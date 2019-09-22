package org.example.algo.sorting;

import org.example.algo.ArraySorter;
import org.example.algo.Utils;

public class SelectionSorter implements ArraySorter {

    public static void main(String[] args) {
        // init an array
        int arr[] = {72, 6, 57, 88, 60, 42, 83, 73, 48, 85};
        Utils.printArray(arr);

        // find the index of the minimum value
        int minIndex = 0;
        for (int j = 0+1; j < arr.length; j++) {
            if (arr[j] < arr[minIndex])
                minIndex = j;
        }
        System.out.println("minIndex: " + minIndex);

        // exchange the values of minIndex and most-left index
        int temp = arr[0];
        arr[0] = arr[minIndex];
        arr[minIndex] = temp;

        // print the sorted array
        Utils.printArray(arr);

        Utils.checkSorter(new SelectionSorter());
    }

    public void sort(int[] arr) {
        // add the outer loop
        // assign index i to the inner loop
        // optimize the outer loop

        // for (int i = 0; i < arr.length; i++) {
        for (int i = 0; i < arr.length-1; i++) {
            // find the index of the minimum value
            // int minIndex = 0;
            int minIndex = i;
            // for (int j = 0+1; j < arr.length; j++) {
            for (int j = i+1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex])
                    minIndex = j;
            }
            // System.out.println("minIndex: " + minIndex);

            // exchange the values of minIndex and most-left index
//            int temp = arr[0];
//            arr[0] = arr[minIndex];
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

}
