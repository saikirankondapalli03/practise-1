Hedge fund sequential approach:

void receiveAndExecuteClientOrders() {
        while (true) {
            Order order = waitForNextOrder();
            order.execute();
        }
    }
    
Unbounded Thread Approach:
 void receiveAndExecuteClientOrdersBetter() {
        while (true) {
            final Order order = waitForNextOrder();
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    order.execute();
                }
            });
            thread.start();
        }
    }

Notes:
1. Thread creation and teardown isn't for free
2. If there are less number of processors than threads then several of them will sit idle tying up memory.
3.There is usually a limit imposed by JVM and the underlying OS on the number of threads that can be created.

In such a scenario, the method will end up with a growing backlog of requests and may cause the program to crash.


