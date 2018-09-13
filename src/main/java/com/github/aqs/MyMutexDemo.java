package com.github.aqs;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author:zhangbo
 * @Date:2018/9/13 11:17
 */
public class MyMutexDemo {

    private static MyMutex lock = new MyMutex();

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>(5));

        for(int i=0;i<10;i++){
            executor.execute(() -> {
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName()+"获取锁");
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                    System.out.println(Thread.currentThread().getName()+"释放锁");
                }
            });
        }

        executor.shutdown();
    }

}
