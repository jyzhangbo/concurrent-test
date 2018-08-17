package com.github.container.threadlocal;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:zhangbo
 * @Date:2018/8/17 17:15
 */
public class CommonThread extends Thread{

    public Map<Integer,Integer> cacheMap =  new HashMap<>();

    @Override
    public void run(){
        super.run();
    }

}
