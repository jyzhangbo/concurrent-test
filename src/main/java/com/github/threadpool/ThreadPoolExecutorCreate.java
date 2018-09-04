package com.github.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池的创建:
 * 1.corePoolSize：表示核心线程池的大小。当提交一个任务时，如果当前核心线程池的线程个数没有达到corePoolSize，
 *   则会创建新的线程来执行所提交的任务，即使当前核心线程池有空闲的线程。如果当前核心线程池的线程个数已经达到了corePoolSize，
 *   则不再重新创建线程。如果调用了prestartCoreThread()或者 prestartAllCoreThreads()，
 *   线程池创建的时候所有的核心线程都会被创建并且启动。
 * 2.maximumPoolSize：表示线程池能创建线程的最大个数。如果当阻塞队列已满时，并且当前线程池线程个数没有超过maximumPoolSize的话，
 *   就会创建新的线程来执行任务
 * 3.keepAliveTime：空闲线程存活时间。如果当前线程池的线程个数已经超过了corePoolSize，并且线程空闲时间超过了keepAliveTime的话，
 *   就会将这些空闲线程销毁，这样可以尽可能降低系统资源消耗。
 * 4.unit：时间单位。为keepAliveTime指定时间单位。
 * 5.workQueue：阻塞队列。用于保存任务的阻塞队列
 * 6.threadFactory：创建线程的工程类。可以通过指定线程工厂为每个创建出来的线程设置更有意义的名字，
 *   如果出现并发问题，也方便查找问题原因。
 * 7.饱和策略。当线程池的阻塞队列已满和指定的线程都已经开启，说明当前线程池已经处于饱和状态了，那么就需要采用一种策略来处理这种情况。
 *   采用的策略有这几种：
 *      AbortPolicy： 直接拒绝所提交的任务，并抛出RejectedExecutionException异常；
 *      CallerRunsPolicy：只用调用者所在的线程来执行任务；
 *      DiscardPolicy：不处理直接丢弃掉任务；
 *      DiscardOldestPolicy：丢弃掉阻塞队列中存放时间最久的任务，执行当前任务
 *
 * @Author:zhangbo
 * @Date:2018/8/22 16:08
 */
public class ThreadPoolExecutorCreate {

    public static void main(String[] args) {

        System.out.println(Runtime.getRuntime().availableProcessors());

        ThreadPoolExecutor executor = new ThreadPoolExecutor(2,5,1000,
                TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>(),new MyThreadFactory("zhang"),
                new ThreadPoolExecutor.AbortPolicy());

        for(int i=0;i<10;i++){
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName()+"开始执行");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"执行结束");
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();

    }

}
