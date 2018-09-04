package com.github.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * FutureTask:表示可获取结果的异步任务.
 * get()方法:阻塞等待线程执行结束或抛出异常,才会获取结果
 *
 * @Author:zhangbo
 * @Date:2018/9/4 10:25
 */
public class FutureTaskLearn {

    public static void main(String[] args) {

        FutureTask task=new FutureTask(() -> {
            System.out.println(Thread.currentThread().getName()+"开始执行");
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName()+"执行结束");
            return "zhangbo";
        });

        task.run();
        try {
            System.out.println(task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }



}
