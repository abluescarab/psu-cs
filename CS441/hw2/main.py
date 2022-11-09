from game import Game

def fitness_fn(state):
    checks = 0

    # number of checks on the board
    for i in range(len(state)):
        for j in range(len(state)):
            if j == i:
                continue

            if abs(j - i) == abs(state[j] - state[i]) or \
                state[j] == state[i]:
                checks += 1

    return checks


if __name__ == "__main__":
    game = Game(fitness_fn)
    game.run(10)
