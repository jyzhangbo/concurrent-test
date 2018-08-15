package com.github.deferred;

/**
 * @Author:zhangbo
 * @Date:2018/8/8 15:34
 */
public class ServerJar {

    public static User get(Integer i){

        System.out.println("ServerJar:"+i);

        User user=new User();
        user.name="zhangbo";
        user.age=i;

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return user;
    }

}
