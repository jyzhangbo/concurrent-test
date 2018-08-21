package com.github.lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLock
 *
 * @Author:zhangbo
 * @Date:2018/8/21 16:32
 */
public class ReentrantReadWriteLockLearn {

    private int num;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) throws InterruptedException {
        ReentrantReadWriteLockLearn lockLearn=new ReentrantReadWriteLockLearn();

        Thread t0 = new Thread(() -> {
            lockLearn.read();
        });
        Thread t1 = new Thread(() -> {
            lockLearn.read();
        });
        Thread t2 = new Thread(() -> {
            lockLearn.write();
        });
        t2.start();

        Thread.sleep(1000);

        t0.start();
        t1.start();

    }

    public void write(){
        try {
            lock.writeLock().lock();
            System.out.println(Thread.currentThread().getName()+"获取锁");
            Thread.sleep(2000);
            num++;
            System.out.println(Thread.currentThread().getName()+"修改num为"+num);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.writeLock().unlock();
            System.out.println(Thread.currentThread().getName()+"释放锁");
        }
    }

    public void read(){
        try {
            lock.readLock().lock();

            System.out.println(Thread.currentThread().getName()+"获取到锁");

            for(int i=0;i<1000;i++){
                System.out.println(Thread.currentThread().getName()+"在读num:"+num);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.readLock().unlock();
            System.out.println(Thread.currentThread().getName()+"释放锁");
        }


    }


}
