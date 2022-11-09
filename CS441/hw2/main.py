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
    game = Game()
    population_sizes = [10, 100, 1000]
    mutation_chances = [0.1, 0.2, 0.5, 1.0, 2.0]
    cycles = [100, 1000, 10000]
    overall = []

    for pop in population_sizes:
        results = []

        for mutation_chance in mutation_chances:
            average = 0

            for cycle in cycles:
                for i in range(10):
                    fitness, result = game.run(fitness_fn, pop, mutation_chance, 100)
                    average += fitness

                result = (average / 10, mutation_chance, cycle, pop)
                print(f"Average fitness for pop {pop} w/ {result[1]}% mutation chance and {cycle} cycles: {result[0]}")
                results.append(result)

        results = sorted(results, key=lambda t: t[0])
        overall.extend(results)
        print(f"Best fitness: {results[0][0]} with {results[0][1]}% mutation chance and {results[0][2]} cycles")

    overall = sorted(overall, key=lambda t: t[0])
    print(f"Best overall: pop. {overall[0][3]} with fitness {overall[0][0]}, {overall[0][1]}% mutation chance, and {overall[0][2]} cycles")
