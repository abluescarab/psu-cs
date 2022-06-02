def tsp(g):
    minPath = list(range(len(g)))
    minWeight = 0

    for v in range(len(g)):
        minWeight += g[v][(v + 1) % len(g)]

    return tsp_rec([0], g, 0, minPath, minWeight)


def tsp_rec(path, g, w, minPath, minWeight):
    if w > minWeight:
        return (minPath, minWeight)

    if len(path) == len(g):
        return (path, w + g[path[-1]][0])

    for v in range(len(g)):
        if v not in path:
            (newPath, newWeight) = tsp_rec(path + [v], g, w + g[path[-1]][v],
                minPath, minWeight)

            if newWeight < minWeight:
                minPath = newPath
                minWeight = newWeight

    return (minPath, minWeight)


if __name__ == "__main__":
    g = [[0, 1, 4, 8, 6],
         [1, 0, 7, 11, 2],
         [4, 7, 0, 9, 12],
         [8, 11, 9, 0, 3],
         [6, 2, 12, 3, 0]]

    print(tsp(g))
