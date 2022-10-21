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

    def __find_blank(self, state):
        """
        Find the blank square in the grid.

        Args:
            state: state to check

        Returns:
            tuple: (row, column) of blank in the grid
        """
        for r in range(len(state)):
            if "b" in state[r]:
                return (r, state[r].index("b"))

        return (-1, -1)

    def __copy_state(self, state):
        new_state = []

        for row in state:
            new_state.append(row.copy())

        return new_state

    def display(self, state):
        """
        Display the board in the command line.
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
        Calculate the step cost to apply an action to the current state.

        Args:
            state: the current state
            action: action to take
        """
        # TODO
        pass

    def result(self, state, action):
        """
        Calculate the result of taking an action in the given state.

        Args:
            state: state to take action in
            action: action to take
        """
        row, col = self.__find_blank(state)
        new_state = self.__copy_state(state)

        if action == Action.left:
            new_state[row][col], new_state[row][col - 1] = \
                new_state[row][col - 1], new_state[row][col]
        elif action == Action.down:
            new_state[row][col], new_state[row + 1][col] = \
                new_state[row + 1][col], new_state[row][col]
        elif action == Action.right:
            new_state[row][col], new_state[row][col + 1] = \
                new_state[row][col + 1], new_state[row][col]
        elif action == Action.up:
            new_state[row][col], new_state[row - 1][col] = \
                new_state[row - 1][col], new_state[row][col]

        return new_state

    def actions(self, state):
        """
        Find the possible actions that the blank square can do.

        Args:
            state: state to search in

        Returns:
            list: list of possible actions
        """
        actions = []
        row, col = self.__find_blank(state)
        rows = len(state)
        cols = len(state[0])

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
        Test if the given state is the same as the goal state.
        """
        return state == self.goal_state
