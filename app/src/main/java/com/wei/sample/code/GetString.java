package com.wei.sample.code;

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description 根据字符串生成不重复的密码
 * @date 2019/3/29
 * @email weishuxin@icourt.cc
 */
public class GetString {

    private static String content = "abcd";

    private static int index = 3;
    private static int count = 0;


    private void generatePWD() {
        int count = 0;
        for (int i = 0; i < content.length(); i++) {
            char a = content.charAt(i);
            for (int k = i + 1; k < content.length(); k++) {
                char b = content.charAt(k);
                for (int j = k+1 ; j < content.length(); j++) {
                    char c = content.charAt(j);
                    count++;
                    System.out.println("" + a + b + c);
                }
            }
        }
        System.out.println("" + count);
    }

    private char[] data = new char[index];

    private void generateText(int deep) {
        for (int i = 0; i < content.length(); i++) {
            data[deep] = content.charAt(i);
            if (deep + 1 < index) {
                generateText(deep + 1);
            } else {
                count++;
                System.out.println(String.valueOf(data));
            }
        }
    }

    private void generateText2(int deep, int num) {
        for (int i = num; i < content.length(); i++) {
            data[deep] = content.charAt(i);
            if (deep + 1 < index && deep + 1 >= num) {
                generateText2(deep + 1, num);
            } else {
                count++;
                System.out.println(String.valueOf(data));
            }
        }
    }

    public static void main(String[] args) {
//        new GetString().generatePWD();
        count = 0;
        new GetString().generateText(0);
//        System.out.println("" + count);
    }
}
