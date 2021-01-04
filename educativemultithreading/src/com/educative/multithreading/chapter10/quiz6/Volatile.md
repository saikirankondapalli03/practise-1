Remember in the absence of synchronization, the compiler, processor or the runtime are free to take liberty in reordering operations. There's no guarantee that the values written to myvalue and done by thread2 are visible to thread1 in the same order or visible at all. The updated values may reside in the processor cache and not be immediately propagated to main memory from where thread1 reads. It's also possible that thread1 sees updated value for one of the variables and not for another. Synchronization is not only about mutual exclusion but also about memory visibility.



a prime example of insufficient synchronization. The reader thread may still see stale values of the shared variables as they may be updated by writer but only in a register or cache.


Volatile:
When a field is declared volatile, it is an indication to the compiler and the runtime that the field is shared and operations on it shouldn't be reordered. Volatile variables aren't cached in registers or caches where they are hidden from other processors. Note that variables declared volatile when read always return the most recent write by any thread.

Furthermore volatile variables only guarantee memory visibility but not atomicity.


Where does volatile fail ?

-> Most common use of volatile variables is as a interruption, completion or status flag

-> When writes to a variables don't depend on its current value e.g. a counter is not suitable to be declared volatile as its next value depends on its current value.



