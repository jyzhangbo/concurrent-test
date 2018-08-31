package com.github.container.blockingqueue;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自己实现得ArrayBlockingQueue.
 *
 *                  items
 *            -------------------------
 * take出队<——|20||30||40||  ||  ||  | <——put入队
 *            -------------------------
 *             ^           ^
 *            takeIndex    putIndex
 *
 *
 * @Author:zhangbo
 * @Date:2018/8/31 14:28
 */
public class MyArrayBlockingQueue {

    private String[] items;
    private int takeIndex;
    private int putIndex;
    private int count;
    private ReentrantLock lock;
    private Condition notEmpty;
    private Condition notFull;

    public MyArrayBlockingQueue(int capacity){
        this.items = new String[capacity];
        lock = new ReentrantLock();
        notFull = lock.newCondition();
        notEmpty = lock.newCondition();
    }

    /**
     * 获取队头数据.
     * @throws InterruptedException
     */
    public String take() throws InterruptedException {
        lock.lockInterruptibly();
        try{
            while (count ==0){
                //如果队列为空，则等待
                notEmpty.await();
            }

            //队列不为空，则获取队头
            String result = items[takeIndex];

            items[takeIndex] = null;
            if(++takeIndex == items.length){
                takeIndex = 0;
            }
            count--;
            notFull.signal();

            return result;
        }finally {
            lock.unlock();
        }
    }


    /**
     * 队尾插入数据.
     * @param msg
     * @throws InterruptedException
     */
    public void put(String msg) throws InterruptedException {
        lock.lockInterruptibly();
        try{
            while (count == items.length){
                //如果队列满了,则等待
                notFull.await();
            }

            //队列未满，插入数据
            items[putIndex] = msg;

            if(++putIndex == items.length){
                //当putIndex到达数据末得时候，需要从开始继续.
                putIndex = 0;
            }
            count++;
            notEmpty.signal();

        }finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "MyArrayBlockingQueue{" +
                "items=" + Arrays.toString(items) +
                '}';
    }
}
