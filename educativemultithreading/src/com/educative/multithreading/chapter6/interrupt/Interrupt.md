Note that there are two methods to check for the interrupt status of a thread.
One is the static method
 Thread.interrupted() 
other is
 Thread.currentThread().isInterrupted(). 
 
The important difference between the two is that the static method would return the interrupt status and also clear it at the same time. On line 22 we deliberately call the object method first followed by the static method. If we reverse the ordering of the two method calls on line 22, the output for the line would be true and false, instead of true and true.