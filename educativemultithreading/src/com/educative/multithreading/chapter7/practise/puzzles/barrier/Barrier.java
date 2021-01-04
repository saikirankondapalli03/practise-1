package com.educative.multithreading.chapter7.practise.puzzles.barrier;

/*
 * We can immediately realize that our solution will need a count variable to track the number of threads that have arrived at the barrier. 
 * If we have n threads, then n-1 threads must wait for the nth thread to arrive.
 * This suggests we have the n-1 threads execute the wait method and the nth thread wakes up all the asleep n-1 threads.
 */
class Barrier {
	public static void main( String args[] ) throws Exception{
        Barrier.runTest();
    }
    int count = 0;
    int released = 0;
    int totalThreads;

    public Barrier(int totalThreads) {
        this.totalThreads = totalThreads;
    }

    public static void runTest() throws InterruptedException {
        final Barrier barrier = new Barrier(3);

        Thread p1 = new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println("Thread 1");
                    barrier.await();
                    System.out.println("Thread 1");
                    barrier.await();
                    System.out.println("Thread 1");
                    barrier.await();
                } catch (InterruptedException ie) {
                }
            }
        });

        Thread p2 = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(500);
                    System.out.println("Thread 2");
                    barrier.await();
                    Thread.sleep(500);
                    System.out.println("Thread 2");
                    barrier.await();
                    Thread.sleep(500);
                    System.out.println("Thread 2");
                    barrier.await();
                } catch (InterruptedException ie) {
                }
            }
        });

        Thread p3 = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1500);
                    System.out.println("Thread 3");
                    barrier.await();
                    Thread.sleep(1500);
                    System.out.println("Thread 3");
                    barrier.await();
                    Thread.sleep(1500);
                    System.out.println("Thread 3");
                    barrier.await();
                } catch (InterruptedException ie) {
                }
            }
        });

        p1.start();
        p2.start();
        p3.start();

        p1.join();
        p2.join();
        p3.join();
    }
  
  
    public synchronized void await() throws InterruptedException {
    	  // block any new threads from proceeding till, all threads from previous barrier are released
    	while (count == totalThreads)
            wait();
    	
     // increment the counter whenever a thread arrives at the barrier
        count++;

        if (count == totalThreads) {
        	// wake up all the threads.
            notifyAll();
         // remember to set released to totalThreads
            released = totalThreads;
        } else {

            while (count < totalThreads) {
            	// wait if you aren't the nth thread
                wait();
            }
        }

        released--;
        if (released == 0) {
          // remember to wakeup any threads waiting on line#84
         // remember to reset count so that barrier can be reused
        	count = 0;
            notifyAll();
        }
    }
}

