package com.lsm.juc.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程售票
 */
public class SaleTicket {
    public static void main(String[] args) {
        Task task = new Task();
        //这里模拟有5个线程同时售票
        ExecutorService pool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            pool.execute(task);
        }
        pool.shutdown();
    }

    static class Task implements Runnable {
        /**
         * 全局只有1000张票
         */
        public static int ticket = 100;

        private Lock lock = new ReentrantLock();

        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    if (ticket > 0) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + "=" + ticket--);
                    }
                    if (ticket <= 0)
                        break;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}


