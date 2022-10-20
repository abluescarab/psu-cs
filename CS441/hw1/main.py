from collections import deque
from enum import Enum

from board import Board


class Node:
    def __init__(self, state, path_cost):
        self.state = state
        self.path_cost = path_cost


class Heuristic(Enum):
    Manhattan = 0
    Misplaced = 1
    Custom = 2


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

def best_first(board: Board, heuristic: Heuristic):
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

def a_star(board: Board, heuristic: Heuristic):
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
