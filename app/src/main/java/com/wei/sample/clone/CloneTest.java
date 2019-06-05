package com.wei.sample.clone;

import java.util.ArrayList;
import java.util.Arrays;

public class CloneTest {
    public static class Person implements Cloneable {
        public String name;
        public int age;
        public String[] names;
        public Baby baby;
        public ArrayList<String> names2 = new ArrayList<>();

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" + "name='" + name + '\'' + ", age=" + age + ", names=" + Arrays.toString(names) + ", baby=" + baby + ", names2=" + names2 + '}';
        }

        @Override
        protected Person clone() throws CloneNotSupportedException {
            Person person = (Person) super.clone();
            person.baby = baby.clone();
            person.names2 = (ArrayList<String>) names2.clone();
            return person;
        }
    }

    public static class Baby implements Cloneable {
        public String name;
        public int age;
        public String[] names;

        public Baby(String name, int age, String[] names) {
            this.name = name;
            this.age = age;
            this.names = names;
        }

        @Override
        public String toString() {
            return "Baby{" + "name='" + name + '\'' + ", age=" + age + ", names=" + Arrays.toString(names) + '}';
        }

        @Override
        protected Baby clone() throws CloneNotSupportedException {
            return (Baby) super.clone();
        }

    }

    public static class Person1 extends Person {
        public String nammmm;
        public Baby baby1;

        public Person1(String name, int age) {
            super(name, age);
        }


        @Override
        public String toString() {
            return "Person1{" +
                    "nammmm='" + nammmm + '\'' +
                    ", baby1=" + baby1 +
                    '}';
        }
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Person1 person = new Person1("person", 10);
        person.names = new String[]{"qq", "www"};
        person.nammmm = "1222";
        person.baby = new Baby("baby", 20, new String[]{"qqqqq", "wwwww"});
        person.baby1 = new Baby("baby", 20, new String[]{"qqqqq", "wwwww"});
        person.names2.add("1111");
        person.names2.add("2222");

        Person1 clone = (Person1) person.clone();
        clone.name = "person1";
        clone.age = 23;
        clone.nammmm = "ffffff";
        clone.names2.add("3333");
        clone.names = new String[]{"111", "222"};

        clone.baby.name = "baby1";
        clone.baby.names = new String[]{"ttttt"};
        clone.baby1.name = "baby1";
        clone.baby1.names = new String[]{"ttttt"};

        System.out.println(person.toString());
        System.out.println(clone.toString());
    }
}
