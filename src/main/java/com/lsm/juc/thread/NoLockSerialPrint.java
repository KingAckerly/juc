package com.lsm.juc.thread;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * 无锁多线程顺序打印
 */
public class NoLockSerialPrint {
    /**
     * 必须加volatile修饰才能保证顺序
     */
    private static volatile int flag = -1;
    private static AtomicInteger num = new AtomicInteger(0);

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            new Thread(() -> {
                while (true) {
                    if (flag == finalI - 1) {
                        System.out.println(Thread.currentThread().getName() + "处理:" + num.getAndIncrement());
                        flag = finalI;
                        break;
                    }
                }
            }, "t" + i).start();
        }
    }
}


