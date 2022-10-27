# TODO: come up with custom heuristic
# TODO: calculate heuristic for each


from collections import deque
from board import Board, Heuristic
from node import Node, NodeQueue
from priority_queue import PriorityQueue


def solution(node: Node):
    current = node
    path = []

    while current:
        path.insert(0, current.state)
        current = current.parent

    return path


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

def best_first(board: Board, heuristic: Heuristic, max_steps=-1):
    node = Node(board.initial_state)
    frontier = PriorityQueue(node)
    explored = set()
    step = 0

    while frontier and ((max_steps == -1) or (step < max_steps)):
        node = frontier.pop()

        if board.goal_test(node.state):
            return solution(node)

        explored.add(node.state)

        for action in board.actions(node.state):
            child = Node(board.result(node.state, action), node.path_cost + 1,
                         node)

            if child.state not in explored and child not in frontier:
                frontier.push(child)
            elif child in frontier:
                if board.path_cost(child.state, heuristic) < frontier[child][0]:
                    frontier.update(child)

        step += 1

    return None


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

def a_star(board: Board, heuristic: Heuristic, max_steps=-1):
    node = Node(board.initial_state, 0)
    frontier = NodeQueue(node)
    explored = []
    step = 0

    while frontier and ((max_steps == -1) or (step < max_steps)):
        node = frontier.pop()

        if board.goal_test(node.state):
            return solution(node)

        if node.state not in explored:
            explored.append(node.state)

        for action in board.actions(node.state):
            child = Node(
                board.result(node.state, action),
                node.path_cost + 1,
                node
            )

            if child.state not in explored and child.state not in frontier:
                frontier.push(child)
            elif child.state in frontier and \
                frontier.adjusted_cost(child) > child.path_cost:
                frontier.update(child)

        step += 1

    return None

def start(values):
    input = (tuple(values[0:3]), tuple(values[3:6]), tuple(values[6:9]))
    board = Board(input)
    max_steps = 2000

    if not board.is_solveable():
        print("Configuration cannot be solved.")
        return

    for i in range(2):
        for j in range(3):
            type_str = ""

            if i == 0:
                type_str = "Best first "
                func = best_first
            else:
                type_str = "A* "
                func = a_star

            if j == 0:
                type_str += "(Misplaced):"
            elif j == 1:
                type_str += "(Manhattan):"
            else:
                type_str += "(Custom):"

            solution = func(board, j, max_steps)
            print(type_str)

            if not solution[0]:
                print(f"Could not find solution in {max_steps} steps.")
            else:
                for i in range(len(solution)):
                    print(f"({i + 1}) ", end="")

                    for row in solution[i]:
                        print(" ".join(row), end=" ")

                    if i < len(solution) - 1 and ((i + 1) % 5 != 0):
                        print("-> ", end="")
                    else:
                        print()

            print()


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
    values = [v for v in vars(args).values()]

    if values.count("b") != 1:
        raise ValueError("Invalid arguments: One square must be \"b\" for blank")

    if any(values.count(i) > 1 for i in values):
        raise ValueError("Invalid arguments: Cannot contain duplicate values")

    try:
        start(values)
    except ValueError as e:
        print(e)
