package com.educative.multithreading.chapter9.threadbasics.executors;

import java.util.Timer;
import java.util.TimerTask;

class TimerDemoBadUse {
    public static void main( String args[] ) throws Exception {
        Timer timer = new Timer();
        TimerTask badTask = new TimerTask() {

            @Override
            public void run() {

                // run forever
                while (true)
                    ;

            }
        };

        TimerTask goodTask = new TimerTask() {

            @Override
            public void run() {

                System.out.println("Hello I am a well-behaved task");

            }
        };

        timer.schedule(badTask, 500);
        timer.schedule(goodTask, 100);

        // By three seconds, both tasks are expected to have launched
        Thread.sleep(3000);
    }
}