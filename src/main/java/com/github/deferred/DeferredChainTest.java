package com.github.deferred;

import com.stumbleupon.async.Callback;
import com.stumbleupon.async.Deferred;

/**
 *
 * 多个deferred复用一个结果,可以用chain将多个deferred连接起来.
 * @Author:zhangbo
 * @Date:2018/8/9 19:38
 */
public class DeferredChainTest {

    public static void main(String[] args) {
        Deferred deferred=new Deferred();
        deferred.addCallback(new UserEB());
        Deferred.fromResult(4).addCallback(new UserCB()).chain(deferred);

    }

    static class UserCB implements Callback<Integer,Integer> {
        @Override
        public Integer call(Integer i) throws Exception {
            System.out.println("UserCb获取到用户信息:"+i);
            return i;
        }
    }

    static class UserEB implements Callback<Integer,Integer> {
        @Override
        public Integer call(Integer e) throws Exception {
            System.out.println("UserEb获取到用户信息:"+e);
            return 0;
        }
    }

}
