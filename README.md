ToxicTodo
=========

ToxicTodo is a server based commandline/terminal todo list. The client application can be easily called through use of unix aliases. For example to add a task you'd simply open up a new terminal window and type "todo add school do my homework", this adds a task "do my homework" to the category "school". The client sends the command to add a new task to the remote server where the task is added to the list. The server saves all changes to the todolist in an easily readable xml-file so that you could easily extend the functionality of this application. All write requests on the server are guarded by semaphores, so that it's perfectly save to use two clients at the same time.
The server itself is multi-threaded and can handle connections from multiple clients. All data that is sent between client and server is encrypted with AES-128, so that you don't have to worry about people messing with your todolist.

![Toxic Todo Client](http://w1nter.com/downloads/toxicTodoClient.png)


**Client-Commands:**

What | Command | Example use
------------- | ------------- | ------------- 
Add a new task to an existing category | add [categoryKeyword] [task description] | add school do more work
Add a new category | add [keyword] [category title] | add school School Work
Complete a task | complete task [id from list] | complete task 2
Remove a task | remove task [id from list] | remove task 2
Remove a category | remove category [categoryKeyword] | remove category school

**Server-Commands:**
*stop / exit* - to shutdown the server application.


**Overview:**


![Toxic Todo Diagram](http://w1nter.com/downloads/toxicTodoDiag.png)
