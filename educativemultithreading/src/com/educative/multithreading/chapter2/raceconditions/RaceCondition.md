What is a race condition

A race condition occurs when the correctness of a computation depends on the relative timing or interleaving of multiple threads by the runtime; in other words, getting the right answer relies on lucky timing. Two scenarios which can lead to a race condition are:

check-then-act: Usually the value of a variable is checked and then an action is taken. Without proper synchronization, the resulting code can have a race condition. An example is below:
         Object myObject = null;
        if (myObject == null) {
            myObject = new Object();
        }
read-modify-write: For instance, whenever a counter variable is increment, the old state of the counter undergoes a transformation to a new state. Without proper synchronization guards, the counter increment operation can become a race condition.