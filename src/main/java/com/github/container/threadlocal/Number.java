package com.github.container.threadlocal;

/**
 * @Author:zhangbo
 * @Date:2018/8/17 16:57
 */
public class Number {

    private CommonThreadLocal value=new CommonThreadLocal(0);

    public void add(){

        value.set(10);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(value.get());
    }

    public void minus(){
        value.set(-10);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(value.get());
    }

}
