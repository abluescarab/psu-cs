from board import Board


class Node:
    def __init__(self, board: Board, parent, action):
        self.state = board.result(parent.state, action)
        self.parent = parent
        self.action = action
        self.path_cost = parent.path_cost + board.step_cost(parent.state, action)
