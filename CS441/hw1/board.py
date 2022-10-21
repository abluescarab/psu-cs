from enum import Enum
from node import Node


class Action(Enum):
    left = 0
    down = 1
    right = 2
    up = 3


class Board:
    def __init__(self, board):
        self.initial_state = board
        self.goal_state = [[1, 2, 3],
                           [4, 5, 6],
                           [7, 8, "b"]]

    def display(self, state):
        """
        Displays the board in the command line.
        """
        print(f"+{'-' * 7}+")

        for i in range(len(state)):
            print("| ", end="")

            for j in range(len(state[i])):
                if type(state[i][j]) == int:
                    print(state[i][j], end=" ")
                else:
                    print(" ", end=" ")

            print("|")

        print(f"+{'-' * 7}+")

    def step_cost(self, state, action):
        """
        Calculates the step cost to apply an action to the current state.

        Args:
            state: the current state
            action: action to take
        """
        # TODO
        pass

    def result(self, state, action):
        """
        Calculates the result of taking an action in the given state.

        Args:
            state: state to take action in
            action: action to take
        """
        # TODO
        pass

    def actions(self, state):
        """
        Finds the possible actions that the blank square can do.

        Args:
            state: state to search in

        Returns:
            list: list of possible actions
        """
        actions = []
        rows = len(state)
        cols = len(state[0])
        row = -1
        col = -1

        for r in range(len(state)):
            if "b" in state[r]:
                row = r
                col = state[r].index("b")
                break

        if row < 0 or row > rows or col < 0 or col > cols:
            return None

        if row - 1 >= 0: # top node
            actions.append(Action.up)

        if col - 1 >= 0: # left node
            actions.append(Action.left)

        if row + 1 < rows: # bottom node
            actions.append(Action.down)

        if col + 1 < cols: # right node
            actions.append(Action.right)

        return actions

    def goal_test(self, state):
        """
        Tests if the given state is the same as the goal state.
        """
        return state == self.goal_state
