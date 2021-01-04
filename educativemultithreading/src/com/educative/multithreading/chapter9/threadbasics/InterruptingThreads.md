In the previous code snippets, we wrapped the calls to join and sleep in try/catch blocks. Imagine a situation where if a rogue thread sleeps forever or goes into an infinite loop, it can prevent the spawning thread from moving ahead because of the join call. Java allows us to force such a misbehaved thread to come to its senses by interrupting it. An example appears below.

