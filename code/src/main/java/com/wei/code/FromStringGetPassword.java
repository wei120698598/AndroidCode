package com.wei.code;

import java.util.LinkedList;

/**
 * @author
 * @description
 * 问题描述：假设字符串中的所有字符不重复，从字符串中选取任意字符，组合成n位的新字符串，输出所有可能的组合字符串
 * 例如，输入字符串为“abc”，输出2位，则输出ab、ba、ac、ca、bc、cb，共6种组合。
 * 输入：
 * 不包含重复字符的字符串 ：str
 * 新的组合字符串的长度  ：  n
 * @date 2019/4/1
 */
public class FromStringGetPassword {

    public static void main(String[] args) {

        String str = "abcdefg";

        LinkedList<Character> charList = new LinkedList<>();
        for (Character character : str.toCharArray()) {
            charList.add(character);
        }

        StringBuilder sb = new StringBuilder();
        printPSW(sb, charList, 3);
    }


    public static void printPSW(StringBuilder sb, LinkedList<Character> linkedList, int n) {
        if (sb.length() == n) {
            System.out.println(sb);
        } else {
            for (int i = 0; i < linkedList.size(); i++) {
                Character remove = linkedList.remove(i);
                sb.append(remove);
                printPSW(sb, linkedList, n);
                linkedList.add(i, remove);
                sb.deleteCharAt(sb.length() - 1);
            }
        }
    }
}
