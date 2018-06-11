/**
 * ymm56.com Inc.
 * Copyright (c) 2013-2018 All Rights Reserved.
 */
package com.gaoinsh.util;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiang.gao
 * @version $Id: InitializationThread.java, v 0.1 2018-06-08 17:46 xiang.gao Exp $$
 */
public class InitializationThread {


    private static ReentrantLock workLock = new ReentrantLock();

    private static ReentrantLock waitLock = new ReentrantLock();

    private static Condition waitResult = waitLock.newCondition();

    private static volatile String val = "aaabbb";

    public static void refresh() {
        if (workLock.tryLock()) {
            try {
                init();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                workLock.unlock();
            }
            sinalAllWaitingThread();
        } else {
            waitWorkingResult();
        }
    }

    public static void refreshNew() {
        if (workLock.tryLock()) {
            try {
                init();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                workLock.unlock();
            }
        } else {
            System.out.println(Thread.currentThread().getName() + " 正在等待结果...");
            workLock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " 已经获取到最新结果... " + val);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                workLock.unlock();
            }
        }
    }

    private static void waitWorkingResult() {
        waitLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 正在等待结果...");
            waitResult.await();
            System.out.println(Thread.currentThread().getName() + " 已经获取到最新结果... " + val);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            waitLock.unlock();
        }
    }

    private static void sinalAllWaitingThread() {
        waitLock.lock();
        try {
            waitResult.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            waitLock.unlock();
        }
    }

    private static void init() {
        try {
            System.out.println(Thread.currentThread().getName() + "开始初始化");
            Thread.sleep(10000);
            val = "bbbccc";
            System.out.println(Thread.currentThread().getName() + "初始化完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}