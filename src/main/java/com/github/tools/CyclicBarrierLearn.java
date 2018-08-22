package com.github.tools;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CyclicBarrier 循环栅栏.
 * 多个线程互等，等大家都完成，再携手共进.
 *
 * @Author:zhangbo
 * @Date:2018/8/21 18:10
 */
public class CyclicBarrierLearn {

    private static CyclicBarrier barrier=new CyclicBarrier(5, () -> {
        System.out.println("所有运动员都到出发点了.");
    });

    public static void main(String[] args) {

        ExecutorService service = Executors.newFixedThreadPool(5);
        for(int i=0;i<5;i++){
            service.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName()+"准备中....");
                    barrier.await();

                    System.out.println(Thread.currentThread().getName()+"出发....");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }

        service.shutdown();

    }

}
