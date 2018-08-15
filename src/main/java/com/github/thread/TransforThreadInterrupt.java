package com.github.thread;

/**
 * 线程的中断标识位，并不是终端线程；只有当一个线程在执行wait,sleep,join方法等待时，
 * 调用改线程的interrupt方法，会抛出异常，终止线程的等待.
 * @Author:zhangbo
 * @Date:2018/8/13 13:56
 */
public class TransforThreadInterrupt {

    public static void main(String[] args) {
        Thread t1=new Thread(() -> {
            for(int i=0;i<1000000;i++) {
                System.out.println(i);
            }
        });

        Thread t2=new Thread(() -> {
            while (true){
            }
        });



        t1.start();
        t2.start();

        t1.interrupt();
        t2.interrupt();

        while (t1.isInterrupted()){
            System.out.println("t1 interrupt");
        }

        System.out.println("t1:"+t1.isInterrupted());
        System.out.println("t2:"+t2.isInterrupted());


    }


}
