package com.wei.code;

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description
 * @date 2019-05-21
 * @email weishuxin@icourt.cc
 */
public class MedianOfTwoSortedArrays {
    /**
     * There are two sorted arrays nums1 and nums2 of size m and n respectively.
     * <p>
     * Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
     * <p>
     * You may assume nums1 and nums2 cannot be both empty.
     * <p>
     * Example 1:
     * <p>
     * nums1 = [1, 3]
     * nums2 = [2]
     * <p>
     * The median is 2.0
     * Example 2:
     * <p>
     * nums1 = [1, 2]
     * nums2 = [3, 4]
     * <p>
     * The median is (2 + 3)/2 = 2.5
     */

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int length = nums1.length + nums2.length;

        int index = 0;

        double result = 0, temp = 0;
        while (index < nums1.length && index < nums2.length) {
            if (nums1[index] > nums2[index] && result < nums1[index]) {
                result = nums1[index];
            } else if (result < nums2[index]) {
                result = nums2[index];
            }
            if (index * 2 >= length / 2) {
                return (nums1[index] + nums2[index]) / 2.0;
            } else if (index * 2 > length / 2) {
                return 0;
            }
            index++;
        }
        return 0;
    }


    public static void main(String[] args) {
        System.out.println(findMedianSortedArrays(new int[]{2, 3, 4}, new int[]{5, 6, 7}) + "");
    }
}
