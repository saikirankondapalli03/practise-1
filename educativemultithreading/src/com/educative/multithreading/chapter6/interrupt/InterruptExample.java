package com.educative.multithreading.chapter6.interrupt;


class InterruptExample {

    public static void main(String args[]) throws InterruptedException {
        InterruptExample.example();
    }
    static public void example() throws InterruptedException {

        final Thread sleepyThread = new Thread(new Runnable() {

            public void run() {
                try {
                    System.out.println("I am too sleepy... Let me sleep for an hour.");
                    Thread.sleep(1000 * 60 * 60);
                } catch (InterruptedException ie) {
                     System.out.println("Oh someone woke me up ! ");
                    boolean flag1= Thread.interrupted();
                    System.out.println("The interrupt flag is set now : " + flag1+ Thread.currentThread().isInterrupted() ); 
                    Thread.currentThread().interrupt();
                  System.out.println("The interrupt flag is cleard : " +Thread.interrupted()+ Thread.currentThread().isInterrupted() + " ");                  
                    
                }
            }
        });

        sleepyThread.start();
        System.out.println("About to wake up the sleepy thread ...");
        sleepyThread.interrupt();
        System.out.println("Woke up sleepy thread ...");

        sleepyThread.join();
    }
}
