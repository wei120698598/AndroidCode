package com.wei.code;

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description
 * @date 2019-05-20
 * @email weishuxin@icourt.cc
 */
public class AddTwoSum {
    /**
     * You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.
     *
     * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
     *
     * Example:
     *
     * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
     * Output: 7 -> 0 -> 8
     * Explanation: 342 + 465 = 807.
     */

    /**
     * Definition for singly-linked list.
     */

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static ListNode solution1(ListNode l1, ListNode l2) {
        ListNode header = new ListNode(0);
        ListNode end = header;

        int i = 0;
        while (l1 != null && l2 != null) {
            int current = l1.val + l2.val + i;
            i = current / 10;
            end.next = new ListNode(current % 10);
            end = end.next;
            l1 = l1.next;
            l2 = l2.next;
        }
        if (i > 0) {
            end.next = new ListNode(i);
        }
        return header;
    }

    public static void main(String[] args) {
        ListNode listNode = solution1(new ListNode(2, new ListNode(4, new ListNode(5))), new ListNode(4, new ListNode(7, new ListNode(8))));
        while (listNode.next != null) {
            System.out.print(listNode.next.val);
            listNode = listNode.next;
        }
    }
}
