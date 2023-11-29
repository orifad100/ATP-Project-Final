# ATP-Project-Final 

  

Java project - Advanced Topics in Programming course, 2nd semester, 2nd year. 

  

This project was done as part of Advanced Topics in Programming course, The aim of the project is to build a computer game that consists of a maze which we solve with differents algorithms. 

  

The project is divided into three parts: 

  

Part A: We needed to translate a pseudo-code of an object-oriented programming algorithm by maintaining Solid principles. 

We used a pseudo-code of the PRIME algorithm to implement the maze. 
 To mark the lines that depend on the problem.  
These lines defined for us the functionality required by the problem definition.  
We defined this functionality with a special interface for the general problem.  
Concrete classes  implemented this interface and thus defined different specific problems.  
Keeping this rule allowed the algorithms to work against the type of the interface instead of against the type of a specific class.  
The polymorphism feature allowed us to replace different implementations of the problem without having to change anything in the code of the algorithm. 

  

After building the maze we solved the maze using three differents algorithms : 

  

Breadth-first search-https://en.wikipedia.org/wiki/Breadth-first_search. 

  

  

Depth-first search-https://en.wikipedia.org/wiki/Depth-first_search. 

  

Best-first search-https://en.wikipedia.org/wiki/Best-first_search. 

  

Task B: 

Later in the project we created a pair of servers that provided service to multiple clients. The first server's task is to generate mazes. The second server's task is to solve mazes. When a client connects to a server that creates mazes, it will send it the parameters to create the maze and receive a maze object. When a client connects to a server that solves mazes, it will send it an existing maze and receives a solution to the maze. In order to shorten the communication time, we compressed the information that passes between them before sending. The receiving side will open the compression and enjoy the information. Another thing we did on the server side is to save solutions that we had already calculated, so that if we are asked to solve a problem that has already been solved, we will pull the solution from the file instead of recalculating it. 

  

Task C: 

In this part we implemented the GUI of the project using the MVVM architecture. 
When we wrote the program using a GUI, We made sure to correctly divide it into classes and make the code generic so that if we wanted to "remove" the maze and use a different game based on the same principles and algorithms we used wouldn't have to redo anything.
