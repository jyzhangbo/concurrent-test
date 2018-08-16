package com.github.keyword.synchro;

/**
 * @Author:zhangbo
 * @Date:2018/8/15 15:50
 */
public class SynchronizedLearn {


    public static void main(String[] args) throws InterruptedException {

        Money money=new Money();
        money.setMoney(1000);

        new Bank(false,800,money).start();
        new Bank(false,800,money).start();
        new Bank(true,800,money).start();
    }

}
