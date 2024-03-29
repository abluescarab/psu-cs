# CS 350: Homework 8
# Due: Week of 6/2
# Name: Alana Gilston

################################################################
# Sudoku
#
# Sudoku is a game played on a 9x9 grid.
# You are given a partially filled in grid
# and there are 3 rules
# 1. no number can appear twice in the same row
# 2. no number can appear twice in the same column
# 3. no number can appear twice in the same 3x3 grid
#
# You need to write a function that takes in a sudoku board
# and return a solved sudoku board.
#
# example:
# +---+---+---+
# |   |26 |7 1|
# |68 | 7 | 9 |
# |19 |  4|5  |
# +---+---+---+
# |82 |1  | 4 |
# |  4|6 2|9  |
# | 5 |  3| 28|
# +---+---+---+
# |  9|3  | 74|
# | 4 | 5 | 36|
# |7 3| 18|   |
# +---+---+---+
#
# Solution:
# +---+---+---+
# |435|269|781|
# |682|571|491|
# |197|834|562|
# +---+---+---+
# |826|195|947|
# |374|682|915|
# |951|743|628|
# +---+---+---+
# |519|326|874|
# |248|957|136|
# |763|418|259|
# +---+---+---+

def can_place(board, row, col, num):
    if num in board[row]:
        return False

    for r in board:
        if r[col] == num:
            return False

    row -= row % 3
    col -= col % 3

    for r in range(0, 3):
        for c in range(0, 3):
            if board[row + r][col + c] == num:
                return False

    return True

def sudoku_rec(board, row, col):
    if row == len(board) - 1 and col == len(board[0]):
        return True

    if col == len(board[0]):
        col = 0
        row += 1

    if board[row][col] != 0:
        return sudoku_rec(board, row, col + 1)

    for i in range(1, 10):
        if can_place(board, row, col, i):
            board[row][col] = i

            if sudoku_rec(board, row, col + 1):
                return True

        board[row][col] = 0

    return False

def sudoku(board):
    """
    >>> board = [ [4,3,0,2,6,0,7,0,1], \
                  [6,8,0,0,7,0,0,9,0], \
                  [0,0,0,0,0,4,5,0,0], \
                  [8,2,0,1,0,0,0,4,0], \
                  [0,0,4,6,0,2,9,0,0], \
                  [0,5,0,0,0,3,0,2,8], \
                  [0,0,9,3,0,0,0,7,4], \
                  [0,4,0,0,5,0,0,3,6], \
                  [7,0,3,0,1,8,0,0,0] ]
    >>> sudoku(board)
    [[4, 3, 5, 2, 6, 9, 7, 8, 1], [6, 8, 2, 5, 7, 1, 4, 9, 3], [1, 9, 7, 8, 3, 4, 5, 6, 2], [8, 2, 6, 1, 9, 5, 3, 4, 7], [3, 7, 4, 6, 8, 2, 9, 1, 5], [9, 5, 1, 7, 4, 3, 6, 2, 8], [5, 1, 9, 3, 2, 6, 8, 7, 4], [2, 4, 8, 9, 5, 7, 1, 3, 6], [7, 6, 3, 4, 1, 8, 2, 5, 9]]
    >>> board = [ [5,0,0,0,0,0,0,3,0], \
                  [0,3,0,0,8,0,1,0,0], \
                  [0,1,0,0,4,3,0,0,0], \
                  [3,0,0,4,6,0,0,0,0], \
                  [0,9,0,0,0,0,0,2,0], \
                  [0,0,0,1,0,9,0,8,3], \
                  [0,4,0,0,0,8,0,1,0], \
                  [0,5,0,9,1,0,0,0,0], \
                  [1,6,0,0,5,2,0,0,0] ]
    >>> sudoku(board)
    [[5, 2, 4, 7, 9, 1, 6, 3, 8], [7, 3, 9, 2, 8, 6, 1, 4, 5], [8, 1, 6, 5, 4, 3, 2, 7, 9], [3, 8, 2, 4, 6, 7, 9, 5, 1], [4, 9, 1, 8, 3, 5, 7, 2, 6], [6, 7, 5, 1, 2, 9, 4, 8, 3], [9, 4, 3, 6, 7, 8, 5, 1, 2], [2, 5, 8, 9, 1, 4, 3, 6, 7], [1, 6, 7, 3, 5, 2, 8, 9, 4]]
    >>> board = [ [0,1,2,4,0,0,9,0,7], \
                  [0,0,0,6,0,0,0,0,2], \
                  [5,0,0,0,9,2,0,0,4], \
                  [0,0,8,0,0,0,0,1,9], \
                  [4,0,5,8,0,0,0,0,0], \
                  [7,0,0,0,0,0,0,0,0], \
                  [0,0,3,1,0,0,8,0,0], \
                  [0,0,0,5,0,4,0,2,0], \
                  [0,0,0,7,0,0,3,0,0] ]
    >>> sudoku(board)
    [[3, 1, 2, 4, 8, 5, 9, 6, 7], [8, 4, 9, 6, 7, 1, 5, 3, 2], [5, 6, 7, 3, 9, 2, 1, 8, 4], [6, 3, 8, 2, 5, 7, 4, 1, 9], [4, 9, 5, 8, 1, 6, 2, 7, 3], [7, 2, 1, 9, 4, 3, 6, 5, 8], [2, 7, 3, 1, 6, 9, 8, 4, 5], [9, 8, 6, 5, 3, 4, 7, 2, 1], [1, 5, 4, 7, 2, 8, 3, 9, 6]]
    """
    sudoku_rec(board, 0, 0)
    return board


if __name__ == "__main__":
    import doctest
    doctest.testmod()
