package com.lsm.juc.algorithm;

import java.util.LinkedList;

/**
 * 求出数组中第三大的数字
 * 前提是没有重复的数字
 */
public class GetThirdMaxNumber {

    public static int getThirdLargest(int[] arr, int length) {
        int temp;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (arr[i] > arr[j]) {
                    temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return arr[length - 3];
    }

    public static void main(String args[]) {
        int arr[] = {1, 2, 3, 4, 5, 6};
        System.out.println("Third Largest: " + getThirdLargest(arr, arr.length));
    }
}

