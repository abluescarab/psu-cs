# CS 350: Homework 4
# Due: Week of 4/25
# Name: Alana Gilston

############################################################################
# Problem 1: Quicksort
#
# implement quicksort described in class.
#
# Recurrence worst case: ϴ(n)
# Recurrence average case: ϴ(n/3)
# Running time worst case: ϴ(n^2)
# Running time average case: ϴ(n*log(n))
#
# When does the worst case happen?
#   The worst case happens when the list is already sorted.
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
# Running time: ϴ(2n)
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
# Running time: ϴ(n-1)
############################################################################

def matrixParens(sizes):
    """
    >>> matrixParens([(1, 1)])
    0
    >>> matrixParens([(3,5), (5,4), (4,7)])
    144
    """
    if len(sizes) == 0 or len(sizes) == 1:
        return 0

    sum1 = sum2 = sizes[0][0]

    for i in range(1, len(sizes)):
        sum1 *= sizes[i][0]
        sum2 *= sizes[i][1]

    return sum1 + sum2

############################################################################
# Problem 4: Convex Hull again!
#
# Use the Divide and Conquer algorithm described in class to compute
# the convex hull of a set of points.
#
# Recurrence worst case:
# Recurrence average case:
# Running time worst case: ϴ(n^3)
# Running time average case: ϴ(n*log(n))
#
# When does the worst case happen?
############################################################################

def calcLine(p1, p2):
    x1, y1 = p1
    x2, y2 = p2

    return {
        "point1": p1,
        "point2": p2,
        "a": y2 - y1,
        "b": x1 - x2,
        "c": (x1 * y2) - (y1 * x2)
    }

def calcSide(line, p):
    x1, y1 = line["point1"]
    x2, y2 = line["point2"]
    x3, y3 = p

    return x1 * y2 + x2 * y3 + x3 * y1 - (x2 * y1 + x3 * y2 + x1 * y3)

def convexHull(l):
    """
    >>> convexHull([(1,1), (4,2), (7,1)])
    [(1, 1), (4, 2), (7, 1)]
    >>> convexHull([(9,1), (7,8), (0,8), (1,2), (12,13), (5,15), (-5,1), (2,0)])
    [(-5, 1), (5, 15), (9, 1), (0, 8), (2, 0), (12, 13)]
    >>> convexHull([(1,1), (2,2), (3,3), (4,4), (5,5)])
    [(1, 1), (2, 2), (3, 3), (4, 4), (5, 5)]
    >>> convexHull([(1,1), (4,2), (4,5), (7,1)])
    [(1, 1), (4, 5), (7, 1)]
    """
    if len(l) < 4:
        return l

    left = min(l)
    right = max(l)
    center = calcLine(left, right)
    hull = [left]
    dists = []

    for i in range(len(l)):
        if l[i] == left or l[i] == right:
            continue

        x3, y3 = l[i]
        p1p2 = center["a"] * x3 + center["b"] * y3 + center["c"]

        if p1p2 == 0:
            dist = 0
        else:
            dist = 2 * calcSide(center, l[i]) / abs(p1p2)

        dists.append((dist, l[i]))

    top = max(dists, key=lambda d: d[0])
    bottom = min(dists, key=lambda d: d[0])

    if top[0] >= 0:
        hull.append(top[1])

    # if top and bottom are the same, only add bottom if top has not been added
    if bottom[0] <= 0 and (top != bottom or len(hull) < 2):
        hull.append(bottom[1])

    left_top = calcLine(left, top[1])           # from left pt to top pt
    right_top = calcLine(right, top[1])         # from right pt to top pt
    left_bottom = calcLine(left, bottom[1])     # from left pt to bottom pt
    right_bottom = calcLine(right, bottom[1])   # from right pt to bottom pt

    for i in range(len(l)):
        if l[i] == left or l[i] == right or l[i] == top[1] or l[i] == bottom[1]:
            continue

        lt_side = calcSide(left_top, l[i])
        lb_side = calcSide(left_bottom, l[i])
        rt_side = calcSide(right_top, l[i])
        rb_side = calcSide(right_bottom, l[i])

        # if the point is on any of the lines, add it
        if lt_side == 0 or lb_side == 0 or rt_side == 0 or rb_side == 0:
            hull.append(l[i])

        if ((lt_side > 0 and rt_side > 0) or (lt_side < 0 and rt_side < 0)) or \
           ((lb_side > 0 and rb_side > 0) or (lb_side < 0 and rb_side < 0)):
            hull.append(l[i])

    return hull + [right]

############################################################################
# Problem 5: Recurrence relations
#
# Give a closed form, and big Theta for the following recurrence relations.
# If it's a divide and conquer relation, then you only need to give the Theta.
#
# a. Give the recurrence relation for Karatsuba's algorithm, and solve it.
#   c_1 = a_h * b_h
#   c_3 = a_l * b_l
#   c_2 = (a_h + a_l) * (b_h + b_l)
#   Return c_1 * 2^n + c_2 * 2^(n/2) + c_3.
#   Return: (a_h * b_h) * 2^n + ((a_h + a_l) * (b_h + b_l)) * 2^(n/2)
#           + (a_l) * (b_l)
# b. Give the recurrence relation for Strassen's algorithm, and solve it.
# c.
# T(1) = 1
# T(n) = T(n-1) + n
#
#   a_0 = T(1) = 1
#   a_1 = T(2) = T(1) + n = 1 + 1 = 2
#   a_2 = T(3) = T(2) + n = 2 + 3 = 5
#   a_3 = T(4) = T(3) + n = 5 + 4 = 9
#   a_4 = T(5) = T(4) + n = 9 + 5 = 14
#   a_5 = T(6) = T(5) + n = 14 + 6 = 20
#
#   a_n = (1/2)(n^2 + 3n)
# d.
# T(1) = 1
# T(n) = 2T(n-2) + 1
#
############################################################################

if __name__ == "__main__":
    import doctest
    doctest.testmod()
