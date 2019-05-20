package com.wei.code;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description
 * @date 2019-05-20
 * @email weishuxin@icourt.cc
 */
public class TwoSum {
    /**
     * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
     * <p>
     * You may assume that each input would have exactly one solution, and you may not use the same element twice.
     * <p>
     * Example:
     * <p>
     * Given nums = [2, 7, 11, 15], target = 9,
     * <p>
     * Because nums[0] + nums[1] = 2 + 7 = 9,
     * return [0, 1].
     */

    /**
     * Time complexity : O(n^2). For each element, we try to find its complement by looping through the rest of array which takes O(n)O(n) time. Therefore, the time complexity is O(n^2).
     * <p>
     * Space complexity : O(1).
     */
    public static int[] solution1(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int k = i + 1; k < nums.length; k++) {
                if (nums[i] + nums[k] == target) {
                    return new int[]{i, k};
                }
            }
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    /**
     * Time complexity : O(n). We traverse the list containing nn elements only once. Each look up in the table costs only O(1)time.
     * <p>
     * Space complexity : O(n). The extra space required depends on the number of items stored in the hash table, which stores at most nn elements.
     */
    public static int[] solution2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution1(new int[]{2, 7, 3, 56, 7, 6, 16, 8}, 9)));
    }
}
