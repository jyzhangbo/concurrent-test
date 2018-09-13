package com.github.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 根据AQS自实现的一个独占锁,每次只有一个线程可以获取到锁.
 * 关键点：
 *  1.定义继承AQS的静态内存类，并重写需要的protected修饰的方法
 *  2.同步组件语义的实现依赖于AQS的模板方法，而AQS模板方法又依赖于被AQS的子类所重写的方法
 *
 * @Author:zhangbo
 * @Date:2018/9/13 10:57
 */
public class MyMutex implements Lock {

    /**
     * 继承AQS的静态内部类,是一个同步器的关键所在.
     */
    private static final class Sync extends AbstractQueuedSynchronizer{

        /**
         * 是否被当前线程所占用.
         * @return
         */
        @Override
        protected boolean isHeldExclusively() {
            return getExclusiveOwnerThread().equals(Thread.currentThread());
        }

        /**
         * 独占式获取同步状态,需要查询当前状态是否符合预期,并进行CAS设置同步状态.
         * @param arg
         * @return
         */
        @Override
        protected boolean tryAcquire(int arg) {
            assert arg == 1;
            if(compareAndSetState(0,1)){
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        /**
         * 独占式释放同步状态.
         * @param arg
         * @return
         */
        @Override
        protected boolean tryRelease(int arg) {
            assert arg == 1;
            if(getState() == 0){
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        final ConditionObject newCondition() {
            return new ConditionObject();
        }

        private void readObject(java.io.ObjectInputStream s)
                throws java.io.IOException, ClassNotFoundException {
            s.defaultReadObject();
            setState(0);
        }
    }

    private final Sync sync = new Sync();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(time));
    }


    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
