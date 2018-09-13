package com.github.producerconsumer.waitnotify;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 假死:如果是多消费者和多生产者情况，如果使用notify方法可能会出现“假死”的情况，即唤醒的是同类线程
 * 解决方法:使用notifyAll()
 *
 * @Author:zhangbo
 * @Date:2018/9/12 16:27
 */
public class FalseDeath {

    private static List<String> linkList = new LinkedList<>();
    private static final Integer maxSize = 5;

    public static void main(String[] args) {
        FalseDeath death = new FalseDeath();
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

    public void produce() {
        while (true) {
            synchronized (linkList) {
                try {
                    while (linkList.size() == maxSize) {
                        System.out.println(Thread.currentThread().getName()+"生产等待");
                        linkList.wait();
                        System.out.println(Thread.currentThread().getName()+"生产等待结束");
                    }
                    Random r = new Random();
                    String valueOf = String.valueOf(r.nextInt());
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName()+"生产：" + valueOf);
                    linkList.add(valueOf);
                    linkList.notifyAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void consume() {
        while (true) {
            synchronized (linkList) {
                try {
                    while (linkList.size() == 0) {
                        System.out.println(Thread.currentThread().getName()+"消费等待");
                        linkList.wait();
                        System.out.println(Thread.currentThread().getName()+"消费等待结束");
                    }
                    Thread.sleep(2000);
                    String remove = linkList.remove(0);
                    System.out.println(Thread.currentThread().getName()+"消费：" + remove);
                    linkList.notifyAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
