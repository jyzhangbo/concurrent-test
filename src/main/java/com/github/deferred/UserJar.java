package com.github.deferred;

import com.stumbleupon.async.Callback;
import com.stumbleupon.async.Deferred;

/**
 * @Author:zhangbo
 * @Date:2018/8/8 15:33
 */
public class UserJar {

    public static void main(String[] args) throws Exception{
            Deferred deferred = ClientJar.get(1);
            deferred.addCallback(new UserCB()).joinUninterruptibly();
            System.out.println("end");

    }

    static class UserCB implements Callback<User,User> {

        @Override
        public User call(User user) throws Exception {
            System.out.println("User获取到用户信息:"+user.toString());

            return user;
        }
    }

}
