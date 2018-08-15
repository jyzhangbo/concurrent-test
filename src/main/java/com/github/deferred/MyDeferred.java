package com.github.deferred;

import com.stumbleupon.async.Callback;
import com.stumbleupon.async.CallbackOverflowError;
import com.stumbleupon.async.Deferred;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @Author:zhangbo
 * @Date:2018/8/9 14:46
 */
public final class MyDeferred<T> {


    //回调链的最大长度（16383）
    private static final short MAX_CALLBACK_CHAIN_LENGTH = (1 << 14) - 1;

    //回调链的初始大小.
    private static final byte INIT_CALLBACK_CHAIN_SIZE = 4;

    //deferred状态.
    private static final byte PENDING = 0;
    private static final byte RUNNING = 1;
    private static final byte PAUSED = 2;
    private static final byte DONE = 3;

    //deferred当前状态.
    private volatile int state;

    //当前的结果.
    private Object result;

    //回调链.
    private Callback[] callbacks;

    //下一个要执行的回调位置.
    private short next_callback;

    //最后一个执行的回调位置.
    private short last_callback;

    //原子操作帮助.
    private static final AtomicIntegerFieldUpdater<MyDeferred> stateUpdater =
            AtomicIntegerFieldUpdater.newUpdater(MyDeferred.class, "state");

    private boolean casState(final int cmp, final int val) {
        return stateUpdater.compareAndSet(this, cmp, val);
    }

    //构造函数，初始化状态
    public MyDeferred() {
        state = PENDING;
    }

    //当可以获取到结果的时候使用它初始化.
    private MyDeferred(final Object result) {
        this.result = result;
        state = DONE;
    }

    //CB函数会立即执行.
    public static <T> MyDeferred<T> fromResult(final T result) {
        return new MyDeferred<T>(result);
    }

    //EB函数会立即执行.
    public static <T> MyDeferred<T> fromError(final Exception error) {
        return new MyDeferred<T>(error);
    }

    public <R, R2, E> MyDeferred<R> addCallbacks(final Callback<R, T> cb, final Callback<R2, E> eb) {
        if (cb == null) {
            throw new NullPointerException("null callback");
        } else if (eb == null) {
            throw new NullPointerException("null errback");
        }

        synchronized (this) {
            if (state == DONE) {
                state = RUNNING;
            } else {
                if (callbacks == null) {
                    callbacks = new Callback[INIT_CALLBACK_CHAIN_SIZE];
                } else if (last_callback == callbacks.length) {
                    final int oldlen = callbacks.length;
                    if (oldlen == MAX_CALLBACK_CHAIN_LENGTH * 2) {
                        throw new CallbackOverflowError("Too many callbacks in " + this
                                + " (size=" + (oldlen / 2) + ") when attempting to add cb="
                                + cb + '@' + cb.hashCode() + ", eb=" + eb + '@' + eb.hashCode());
                    }
                    final int len = Math.min(oldlen * 2, MAX_CALLBACK_CHAIN_LENGTH * 2);
                    final Callback[] newcbs = new Callback[len];
                    System.arraycopy(callbacks, next_callback,  // Outstanding callbacks.
                            newcbs, 0,            // Move them to the beginning.
                            last_callback - next_callback);  // Number of items.
                    last_callback -= next_callback;
                    next_callback = 0;
                    callbacks = newcbs;
                }
                callbacks[last_callback++] = cb;
                callbacks[last_callback++] = eb;
                return (MyDeferred<R>) ((MyDeferred) this);
            }
        }

        if (!doCall(result instanceof Exception ? eb : cb)) {
            boolean more;
            synchronized (this) {
                more = callbacks != null && next_callback != last_callback;
            }
            if (more) {
                runCallbacks();  // Will put us back either in DONE or in PAUSED.
            } else {
                state = DONE;
            }
        }

        return (MyDeferred<R>) ((Object) this);
    }


    public <R> MyDeferred<R> addCallback(final Callback<R, T> cb) {
        return addCallbacks(cb, Callback.PASSTHROUGH);
    }

    public <R, D extends Deferred<R>> MyDeferred<R> addCallbackDeferring(final Callback<D, T> cb) {
        return addCallbacks((Callback<R, T>) ((Object) cb), Callback.PASSTHROUGH);
    }

    public <R, E> MyDeferred<T> addErrback(final Callback<R, E> eb) {
        return addCallbacks((Callback<T, T>) ((Object) Callback.PASSTHROUGH), eb);
    }

    public <R> MyDeferred<R> addBoth(final Callback<R, T> cb) {
        return addCallbacks(cb, cb);
    }

    public <R, D extends MyDeferred<R>>
    MyDeferred<R> addBothDeferring(final Callback<D, T> cb) {
        return addCallbacks((Callback<R, T>) ((Object) cb),
                (Callback<R, T>) ((Object) cb));
    }


    private boolean doCall(final Callback cb) {
        try {
            result = cb.call(result);
        } catch (Exception e) {
            result = e;
        }

        if (result instanceof MyDeferred) {
            handleContinuation((MyDeferred) result, cb);
            return true;
        }
        return false;
    }

    private void handleContinuation(final MyDeferred d, final Callback cb) {
        if (this == d) {
            final String cb2s = cb == null ? "null" : cb + "@" + cb.hashCode();
            throw new AssertionError("After " + this + " executed callback=" + cb2s
                    + ", the result returned was the same Deferred object.  This is illegal"
                    + ", a Deferred can't run itself recursively.  Something is wrong.");
        }

        if (d.casState(DONE, RUNNING)) {
            result = d.result;  // No one will change `d.result' now.
            d.state = DONE;
            runCallbacks();
            return;
        }

        state = PAUSED;
        d.addBoth(new Continue(d, cb));

    }

    //执行回调链.
    private void runCallbacks() {
        while (true) {
            Callback cb = null;
            Callback eb = null;
            synchronized (this) {
                if (callbacks != null && next_callback != last_callback) {
                    cb = callbacks[next_callback++];
                    eb = callbacks[next_callback++];
                } else {
                    //如果会掉链执行完毕，则跳出循环
                    state = DONE;
                    callbacks = null;
                    next_callback = last_callback = 0;
                    break;
                }
            }

            if (doCall(result instanceof Exception ? eb : cb)) {
                break;
            }
        }
    }


    private final class Continue implements Callback<Object, Object> {

        private final MyDeferred d;
        private final Callback cb;

        public Continue(final MyDeferred d, final Callback cb) {
            this.d = d;
            this.cb = cb;
        }

        @Override
        public Object call(final Object arg) {
            if (arg instanceof MyDeferred) {
                handleContinuation((MyDeferred) arg, cb);
            } else if (!casState(PAUSED, RUNNING)) {
                final String cb2s = cb == null ? "null" : cb + "@" + cb.hashCode();
                throw new AssertionError("Tried to resume the execution of "
                        + MyDeferred.this + ") although it's not in state=PAUSED."
                        + "  This occurred after the completion of " + d
                        + " which was originally returned by callback=" + cb2s);
            }
            result = arg;
            runCallbacks();
            return arg;
        }

        @Override
        public String toString() {
            return "(continuation of Deferred@" + MyDeferred.super.hashCode()
                    + " after " + (cb != null ? cb + "@" + cb.hashCode() : d) + ')';
        }
    }

    public static void main(String[] args) {
        System.out.println(MAX_CALLBACK_CHAIN_LENGTH);
    }

}
