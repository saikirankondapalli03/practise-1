A Task goes through the various stages of its life 

a) Created
b) Submitted
c) Started
d) Completed

Future Interface manages a task life cycle as well as retrieve results from it. 

Future get method:

The get method is a blocking call. It'll block till the task completes. We can also write a polling version, where we poll periodically to check if the task is complete or not. Future also allows us to cancel tasks. If a task has been submitted but not yet executed, then it'll be cancelled. However, if a task is currently running, then it may or may not be cancellable. We'll discuss cancelling tasks in detail in future lessons.


Future methods:
1. cancel it 
2. poll it and check for completion using ==> isDone()

