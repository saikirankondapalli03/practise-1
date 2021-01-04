When the Collections Framework was introduced in JDK 1.2, it didn't come with collections that were synchronized. However, to cater for multithreaded scenarios, the framework provided static methods to wrap vanilla collections in thread-safe wrapper objects. These thread-safe wrapper objects came to be known as wrapper collections

If you need thread safety, the concurrent collections generally provide much better performance than synchronized (wrapper) collections.

The concurrent collections use a variety of ways to achieve thread-safety while avoiding traditional synchronization for better performance

Copy on Write: Concurrent collections utilizing this scheme are suitable for read-heavy use cases. An immutable copy is created of the backing collection and whenever a write operation is attempted, the copy is discarded and a new copy with the change is created. Reads of the collection don’t require any synchronization, though synchronization is needed briefly when the new array is being created. Examples include CopyOnWriteArrayList and CopyOnWriteArraySet


Compare and Swap:
examples in java : ConcurrentLinkedQueue and ConcurrentSkipListMap

Lock:
a) LinkedBlockingQueue
b) ConcurrentHashMap
c) BlockingQueue

