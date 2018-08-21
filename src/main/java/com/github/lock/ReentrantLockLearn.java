package com.github.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock使用方法：
 * 1.需要将ReentrantLock使用方法定义为全局变量，这样每个线程使用的才是同一把锁
 *
 * @Author:zhangbo
 * @Date:2018/8/21 15:15
 */
public class ReentrantLockLearn {

    private int num = 0;
    private Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        ReentrantLockLearn lockLearn = new ReentrantLockLearn();

        /*for(int i=0;i<10;i++){
            new Thread(() -> {
                //lockLearn.runLock();
                lockLearn.runTryLock();
            }).start();
        }*/

        Thread t1 = new Thread(() -> {
            lockLearn.runLockInterruptibly();
        });
        Thread t2 = new Thread(() -> {
            lockLearn.runLockInterruptibly();
        });

        System.out.println("启动"+t1.getName());
        t1.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("启动"+t2.getName());
        t2.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t2.interrupt();
    }

    /**
     * lockInterruptibly可以中断线程的等待。
     * 假如有两个线程都调用lockInterruptibly获取锁，线程A获取到了锁，线程B处于等待状态，这时候对线程B执行interrupt,
     * 则可以中断线程B的等待.
     */
    public void runLockInterruptibly() {

        try {
            System.out.println(Thread.currentThread().getName()+"开始获取锁");
            lock.lockInterruptibly();
            System.out.println(Thread.currentThread().getName() + "获取到锁，休息10s");
            Thread.sleep(10000);
            num++;
            System.out.println("开始干活num:" + num);
        } catch (Exception e) {
            System.out.println(Thread.currentThread().getName() + "被中断");
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + "释放锁");
        }

    }

    /**
     * tryLock使用.
     */
    public void runTryLock() {
        if (lock.tryLock()) {
            try {
                //获取到锁
                System.out.println(Thread.currentThread().getName() + "获取到锁");
                System.out.println("num:" + num);
                num++;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("num:" + num);
                System.out.println(Thread.currentThread().getName() + "释放锁");
                lock.unlock();
            }

        } else {
            //没有获取到锁
            System.out.println(Thread.currentThread().getName() + "没有获取到锁");
        }

    }

    /**
     * lock使用.
     */
    public void runLock() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + ":" + num);
            num++;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + ":" + num);
            lock.unlock();
        }

    }

}
