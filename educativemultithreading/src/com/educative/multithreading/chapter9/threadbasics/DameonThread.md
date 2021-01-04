A daemon thread runs in the background but as soon as the main application thread exits, all daemon threads are killed by the JVM. A thread can be marked daemon as follows:

innerThread.setDaemon(true);
Note that in case a spawned thread isn't marked as daemon then even if the main thread finishes execution, JVM will wait for the spawned thread to finish before tearing down the process.