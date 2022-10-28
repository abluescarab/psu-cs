# TODO: come up with custom heuristic
# TODO: calculate heuristic for each


from board import Board, Heuristic
from node import Node, NodeQueue


def solution(node: Node, board: Board, heuristic: Heuristic):
    current = node
    path = []
    cost = 0

    while current:
        cost += board.path_cost(current.state, heuristic)
        path.insert(0, current.state)
        current = current.parent

    return (cost, path)


def uniform_search(board: Board, heuristic: Heuristic, use_heuristic, max_steps=-1):
    node = Node(board.initial_state)
    frontier = NodeQueue(node)
    explored = set()
    step = 0

    while frontier and ((max_steps == -1) or (step < max_steps)):
        node = frontier.pop()

        if board.goal_test(node.state):
            return solution(node, board, heuristic)

        explored.add(node.state)

        for child in node.expand(board):
            if child.state not in explored and child not in frontier:
                frontier.push(child)
            elif child in frontier:
                # only difference: whether to use heuristic in path calculation
                cost = board.path_cost(child.state,
                        heuristic if use_heuristic else None)

                if cost < frontier[child][0]:
                    frontier.update(child)

        step += 1

    return (0, None)


def start(values):
    input = (tuple(values[0:3]), tuple(values[3:6]), tuple(values[6:9]))
    board = Board(input)
    max_steps = 10000

    if not board.is_solveable():
        print("Configuration cannot be solved.")
        return

    for i in range(2):
        for j in range(2):
            type_str = ""

            if i == 0:
                type_str = "Best first "
                use_heuristic = False
            else:
                type_str = "A* "
                use_heuristic = True

            if j == 0:
                type_str += "(Misplaced):"
            elif j == 1:
                type_str += "(Manhattan):"
            else:
                type_str += "(Custom):"

            solution = uniform_search(board, Heuristic(j), use_heuristic, max_steps)
            print(type_str)

            if not solution[1]:
                print(f"Could not find solution in {max_steps} steps.")
            else:
                for n in range(len(solution[1])):
                    print(f"({n + 1}) ", end="")

                    for row in solution[1][n]:
                        print(" ".join(row), end=" ")

                    if n < len(solution[1]) - 1 and ((n + 1) % 5 != 0):
                        print("-> ", end="")
                    else:
                        print()

            print(f"Heuristic cost: {solution[0]}")
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
