package com.github.atomic;

/**
 * @Author:zhangbo
 * @Date:2018/8/22 15:13
 */
public class Age {

    public String name;

    public volatile int age;

    public Age(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Age{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
