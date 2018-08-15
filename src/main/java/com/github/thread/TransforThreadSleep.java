package com.github.thread;

import java.sql.Time;

/**
 *
 * sleep和wait的区别：
 * 1.sleep()方法是Thread的静态方法，而wait是Object实例方法
 * 2.wait()方法必须要在同步方法或者同步块中调用，也就是必须已经获得对象锁。
 *  而sleep()方法没有这个限制可以在任何地方种使用。另外，wait()方法会释放占有的对象锁，
 *  使得该线程进入等待池中，等待下一次获取资源。而sleep()方法只是会让出CPU并不会释放掉对象锁
 * 3.sleep()方法在休眠时间达到后如果再次获得CPU时间片就会继续执行，而wait()方法必须等待Object.notift/Object.notifyAll通知后，
 *  才会离开等待池，并且再次获得CPU时间片才会继续执行
 *
 * @Author:zhangbo
 * @Date:2018/8/13 14:46
 */
public class TransforThreadSleep {

    public static void main(String[] args) {

        System.out.println("方法开始时间:"+System.currentTimeMillis());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("方法结束时间:"+System.currentTimeMillis());

        TransforThreadSleepTest test=new TransforThreadSleepTest();

        Thread t2=new Thread(() -> {
            synchronized (test){
                System.out.println("进入同步代码块");
                try {
                    test.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                test.get();

                System.out.println("同步代码块执行完毕");
            }
        });


        Thread t1=new Thread(() -> {
            synchronized (test) {
                try {
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                test.notifyAll();
            }
        });

        t2.start();
        t1.start();


    }

    static class TransforThreadSleepTest{

        public void get(){
            System.out.println("执行get方法");
        }

    }

}
