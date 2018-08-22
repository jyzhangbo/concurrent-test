package com.github.tools;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Exchanger 线程间交换数据的工具.
 *
 * @Author:zhangbo
 * @Date:2018/8/22 10:49
 */
public class ExchangerLearn {

    private static Exchanger exchanger=new Exchanger();

    public static void main(String[] args) {

        ExecutorService service = Executors.newFixedThreadPool(2);

        Thread t1=new Thread();
        t1.setName("AAAAA");
        service.execute(() -> {
            Thread.currentThread().setName("AAAAA");

            System.out.println(Thread.currentThread().getName()+"开始执行");
            try {

               String result = (String) exchanger.exchange("AAAAA");

                System.out.println(Thread.currentThread().getName()+"结束,result:"+result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        service.execute(() -> {
            Thread.currentThread().setName("BBBBB");

            System.out.println(Thread.currentThread().getName()+"开始执行");
            try {

                String result = (String) exchanger.exchange("BBBBB");

                System.out.println(Thread.currentThread().getName()+"结束,result:"+result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

}
