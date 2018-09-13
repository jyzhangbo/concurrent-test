package com.github.producerconsumer.blockingqueue;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author:zhangbo
 * @Date:2018/9/12 17:16
 */
public class ProducerConsumer {

    private static LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue(5);

    public static void main(String[] args) {
        ProducerConsumer death = new ProducerConsumer();
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
            Random r = new Random();
            int i = r.nextInt();
            try {

                queue.put(i);
                System.out.println(Thread.currentThread().getName()+"生产："+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void consume(){
        while (true){
            try {
                Integer take = queue.take();
                System.out.println(Thread.currentThread().getName()+"消费："+take);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
