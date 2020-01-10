package com.example.threadtest;

import java.util.Calendar;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyClass {

    public static void main(String[] args) {
        System.out.println("Main startTime:" + Calendar.getInstance().getTime());
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 1, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(5)
                , new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable);
            }
        });

        for (int i = 0; i < 15; i++) {
            executor.execute(new Handle(i * 100));
        }

        executor.shutdown();
        System.out.println("Main endTime:" + Calendar.getInstance().getTime());
    }

}
