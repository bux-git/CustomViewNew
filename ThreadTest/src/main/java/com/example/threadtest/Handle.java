package com.example.threadtest;

import java.util.Calendar;

/**
 * @description：
 * @author：bux on 2019/12/12 21:05
 * @email: 471025316@qq.com
 */
public class Handle implements Runnable {

    private String name;
    private int sleep;

    public Handle(int sleep) {
        this.name = "Task:"+name;
        this.sleep=sleep;
    }

    @Override
    public void run() {

        System.out.println(Thread.currentThread().getName()+" startTime:"+ Calendar.getInstance().getTime());

        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName()+" endTime:"+ Calendar.getInstance().getTime());


    }
}
