package com.github.producerconsumer.waitnotify;

import java.util.ArrayList;
import java.util.List;

/**
 * 等待wait的条件发生变化
 * 导致问题:如果有两个线程等待,lockObject.remove(0)就会有问题
 *
 * @Author:zhangbo
 * @Date:2018/9/12 16:08
 */
public class ConditionChange {

    private List<String> lockObject = new ArrayList<>();

    public static void main(String[] args) {
        ConditionChange conditionChange = new ConditionChange();
        new Thread(() -> {
            conditionChange.waitNew();
        }).start();
        new Thread(() -> {
            conditionChange.waitNew();
        }).start();
        new Thread(() -> {
            conditionChange.notifyThread();
        }).start();

    }

    public void waitNew(){
        synchronized (lockObject){
            while(lockObject.isEmpty()){
                System.out.println("进入wait");
                try {
                    lockObject.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("结束wait");
            }

            lockObject.remove(0);
            System.out.println("移除数据");
        }
    }

    public void notifyThread(){
        synchronized (lockObject){
            System.out.println("进入notify");
            lockObject.add("zhangbo");
            lockObject.notifyAll();
            System.out.println("结束notify");
        }
    }

    public void waitThread(){
        synchronized (lockObject){
            if(lockObject.size() == 0){
                System.out.println("进入wait");
                try {
                    lockObject.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("结束wait");
            }

            lockObject.remove(0);
        }
    }

}
