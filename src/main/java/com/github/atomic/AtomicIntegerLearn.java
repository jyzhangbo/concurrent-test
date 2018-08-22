package com.github.atomic;

import sun.java2d.SurfaceDataProxy;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicInteger
 *
 * @Author:zhangbo
 * @Date:2018/8/22 11:34
 */
public class AtomicIntegerLearn {

    private AtomicInteger num=new AtomicInteger(0);

    private CountDownLatch latch=new CountDownLatch(10);

    public static void main(String[] args) {
        AtomicIntegerLearn learn=new AtomicIntegerLearn();

        /*ExecutorService service = Executors.newFixedThreadPool(10);
        for(int i=0;i<10;i++){
            service.execute(() -> {
//                learn.addAndGet();
                learn.incrementAndGet();
            });
        }

        try {
            learn.latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(learn.num);

        service.shutdown();*/

       // learn.getAndSet();
        learn.getAndIncrement();
        System.out.println(learn.num);

    }

    /**
     * 以原子的方式将实例中的原值加1，返回的是自增前的旧值；
     */
    public void getAndIncrement(){
        int newNum = num.getAndIncrement();
        System.out.println(newNum);
    }

    /**
     * 将实例中的值更新为新值，并返回旧值；
     */
    public void getAndSet(){
        int newNum = num.getAndSet(10);
        System.out.println(newNum);
    }

    /**
     * 以原子的方式将实例中的原值进行加1操作，并返回最终相加后的结果；
     */
    public void incrementAndGet(){
        for(int i=0;i<10000;i++){
            num.incrementAndGet();
        }
        latch.countDown();
    }

    /**
     * 以原子方式将输入的数值与实例中原本的值相加，并返回最后的结果
     */
    public void addAndGet(){
        for(int i=0;i<10000;i++) {
            num.addAndGet(2);
        }

        latch.countDown();
    }
}
