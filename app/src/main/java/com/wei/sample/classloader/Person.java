package com.wei.sample.classloader;

import androidx.annotation.NonNull;

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description
 * @date 2019-04-27
 * @email weishuxin@icourt.cc
 */
public class Person {
    public String name;
    private String age;
    private String gender;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
