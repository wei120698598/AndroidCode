package com.wei.code;

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description
 * @date 2019-05-23
 * @email weishuxin@icourt.cc
 */
public class StringRemoveRepeat {

    public static void main(String[] args) {
//        String text = "ababsjkkj";
//        int a =0;
//
//        char[] chars = text.toCharArray();
//        for (char aChar : chars) {
//            a^=aChar;
//        }
        String text = "abcdee";
        int a =0;
        int b =0;

        char[] chars = text.toCharArray();
        for (char aChar : chars) {
            a^=aChar;
        }
        for (char aChar : chars) {
            b^=aChar;
        }

        System.out.println((char)(a^b));
        System.out.println(5^-1);

        int[] arr = new int[]{1, 2, 4, 1, 2, 3};
        int length = arr.length;

        int i;
        int allXOR = 0;
        //全部异或
        for (i = 0; i < length; i++) {
            allXOR ^= arr[i];
        }

        //找出第几位为1，如010
        int res = allXOR & (-allXOR);

        int num1 = 0, num2 = 0;

        for (i = 0; i < length; i++) {
            if ((arr[i] & res) != 0) {
                num1 ^= arr[i];
            } else {
                num2 ^= arr[i];
            }
        }
        System.out.println(num1 + " : " + num2);
    }
}
