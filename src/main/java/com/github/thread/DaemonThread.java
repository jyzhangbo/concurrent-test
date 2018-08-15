package com.github.thread;

/**
 * 守护线程.
 *  用户线程完全结束后,守护线程自然而然就会退。
 * @Author:zhangbo
 * @Date:2018/8/15 14:47
 */
public class DaemonThread {

    public static void main(String[] args) {
        Thread t1=new Thread(() -> {
            while (true) {
                try {
                    System.out.println("i am alive");
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("finally block");
                }
            }
        });

        t1.setDaemon(true);
        t1.start();

        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
