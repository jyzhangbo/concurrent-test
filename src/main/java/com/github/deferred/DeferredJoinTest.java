package com.github.deferred;

import com.stumbleupon.async.Callback;
import com.stumbleupon.async.Deferred;

/**
 * @Author:zhangbo
 * @Date:2018/8/9 20:12
 */
public class DeferredJoinTest {

    public static void main(String[] args) throws Exception {

        /*Deferred.fromResult(get()).addCallback(new UserCB()).join(1000);

        System.out.println("end");*/

        Deferred deferred=new Deferred();

        deferred.addCallback(new UserCB());

        deferred.callback(get());

        deferred.join(1000);


        System.out.println("end");

    }

    public static Integer get(){
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 4;
    }

    static class UserCB implements Callback<Integer,Integer> {
        @Override
        public Integer call(Integer i) throws Exception {
            System.out.println("UserCb获取到用户信息:"+i);
            return i;
        }
    }

}
