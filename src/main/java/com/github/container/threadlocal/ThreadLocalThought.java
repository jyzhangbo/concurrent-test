package com.github.container.threadlocal;

/**
 * 核心思想:每一个线程都有一个map来存放值，key是CommonThreadLocal，value是值
 *
 * @Author:zhangbo
 * @Date:2018/8/17 15:39
 */
public class ThreadLocalThought {


    public static void main(String[] args) {
        Number number=new Number();

        CommonThread thread1=new CommonThread(){

            @Override
            public void run(){
                number.add();
            }
        };
        CommonThread thread2=new CommonThread(){

            @Override
            public void run(){
                number.minus();
            }
        };

        thread1.start();
        thread2.start();

    }


}
