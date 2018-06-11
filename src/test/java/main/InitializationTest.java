/**
 * ymm56.com Inc.
 * Copyright (c) 2013-2018 All Rights Reserved.
 */
package main;

import com.gaoinsh.util.InitializationThread;

import java.util.concurrent.CountDownLatch;

/**
 * @author xiang.gao
 * @version $Id: InitializationTest.java, v 0.1 2018-06-08 18:09 xiang.gao Exp $$
 */
public class InitializationTest {


    public static void main(String[] args) {

        final CountDownLatch cdl = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        cdl.await();
                        InitializationThread.refresh();
                        System.out.println(Thread.currentThread().getName() + " 已经获取到了结果: " + InitializationThread.getVal());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.setName("refresh-thread-" + i);
            thread.start();
        }

        cdl.countDown();
    }
}