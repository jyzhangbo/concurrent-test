package com.github.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Author:zhangbo
 * @Date:2018/9/4 14:08
 */
public class UnsafeLearn {

    public String item;

    public Unsafe UNSAFE;

    public long offsetItem;

    public static void main(String[] args) {
       UnsafeLearn learn=new UnsafeLearn();
       learn.initUnsafe();
       learn.objectFieldOffset();
       learn.putOrderObject();
       learn.putObject();

    }

    /**
     * 设置obj对象中offset偏移地址对应的object型field的值为指定值。不保证值的改变被其他线程立即看到.
     *
     */
    public void putObject(){
        UNSAFE.putObject(this,offsetItem,"zhaokun");
        System.out.println(item);
    }

    /**
     * 设置obj对象中offset偏移地址对应的object型field的值为指定值.
     */
    public void putOrderObject(){
        UNSAFE.putOrderedObject(this,offsetItem,"zhangbo");
        System.out.println(item);
    }

    /**
     * 返回指定静态field的内存地址偏移量.
     */
    public void objectFieldOffset(){
        try {
            offsetItem = UNSAFE.objectFieldOffset(UnsafeLearn.class.getField("item"));
            System.out.println(offsetItem);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过反射的机制获取UNSAFE实例.
     */
    public void initUnsafe(){
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(Unsafe.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
