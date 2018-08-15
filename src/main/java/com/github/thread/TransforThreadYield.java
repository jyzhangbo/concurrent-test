package com.github.thread;

/**
 * yield当前线程让出cpu.
 * @Author:zhangbo
 * @Date:2018/8/15 14:23
 */
public class TransforThreadYield {

    public static void main(String[] args) {
        Thread t1=new Thread(() -> {

            for (int i=0;i<1000;i++) {
                System.out.println(Thread.currentThread().getName());
                Thread.yield();
            }

        });

        t1.setPriority(10);

        Thread t2=new Thread(() -> {

            for (int i=0;i<1000;i++) {
                System.out.println(Thread.currentThread().getName());
                Thread.yield();
            }

        });

        t2.setPriority(9);

        t1.start();
        t2.start();

    }

}
