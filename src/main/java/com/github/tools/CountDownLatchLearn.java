package com.github.tools;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch 倒计时器.
 * 强调一个线程等多个线程完成某件事情
 *
 *
 * @Author:zhangbo
 * @Date:2018/8/21 17:50
 */
public class CountDownLatchLearn {

    private static CountDownLatch startLatch=new CountDownLatch(1);
    private static CountDownLatch endLatch=new CountDownLatch(5);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(5);
        for(int i=0;i<5;i++){
            service.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName()+"准备中.....");
                    startLatch.await();
                    System.out.println(Thread.currentThread().getName()+"全力冲刺！");
                    endLatch.countDown();
                    System.out.println(Thread.currentThread().getName()+"到达终点");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(5000);

        System.out.println("裁判发号施令....");
        startLatch.countDown();

        endLatch.await();

        System.out.println("裁判统计成绩");

        service.shutdown();

    }

}
