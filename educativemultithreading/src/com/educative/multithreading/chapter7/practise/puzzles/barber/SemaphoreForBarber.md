First of all, we need to understand the different state transitions for this problem before we devise a solution. Let's look at them piecemeal:

A customer enters the shop and if all N chairs are occupied, he leaves. This hints at maintaining a count of the waiting customers.

If any of the N chairs is free, the customer takes up the chair to wait for his turn. Note this translates to using a semaphore on which threads that have found a free chair wait on before being called in by the barber for a haircut.

If a customer enters the shop and the barber is asleep it implies there are no customers in the shop. The just-entered customer thread wakes up the barber thread. This sounds like using a signaling construct to wake up the barber thread.