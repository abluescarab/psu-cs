from collections import deque


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
            value (_type_): value to search for

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


class Node:
    def __init__(self, board: Board, parent, action):
        self.state = board.result(parent.state, action)
        self.parent = parent
        self.action = action
        self.path_cost = parent.path_cost + board.step_cost(parent.state, action)


# func Tree-Search(problem) returns solution or failure
#   init frontier using init state of problem
#   loop do
#     if frontier empty return failure
#     choose leaf & remove from frontier
#     if node contains goal state return solution
#     expand node, add resulting nodes to frontier

# func Graph-Search(problem) returns solution or failure
#   init frontier
#   init explored set to empty
#   loop do
#     if frontier empty return failure
#     choose leaf & remove from frontier
#     if node contains goal return solution
#     add node to explored state
#     expand node, add resulting nodes to frontier (only if not in frontier or
#       explored set)

def best_first(board: Board, heuristic_value):
    # TODO
    frontier = deque()
    explored = {}
    moves = board.connected("b")

    print(moves)

    while True:
        if len(frontier) == 0:
            return False


# func Uniform-Cost-Search(problem) returns solution or failure
#   node <- node w/ State = problem.Init-State, Path-Cost = 0
#   frontier <- priority queue ordered by Path-Cost, node the only element
#   explored <- empty set
#   loop do
#     if Empty?(frontier) return failure
#     node <- Pop(frontier) # chooses lowest-cost node
#     if problem.Goal-Test(node.State) return Solution(node)
#     add node.State to explored
#     for each action in problem.Actions(node.State) do
#       child <- Child-Node(problem, node, action)
#       if child.State not in explored or frontier
#         frontier <- Insert(child, frontier)
#       else if child.State in frontier with higher Path-Cost
#         replace frontier node with child

def a_star(board: Board, heuristic_value):
    # TODO
    pass


if __name__ == "__main__":
    import argparse
    parser = argparse.ArgumentParser(epilog="The blank square should be "
                                     "denoted with \"b\".")
    parser.add_argument("top_left")
    parser.add_argument("top_middle")
    parser.add_argument("top_right")
    parser.add_argument("center_left")
    parser.add_argument("center_middle")
    parser.add_argument("center_right")
    parser.add_argument("bottom_left")
    parser.add_argument("bottom_middle")
    parser.add_argument("bottom_right")

    args = parser.parse_args()

    try:
        values = [int(v) if v.isdigit() else v for v in vars(args).values()]

        if values.count("b") != 1:
            raise ValueError("Invalid arguments: One square must be \"b\" for blank")

        input = [values[0:3], values[3:6], values[6:9]]
        board = Board(input)

        board.display()
        best_first(board, [])
    except ValueError as e:
        print(e)
