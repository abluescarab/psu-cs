# CS 441 - Artificial Intelligence
## 1.10
Consider a 3x3 grid for the vacuum world environment. The agent can go up, down, left right, suck or do nothing. Randomize the starting location of the robot, the number of rooms with dirt piles (let’s say in separate cases: 1, 3 and 5 piles initially, respectively), as well as the locations of the dirt piles. Record and compare the performance score (e.g. total number of moves to clean the environment – you are free to make up your own performance score/evaluation standard). You should cap the number of steps in your simulations reasonably to avoid infinite loops. Report results for the following types of agents:

1. A simple reflex agent (in this case define a “rule table” based on the location/dirt present – just as in the book); in this case you can just make up a rule for where the robot moves next, according to its current location. Please note that the “where to move next” rule is only based on the current percept and should be fixed by the rule table (which is to say we don’t actively “learn” a movement strategy).
2. A randomized agent (movement and suck/no suck are randomized). Describe explicitly how you randomize the agent.
3. Apply Murphy’s law with the same environment for a reflex agent. Murphy’s law: 25% of the time the suck action fails to clean the floor if it is dirty and deposits dirt onto the floor if the floor is clean; suppose also that the “dirt sensor” give the wrong answer 10% of the time.
4. Apply Murphy’s law conditions (as in iii) for a randomized agent.

In summary: in the end, you should have comparative performance measures for each of the (4) agents, for cases of 1, 3 and 5 initial dirt piles (12 cases in all), respectively; run each experiment 100 or so times.

## HW1
Implement best-first search and A\* search on a sliding puzzle using three different heuristics.
