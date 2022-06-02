def queens(n):
    res = queens_rec([], n)

    for x in res:
        print(x)


def queens_rec(qs, n):
    if len(qs) == n:
        yield qs

    for c in range(n):
        if c not in qs and allSafe(qs, len(qs), c):
            yield from queens_rec(qs + [c], n)


def allSafe(qs, r, c):
    for old_r in range(len(qs)):
        old_c = qs[old_r]
        dr = abs(r - old_r)
        dc = abs(c - old_c)
        if dr == dc:
            return False

    return True


if __name__ == "__main__":
    queens(4)
