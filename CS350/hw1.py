
# CS 350: Homework 1
# Due: Week of 4/4
# Name: Alana Gilston

# This homework is largely review, and to make sure you have a working version of python.

############################################################################
#
# Problem 1
# Find the largest two elements in a list.
# Return your answer in a tuple as (largest, secondLargest)
#
# Running Time: O(n)
############################################################################

def largest2(l):
    """
    >>> largest2([1, 2, 3, 4, 5, 6, 7])
    (7, 6)
    >>> largest2([7, 6, 5, 4, 3, 2, 1])
    (7, 6)
    """
    largest = l[0]
    second_largest = l[1]

    for i in range(len(l)):
        if l[i] > largest:
            second_largest = largest
            largest = l[i]

    return (largest, second_largest)

############################################################################
#
# Problem 2
# Reverse a list in place,
# and returned the reversed list.
#
# Running Time: O(n)
############################################################################

def reverse(l):
    """
    >>> l = [1, 2, 3, 4, 5]
    >>> reverse(l)
    [5, 4, 3, 2, 1]
    >>> l
    [5, 4, 3, 2, 1]
    """
    length = len(l)

    for i in range(length - 1, -1, -1):
        l.append(l.pop(i))

    return l

############################################################################
#
# Problem 3
# Compute the transpose of a matrix in place.
#
# What is the input size measuring?
#       It is measuring the number of rows.
# Running Time: O(2n^2)
############################################################################

def transpose(m):
    """
    >>> m = [[1,2,3],[4,5,6],[7,8,9]]
    >>> transpose(m)
    [[1, 4, 7], [2, 5, 8], [3, 6, 9]]
    >>> m
    [[1, 4, 7], [2, 5, 8], [3, 6, 9]]
    """
    temp = []

    # create a deep copy of m
    for i in range(len(m)):
        temp.append([])
        for j in range(len(m[i])):
            temp[i].append(m[i][j])

    for i in range(len(m)):
        for j in range(len(m[i])):
            m[i][j] = temp[j][i]

    return m

############################################################################
#
# Problem 4
# Given a list of points, return the distance between the two closest points.
# The distance between two points (x1,y1) and (x2,y2) is:
# d = sqrt((x2-x1)^2 + (y2-y1)^2)
#
# Running Time: O(n^2)
############################################################################

def pointDist(points):
    """
    >>> pointDist([(1,1), (4,5), (13,6)])
    5
    """
    import math
    smallest = 0

    for i in range(len(points)):
        for j in range(len(points)):
            if points[i] == points[j]:
                continue

            x1, y1 = points[i]
            x2, y2 = points[j]

            d = abs(int(math.sqrt((x2 - x1) ** 2 + (y2 - y1) ** 2)))

            if smallest == 0 or d < smallest:
                smallest = d

    return smallest

############################################################################
#
# Problem 5
# multiply two matrices A and B.
# For the running time A is an m*n matrix, and B is an n*l matrix.
#
# what is the size of the output?
#       2*2
# Running Time: O(2n^2)
############################################################################

def matMul(A,B):
    """
    >>> matMul([[1, 2, 3], [4, 5, 6]], [[7, 8], [9, 10], [11, 12]])
    [[58, 64], [139, 154]]
    """
    sums = []

    for i in range(len(A)):
        products = []

        for j in range(len(B[0])):
            col = [n[j] for n in B]
            sum = 0

            for n in range(len(A[i])):
                sum += A[i][n] * col[n]

            products.append(sum)

        sums.append(products)

    return sums

############################################################################
#
# Problem 6
# Compute the number of 1s that would be in the binary representation of x
# for example: 30 = 11110 in base 2, and it has 4 1s.
#
# For full credit, you should assume that
# arithmetic operations are *not* constant time.
# bitwise operations are constant time though.
#
# What is the input size?
#       The number of times the number must be bitwise ANDed.
# Running Time: O(n)
############################################################################

def popcount(x):
    """
    >>> popcount(7)
    3
    >>> popcount(30)
    4
    >>> popcount(256)
    1
    """
    ones = 0

    while x > 0:
        x &= (x - 1)
        ones += 1

    return ones

############################################################################
#
# Problem 7
# compute the integer square root of x.
# This is the largest number s such that s^2 <= x.
#
# You can assume that arithmetic operations are constant time for this algorithm.
#
# What is the input size?
#       The number of times i is incremented.
# Running Time: O(n)
############################################################################

def isqrt(x):
    """
    >>> isqrt(6)
    2
    >>> isqrt(121)
    11
    >>> isqrt(64)
    8
    >>> isqrt(20)
    4
    """
    # edge cases
    if x == 0 or x == 1:
        return x

    i = 2

    while i * i < x:
        i += 1

    if i * i > x:
        i -= 1

    return i

############################################################################
#
# Problem 8: Word Search
#
# determine if string s is any where in the word grid g.
#
# for example s = "bats"
# g = ["abrql",
#      "exayi",
#      "postn",
#      "cbkrs"]
#
# Then s is in the word grid
#     [" b   ",
#      "  a  ",
#      "   t ",
#      "    s"]
#
# What is your input size?
# Running Time: O(2n^2)
############################################################################

def wordSearch(word,grid):
    """
    >>> g = ["abrql", "exayi", "postn", "cbkrs"]
    >>> h = ["cbkrs", "postn", "exayi", "abrql"]
    >>> wordSearch("bats",g)
    True
    >>> wordSearch("bats",h)
    True
    >>> wordSearch("cbkr",g)
    True
    >>> wordSearch("cbkr",h)
    True
    >>> wordSearch("axsr",g)
    True
    >>> wordSearch("lins",h)
    True
    """
    result = word[0]
    positions = []
    all_directions = [
        # Top left, top center, top right
        [-1, -1], [0, -1], [1, -1],
        # Left, right
        [-1, 0], [1, 0],
        # Bottom left, bottom center, bottom right
        [-1, 1], [0, 1], [1, 1]
    ]
    directions = []

    for r in range(len(grid)):
        c = grid[r].find(word[0])

        if c >= 0:
            positions.append((c, r))

    for pc, pr in positions:
        directions.clear()

        for dc, dr in all_directions:
            try:
                if grid[pr + dr][pc + dc] == word[1]:
                    directions.append((dc, dr))
            except IndexError:
                pass

        for dc, dr in directions:
            c = pc
            r = pr

            for l in range(1, len(word)):
                if grid[r + dr][c + dc] == word[l]:
                    result += word[l]
                    c += dc
                    r += dr
                else:
                    result = word[0]
                    break

            if result == word:
                break

        if result == word:
            break

    return result == word

############################################################################
#
# Problem 9: Convex Hull
#
# In class we learned about the convex hull problem.
# We also learned that for any line segment on the convex hull,
# every other point will we on the same side of that line.
#
# Use this fact to write an algorithm to find all of the points in the convex hull.
#
# for example: [(1,1), (4,2), (4,5), (7,1)] are the points shown below
#
#    *
#
#    *
# *     *
#
# The convex hull is [(1,1), (4,5), (7,1)]
#    *
#   / \
#  /   \
# *-----*
#
# Running Time:
############################################################################

def convexHull(points):
    """
    >>> convexHull([(1,1), (4,2), (4,5), (7,1)])
    [(1, 1), (4, 5), (7, 1)]
    """
    pass

############################################################################
#
# Problem 10: Running time
#
# Find the Theta time complexity for the following functions.
# If the problem is a summation, give a closed form first.
#
# 1. f(n) = n^2 + 2n + 1
# 2. f(n) = sum(i=0, n, sum(j=0, i, 1) )
# 3. f(n) = (n+1)!
# 4. f(n) = sum(i=0, n, log(i))
# 5. f(n) = log(n!)
############################################################################

if __name__ == "__main__":
    import doctest
    doctest.testmod()
    # wordSearch("bats", ["abrql", "exayi", "postn", "cbkrs"])
    # wordSearch("bats", ["cbkrs", "postn", "exayi", "abrql"])
    # wordSearch("bkrs", ["cbkrs", "postn", "exayi", "abrql"])
