
# CS 350: Homework 3
# Due: Week of 4/18
# Name: Alana Gilston

# for this homework, unless I'm asking you to sort a list,
# you are allowed to use the sorted function in Python.
# sorted takes a list, and returns a sorted copy of the list in Theta(n*log(n)) time.

############################################################################
#
# Problem 1
# Compute the largest gap between two numbers in a list.
#
# for example: gap([1,6,2,4,9]) == 3 because the gap between 6 and 9 is 3.
# The gap isn't 8 because even thought 9-1 is 8, there is a 4 in the middle
# of those numbers.
#
# Running Time: O(n*log(n) + n) = O(n(log(n) + 1))
############################################################################

def gap(l):
    """
    >>> gap([1,6,2,4,9])
    3
    >>> gap([1])
    >>> gap([1,45,67,99])
    44
    >>> gap([2,45,67,99])
    43
    >>> gap([1,3])
    2
    """
    if len(l) == 1:
        return

    list = sorted(l)                # O(n log(n))
    result = 0

    for i in range(len(list) - 1):
        gap = abs(list[i] - list[i + 1])

        if gap > result:
            result = gap

    return result

############################################################################
#
# Problem 2
# We can concatenate two numbers together to get a new number.
# for example: 44 concatenated with 55 = 4455
# We can concatenate a list of numbers by concatenating all the numbers.
# concat([1,2,55,3]) = 12553
#
# If we rearrange the list, we can get a different number.
# concat([2,55,1,3]) = 25513
#
# Write a function to find the largest value we can get from concatenating a list.
#
# Running Time: O(2n)
############################################################################

def concatenate(l):
    out = ""
    for x in l:
        out = out + str(x)
    return int(out)

def largestConcat(l):
    """
    >>> largestConcat([1,2,55,3])
    55321
    >>> largestConcat([2,3,8,99,3,100,8])
    9988332100
    >>> largestConcat([2,3,8,99,3,899,100,8])
    9989988332100
    >>> largestConcat([98,9899])
    989998
    >>> largestConcat([93,9349999])
    939349999
    >>> largestConcat([9,8,9])
    998
    """
    if len(l) == 1:
        return l

    forward = concatenate(sorted(l, key=lambda i: str(i)))
    backward = concatenate(sorted(l, reverse=True, key=lambda i: str(i)))

    return forward if forward > backward else backward

############################################################################
#
# Problem 3
# Write a function to return the number of unique elements in an array.
# for example the list [3,6,2,3,2,7,4] has 3 unique elements, 6, 7, and 4.
#
# Running Time: O(n*log(n) + n) = O(n(log(n) + 1))
############################################################################

def numberUnique(l):
    """
    >>> numberUnique([3,6,2,3,2,7,4,3])
    3
    >>> numberUnique([3,6,2,3,2,7,4])
    3
    >>> numberUnique([1,5,3,2,8,6])
    6
    >>> numberUnique([1,5,2,4,6,5,2,1,6,4])
    0
    >>> numberUnique([1])
    1
    >>> numberUnique([1,1])
    0
    """
    count = len(l)

    if count == 0 or count == 1:
        return count

    uniques = []
    numbers = sorted(l)         # O(nlog(n))

    for i in range(count):
        if (i + 1 < count and numbers[i] == numbers[i + 1]) or \
           (i - 1 >= 0 and numbers[i] == numbers[i - 1]):
           continue

        uniques.append(numbers[i])

    return len(uniques)

############################################################################
#
# Problem 4
# Implement insertion sort from class.
#
# Running Time: O(n - 1) - not sure about while loop
############################################################################

def insertionSort(l):
    """
    >>> insertionSort([3,4,2,7,99,21,1,0])
    [0, 1, 2, 3, 4, 7, 21, 99]
    >>> insertionSort([9,8,7,6,5,4,3,2,1,0])
    [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
    >>> insertionSort([1])
    [1]
    >>> insertionSort([1,2])
    [1, 2]
    >>> insertionSort([2,1])
    [1, 2]
    >>> insertionSort([1,1])
    [1, 1]
    >>> insertionSort([1,2,1])
    [1, 1, 2]
    """
    for i in range(1, len(l)):
        if l[i] >= l[i - 1]:
            continue

        index = i

        while index - 1 >= 0 and l[index] < l[index - 1]:   # O(???)
            l[index], l[index - 1] = l[index - 1], l[index]
            index -= 1

    return l

############################################################################
#
# Problem 5
# Use the heap from last homework to sort an array.
#
# Running Time: O(2n*log(n))
############################################################################

def heapSort(n):
    """
    >>> heapSort([3,6,2,5,1])
    [1, 2, 3, 5, 6]
    """
    from hw2 import Heap

    heap = Heap()
    elems = []

    for e in n:                     # O(n)
        heap.push(e)                # O(log(n))

    while len(heap.heap) > 0:       # O(n)
        elems.append(heap.pop())    # O(log(n))

    return elems


if __name__ == "__main__":
    import doctest
    doctest.testmod()
