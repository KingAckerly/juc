package com.lsm.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock实现死锁
 */
public class ReentrantLockDeadLock {

    private static Lock lock1 = new ReentrantLock();
    private static Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            lock1.lock();
            try {
                System.out.println("t1 get lock1");
                Thread.sleep(1000);
                lock2.lock();
                try {
                    System.out.println("t1 get lock2");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock2.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock1.unlock();
            }
            System.out.println("t1 end");
        });

        Thread t2 = new Thread(() -> {
            lock2.lock();
            try {
                System.out.println("t2 get lock2");
                Thread.sleep(1000);
                lock1.lock();
                try {
                    System.out.println("t2 get lock1");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock1.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock2.unlock();
            }
            System.out.println("t2 end");
        });

        t1.start();
        t2.start();
    }
}