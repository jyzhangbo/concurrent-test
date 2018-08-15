package com.github.deferred;

import com.stumbleupon.async.Callback;
import com.stumbleupon.async.Deferred;

/**
 * @Author:zhangbo
 * @Date:2018/8/7 15:20
 */
public class ClientJar {

    public static Deferred get(Integer i){

        System.out.println("ClientJar:"+i);

        Deferred deferred=Deferred.fromResult(i).addCallback(new ClientCB());

        return deferred;

    }

    static class ClientCB implements Callback<User,Integer>{

        @Override
        public User call(Integer i) throws Exception {
            System.out.println("ClientCB:"+i);
            User user = ServerJar.get(i);

            return user;
        }
    }


}
