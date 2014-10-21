ToxicTodo
=========

ToxicTodo is a server based todo list. It has a nice, mac-optimized GUI as well as a fully-featured command line mode. ToxicTodo uses a custom category-tasks model that allows you to make special categories for certain kinds of task.  
The server application is intended to be run on a dedicated server (e.g. some cheap VPS), so that you have a synchronised todo list across all your computers. However if you just want to quickly test ToxicTodo or if you’re only using one computer it also comes with a integrated server. So you can just double-click the ToxicTodo.jar and you’re ready to go :).

##Graphical mode
![Toxic Todo GUI](https://raw.githubusercontent.com/aerobless/ToxicTodo/master/ToxicTodo_GUI.png)

###Features:
 * Create a task containing a description and meta data such as task priority and your location (based on IP).
 * Create daily/weekly/monthly tasks, that can be completed multiple times.
 * Add a hyperlink to as task, so you can launch a website associated with it right from the app.
 * Dynamic category that features daily tasks that haven't been completed yet.
 * Remove a task without logging.
 * Complete a task, logging the complition data, location and other meta data.
 * View Statistics (currently only amount of completed tasks, work in progress).
 * Add a category with a description, keyword and one of many awesomeFont icons.
 * Edit or delete categories.
 * Search a task in a specific category or in all categories.
 * Change the settings (internal or external server, password, port etc.)
 * Update ToxicTodo to the latest build on Jenkins (BEWARE: builds on Jenkins may not be stable, use at your own risk)

##Commandline mode
![Toxic Todo Client](https://raw.githubusercontent.com/aerobless/ToxicTodo/master/ToxicTodo_CLI.png)

What | Command | Example use
------------- | ------------- | ------------- 
Add a new task to an existing category | add [categoryKeyword] [task description] | add school do more work
Add a new category | add [keyword] [category title] | add school School Work
Show all categories | categories | categories
Complete a task | complete task [id from list] **OR** complete [id from list] | complete task 2 **OR** complete 2
Remove a task | remove task [id from list] **OR** remove [id from list] | remove task 2 **OR** remove 2
Remove a category | remove category [categoryKeyword] | remove category school
Show about info | about **OR** identify | about **OR** identify
Update to the latest version from the CI server | update | update

##Server
![Toxic Todo Diagram](http://w1nter.com/downloads/toxicTodoDiag.png)

**Server-Commands:**
*stop / exit* - to shutdown the server application.


##Download
**ToxicTodo Server .JAR download:** [download.theowinter.ch/ToxicTodoServer.jar](http://download.theowinter.ch/ToxicTodoServer.jar)  
**ToxicTodo Client .JAR download:** [download.theowinter.ch/ToxicTodoClient.jar](http://download.theowinter.ch/ToxicTodoClient.jar)  


##License
> Copyright (c) 2014 Theo Winter

> Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

> The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

> THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
