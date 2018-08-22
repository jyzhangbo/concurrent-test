package com.github.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @Author:zhangbo
 * @Date:2018/8/22 14:33
 */
public class AtomicIntegerArrayLearn {

    private int[] value = new int[]{1, 2, 3, 4, 5};
    private AtomicIntegerArray integerArray = new AtomicIntegerArray(value);

    private CountDownLatch latch=new CountDownLatch(10);

    public static void main(String[] args) {
        AtomicIntegerArrayLearn learn=new AtomicIntegerArrayLearn();
        /*learn.getAndAdd();
        System.out.println(learn.integerArray);*/

        ExecutorService service = Executors.newFixedThreadPool(10);
        for(int i=0;i<10;i++){
            service.execute(() -> {
                learn.getAndIncrement();
            });
        }

        try {
            learn.latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(learn.integerArray);
        service.shutdown();

    }

    public void getAndIncrement(){
        for(int i=0;i<10000;i++){
            integerArray.getAndIncrement(3);
        }
        latch.countDown();
    }

    public void getAndAdd() {
        int i = integerArray.getAndAdd(3, 1);
        System.out.println(i);
    }

    public void addAndGet() {
        int i = integerArray.addAndGet(3, 1);
        System.out.println(i);
    }

}
