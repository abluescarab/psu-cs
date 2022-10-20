class Board:
    def __init__(self, board):
        self.board = board

    def display(self):
        """
        Displays the board in the command line.
        """
        print(f"+{'-' * 7}+")

        for i in range(len(self.board)):
            print("| ", end="")

            for j in range(len(self.board[i])):
                if type(self.board[i][j]) == int:
                    print(self.board[i][j], end=" ")
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

    def goal_test(self):
        """
        Tests if the board is in its goal state.
        """
        if self.board == [[1, 2, 3], [4, 5, 6], [7, 8, "b"]]:
            return True

        return False

    def connected(self, value):
        """
        Finds the squares connected to the given value.

        Args:
            value: value to search for

        Returns:
            list: list of connected squares
        """
        connected = []
        rows = len(self.board)
        cols = len(self.board[0])
        row = -1
        col = -1

        for r in range(len(self.board)):
            if value in self.board[r]:
                row = r
                col = self.board[r].index(value)
                break

        if row < 0 or row > rows or col < 0 or col > cols:
            return None

        if row - 1 >= 0: # top node
            connected.append(self.board[row - 1][col])

        if col - 1 >= 0: # left node
            connected.append(self.board[row][col - 1])

        if row + 1 < rows: # bottom node
            connected.append(self.board[row + 1][col])

        if col + 1 < cols: # right node
            connected.append(self.board[row][col + 1])

        return connected
