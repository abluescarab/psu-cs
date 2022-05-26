import math

################################################################
# Problem 1
#
# We're going to take the job scheduling problem from class,
# but this time, I want to make sure every job is scheduled.
# If I have a set of n jobs where each job is represented
# by a tuple (s,f),
# Give a greedy algorithm to schdule the jobs on the fewest
# number of processors total
#
# Running Time: Theta(nlog(n)+n^2)
#
################################################################

def schedule(jobs):
    """
    >>> schedule([(5,40),(30,35),(6,20),(19,31),(23,29),(28,32)])
    [[(6, 20), (23, 29), (30, 35)], [(19, 31)], [(28, 32)], [(5, 40)]]
    >>> schedule([(3,6),(9, 20),(12,30),(5, 6),(14, 30),(20, 35),(32, 40)])
    [[(3, 6), (9, 20), (20, 35)], [(5, 6), (12, 30), (32, 40)], [(14, 30)]]
    >>> schedule([(1,2)])
    [[(1, 2)]]
    >>> schedule([(3,4),(1,2)])
    [[(1, 2), (3, 4)]]
    >>> schedule([(1,3),(3,4)])
    [[(1, 3), (3, 4)]]
    >>> schedule([(1,3),(2,4)])
    [[(1, 3)], [(2, 4)]]
    """
    jobs_sorted = sorted(jobs, key=lambda j: j[1])                  # O(n log n)
    scheduled = [[jobs_sorted[0]]]
    processor = 0
    visited = [jobs_sorted[0]]

    while len(visited) < len(jobs_sorted):                          # O(n)
        for i in range(1, len(jobs_sorted)):                        # O(n-1)
            if jobs_sorted[i] not in visited:
                start = jobs_sorted[i][0]
                last_end = 0

                if len(scheduled[processor]) > 0:
                    last_end = scheduled[processor][-1][1]

                if start >= last_end:
                    scheduled[processor].append(jobs_sorted[i])
                    visited.append((jobs_sorted[i]))

        if len(visited) < len(jobs_sorted):
            scheduled.append([])
            processor += 1

    return scheduled

################################################################
# Problem 2
#
# Given a list of string (strings)
# Find s short string (bigstring) that
# for every s in string, s is a substring of bigstring.
#
# Use the approximation algorithm we gave in class.
#
# Running Time: Theta(n^4)
#
################################################################

def find_overlap(s, t):                         # running time: O(2n) + O(2i)
    i = 0
    largest = ""
    order = (s, t)
    minLen = min(len(s), len(t))

    for i in range(minLen):                     # O(n)
        slice = s[i:]                           # O(i)
        if t.startswith(slice):
            largest = slice
            break

    for i in range(minLen):                     # O(n)
        slice = t[i:]                           # O(i)

        if s.startswith(slice):
            if len(slice) > len(largest):
                largest = slice
                order = (t, s)
                break

    return (largest, *order)

def superstring(strings):
    """
    >>> superstring(["CADBC","CDAABD","BCDA","DDCA","ADBCADC"])
    'BCDAABDDCADBCADC'
    >>> superstring(["A","B","C","D","E"])
    'AEDCB'
    >>> superstring(["AHHI","HIP"])
    'AHHIP'
    >>> superstring(["HIP","IPP"])
    'HIPP'
    """
    while len(strings) > 1:                             # O(n-1)
        longest_str = ""
        full_str = ""
        a = ""
        b = ""

        for i in range(len(strings)):                   # O(n)
            for j in range(len(strings)):               # O(n)
                if i == j:
                    continue

                strr, str1, str2 = find_overlap(strings[i], strings[j]) # O(2n)

                if len(strr) > len(longest_str):
                    longest_str = strr
                    a = str1
                    b = str2

        if longest_str == "":
            strings[0] += strings[len(strings) - 1]
            strings.pop(len(strings) - 1)
        else:
            full_str = a[:-len(longest_str)] + longest_str + b[len(longest_str):]

            if full_str not in strings:
                strings.insert(0, full_str)

            strings.remove(a)
            strings.remove(b)

    return strings[0]

################################################################
# Problem 3
#
# Find the shortest path from a to b
# in a weighted graph g that is represented by an adjacency list.
# You can assume all edge weights are positive.
#
# Running time: Theta((V + E) * p(V))
################################################################

def dijkstra(g, a, b):
    """
    >>> g = [ [(1,3), (2,6)], \         # 0
              [(0,3), (4,4)], \         # 1
              [(0,6), (3,2), (5,7)], \  # 2
              [(2,2), (4,4), (8,1)], \  # 3
              [(1,4), (3,4), (6,9)], \  # 4
              [(2,7), (6,2), (7,8)], \  # 5
              [(4,9), (5,2), (9,4)], \  # 6
              [(5,8), (8,3)], \         # 7
              [(3,1), (7,3), (9,2)], \  # 8
              [(6,4), (8,2)], \         # 9
              [(11,1)], \               # 10
              [(10,1)]]                 # 11
    >>> dijkstra(g,0,9)
    [0, 2, 3, 8, 9]
    >>> dijkstra(g,0,7)
    [0, 2, 3, 8, 7]
    >>> dijkstra(g,0,10)
    >>> dijkstra(g,0,11)
    >>> dijkstra(g,11,10)
    [11, 10]
    >>> dijkstra(g,7,7)       # Just make a special case for this
    [7]
    >>> dijkstra(g,0,1)
    [0, 1]
    >>> dijkstra(g,5,3)
    [5, 2, 3]
    >>> dijkstra(g,5,1)
    [5, 6, 4, 1]
    """
    dists = [math.inf] * len(g)
    prevs = [None] * len(g)
    visited = []
    path = []
    temp = b

    dists[a] = 0

    while len(visited) < len(g):
        v, w = -1, math.inf

        for u, weight in enumerate(dists):
            if u not in visited and weight < w:
                v = u
                w = weight

        if v == b:
            break

        visited.append(v)

        for u, weight in g[v]:
            if u not in visited and dists[v] + weight < dists[u]:
                dists[u] = dists[v] + weight
                prevs[u] = v

    # traverse from b to a
    if prevs[temp] is not None or temp == a:
        while temp is not None:
            path.insert(0, temp)
            temp = prevs[temp]

    return path if path else None


if __name__ == "__main__":
    import doctest
    doctest.testmod()
