# Minotaur Showroom Problems
This is a readme file for the Minotaur Showroom Problems program. The program simulates two problems - Problem 1 and Problem 2.

## Problem 1: Cupcake Labyrinth
The approach used for this problem is similar to the prisoners and warden problem. The goal is to count the number of prisoners in the labyrinth. The following rules are enforced:

### Prisoners (including leader)
* The prisoners can eat the cupcake once and only once. They can only eat the cupcake if there is a cupcake on the plate. If there is no cupcake, they cannot request one or eat one.
* The prisoners cannot eat the cupcake if they have already eaten it.

### The Leader

* If the leader is chosen to visit the labyrinth and there is a cupcake on the plate, and the leader has not yet eaten a cupcake, they will eat the cupcake and increment a counter.
* If there is a cupcake on the plate, but the leader has already eaten a cupcake, they will not eat the cupcake or increment the counter.
* If there is no cupcake on the plate, the leader will increment the counter and request a cupcake to be placed on the plate.


The efficiency of this algorithm depends on how frequently the leader is chosen. If the leader is chosen often and after other prisoners eat a cupcake, the algorithm will run faster.

### Issues Encountered
Initially, I used threads associated with each guest, but I realized this was not viable because threads cannot be started multiple times in Java. I fixed this issue by using a while loop with a boolean and an if statement inside the loop.
From my experiments, the efficiency of this algorithm is good, running for 5 to 7 seconds.

##  Problem 2: Showroom Access
For this problem, the second approach was chosen as it is more efficient and realistic. This approach avoids contention by preventing multiple threads from accessing the room simultaneously. This approach is preferred over the third approach because guests can enjoy other activities while waiting for their turn in the showroom.

### Summary
Problem 2 is simpler than Problem 1 since guests check the sign to enter the room, rather than being chosen by the Minotaur. A loop in the main thread runs until all guests have had their turn in the room.