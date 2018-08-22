package com.github.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author:zhangbo
 * @Date:2018/8/22 14:41
 */
public class AtomicReferenceLearn {

    private AtomicReference<User> reference=new AtomicReference<>();
    private CountDownLatch latch=new CountDownLatch(10);

    public static void main(String[] args) {
        AtomicReferenceLearn learn=new AtomicReferenceLearn();
        User user=new User();
        user.setName("zhangbo");
        user.setAge(18);
        learn.reference.set(user);

        learn.run();
        System.out.println(learn.reference.get());

    }

    public void run(){
        User user=new User();
        user.setAge(20);
        user.setName("zhangbo");
        User oldUser = reference.getAndSet(user);
        System.out.println(oldUser);
    }

}
