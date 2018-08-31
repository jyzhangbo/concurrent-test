package com.github.keyword.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:zhangbo
 * @Date:2018/8/31 10:32
 */
public class ConditionLearn {

    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();
    private static boolean temp = false;

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                while (!temp) {
                    System.out.println(Thread.currentThread().getName() + "不符合条件");
                    condition.await();
                }
                System.out.println(Thread.currentThread().getName()+"符合条件");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }

        });

        t1.start();

        Thread t2 = new Thread(() -> {
            try {
                lock.lock();
                temp = true;
                condition.signal();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });
        t2.start();


    }
}
