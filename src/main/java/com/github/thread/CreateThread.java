package com.github.thread;

import java.util.concurrent.*;

/**
 * 线程的创建.
 * @Author:zhangbo
 * @Date:2018/8/13 11:33
 */
public class CreateThread {

    public static void main(String[] args) {


        //继承Thread
        Thread thread=new Thread(() -> {
           System.out.println("线程名称："+Thread.currentThread().getName());
        });

        thread.start();

        //实现Runnable
        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程名称："+Thread.currentThread().getName());
            }
        });

        thread1.start();

        //实现Callable
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<String> future = service.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(2000);
                return "callable接口返回";
            }
        });

        try {
            String result = future.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

}
