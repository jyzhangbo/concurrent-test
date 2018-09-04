package com.github.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author:zhangbo
 * @Date:2018/9/3 10:55
 */
public class MyThreadPoolExecutor {

    private static final int COUNT_BITS = Integer.SIZE-3;  //29
    private static final int CAPACITY = (1 << COUNT_BITS) -1;  //29个1

    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));

    private static final int RUNNING    = -1 << COUNT_BITS;  //111
    private static final int SHUTDOWN   =  0 << COUNT_BITS;  //000
    private static final int STOP       =  1 << COUNT_BITS;  //001
    private static final int TIDYING    =  2 << COUNT_BITS;  //010
    private static final int TERMINATED =  3 << COUNT_BITS;  //011

    private static int ctlOf(int rs, int wc) { return rs | wc; }
    private static int runStateOf(int c)     { return c & ~CAPACITY; }

    /**
     * 获取当前活动线程数.
     * @param c
     * @return
     */
    private static int workerCountOf(int c)  { return c & CAPACITY; }

    private final BlockingQueue<Runnable> workQueue;
    private volatile ThreadFactory threadFactory;
    private volatile RejectedExecutionHandler handler;
    private volatile long keepAliveTime;
    private volatile int corePoolSize;
    private volatile int maximumPoolSize;

    private static boolean isRunning(int c) {
        return c < SHUTDOWN;
    }


    public MyThreadPoolExecutor(int corePoolSize,
                                int maximumPoolSize,
                                long keepAliveTime,
                                TimeUnit unit,
                                BlockingQueue<Runnable> workQueue,
                                ThreadFactory threadFactory,
                                RejectedExecutionHandler handler){

        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.workQueue = workQueue;
        this.keepAliveTime = unit.toNanos(keepAliveTime);
        this.threadFactory = threadFactory;
        this.handler = handler;
    }

    public void execute(Runnable command){
        //获取ctl的值
        int c = ctl.get();

        //如果当前活动线程数小于corePoolSize则新建一个线程放入线程池,并将任务添加到该线程中
        if(workerCountOf(c) < corePoolSize){
            if(addWorker(command,true)){
                return;
            }
            //如果添加失败,重新获取ctl
            c = ctl.get();
        }

        if(isRunning(c) && workQueue.offer(command)){
            // 重新获取ctl值
            int recheck = ctl.get();

            if(!isRunning(recheck) && remove(command)){
                reject(command);
            }else if(workerCountOf(recheck) ==0){
                addWorker(null,false);
            }
        }else if(!addWorker(command,false)){
            reject(command);
        }
    }

    private void reject(Runnable command) {
    }

    private boolean remove(Runnable command) {
        return true;
    }

    private boolean addWorker(Runnable command, boolean b) {

        return true;
    }

}
