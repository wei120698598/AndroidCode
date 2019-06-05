package com.wei.sample.handler;

public class Looper {
    public MessageQueue messageQueue;
    private static ThreadLocal<Looper> threadLocal = new ThreadLocal<>();


    private Looper() {
        messageQueue = new MessageQueue();
    }

    public static void prepare() {
        if (threadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        threadLocal.set(new Looper());
    }

    public static Looper myLooper() {
        return threadLocal.get();
    }

    public static void loop() {
        while (true) {
            Looper looper = myLooper();
            Message message = looper.messageQueue.next();
            if (message != null) {
                message.target.dispatchMessage(message);
            }
        }
    }
}
