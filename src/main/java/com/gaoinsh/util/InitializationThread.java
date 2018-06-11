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
 * <p>
 * 解决问题场景:
 * 多线程情况下，多个线程同时调用refresh方法,但只允许一个线程完成refresh的工作，其他线程检测到有线程在进行refresh之后，阻塞等待refresh完成。
 */
public class InitializationThread {


    private static ReentrantLock mainLock = new ReentrantLock();

    private static ReentrantLock awaitLock = new ReentrantLock();

    private static Condition waitCondition = awaitLock.newCondition();

    private static volatile String val = "1";

    /**
     * 两个锁，一个锁为refresh工作线程持有
     * 另外一个锁,用做阻塞等待结果的队列,refresh工作线程完成后,通过获取该锁，来唤醒await该锁上的condition的线程。
     */
    public static void refresh() {
        if (mainLock.tryLock()) {
            try {
                init();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mainLock.unlock();
            }
            sinalAllWaitingThread();
        } else {
            waitWorkingResult();
        }
    }

    /**
     * 执行refresh的线程获取锁，其他等待结果的线程调用lock方法排队，然后等待refresh工作线程unlock来唤醒自己，只需要一个锁即可实现
     */
    public static void refreshNew() {
        if (mainLock.tryLock()) {
            try {
                init();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mainLock.unlock();
            }
        } else {
            System.out.println(Thread.currentThread().getName() + " 正在等待结果...");
            mainLock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " 已经获取到最新结果... " + val);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mainLock.unlock();
            }
        }
    }


    private static void waitWorkingResult() {
        if (mainLock.isLocked()) {
            awaitLock.lock();
            try {
                /*
                 *  workThread在unlock,signal之后,有可能仍有线程尝试在此处阻塞,等待workThread唤醒，这部分线程会无法被唤醒。
                 *  加一层判断,如果worklock已经解除占用,认为work过程已经完成,不在需要阻塞当前线程,直接获取结果
                 */
                if (mainLock.isLocked()) {
                    System.out.println(Thread.currentThread().getName() + " 正在等待结果...");
                    waitCondition.await();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                awaitLock.unlock();
            }
        }
    }

    private static void sinalAllWaitingThread() {
        awaitLock.lock();
        try {
            waitCondition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            awaitLock.unlock();
        }
    }

    private static void init() {
        try {
            System.out.println(Thread.currentThread().getName() + "开始初始化");
//            Thread.sleep(10000);
            val = val + "-n";
            System.out.println(Thread.currentThread().getName() + "初始化完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getVal() {
        return val;
    }

}