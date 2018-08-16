package com.github.keyword.volat;

/**
 * volatile保证可见性
 * 保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的。
 *
 * @Author:zhangbo
 * @Date:2018/8/15 19:29
 */
public class VolatileVisibility {

    private volatile boolean stop = false;

    public static void main(String[] args) {

        VolatileVisibility learn=new VolatileVisibility();

        new Thread(() -> {
            learn.thread1();
        }).start();

        new Thread(() -> {
            learn.thread2();
        }).start();

    }

    public void thread1(){
        while (!stop){
            System.out.println("hello");
        }
    }

    public void thread2(){
        stop=true;
    }


}
