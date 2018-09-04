package com.github.container.copyonwrite;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * COW:牺牲数据实时性而保证数据最终一致性.
 * 缺点:
 *  1.内存占用问题:在进行写操作的时候，会复制一份列表的内容,如果内容过大，会造成频繁的GC
 *  2.数据一致性问题:容器只能保证数据的最终一致性，不能保证数据的实时一致性
 *
 * @Author:zhangbo
 * @Date:2018/9/4 10:38
 */
public class CopyOnWriteArrayListLearn {

    private static CopyOnWriteArrayList list=new CopyOnWriteArrayList();

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"开始");
            for (int i=0;i<10;i++) {
                list.add("zhangbo");
            }
        });

        t1.start();

        Thread t2=new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"开始");
            for(int i=0;i<1000;i++) {
                System.out.println(list.size());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t2.start();

    }

}
