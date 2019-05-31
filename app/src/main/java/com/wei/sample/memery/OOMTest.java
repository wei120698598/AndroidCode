package com.wei.sample.memery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description
 * @date 2019-05-17
 * @email weishuxin@icourt.cc
 */
public class OOMTest {
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private static Map<String, OOMTest> map = new HashMap<>();

    private void submitTask() {
        for (int i = 0; i < 10; i++) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "正处理");
                }
            });
        }
    }

    private void threadOOM() {
        while (true) {
            OOMTest ct1 = new OOMTest();
            map.put("1", ct1);
            ct1.submitTask();
            map.remove("1");
        }
    }

    /**
     * 方法区和运行时常量池溢出
     */
    private void metaspaceOOM() {
//        while (true) {
//            Enhancer enhancer = new Enhancer();
//            enhancer.setSuperclass(OOMTest.class);
//            enhancer.setUseCache(false);
//            enhancer.setCallback(new MethodInterceptor() {
//                @Override
//                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
//                    return methodProxy.invoke(o, objects);
//                }
//            });
//            enhancer.create();
//        }
    }


    private int stackLength = 1;

    /**
     * 虚拟机栈和本地方法栈溢出
     * 1.StackOverflowError,线程请求的栈深度大小大于虚拟机所允许的最大深度
     * 2.OutOfMemoryError,扩展栈时无法申请足够的内存空间
     * 使用-xss128K参数较少栈的内存容量
     */
    private void stackLeak() {
        stackLength++;
        stackLeak();
    }

    private void stackOOM() {
        OOMTest stackOOM = new OOMTest();
        try {
            stackOOM.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length: " + stackOOM.stackLength);
            throw e;
        }
    }

    /**
     * 堆溢出
     */

    private void heapOOM() {
        List<OOMTest> list = new ArrayList<>();
        while (true) {
            list.add(new OOMTest());
        }
    }

    private static final int _1MB = 1024 * 1024;

    /**
     * DirectMemory容量可以通过 -XX:MaxDirectMemorySize指定，如果不指定，则默认与Java堆最大值一样。
     * 由DirectMemory导致的内存溢出，一个明显的特征是Heap Dump文件中不会看见明显的异常，如果OOM之后dump文件很小，并且程序中直接或间接使用了NIO，可能是直接内存溢出导致的。
     */
    private void directOOM() {
//        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
//        unsafeField.setAccessible(true);
//        Unsafe unsafe = (Unsafe) unsafeField.get(null);
//        while (true) {
//            unsafe.allocateMemory(_1MB);
//        }
    }

    public static void main(String[] args) throws InterruptedException {

    }

}
