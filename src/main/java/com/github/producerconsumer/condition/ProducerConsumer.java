package com.github.producerconsumer.condition;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:zhangbo
 * @Date:2018/9/12 17:04
 */
public class ProducerConsumer {

    private static ReentrantLock lock = new ReentrantLock();
    private static Condition fullCondition = lock.newCondition();
    private static Condition emptyCondition = lock.newCondition();

    private static LinkedList<Integer> linkedList = new LinkedList<>();
    private static Integer maxSize = 5;

    public static void main(String[] args) {
        ProducerConsumer death =new ProducerConsumer();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 20, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5));
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                death.produce();
            });
        }
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                death.consume();
            });
        }
    }

    public void produce(){
        while (true){
            try {
                lock.lock();
                while (linkedList.size() == maxSize){
                    System.out.println(Thread.currentThread().getName()+"生产等待");
                    fullCondition.await();
                    System.out.println(Thread.currentThread().getName()+"生产等待结束");
                }
                Random r = new Random();
                int i = r.nextInt();
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName()+"生产：" + i);
                linkedList.add(i);
                emptyCondition.signalAll();

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }

    public void consume(){
        while (true){
            try {
                lock.lock();
                while (linkedList.size() == 0){
                    System.out.println(Thread.currentThread().getName()+"消费等待");
                    emptyCondition.await();
                    System.out.println(Thread.currentThread().getName()+"消费等待结束");
                }

                Thread.sleep(2000);
                int remove = linkedList.remove(0);
                System.out.println(Thread.currentThread().getName()+"消费：" + remove);
                fullCondition.signalAll();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }
}
