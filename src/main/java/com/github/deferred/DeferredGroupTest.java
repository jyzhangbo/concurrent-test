package com.github.deferred;

import com.stumbleupon.async.Callback;
import com.stumbleupon.async.Deferred;

import java.util.ArrayList;
import java.util.List;

/**
 * 将多个Deferred合在一起，当所有的deferred都能得到result的时候，调用一个callback.
 * @Author:zhangbo
 * @Date:2018/8/9 19:57
 */
public class DeferredGroupTest {

    public static void main(String[] args) {

        List<Deferred<Integer>> deferreds=new ArrayList<>(5);

        for(int i=0;i<5;i++){
            deferreds.add(Deferred.fromResult(get(i)));
        }

        Deferred.group(deferreds).addCallback(new UserCB());

    }

    public static Integer get(Integer i){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return i;
    }

    static class UserCB implements Callback<Integer,ArrayList<Integer>> {
        @Override
        public Integer call(ArrayList<Integer> arg) throws Exception {
            System.out.println("UserCb获取到用户信息:"+arg);
            return 1;
        }
    }

}
