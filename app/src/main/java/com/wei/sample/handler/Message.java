package com.wei.sample.handler;

public class Message {
    public Handler target;
    public Object data;

    @Override
    public String toString() {
        return "Message{" +
                "target=" + target +
                ", data=" + data.toString() +
                '}';
    }
}
