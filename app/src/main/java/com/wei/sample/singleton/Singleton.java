package com.wei.sample.singleton;

/**
 * 懒汉式，线程不安全
 */
public class Singleton {
    private static Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}

/**
 * 懒汉式，线程安全
 */
class Singleton1 {
    private static Singleton1 instance;

    private Singleton1() {
    }

    public static synchronized Singleton1 getInstance() {
        if (instance == null) {
            instance = new Singleton1();
        }
        return instance;
    }
}

/**
 * 饿汉式
 */
class Singleton2 {
    private static Singleton2 instance = new Singleton2();

    private Singleton2() {
    }

    public static Singleton2 getInstance() {
        return instance;
    }
}

/**
 * 双检锁/双重校验锁（DCL，即 double-checked locking）
 */
class Singleton3 {
    private volatile static Singleton3 singleton;

    private Singleton3() {
    }

    public static Singleton3 getSingleton() {
        if (singleton == null) {
            synchronized (Singleton3.class) {
                if (singleton == null) {
                    singleton = new Singleton3();
                }
            }
        }
        return singleton;
    }
}

/**
 * 登记式/静态内部类
 */
class Singleton4 {
    private static class SingletonHolder {
        private static final Singleton4 INSTANCE = new Singleton4();
    }

    private Singleton4() {
    }

    public static final Singleton4 getInstance() {
        return SingletonHolder.INSTANCE;
    }
}

/**
 * 枚举
 */
enum Singleton5 {
    INSTANCE2;

    public void whateverMethod() {
        System.out.println("....");
    }
}

