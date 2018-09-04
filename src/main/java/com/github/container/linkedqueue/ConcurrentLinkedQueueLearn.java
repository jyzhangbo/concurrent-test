package com.github.container.linkedqueue;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * ConcurrentLinkedQueue:线程安全的链表队列.
 *
 * @Author:zhangbo
 * @Date:2018/9/4 11:00
 */
public class ConcurrentLinkedQueueLearn {


    public static void main(String[] args) {
        ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();
        queue.offer(1);
        queue.offer(2);

        queue.poll();
        queue.poll();

    }


}
