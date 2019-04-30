package com.wei.sample.classloader;

import java.io.IOException;
import java.io.InputStream;

import dalvik.system.PathClassLoader;

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description
 * @date 2019/3/26
 * @email weishuxin@icourt.cc
 */
public class ClassLoaderTest {

    public static void main(String[] args) {
        ClassLoader newClassLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream resourceAsStream = getClass().getResourceAsStream(fileName);
                    if (resourceAsStream == null) {
                        return super.loadClass(name);
                    }
                    byte[] bytes = new byte[resourceAsStream.available()];
                    int read = resourceAsStream.read(bytes);
                    resourceAsStream.close();
                    return defineClass(name, bytes, 0, read);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return super.loadClass(name);
            }
        };

        ClassLoader appClassLoader = ClassLoader.getSystemClassLoader();

        ClassLoader parentClassLoader = ClassLoaderTest.class.getClassLoader().getParent();


        try {
            String className = "com.wei.sample.classloader.ClassLoaderTest";
            Class<?> aClass = newClassLoader.loadClass(className);
            System.out.println(aClass);
            System.out.println(aClass.newInstance() instanceof com.wei.sample.classloader.ClassLoaderTest);
            System.out.println(ClassLoaderTest.class.getClassLoader().loadClass(className).newInstance() instanceof com.wei.sample.classloader.ClassLoaderTest);
            System.out.println(appClassLoader.loadClass(className).newInstance() instanceof com.wei.sample.classloader.ClassLoaderTest);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
