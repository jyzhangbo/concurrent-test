package com.github.producerconsumer.waitnotify;

/**
 * nofity提前通知
 * 导致的结果:wait线程会一直等待在wait方法处
 * 解决方案:添加一个状态标识,在调用wait方法前进行判断,如果已经通知过,则不调用wait
 *
 * @Author:zhangbo
 * @Date:2018/9/12 15:28
 */
public class AheadNotify {

    private boolean notifyTemp = false;

    public static void main(String[] args) {
        AheadNotify aheadNotify = new AheadNotify();

        new Thread(() -> {
            aheadNotify.notifyNew();
        }).start();

        new Thread(() -> {
            aheadNotify.waitNew();
        }).start();

    }

    public synchronized void waitNew(){
        System.out.println("进入wait");
        try {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (!notifyTemp){
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("结束wait");
    }

    public synchronized void notifyNew(){
        System.out.println("进入notify");
        notify();
        notifyTemp = true;
        System.out.println("结束notify");
    }

    public synchronized void notifyThread() {
        System.out.println("进入notify");
        notify();
        System.out.println("结束notify");

    }

    public synchronized void waitThread() {
        System.out.println("进入wait");
        try {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("结束wait");

    }

}
