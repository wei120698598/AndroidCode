package com.wei.sample.lock;

import java.util.concurrent.locks.Lock;

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description
 * @date 2019-05-05
 * @email weishuxin@icourt.cc
 */
public class SynchronizeTest {

    private int i;

    public static void main(String[] args) {
        final SynchronizeTest synchronizeTest = new SynchronizeTest();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                synchronizeTest.methodLock();
            }
        };
        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable).start();
    }


    public void methodLock() {

        for (int j = 0; j < 100; j++) {
            i++;
            System.out.println("thread:" + Thread.currentThread().getName() + " = " + i);
        }
    }
}
