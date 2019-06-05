package com.wei.sample.handler;

import java.util.UUID;

public class HandlerTest {
    public static void main(String[] args) {
        Looper.prepare();
        Handler handler = new Handler() {
            @Override
            public void handlerMessage(Message message) {
                super.handlerMessage(message);
                System.out.println(Thread.currentThread().getName() + " handlerMessage:" + message.data.toString());
            }
        };
        Looper.loop();

        new Thread(new Runnable() {
            @Override
            public void run() {
               while (true){
                   Message message = handler.obtainMessage();
                   message.data = UUID.randomUUID().toString();
                   handler.sendMessage(message);
                   System.out.println(Thread.currentThread().getName() + " sendMessage:" + message.data.toString());
               }
            }
        }).start();
    }
}
