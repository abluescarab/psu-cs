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
# Running Time:
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
    pass

################################################################
# Problem 2
#
# Given a list of string (strings)
# Find s short string (bigstring) that
# for every s in string, s is a substring of bigstring.
#
# Use the approximation algorithm we gave in class.
#
# Running Time:
#
################################################################

def superstring(strings):
    """
    >>> superstring(["CADBC","CDAABD","BCDA","DDCA","ADBCADC"])
    'BCDAABDDCADBCADC'
    >>> superstring(["BADCH","HWBA","CHADIS","ADCHDJKSHWB"])
    'CHADISBADCHDJKSHWBA'
    >>> superstring(["A","B","C","D","E"])
    'ABCDE'
    >>> superstring(["AHHI","HIP"])
    'AHHIP'
    >>> superstring(["HIP","IPP"])
    'HIPP'
    >>> superstring(["HELLO","LOHELLPO","OPO"])
    'HELLOHELLPOPO'
    """
    pass

################################################################
# Problem 3
#
# Find the shortest path from a to b
# in a weighted graph g that is represented by an adjacency matrix.
# You can assume all edge weights are positive.
#
# Running time:
################################################################

def dijkstra(g, a, b):
    """
    >>> g = [ [(1,3), (2,6)], \
              [(0,3), (4,4)], \
              [(0,6), (3,2), (5,7)], \
              [(2,2), (4,4), (8,1)], \
              [(1,4), (3,4), (6,9)], \
              [(2,7), (6,2), (7,8)], \
              [(4,9), (5,2), (9,4)], \
              [(5,8), (8,3)], \
              [(3,1), (7,3), (9,2)], \
              [(6,4), (8,2)], \
              [(11,1)], \
              [(10,1)]]
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
    if a == b:
        return [a]

    path = [a]
    visited = [a]

    for i in range(len(g)):
        if g[i] in visited:
            return None

        for v, w in g[i]:
            pass


if __name__ == "__main__":
    import doctest
    # doctest.testmod()

    from test_suite import TestSuite

    suite = TestSuite(True)
    suite.add_test(schedule, [[(6, 20), (23, 29), (30, 35)], [(19, 31)], [(28, 32)], [(5, 40)]],
        [(5,40),(30,35),(6,20),(19,31),(23,29),(28,32)])
    suite.add_test(schedule, [[(3, 6), (9, 20), (20, 35)], [(5, 6), (12, 30), (32, 40)], [(14, 30)]],
        [(3,6),(9, 20),(12,30),(5, 6),(14, 30),(20, 35),(32, 40)])
    suite.add_test(schedule, [[(1, 2)]],
        [(1,2)])
    suite.add_test(schedule, [[(1, 2), (3, 4)]],
        [(3,4),(1,2)])
    suite.add_test(schedule, [[(1, 3), (3, 4)]],
        [(1,3),(3,4)])
    suite.add_test(schedule, [[(1, 3)], [(2, 4)]],
        [(1,3),(2,4)])

    suite.add_test(superstring, 'BCDAABDDCADBCADC',
        ["CADBC","CDAABD","BCDA","DDCA","ADBCADC"])
    suite.add_test(superstring, 'CHADISBADCHDJKSHWBA',
        ["BADCH","HWBA","CHADIS","ADCHDJKSHWB"])
    suite.add_test(superstring, 'ABCDE',
        ["A","B","C","D","E"])
    suite.add_test(superstring, 'AHHIP',
        ["AHHI","HIP"])
    suite.add_test(superstring, 'HIPP',
        ["HIP","IPP"])
    suite.add_test(superstring, 'HELLOHELLPOPO',
        ["HELLO","LOHELLPO","OPO"])

    g = [ [(1,3), (2,6)], \
        [(0,3), (4,4)], \
        [(0,6), (3,2), (5,7)], \
        [(2,2), (4,4), (8,1)], \
        [(1,4), (3,4), (6,9)], \
        [(2,7), (6,2), (7,8)], \
        [(4,9), (5,2), (9,4)], \
        [(5,8), (8,3)], \
        [(3,1), (7,3), (9,2)], \
        [(6,4), (8,2)], \
        [(11,1)], \
        [(10,1)]]
    suite.add_test(dijkstra, [0, 2, 3, 8, 9], g, 0, 9)
    suite.add_test(dijkstra, [0, 2, 3, 8, 7], g, 0, 7)
    suite.add_test(dijkstra, None, g, 0, 10)
    suite.add_test(dijkstra, None, g, 0, 11)
    suite.add_test(dijkstra, [11, 10], g, 11 ,10)
    suite.add_test(dijkstra, [7], g, 7, 7)
    suite.add_test(dijkstra, [0, 1], g, 0, 1)
    suite.add_test(dijkstra, [5, 2, 3], g, 5, 3)
    suite.add_test(dijkstra, [5, 6, 4, 1], g, 5, 1)

    suite.run()
