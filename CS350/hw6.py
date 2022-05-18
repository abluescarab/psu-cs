# CS 350: Homework 6
# Due: Week of 6/16
# Name: Alana Gilston

import math

def machine(data, code):
    i = 0
    value = 0
    for instruction in code:
        if i >= len(data):
            raise Exception("Ran out of numbers")
        if instruction == "ADD":
            value += data[i]
            i += 1
        elif instruction == "MUL":
            value += data[i] * data[i+1]
            i += 2
        else:
            raise Exception("Illegal Instruction: " + instruction)
    if i < len(data):
        raise Exception("has leftover numbers")
    return value

###########################################################################
# Problem 1:
#
# I've constructed a new data processing language that I call addmul.
# It is a very simple language, programs in addmul consist of two instructions.
# ADD take a value from the data stream and adds it to the current total
# MUL takes the next two number from the current data stream, multiplies them
# together, and adds them to the total.
# That's it.
#
# Your job is to take the data stream (just a list of numbers), and determine
# the program that will produce the largest value.
#
# example:
# largetstProgram([2,3,5])
# should return
# ["ADD","MUL"]
# because this will return 17
# where ["MUL","ADD"] will return 11
# and ["ADD","ADD","ADD"] will return 10
#
# You can run your program by calling
# machine([2,3,4], ["ADD","MUL"])
# to run your program
#
# you can use
# machine(numbers, largestProgram(numbers))
# to test your algorithm on any list of numbers.
#
# running time:
###########################################################################

def largestProgram(data):
    """
    >>> largestProgram([2,3,5])
    ['ADD', 'MUL']
    >>> largestProgram([1])
    ['ADD']
    >>> largestProgram([1,5])
    ['ADD', 'ADD']
    >>> largestProgram([2,5])
    ['MUL']
    >>> largestProgram([5,5,5,5])
    ['MUL', 'MUL']
    >>> largestProgram([5,5,5,1])
    ['ADD', 'MUL', 'ADD']
    >>> largestProgram([9,7,5,3,5,2,0,1,3,-9,2])
    ['MUL', 'MUL', 'MUL', 'ADD', 'ADD', 'ADD', 'ADD', 'ADD']
    >>> largestProgram([9,7,5,3,5,2,0,2,-1,-9,2])
    ['MUL', 'MUL', 'MUL', 'ADD', 'ADD', 'MUL', 'ADD']
    """
    pass

###########################################################################
# Problem 2
#
# Implemnt the Floyd-Warshal algorithm from class
#
# For example, the adjacency matrix:
#    [ [  0, inf,  -2, inf],
#      [  4,   0,   3, inf],
#      [inf, inf,   0,   2],
#      [inf,  -1, inf,   0] ]
# should give the distance matrix:
#    [ [  0,  -1,  -2,   0],
#      [  4,   0,   2,   4],
#      [  5,   1,   0,   2],
#      [  3,  -1,   1,   0] ]
#
# Running Time: Theta(|V|^3)
###########################################################################

def floyd(g):
    """
    >>> floyd([[0,math.inf,-2,math.inf],[4,0,3,math.inf],[math.inf,math.inf,0,2],[math.inf,-1,math.inf,0]])
    [[0, -1, -2, 0], [4, 0, 2, 4], [5, 1, 0, 2], [3, -1, 1, 0]]
    >>> floyd([[0,math.inf,-2,math.inf,math.inf],[4,0,3,math.inf,math.inf],[math.inf,math.inf,0,2,2],[math.inf,-1,math.inf,0,math.inf],[1,math.inf,math.inf,math.inf,0]])
    [[0, -1, -2, 0, 0], [4, 0, 2, 4, 4], [3, 1, 0, 2, 2], [3, -1, 1, 0, 3], [1, 0, -1, 1, 0]]
    >>> floyd([[0,0,math.inf,math.inf,math.inf],[math.inf,0,7,-5,3],[math.inf,math.inf,0,1,math.inf],[5,math.inf,math.inf,0,math.inf],[-2,math.inf,math.inf,math.inf,0]])
    [[0, 0, 7, -5, 3], [0, 0, 7, -5, 3], [6, 6, 0, 1, 9], [5, 5, 12, 0, 8], [-2, -2, 5, -7, 0]]
    >>> floyd([[0,5,math.inf,10],[math.inf,0,3,math.inf],[math.inf,math.inf,0,1],[math.inf,math.inf,math.inf,0]])
    [[0, 5, 8, 9], [inf, 0, 3, 4], [inf, inf, 0, 1], [inf, inf, inf, 0]]
    >>> floyd([[0,3,math.inf,5],[2,0,math.inf,4],[math.inf,1,0,math.inf],[math.inf,math.inf,2,0]])
    [[0, 3, 7, 5], [2, 0, 6, 4], [3, 1, 0, 5], [5, 3, 2, 0]]
    """
    D = g

    for w in range(len(g)):
        for u in range(len(g)):
            for v in range(len(g)):
                if D[u][w] + D[w][v] < D[u][v]:
                    D[u][v] = D[u][w] + D[w][v]

    return D

###########################################################################
# Problem 3
#
# Congratulations! You know own a factory that cuts rods.
# Customers will pay a certain value for a length of rods
# for example
# rod length:  3  4  5  6   7
# price:       2  3  6  8  11
#
# You just received a rod of length d,
# Write a function to determine the most efficient way to cut the rod
# to maximize the profit.
# You should return the maximum profit you can make.
#
# Running Time:
###########################################################################

def rods_rec(lengths, prices, length, memos):
    if memos[length] is not None:
        return memos[length]

    result = 0

    for i in range(len(lengths)):
        value = 0

        if length >= lengths[i]:
            value = rods_rec(lengths, prices, length - lengths[i], memos) + prices[i]

        result = value if value > result else result

    memos[length] = result
    return result

def rods(weights, prices, d):
    """
    >>> rods([3,4,5,6,7], [2,3,6,8,11], 20)
    30
    >>> rods([1,2,3,4,5,6,7,8], [1,5,8,9,10,17,17,20], 8)
    22
    >>> rods([1,2,3,4,5,6,7,8], [3,5,8,9,10,17,17,20], 8)
    24
    >>> rods([1], [3], 8)
    24
    >>> rods([1,2], [3,9], 0)
    0
    >>> rods([2,4,8], [1,4,10], 32)
    40
    """
    memos = [None] * (d + 1)
    return rods_rec(weights, prices, d, memos)

############################################################################
# Problem 4: Parenthesizing matrices.
#
# This is the same problem as homework 4, problem 3,
# but this time I want you to do it in polynomial time using dynamic programmign.
#
# If I multiply and m*l matrix A by an l*n matrix B
# That will take O(n*l*m) time to compute.
#
# If I include a n*o matrix C in this product
# Then I have the m*o matrix A*B*C.
# This is perfectly well defined, but I have a choice.
# Do I multiply (A*B)*C (giving a running time of n*l*m + n*m*o)
# or do i multiply A*(B*C) (giving a running time of l*m*o + n*l*o)
#
# Since matrix multiplication is associative, We will get the same answer.
#
# So, given a list of dimensions of matrices
# (for example [(n,l), (l,m), (m,o)])
# compute the fastest running time that we can do matrix multiplication in.
#
# example [(3,5), (5,4), (4,7)]
# is 3*5*4 + 3*4*7 = 144
#
# Running time:
############################################################################

def matrixParens(sizes):
    """
    >>> matrixParens([(3,5),(5,4),(4,7)])
    144
    >>> matrixParens([(6,10),(10,30),(30,12),(12,16),(16,9),(9,1),(1,3)])
    1074
    >>> matrixParens([(4,9),(9,2)])
    72
    >>> matrixParens([(10,30),(30,5),(5,60)])
    4500
    >>> matrixParens([(40,20),(20,30),(30,10),(10,30)])
    26000
    >>> matrixParens([(10,20),(20,30),(30,40),(40,30)])
    30000
    """
    pass


if __name__ == "__main__":
    import doctest
    doctest.testmod()

    import utils
    # utils.asrt(rods([3,4,5,6,7], [2,3,6,8,11], 20), 30)
    # utils.asrt(rods([1,2,3,4,5,6,7,8], [1,5,8,9,10,17,17,20], 8), 22)
    # utils.asrt(rods([1,2,3,4,5,6,7,8], [3,5,8,9,10,17,17,20], 8), 24)
    # utils.asrt(rods([1], [3], 8), 24)
    # utils.asrt(rods([1,2], [3,9], 0), 0)
    # utils.asrt(rods([2,4,8], [1,4,10], 32), 40)
