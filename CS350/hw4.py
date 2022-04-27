# CS 350: Homework 4
# Due: Week of 4/25
# Name: Alana Gilston

############################################################################
# Problem 1: Quicksort
#
# implement quicksort described in class.
#
# Recurrence worst case:
# Recurrence average case:
# Running time worst case:
# Running time average case:
#
# When does the worst case happen?
############################################################################

def quicksort(l):
    """
    >>> quicksort([3,2,6,1,4])
    [1, 2, 3, 4, 6]
    >>> quicksort([5,4,3,2,1])
    [1, 2, 3, 4, 5]
    >>> quicksort([1,2,3,4,5])
    [1, 2, 3, 4, 5]
    >>> quicksort([1])
    [1]
    >>> quicksort([2,1])
    [1, 2]
    >>> quicksort([432,12,12,12,78,21,90])
    [12, 12, 12, 21, 78, 90, 432]
    >>> quicksort([3,3,3,3,3])
    [3, 3, 3, 3, 3]
    >>> quicksort([])
    []
    """
    if len(l) == 0 or len(l) == 1:
        return l

    pivot = len(l) // 2
    big = []
    small = []

    for i in range(len(l)):
        if i == pivot:
            continue

        if l[i] <= l[pivot]:
            small.append(l[i])
        elif l[i] > l[pivot]:
            big.append(l[i])

    return quicksort(small) + [l[pivot]] + quicksort(big)

############################################################################
# Problem 2: maximum sublist sum
#
# A sublist is a contiguous piece of a list
# [1,2,1] is a sublist of [4,1,2,1,3]
# but [1,2,3] isn't.
#
# the sum of a list is just adding all of the elements.
#
# compute the maximum sum of any sublist.
# For example:  [-2,1,-3,4,-1,2,1,-5,4]
# the maximum sublist is [4,-1,2,1] with a sum of 6
#
# Running time:
############################################################################

def maxSublist(l):
    """
    >>> maxSublist([-2,1,-3,4,-1,2,1,-5,4])
    [4, -1, 2, 1]
    >>> maxSublist([-9,-2,-3,-1,-3,-2,-11])
    [-1]
    >>> maxSublist([-9,-2,-3,1,-3,-2,-11])
    [1]
    >>> maxSublist([1,2,3,4,5])
    [1, 2, 3, 4, 5]
    >>> maxSublist([5,4,3,2,1])
    [5, 4, 3, 2, 1]
    >>> maxSublist([1, 2, -8, -4, 2, 1])
    [1, 2]
    >>> maxSublist([1, 2, -8, 3, 2, 1])
    [3, 2, 1]
    >>> maxSublist([8, -1, 5, -6, 78, -77, 9])
    [8, -1, 5, -6, 78]
    >>> maxSublist([8, -4, 5, -78, 78, -77, 9])
    [78]
    >>> maxSublist([-1,-4,-10,-93,-19,-45,-2,0])
    [0]
    """
    sum = l[0]
    max_sum = l[0]
    start = 0
    end = 0
    new_start = 0

    for i in range(len(l)):
        if sum > 0:
            sum += l[i]
        else:
            sum = l[i]
            new_start = i

        if sum > max_sum:
            max_sum = sum
            start = new_start
            end = i + 1

    if start == end:
        return [max(l)]

    return l[start:end]

############################################################################
# Problem 3: Parenthesizing matrices.
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
    >>> matrixParens([(4,7), (3,5), (5,4)])
    144
    >>> matrixParens([(3,5), (5,4), (4,7)])
    144
    >>> matrixParens([(9,1), (10,30), (12,16), (6,10), (1,3), (30,12), (16,9)])
    1074
    >>> matrixParens([(9,2), (4,9)])
    72
    """
    pass

############################################################################
# Problem 4: Convex Hull again!
#
# Use the Divide and Conquer algorithm described in class to compute
# the convex hull of a set of points.
#
# Recurrence worst case:
# Recurrence average case:
# Running time worst case:
# Running time average case:
#
# When does the worst case happen?
############################################################################

def convexHull(l):
    """
    >>> convexHull([(9,1), (7,8), (0,8), (1,2), (12,13), (5,15), (-5,1), (2,0)])
    [(-5, 1), (5, 15), (9, 1), (2, 0), (12, 13)]
    >>> convexHull([(1,1), (2,2), (3,3), (4,4), (5,5)])
    [(1, 1), (5, 5)]
    >>> convexHull([(1,1), (4,2), (4,5), (7,1)])
    [(1, 1), (4, 5), (7, 1)]
    >>> convexHull([(1,1), (4,2), (7,1)])
    [(1, 1), (4, 2), (7, 1)]
    """
    pass

############################################################################
# Problem 5: Recurrence relations
#
# Give a closed form, and bit Theta for the following recurrence relations.
# If it's a divide and conquer relation, then you only need to give the Theta.
#
# a. Give the recurrence relation for Karatsuba's algorithm, and solve it.
# b. Give the recurrence relation for Strassen's algorithm, and solve it.
# c.
# T(1) = 1
# T(n) = T(n-1) + n
# d.
# T(1) = 1
# T(n) = 2T(n-2) + 1
#
############################################################################

if __name__ == "__main__":
    import doctest
    doctest.testmod()
