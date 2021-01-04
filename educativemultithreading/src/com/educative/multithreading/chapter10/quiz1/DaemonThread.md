Since we marked the innerThread as a daemon thead, when the main thread exits, the JVM also kills any threads marked daemon, therefore if run on the console, only a few messages will be printed by the innerThread. The innerThread will be killed by JVM before it can run to completion.

