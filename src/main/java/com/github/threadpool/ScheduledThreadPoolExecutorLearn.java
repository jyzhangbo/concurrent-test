package com.github.threadpool;

import java.util.concurrent.*;

/**
 * ScheduledThreadPoolExecutor:延迟或周期执行任务的线程池.
 *
 * @Author:zhangbo
 * @Date:2018/9/3 16:21
 */
public class ScheduledThreadPoolExecutorLearn {
    private ScheduledThreadPoolExecutor executor=new ScheduledThreadPoolExecutor(2,new MyThreadFactory("zhangbo"));

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledThreadPoolExecutorLearn learn=new ScheduledThreadPoolExecutorLearn();
        //learn.scheduled();
        //learn.scheduledFuture();
        learn.scheduleAtFixedRate();
        //learn.scheduleWithFixedRate();
       // learn.shutdown();
    }

    /**
     * 推迟一定时间后，开始周期性执行任务，周期间隔为:上一个任务执行结束后到下一次任务执行.
     */
    public void scheduleWithFixedRate(){
        executor.scheduleWithFixedDelay(() -> {
            System.out.println(Thread.currentThread().getName() + "开始执行,Time:"+System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "执行结束,Time:"+System.currentTimeMillis());
        },2000,5000,TimeUnit.MILLISECONDS);
    }

    /**
     * 推迟一定时间后，开始周期性执行任务,周期间隔为:
     * 以上一个任务开始的时间计时,period时间过去后,检测上一个任务是否执行完毕，如果上一个任务执行完毕,
     * 则当前任务立即执行，如果上一个任务没有执行完毕，则需要等上一个任务执行完毕后立即执行
     */
    public void scheduleAtFixedRate(){
        executor.scheduleAtFixedRate(() -> {
            System.out.println(Thread.currentThread().getName() + "开始执行,Time:"+System.currentTimeMillis());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "执行结束,Time:"+System.currentTimeMillis());
        },2000,2000,TimeUnit.MILLISECONDS);
    }

    /**
     * 推迟一定的时间执行,有返回值.
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void scheduledFuture() throws ExecutionException, InterruptedException {
        ScheduledFuture<String> future = executor.schedule(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println(Thread.currentThread().getName() + "开始执行");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "执行结束");
                return "hello";
            }
        }, 2000, TimeUnit.MILLISECONDS);

        System.out.println("scheduled future:"+future.get());
    }

    /**
     * 推迟一定的时间执行,没有返回值.
     */
    public void scheduled() throws ExecutionException, InterruptedException {
        ScheduledFuture<?> future = executor.schedule(() -> {
            System.out.println(Thread.currentThread().getName() + "开始执行");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "执行结束");

        }, 2000, TimeUnit.MILLISECONDS);

        System.out.println(future.get());

    }

    public void shutdown(){
        executor.shutdown();
    }

}
