# ATP-Project-Final

C# project - Advanced Topic in Programming course, 2nd semester, 2nd year.

This project was done as part of the “Advanced Topic in Programming course”, The aim of the project is to build a maze computer game which is based on algorithms.

The project is divided into 3 tasks:

Task A: You were to translate a pseudo-code of an object-oriented programming algorithm by maintaining Solid principles.
We used a pseudo-code of the PRIME algorithm to implement the maze. Mark the lines that depend on the problem. These lines defined for us the functionality required by the problem definition. We will define this functionality in a special interface for the general problem. Later concrete classes will implement this interface and thus define different specific problems. Keeping this rule will allow the algorithm to work against the type of the interface instead of against the type of a specific class. The polymorphism feature will allow us to replace different implementations of the problem without having to change anything in the code of the algorithm

After building the maze we are interested in solving the maze using 3 different algorithms :

Breadth-first search-https://en.wikipedia.org/wiki/Breadth-first_search.


Depth-first search-https://en.wikipedia.org/wiki/Depth-first_search.

Best-first search-https://en.wikipedia.org/wiki/Best-first_search.

Task B:
Later in the project we created a pair of servers that provide service to a multitude of clients. The first server's role is to generate mazes on demand. The second server's role is to solve mazes. When a client connects to a server that creates mazes, it will send it the parameters to create the maze and receive back a maze object. When a client connects to a server that solves mazes, it will send it an existing maze and receive back from the server a solution to the maze. In order to shorten the communication time, we will have to compress the information that passes between them before sending. The receiving party will open the compression and enjoy the information. Another thing we do on the server side is to save solutions that we have already calculated, so that if we are asked to solve a problem that has already been solved, we will pull the solution from the file instead of recalculating it.

Task C:
In this part I wrote the GUI of the project using the MVVM architecture.
When I wrote the program using a GUI, I made sure to correctly divide it into classes and that the code would be generic so that if we wanted to "remove" the maze and use a different game based on the same principles and algorithms we used, we wouldn't have to redo everything.
