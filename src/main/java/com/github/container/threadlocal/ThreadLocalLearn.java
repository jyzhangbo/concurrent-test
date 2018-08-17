package com.github.container.threadlocal;

/**
 * 线程本地变量.
 *
 *
 *
 * @Author:zhangbo
 * @Date:2018/8/17 10:48
 */
public class ThreadLocalLearn {


    public static void main(String[] args) {
        ThreadLocal<String> local=new ThreadLocal(){
            @Override
            protected Object initialValue() {
                return "zhaokun";
            }
        };

        System.out.println(Thread.currentThread().getName()+local.get());
        local.set("zhangbo");
        System.out.println(Thread.currentThread().getName()+local.get());

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+local.get());
            local.set("wenjian");
            System.out.println(Thread.currentThread().getName()+local.get());
        }).start();


        System.out.println(Thread.currentThread().getName()+local.get());

    }

}
