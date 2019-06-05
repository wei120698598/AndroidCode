package com.wei.sample.handler;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue {

    private BlockingQueue<Message> messageBlockingQueue = new LinkedBlockingQueue<>(20);

    public Message next() {
        Message message = null;
        try {
            message = messageBlockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return message;
    }

    public void enqueueMessage(Message message) {
        try {
            messageBlockingQueue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
