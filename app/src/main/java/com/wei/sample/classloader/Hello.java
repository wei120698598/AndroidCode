package com.wei.sample.classloader;

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description 先说一下Java程序初始化的顺序:父类静态变量>父类静态代码块>子类静态变量>子类静态代码块>父类非静态变量>父类非静态代码块>父类构造器>子类非静态变量>子类非静态代码块>子类构造器。
 * <p>
 * Java程序初始化一般遵循3个原则
 * 1.静态对象（变量）先于非静态对象（变量）初始化。其中静态对象（变量）只初始化一次，因为static在jvm中只有一块区域存储，方法区（Method Area），
 *   他之所以被称为静态是因为从程序创建到死亡他的地址值都不会改变，他只在class类对象初次加载时初始化，因此static只需要初始化一次，而非静态对象（变量）可能会初始化很多次。
 * <p>
 * 2.如果类之间存在继承关系，那么父类优先于子类进行初始化。
 * <p>
 * 3.按照成员变量的定义顺序进行初始化。即使变量定义散布于方法之中，他们依然在任何方法（包括构造函数）被调用前先初始化
 * @date 2018/12/18
 * @email weishuxin@icourt.cc
 */
class ClassLoaderA {
    static {
        System.out.print("1");
    }

    {
        System.out.print("3");
    }

    public ClassLoaderA() {
        System.out.print("2");
    }

    public void print() {
        System.out.print("4");
    }
}

class ClassLoaderB extends ClassLoaderA {
    static {
        System.out.print("a");
    }

    {
        System.out.print("c");
    }

    public ClassLoaderB() {
        System.out.print("b");
    }

    @Override
    public void print() {
        System.out.print("d");
    }
}

public class Hello {
    public static void main(String[] args) {
        ClassLoaderA classLoaderB = new ClassLoaderB();
        classLoaderB.print();
        classLoaderB = new ClassLoaderB();
    }
}