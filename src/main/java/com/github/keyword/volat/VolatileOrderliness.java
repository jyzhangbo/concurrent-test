package com.github.keyword.volat;

/**
 * 保证有序性.
 * 1.当程序执行到volatile变量的读写操作时，在其前面的操作肯定已经执行完毕，后面的操作还没开始
 * 2.在进行指令优化时，不能将volatile变量前面的操作放在其后面执行，也不能把volatile变量后面的语句放到其前面执行
 *
 * user.setName("zhangbo")和inited = true的执行顺序是不确定的，可能会inited = true先执行，
 * 这样线程2在获取用户姓名的时候就获取不到.
 *
 * 用volatile关键字对inited变量进行修饰，就不会出现这种问题了，因为当执行到语句inited = true时，
 * 必定能保证user.setName("zhangbo")已经执行完毕
 *
 *
 * @Author:zhangbo
 * @Date:2018/8/16 11:14
 */
public class VolatileOrderliness {

    private User user;

    private volatile boolean inited;

    public static void main(String[] args) {

        VolatileOrderliness learn=new VolatileOrderliness();

        new Thread(() -> {
            learn.thread1();
        }).start();

        new Thread(() -> {
            learn.thread2();
        }).start();

    }

    public void thread1(){
        user=new User();
        user.setName("zhangbo");
        inited = true;
    }

    public void thread2(){
        while (!inited){
            System.out.println("未初始化");
        }

        System.out.println(user.getName());
    }

}
