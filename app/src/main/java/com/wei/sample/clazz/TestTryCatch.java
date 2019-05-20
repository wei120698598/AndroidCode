package com.wei.sample.clazz;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description
 * @date 2019-05-16
 * @email weishuxin@icourt.cc
 */
public class TestTryCatch {
    private String feild = "this is class variables";
    public static String staticFeild = "this is class static variables";
    public static final String staticFinalFeild = "this is class static final variables";
    public volatile String volatileFeild = "this is class volatile variables";

    static {
        System.out.println("from the class static block");
    }

    {
        System.out.println("from the class  block");
    }

    public TestTryCatch() {
    }

    public TestTryCatch(String feild) {
        this.feild = feild;
    }

    public static String staticMethod() {
        int a = 1;
        int b = 10;
        a = b;
        return "from static method";
    }

    public synchronized void syncMethod() {
        System.out.println("from sync method ");
    }

    public void syncBlock() {
        synchronized (this) {
            System.out.println("from sync block");
        }
    }

    public String main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new Runnable() {
            @Override
            public void run() {

            }
        });

        try {
            staticMethod();
            syncBlock();
            return "from try";
        } catch (Exception e) {
            e.printStackTrace();
            return "from catch";
        } finally {
            return "from finally";
        }
    }
}
