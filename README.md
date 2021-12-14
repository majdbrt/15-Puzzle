# 15-Puzzle
A multi-threaded Java App with a GUI created using JavaFX.


![Preview Screenshot 1](https://user-images.githubusercontent.com/54665027/145910825-3fe89fe5-1397-45d3-8e47-ae30ba974765.png)


Game Description:
The game starts with a welcome scene that pauses for 2.5 seconds before it opens the gameplay scene.
Once the gameplay scene is open, the user can click on the grid to play the game and change the order of the numbered buttons. The game is won when the buttons are in the following order: "Empty", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15.

There are 15 different possible puzzle in this game with varying level of difficulty. Clicking the "New Puzzle" button will randomely choose a puzzle out of the 15 available puzzles.

When the user succefully solves the puzzle, the grid buttons will disappear and a congraulating message will show asking the user to either choose a new puzzle or to exit the game.

The "AI H1" and "AI H2" buttons are two AI solvers that can attempt to solve the puzzle using:
* A* search with heuristic 1 (misplaced tiles)
* A* search with heuristic 2 (manhattan)
When either of these buttons are clicked, the AI solver will run in the background without interrupting the GUI JavaFX Application Thread. This is accompliched by the use of multi-threading and ExecutorService. 
Once the AI solver is done and the solution is generated, the "See The Solution" button will be enabled and when clicked, the grid buttons will automatically move with a 0.5 sec pause. This will animate the solution for only 10 forward steps. 
If the puzzle is solved in these 10 steps, the gmae will end and the grid will disappear prompting the user to either pick new puzzle or exit the game.
If the puzzle is not solved, the user can continue changing the grid in an attempt to solve the puzzle or they can click on the AI solver buttons to solve the next 10 steps of the game.


This project uses extensive knowledge in the followig topics:
* Apache Maven
* JavaFX and CSS
* Multi-threading management using ExecutorService
* Data Structures such as queues and 2D matrices
* Object-oriented design and implementation and the use of clean coding standards

