# CS 350: Homework 5
# Due: Week of 5/9
# Name: Alana Gilston

# You should not assume anything about the data for these problems
# other than it's valid.
# Adjacency lists might not be in any particular order
# and graphs may not be connected.

############################################################################
#
# Problem 1
#
# write a function that returns the set of connected components
# of an undirected graph g.
# g is represented as an adjacency list
# you should return a list of components, where each component is a list of vertices.
# Example g = [[1,2], [0,2], [0,1], [4], [3]]
# Should return a list of two components [[0,1,2],[3,4]]
#
# Running time?
#
############################################################################

def components(g):
    """
    >>> components([[1,2],[0,2],[0,1],[4],[3]])
    [[0, 1, 2], [3, 4]]
    >>> components([[]])
    [[0]]
    >>> components([[1],[0]])
    [[0, 1]]
    >>> components([[3,4,7],[3,5,6],[4,5,7],[0,1],[0,2],[1,2],[1],[0,2]])
    [[0, 1, 2, 3, 4, 5, 6, 7]]
    """
    # connections = []

    # for i in range(len(g)):                             # O(n)
    #     current = set(sorted([i] + g[i]))
    #                                                     # O(n^2)
    #     for j in range(len(g)):                         # O(n)
    #         if i == j:
    #             continue

    #         if any(x in current for x in g[j]):         # O(n)
    #             current.update(g[j])

    #     l = list(current)                               # O(n)
    #                                                     # O(2n)
    #     if l not in connections:                        # O(n)
    #         connections.append(l)

    # return connections

############################################################################
#
# Problem 2
#
# write a function the returns True if, and only if, graph g is bipartite
# g is represented as an adjacency list
#
# Running time?
#
############################################################################

def bipartite(g):
    """
    >>> bipartite([[3,4,7], [3,5,6], [4,5,7], [0,1], [0,2], [1,2], [1], [0,2]])
    True
    >>> bipartite([[1,2],[0,2],[0,1]])
    False
    >>> bipartite([[]])
    True
    >>> bipartite([[1],[0]])
    True
    >>> bipartite([[1,3],[0,2],[1,3,5],[0,2],[5],[2,4]])
    True
    >>> bipartite([[1,3],[0,2],[1,3,5,4],[0,2],[5,2],[2,4]])
    False
    """
    if len(g) == 0 or len(g[0]) == 0:
        return True

    t = set()
    s = set()

    for v in range(len(g)):
        for w in g[v]:
            pass

    return True

############################################################################
#
# Problem 3
#
# write a function the returns True if, and only if, graph g is a forrest
# g is represented by a adjacency list.
#
# Running time?
#
############################################################################

def isForrest(g):
    """
    >>> isForrest([[1,2], [3,4], [5,6], [], [], [], []])
    True
    >>> isForrest([[1,2], [3,4], [5,4], [], [], []])
    False
    """
    pass

############################################################################
#
# Problem 4
#
# write a function to topologically sort the vertices of a directed graph d
# Assume d is an adjacency list.
#
# Running time?
#
############################################################################

def topsort(d):
    """
    >>> topsort([[1, 2], [3], [3], []])
    [0, 1, 2, 3]
    """
    pass

############################################################################
#
# Problem 5
#
# write a function to determine the strongly connected components of digraph d.
# Just like the components example, you should return a list of strongly connected components.
#
# Running time?
#
############################################################################

def scc(d):
    """
    >>> scc([[1], [2], [0,3], [1,2], [3,5,6], [4], [7], [8], [6]])
    [[0, 1, 2, 3], [4, 5], [6, 7, 8]]
    """
    pass

############################################################################
#
# Problem 6
#
# a. What doe we need to change about BFS/DFS if we use an adjacency matrix?
#
# b. What is the running time for BFS/DFS if we use and adjacency matrix?
#
# c. Give an example of a weighted graph where BFS doesn't return the shortes path.
#
############################################################################

if __name__ == "__main__":
    import doctest
    # doctest.testmod()

    import utils
    # utils.asrt(components([[1,2], [0,2], [0,1], [4], [3]]), [[0, 1, 2], [3, 4]])
    # utils.asrt(components([[]]), [[0]])
    # utils.asrt(components([[1],[0]]), [[0, 1]])
    # utils.asrt(components([[3,4,7],[3,5,6],[4,5,7],[0,1],[0,2],[1,2],[1],[0,2]]), [[0, 3, 1, 5, 2, 4, 7, 6]])
    # utils.asrt(components([[1,2],[0,3,4],[0,6],[1],[1,8,9],[6],[5,7],[6],[4],[4],[11,12],[10,12],[10,11]]), [[0, 1, 3, 4, 8, 9, 2, 6, 5, 7], [10, 11, 12]])
    utils.asrt(bipartite([[3,4,7], [3,5,6], [4,5,7], [0,1], [0,2], [1,2], [1], [0,2]]), True)
    # utils.asrt(bipartite([[1,2],[0,2],[0,1]]), False)
    # utils.asrt(bipartite([[]]), True)
    # utils.asrt(bipartite([[1],[0]]), True)
    # utils.asrt(bipartite([[1,3],[0,2],[1,3,5],[0,2],[5],[2,4]]), True)
    # utils.asrt(bipartite([[1,3],[0,2],[1,3,5,4],[0,2],[5,2],[2,4]]), False)
