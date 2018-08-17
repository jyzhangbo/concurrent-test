package com.github.container.threadlocal;

import java.util.Map;

/**
 * @Author:zhangbo
 * @Date:2018/8/17 17:19
 */
public class CommonThreadLocal {

    private Integer defaultValue;

    public CommonThreadLocal(Integer defaultValue){
        this.defaultValue = defaultValue;
    }

    public Integer get(){
        Map<Integer, Integer> map = getMap();
        Integer key = this.hashCode();

        if(map.containsKey(key)){
            return map.get(key);
        }

        return defaultValue;
    }

    public void set(Integer value){

        Map<Integer, Integer> map = getMap();
        Integer key = this.hashCode();
        map.put(key,value);

    }

    public Map<Integer,Integer> getMap(){
        CommonThread thread = (CommonThread) Thread.currentThread();
        return thread.cacheMap;
    }

}
