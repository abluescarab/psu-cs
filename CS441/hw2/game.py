import random
from state_queue import StateQueue


class Game:
    def _generate_state(self):
        """
        Generates a game state.

        Returns:
            list: queen positions by column then row
        """
        return random.sample(range(8), 8)

    def _select_individuals(self, population, count):
        """
        Selects individuals from the population.

        Args:
            population (list): current population of states
            count (int): number of individuals to select
        """
        if count > len(population):
            count = len(population)

        return [population.pop()[1] for _ in range(count)]

    def _reproduce(self, x, y):
        """
        Reproduces with parents x and y.

        Args:
            x, y: individuals from the population

        Returns:
            list: state produced from parents
        """
        # ensure at least one element from x and y
        c = random.randint(1, 7)
        return x[:c] + y[c:]

    def _mutate(self, individual, mutation_chance):
        """
        Mutates a given individual.

        Args:
            individual: state from the population
            mutation_chance: percent chance to mutate out of 100
        """
        if random.choices([True, False],
                          [mutation_chance, 100.0 - mutation_chance])[0]:
            # pick the queen by column
            column = random.randint(0, 7)
            # generate the list of possible positions, minus the current position
            positions = list(range(8))
            positions.remove(individual[column])
            # change the position of the queen
            individual[column] = random.choice(positions)

    def run(self, fitness_fn, population_size, mutation_chance, max_cycles):
        """
        Run the game.

        Args:
            fitness_fn: function to calculate individual fitness
            population_size: number of individuals in population
            mutation_chance: percent chance for a child to mutate out of 100
            max_cycles: maximum number of reproduction cycles

        Returns:
            list: most fit individual
        """
        population = StateQueue(fitness_fn)

        while len(population) < population_size:
            state = self._generate_state()

            if state not in population:
                population.push(state)

        cycle = 0

        while cycle < max_cycles and population.peek()[0] != 0:
            x, y = self._select_individuals(population, 2)

            for _ in range(2):
                child = self._reproduce(x, y)
                self._mutate(child, mutation_chance)
                population.push(child)

            cycle += 1

        return population.pop()
