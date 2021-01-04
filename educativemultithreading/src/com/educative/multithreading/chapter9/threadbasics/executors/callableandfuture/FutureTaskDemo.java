package com.educative.multithreading.chapter9.threadbasics.executors.callableandfuture;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

class FutureTaskDemo {

    @SuppressWarnings("unchecked")
    public static void main( String args[] ) throws Exception{
        FutureTask<Integer> futureTask = new FutureTask(new Callable() {
            public Object call() throws Exception {
                try{
                    Thread.sleep(1);
                }
                catch(InterruptedException ie){
                    // swallow exception
                }
                return 5;
            }
        });
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        Future duplicateFuture = threadPool.submit(futureTask);
        // Awful idea to busy wait
        while (!futureTask.isDone()) {
            System.out.println("Waiting");
        }
        if(duplicateFuture.isDone() != futureTask.isDone()){
            System.out.println("This should never happen.");
        }
        System.out.println((int)futureTask.get());
        threadPool.shutdown();                              
    }
}