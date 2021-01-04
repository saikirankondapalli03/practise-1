http://tutorials.jenkov.com/java-concurrency/java-memory-model.html

main memory 
processor cache 

synchronization establish happens-before relationship between the two threads. => invalidating local processor cache and reloading variables from the main memory so that the entering thread is able to see the latest values. 

