package com.github.thread;

import sun.java2d.loops.GraphicsPrimitive;

/**
 * join中断当前线程，另一个线程执行结束后，继续执行当前线程
 * @Author:zhangbo
 * @Date:2018/8/13 14:37
 */
public class TransforThreadJoin {

    public static void main(String[] args) {
        Thread t1=new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"开始执行");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"执行结束");
        });

        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"开始执行");
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName()+"执行结束");
        });

        t1.start();
        t2.start();

    }
}
