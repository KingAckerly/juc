package com.lsm.juc.lock;

/**
 * synchronized实现死锁
 */
public class SynchronizedDeadLock {

    private static Object lock1 = new Object();
    private static Object lock2 = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("t1 get lock1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println("t1 get lock2");
                }
            }
            System.out.println("t1 end");
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("t2 get lock2");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1) {
                    System.out.println("t2 get lock1");
                }
            }
            System.out.println("t2 end");
        });

        t1.start();
        t2.start();
    }
}