package com.lsm.juc.lock;

public class PrintABCUsingWaitNotify {
    private int state;
    private static final Object LOCK = new Object();

    private void printLetter(String name, int targetState) {
        while (true) {
            synchronized (LOCK) {
                while (state % 3 != targetState) {
                    try {
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                state++;
                System.out.print(name);
                LOCK.notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        PrintABCUsingWaitNotify printABC = new PrintABCUsingWaitNotify();
        new Thread(() -> {
            printABC.printLetter("A", 0);
        }, "A").start();
        new Thread(() -> {
            printABC.printLetter("B", 1);
        }, "B").start();
        new Thread(() -> {
            printABC.printLetter("C", 2);
        }, "C").start();
    }

}
