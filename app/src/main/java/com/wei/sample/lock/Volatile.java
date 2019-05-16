package com.wei.sample.lock;

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description
 * @date 2019-05-06
 * @email weishuxin@icourt.cc
 */
public class Volatile {
    public volatile int inc = 0;

    public void increase() {
        try {
            String name = "asdf";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("哈哈");
        }
    }

    public static void main(String[] args) {
        final Volatile test = new Volatile();
        for (int i = 0; i < 10; i++) {
            new Thread() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        test.inc = j;
                    }
                }
            }.start();
        }
        while (Thread.activeCount() != 1) {
            Thread.yield();
        }
        System.out.println("" + test.inc);
    }


    public static void testTry() {
        try {
            String name = "asdf";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("哈哈");
        }
    }
}
