package com.wei.sample.livedata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shuxshuxinin
 * @version v1.0.0
 * @date 2019-06-24
 * @description https://tech.meituan.com/2018/07/26/android-livedatabus.html
 * https://blog.csdn.net/mingyunxiaohai/article/details/89605994
 */
public final class LiveDataBus {

    private final Map<String, BusMutableLiveData<Object>> bus;

    private LiveDataBus() {
        bus = new HashMap<>();
    }

    private static class SingletonHolder {
        private static final LiveDataBus DEFAULT_BUS = new LiveDataBus();
    }

    public static LiveDataBus get() {
        return SingletonHolder.DEFAULT_BUS;
    }

    public <T> MutableLiveData<T> with(String key, Class<T> type) {
        if (!bus.containsKey(key)) {
            bus.put(key, new BusMutableLiveData<>());
        }
        return (MutableLiveData<T>) bus.get(key);
    }

    public MutableLiveData<Object> with(String key) {
        return with(key, Object.class);
    }

    private static class ObserverWrapper<T> implements Observer<T> {

        private Observer<T> observer;

        public ObserverWrapper(Observer<T> observer) {
            this.observer = observer;
        }

        @Override
        public void onChanged(@Nullable T t) {
            if (observer != null) {
                if (isCallOnObserve()) {
                    return;
                }
                observer.onChanged(t);
            }
        }

        private boolean isCallOnObserve() {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            if (stackTrace != null && stackTrace.length > 0) {
                for (StackTraceElement element : stackTrace) {
                    if ("android.arch.lifecycle.LiveData".equals(element.getClassName()) &&
                            "observeForever".equals(element.getMethodName())) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    private static class BusMutableLiveData<T> extends MutableLiveData<T> {

        private Map<Observer, Observer> observerMap = new HashMap<>();


        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
            super.observe(owner, observer);
            try {
                hook(observer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void observeForever(@NonNull Observer<? super T> observer) {
            if (!observerMap.containsKey(observer)) {
                observerMap.put(observer, new ObserverWrapper(observer));
            }
            super.observeForever(observerMap.get(observer));
        }

        @Override
        public void removeObserver(@NonNull Observer<? super T> observer) {
            Observer realObserver = null;
            if (observerMap.containsKey(observer)) {
                realObserver = observerMap.remove(observer);
            } else {
                realObserver = observer;
            }
            super.removeObserver(realObserver);
        }

        /**
         * 问题：在使用这个LiveDataBus的过程中，订阅者会收到订阅之前发布的消息。
         * 对于一个消息总线来说，这是不可接受的。无论EventBus或者RxBus，订阅方都不会收到订阅之前发出的消息。对于一个消息总线，LiveDataBus必须要解决这个问题。
         * <p>
         * 原因：当LifeCircleOwner的状态发生变化的时候，会调用LiveData.ObserverWrapper的activeStateChanged函数，如果这个时候ObserverWrapper的状态是active，就会调用LiveData的dispatchingValue，
         * 如果ObserverWrapper的mLastVersion小于LiveData的mVersion，就会去回调mObserver的onChanged方法。
         * 而每个新的订阅者，其version都是-1，LiveData一旦设置过其version是大于-1的（每次LiveData设置值都会使其version加1），
         * 这样就会导致LiveDataBus每注册一个新的订阅者，这个订阅者立刻会收到一个回调，即使这个设置的动作发生在订阅之前。
         */
        private void hook(@NonNull Observer<? super T> observer) throws Exception {
            //获取LiveData的class
            Class<LiveData> liveDataClass = LiveData.class;
            //反射回去LiveData的成员变量mObservers
            Field fileObservers = liveDataClass.getDeclaredField("mObservers");
            //设置该属性可更改
            fileObservers.setAccessible(true);
            //get方法获取的是当前对象的实例，这里就是mObservers这个Map集合
            Object objectObservers = fileObservers.get(this);
            //获取map对象的类
            Class<?> classObservers = objectObservers.getClass();
            //获取集合的Map方法
            Method methodGet = classObservers.getDeclaredMethod("get", Object.class);
            //设置get方法可以被访问
            methodGet.setAccessible(true);
            //执行get方法拿出当前观察者对应的对象
            Object objectWrapperEntry = methodGet.invoke(objectObservers, observer);
            //定义一个空对象
            Object objectWrapper = null;
            //判断objectWrapperEntry是否是Map.Entry类型
            if (objectWrapperEntry instanceof Map.Entry) {
                //如果是拿出他的值,其实就是LifecycleBoundObserver
                objectWrapper = ((Map.Entry) objectWrapperEntry).getValue();
            }
            //如果是空抛个异常
            if (objectWrapper == null) {
                throw new RuntimeException("objectWrapper is null");
            }
            //因为mLastVersion在LifecycleBoundObserver的父类ObserverWrapper中，所以拿到它的父类
            Class<?> classObserverWrapper = objectWrapper.getClass().getSuperclass();
            //获取到mLastVersion字段
            Field fieldLastVersion = classObserverWrapper.getDeclaredField("mLastVersion");
            //设置该字段可以更改
            fieldLastVersion.setAccessible(true);

            //获取LiveData中的mVersion值
            Field fileVersion = liveDataClass.getDeclaredField("mVersion");
            //设置该值可以被访问
            fileVersion.setAccessible(true);
            //获取mVersion的值
            Object objVersion = fileVersion.get(this);
            //给mLastVersion赋值
            fieldLastVersion.set(objectWrapper, objVersion);
        }
    }
}
