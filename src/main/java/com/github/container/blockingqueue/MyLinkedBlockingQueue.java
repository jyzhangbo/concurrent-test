package com.github.container.blockingqueue;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *     head
 *       |
 *  -----------     -----------
 *  |null|    | ——> | msg|null|
 *  -----------     -----------
 *                       |
 *                      last
 *
 * @Author:zhangbo
 * @Date:2018/8/31 16:30
 */
public class MyLinkedBlockingQueue {

    private AtomicInteger count = new AtomicInteger();
    private ReentrantLock putLock = new ReentrantLock();
    private Condition notFull = putLock.newCondition();
    private ReentrantLock takeLock = new ReentrantLock();
    private Condition notEmpty = takeLock.newCondition();
    private int capacity;
    private Node head;
    private Node last;

    public MyLinkedBlockingQueue(int capacity){
        this.capacity = capacity;
        head = last = new Node(null);
    }

    public String take() throws InterruptedException {
        takeLock.lockInterruptibly();
        String result;
        int c = -1;
        try {
            //队列为空，则等待
            while (count.get() == 0){
                notEmpty.await();
            }
            //队列不为空，取数
            Node h = head;
            Node first = h.next;
            //将h.next赋值h,这样h就没有引用,gc的时候就可以回收
            h.next = h;

            head = first;
            result = first.item;
            //将head的item赋值为空
            first.item = null;

            c = count.getAndDecrement();
            if(c > 1){
                notEmpty.signal();
            }

        }finally {
            takeLock.unlock();
        }
        if(c == capacity){
            putLock.lock();
            try {
                notFull.signal();
            }finally {
                putLock.unlock();
            }
        }
        return result;
    }

    public void put(String msg) throws InterruptedException {
        putLock.lockInterruptibly();
        Node node = new Node(msg);
        int c=-1;
        try{
            while (count.get() == capacity){
                //如果队列已满，则等待
                notFull.await();
            }

            //队列未满，插入数据
            //赋值是从右到左执行
            last = last.next = node;
            c = count.getAndIncrement();

            //如果链表未满,通知其他线程插入数据
            if(c + 1 < capacity){
                notFull.signal();
            }
        }finally {
            putLock.unlock();
        }

        //c为0,表明在put之前,链表为空,此时的take操作是阻塞状态,因此需要signal
        if(c ==0){
            takeLock.lock();
            try {
                notEmpty.signal();
            }finally {
                takeLock.unlock();
            }
        }
    }

    class Node{
        String item;
        Node next;
        Node(String msg){
            item = msg;
        }

        @Override
        public String toString() {
            StringBuilder builder=new StringBuilder();
            builder.append(item);
            Node temp = next;
            while (temp!=null){
                builder.append(temp.item);
                builder.append("  |  ");
                temp = temp.next;
            }
            return builder.toString();
        }
    }

    @Override
    public String toString() {
        return "MyLinkedBlockingQueue{Node:"+head+"}";
    }
}
