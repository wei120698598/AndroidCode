package com.wei.sample.handler;


public class Handler {
    final Looper mLooper;
    final MessageQueue mQueue;

    public Handler() {
        this(Looper.myLooper());
    }

    public Handler(Looper mLooper) {
        this.mLooper = mLooper;
        this.mQueue = mLooper.messageQueue;
    }

    public void handlerMessage(Message message) {

    }

    void dispatchMessage(Message message) {
        handlerMessage(message);
    }

    public void sendMessage(Message message) {
        message.target = this;
        mQueue.enqueueMessage(message);
    }

    public Message obtainMessage() {
        return new Message();
    }
}
