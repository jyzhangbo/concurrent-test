package com.github.container.blockingqueue;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * ArrayBlockingQueue:数组实现的有界阻塞队列.
 * LinkedBlockingQueue:链表实现得有界阻塞队列.
 *
 * @Author:zhangbo
 * @Date:2018/8/23 10:58
 */
public class BlockingQueueLearn {

    private MyLinkedBlockingQueue queue=new MyLinkedBlockingQueue(5);

    public static void main(String[] args) {
        BlockingQueueLearn learn=new BlockingQueueLearn();

        new Thread(() -> {
            for(int i=0;i<10;i++) {
                learn.put();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            for(int i=0;i<10;i++) {
                learn.take();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void take(){
        try {
            queue.take();
            System.out.println("消费数据"+queue);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void put(){
        try {
            queue.put("鸡蛋");
            System.out.println("生产数据"+queue);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
