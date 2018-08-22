package com.github.atomic;

import com.oracle.deploy.update.Updater;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * AtomicIntegeFieldUpdater:原子更新整型字段类
 *
 * 1.原子更新字段类都是抽象类，只能通过静态方法newUpdater来创建一个更新器，并且需要设置想要更新的类和属性
 * 2.更新类的属性必须使用public volatile进行修饰
 * 3.更新类得属性必须是int,不能是Integer
 *
 * @Author:zhangbo
 * @Date:2018/8/22 15:12
 */
public class AtomicIntegerFieldUpdaterLearn {

    private AtomicIntegerFieldUpdater updater = AtomicIntegerFieldUpdater.newUpdater(Age.class,"age");
    private Age age=new Age("zhangbo",0);

    private CountDownLatch latch=new CountDownLatch(10);

    public static void main(String[] args) {
        AtomicIntegerFieldUpdaterLearn learn=new AtomicIntegerFieldUpdaterLearn();
        ExecutorService service = Executors.newFixedThreadPool(10);
        for(int i=0;i<10;i++){
            service.execute(() -> {
                learn.run();
            });
        }

        try {
            learn.latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(learn.age);

        service.shutdown();

    }


    public void run(){
        for(int i=0;i<1000;i++) {
            updater.getAndIncrement(age);
        }

        latch.countDown();
    }


}
