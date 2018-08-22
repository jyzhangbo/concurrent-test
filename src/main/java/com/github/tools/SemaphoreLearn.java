package com.github.tools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore 控制资源并发访问
 *
 * 控制资源能够被并发访问的线程数量，以保证多个线程能够合理的使用特定资源
 *
 * @Author:zhangbo
 * @Date:2018/8/22 10:33
 */
public class SemaphoreLearn {

    private Semaphore semaphore=new Semaphore(5);

    public static void main(String[] args) {
        SemaphoreLearn learn=new SemaphoreLearn();

        ExecutorService service = Executors.newFixedThreadPool(10);

        for(int i=0;i<10;i++){
            service.execute(() -> {
                learn.run();
            });
        }

        service.shutdown();

    }

    public void run(){
        String name = Thread.currentThread().getName();
        try {
            System.out.println(name+"准备中....");

            semaphore.acquire();
            System.out.println(name+"执行中");
            Thread.sleep(10000);

            System.out.println(name+"执行完");
            semaphore.release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
