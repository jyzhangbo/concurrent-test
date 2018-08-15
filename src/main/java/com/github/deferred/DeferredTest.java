package com.github.deferred;

import com.stumbleupon.async.Callback;
import com.stumbleupon.async.Deferred;

/**
 * @Author:zhangbo
 * @Date:2018/8/9 17:11
 */
public class DeferredTest {

    public static void main(String[] args) throws Exception{
        Deferred.fromResult(4).addCallbackDeferring(new DeferredCB());
    }


    static class DeferredCB implements Callback<Deferred<Integer>,Integer>{

        @Override
        public Deferred<Integer> call(Integer arg) throws Exception {
            System.out.println(arg);
            return Deferred.fromResult(3).addCallback(new UserCB());
        }
    }


    static class UserCB implements Callback<Integer,Integer> {
        @Override
        public Integer call(Integer i) throws Exception {
            System.out.println("User获取到用户信息:"+i);
            return i;
        }
    }

    static class UserEB implements Callback<Integer,Exception> {
        @Override
        public Integer call(Exception e) throws Exception {
            System.out.println("User获取到用户信息:"+e);
            return 0;
        }
    }

}
