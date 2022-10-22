from enum import Enum


class Action(Enum):
    left = 0
    down = 1
    right = 2
    up = 3


class Heuristic(Enum):
    Misplaced = 0
    Manhattan = 1
    Custom = 2


class Board:
    def __init__(self, board):
        self.initial_state = board
        self.goal_state = [["1", "2", "3"],
                           ["4", "5", "6"],
                           ["7", "8", "b"]]

    def _find_blank(self, state):
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

    def _copy_state(self, state):
        """
        Copy a state to a new array.

        Args:
            state: state to copy

        Returns:
            list: copy of state
        """
        new_state = []

        for row in state:
            new_state.append(row.copy())

        return new_state

    def _calculate_misplaced(self, state):
        """
        Calculate the misplaced tiles heuristic.

        Args:
            state: state to check against goal

        Returns:
            int: number of misplaced tiles
        """
        misplaced = set()

        for i in range(len(state)):
            for j in range(len(state[i])):
                if state[i][j] != "b" and state[i][j] != self.goal_state[i][j]:
                    misplaced.add(state[i][j])

        return len(misplaced)

    def _calculate_manhattan(self, state):
        """
        Calculate the Manhattan distance heuristic.

        Args:
            state: state to check against goal

        Returns:
            int: sum of Manhattan distances
        """
        sum = 0
        goal_dict = {self.goal_state[x][y]: (x, y)
            for x in range(len(self.goal_state))
            for y in range(len(self.goal_state[x]))
            if self.goal_state[x][y] != "b"}

        for i in range(len(state)):
            for j in range(len(state[i])):
                if state[i][j] == "b":
                    continue

                x, y = goal_dict[state[i][j]]
                sum += abs(x - i) + abs(y - j)

        return sum

    def _calculate_custom(self, state):
        pass

    def is_solveable(self):
        seen = []
        inversions = 0

        for i in range(len(self.initial_state)):
            for j in range(len(self.initial_state[i])):
                # print(seen, self.initial_state[i][j])
                if any(self.initial_state[i][j] > n for n in seen):
                    inversions += 1

                seen.append(self.initial_state[i][j])

        # print(inversions)
        return inversions % 2 == 0

    def display(self, state):
        """
        Display the board in the command line.
        """
        print(f"+{'-' * 7}+")

        for i in range(len(state)):
            print("| ", end="")

            for j in range(len(state[i])):
                print(" " if state[i][j] == "b" else state[i][j], end=" ")

            print("|")

        print(f"+{'-' * 7}+")

    def result(self, state, action: Action):
        """
        Calculate the result of taking an action in the given state.

        Args:
            state: state to take action in
            action: action to take

        Returns:
            state: state after action taken
        """
        row, col = self._find_blank(state)
        new_state = self._copy_state(state)

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
        row, col = self._find_blank(state)
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

    def remaining_cost(self, state, heuristic: Heuristic):
        """
        Calculate the remaining cost on a state.

        Args:
            state: state to perform action on
            heuristic: heuristic to calculate cost

        Returns:
            int: remaining cost with heuristic
        """
        func = self._calculate_custom

        if heuristic == Heuristic.Misplaced:
            func = self._calculate_misplaced
        elif heuristic == Heuristic.Manhattan:
            func = self._calculate_manhattan

        return func(state)

    def goal_test(self, state):
        """
        Test if the given state is the same as the goal state.
        """
        return state == self.goal_state
