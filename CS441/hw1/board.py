from enum import Enum


class Action(Enum):
    Left = 0
    Down = 1
    Right = 2
    Up = 3


class Heuristic(Enum):
    Misplaced = 0
    Manhattan = 1
    Custom = 2


class Board:
    def __init__(self, board):
        self.initial_state = board
        self.goal_state = (("1", "2", "3"),
                           ("4", "5", "6"),
                           ("7", "8", "b"))

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
        flat = [n for row in self.initial_state for n in row]
        inversions = 0

        for i in range(len(flat)):
            for j in range(i + 1, len(flat)):
                if (flat[i] > flat[j]) and flat[i] != "b" and flat[j] != "b":
                    inversions += 1

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
        new_state = [[item for item in row] for row in state]

        if action == Action.Left:
            new_state[row][col], new_state[row][col - 1] = \
                new_state[row][col - 1], new_state[row][col]
        elif action == Action.Down:
            new_state[row][col], new_state[row + 1][col] = \
                new_state[row + 1][col], new_state[row][col]
        elif action == Action.Right:
            new_state[row][col], new_state[row][col + 1] = \
                new_state[row][col + 1], new_state[row][col]
        elif action == Action.Up:
            new_state[row][col], new_state[row - 1][col] = \
                new_state[row - 1][col], new_state[row][col]

        return tuple(tuple(item for item in row) for row in new_state)

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
            actions.append(Action.Up)

        if col - 1 >= 0: # left node
            actions.append(Action.Left)

        if row + 1 < rows: # bottom node
            actions.append(Action.Down)

        if col + 1 < cols: # right node
            actions.append(Action.Right)

        return actions

    def path_cost(self, state, heuristic: Heuristic):
        """
        Calculate the path cost on a state.

        Args:
            state: state to perform action on
            heuristic: heuristic to calculate cost

        Returns:
            int: path cost with heuristic
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
