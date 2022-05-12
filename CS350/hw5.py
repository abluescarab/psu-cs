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
# you should return a list of components, where each component is a list of
# vertices.
# Example g = [[1,2], [0,2], [0,1], [4], [3]]
# Should return a list of two components [[0,1,2],[3,4]]
#
# Running time?
#
############################################################################

def components_rec(g, v, visited):
    result = []

    for w in g[v]:
        if w not in visited:
            visited.append(w)
            result.extend([w] + components_rec(g, w, visited))

    return result

def components(g):
    """
    >>> components([[1,2],[0,2],[0,1],[4],[3]])
    [[0, 1, 2], [3, 4]]
    >>> components([[]])
    [[0]]
    >>> components([[1],[0]])
    [[0, 1]]
    >>> components([[3,4,7],[3,5,6],[4,5,7],[0,1],[0,2],[1,2],[1],[0,2]])
    [[0, 3, 1, 5, 2, 4, 7, 6]]
    """
    visited = []
    result = []

    for v in range(len(g)):
        if v not in visited:
            visited.append(v)
            result.append([v] + components_rec(g, v, visited))

    return result

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

def bipartite_rec(g, v, sets, visited):
    for w in g[v]:
        if w not in visited:
            visited.append(w)

            sets[w] = 1 if sets[v] == 0 else 0

            if not bipartite_rec(g, v, sets, visited):
                return False
        elif sets[v] == sets[w]:
            return False

    return True

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
    visited = []
    sets = [0] * len(g)

    for v in range(len(g)):
        if not bipartite_rec(g, v, sets, visited):
            return False

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

def isForest_rec(g, v, parent, visited):
    for w in g[v]:
        if w not in visited:
            visited.append(w)

            if not isForest_rec(g, w, v, visited):
                return False
        elif parent != w:
            return False

    return True

def isForrest(g):
    """
    >>> isForrest([[1,2],[0,3,4],[0,6],[1],[1,8,9],[6],[5,7],[6],[4],[4]])
    True
    >>> isForrest([[1,2],[0,3,4],[0,6],[1],[1,8,9],[6],[5,7],[6],[4],[4],[11],[10]])
    True
    >>> isForrest([[1,2],[0,3,4],[0,6],[1],[1,8,9],[6],[5,7],[6,8],[4,7],[4]])
    False
    >>> isForrest([[1,2],[0,3,4],[0,6],[1],[1,8,9],[6],[5,7],[6],[4],[4],[11,12],[10,12],[10,11]])
    False
    >>> isForrest([[]])
    True
    >>> isForrest([[1],[0]])
    True
    """
    visited = []

    for v in range(len(g)):
        if v not in visited:
            visited.append(v)

            if not isForest_rec(g, v, None, visited):
                return False

    return True

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

def topsort_rec(d, v, visited, top_sort):
    visited.append(v)

    for w in d[v]:
        if w not in visited:
            topsort_rec(d, w, visited, top_sort)

    top_sort.append(v)

def topsort(d):
    """
    >>> topsort([[1,2],[3],[3],[]])
    [0, 2, 1, 3]
    >>> topsort([[3],[1,2],[3],[]])
    [1, 2, 0, 3]
    >>> topsort([[6,5],[0,4],[0,5],[1,2],[6],[6],[]])
    [3, 2, 1, 4, 0, 5, 6]
    >>> topsort([[1,2,3],[4],[],[4],[5,6,7],[],[],[6]])
    [0, 3, 2, 1, 4, 7, 6, 5]
    """
    visited = []
    top_sort = []

    for v in range(len(d)):
        if v not in visited:
            topsort_rec(d, v, visited, top_sort)

    top_sort.reverse()
    return top_sort

############################################################################
#
# Problem 5
#
# write a function to determine the strongly connected components of digraph d.
# Just like the components example, you should return a list of strongly
# connected components.
#
# Running time?
#
############################################################################

def scc_rec(d, v, visited, result):
    visited.append(v)

    for w in d[v]:
        if w not in visited:
            scc_rec(d, w, visited, result)

    result.append(v)

def scc(d):
    """
    >>> scc([[1],[2],[0,3],[1,2],[3,5,6],[4],[7],[8],[6]])
    [[5, 4], [7, 8, 6], [3, 1, 2, 0]]
    >>> scc([[1],[2],[0,3],[1,2,4],[3,5,6],[4],[7],[8],[6]])
    [[5, 4, 3, 1, 2, 0], [7, 8, 6]]
    >>> scc([[]])
    [[0]]
    >>> scc([[1],[]])
    [[0], [1]]
    >>> scc([[1],[0]])
    [[1, 0]]
    >>> scc([[1],[0,2,4],[0],[4],[3,5],[],[5,7],[4,9],[3,7],[10],[6,11],[]])
    [[8], [7, 9, 10, 6], [11], [1, 2, 0], [3, 4], [5]]
    """
    dt = [[] for _ in range(len(d))]
    visited = []
    visited_t = []
    dead_ends = []
    result = []

    # get verts in the order they dead end
    for v in range(len(d)):
        if v not in visited:
            scc_rec(d, v, visited, dead_ends)

    # reverse edges
    for v, adj in enumerate(d):
        for w in adj:
            dt[w].append(v)

    # get strongly connected components
    while dead_ends:
        v = dead_ends.pop()

        if v not in visited_t:
            result.append([])
            scc_rec(dt, v, visited_t, result[-1])

    return result

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
    doctest.testmod()
