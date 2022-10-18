from argparse import ArgumentError


class Agent:
    def __init__(self):
        pass


class BestFirstAgent:
    def __init__(self):
        pass


class AStarAgent:
    def __init__(self):
        pass


class Board:
    def __init__(self, board):
        self.board = board

    def display(self):
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

    def solve(self):
        if self.board == [[1, 2, 3], [4, 5, 6], [7, 8, "b"]]:
            print("Solved!")

if __name__ == "__main__":
    import argparse
    parser = argparse.ArgumentParser()
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
    except ValueError as e:
        print(e)
