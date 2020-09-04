package com.basicjava.multithreading;

public class CLLock implements Runnable {        
    @Override
    public void run() {
        Lock();
    }

    public void Lock() {
        System.out.println(Thread.currentThread().getName());
        synchronized(CLLock.class) {
            System.out.println("in block " + Thread.currentThread().getName());
            System.out.println("in block " + Thread.currentThread().getName() + " end");
        }
    }

    public static void main(String[] args) {
        CLLock b1 = new CLLock();
        Thread t1 = new Thread(b1);
        Thread t2 = new Thread(b1);             
        CLLock b2 = new CLLock();
        Thread t3 = new Thread(b2);             
        t1.setName("t1");
        t2.setName("t2");
        t3.setName("t3");             
        t1.start();
        t2.start();
        t3.start();
    }
}