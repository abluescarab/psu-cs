import random
from state_queue import StateQueue


class Game:
    def _generate_state(self):
        return random.sample(range(8), 8)

    def _select_states(self, population, count):
        if count > len(population):
            count = len(population)

        return [population.peek(i)[1]
                for i in random.sample(range(len(population)), count)]

    def _reproduce(self, x, y):
        # ensure at least one element from x and y
        c = random.randint(1, 7)
        return x[:c] + y[c:]

    def _mutate(self, state):
        pass

    # function Genetic-Algorithm(population, Fitness-Fn) returns an individual
    #     inputs: population, a set of individuals
    #             Fitness-Fn, a function that measures fitness of individual

    #     repeat
    #         new_population <- empty set
    #         for i = 1 to Size(population) do
    #             x <- Random-Selection(population, Fitness-Fn)
    #             y <- Random-Selection(population, Fitness-Fn)
    #             child <- Reproduce(x, y)
    #             if small random probability then child <- Mutate(child)
    #             add child to new_population
    #         population <- new_population
    #     until some individual is fit enough, or enough time has elapsed
    #     return the best individual in population, according to Fitness-Fn
    def run(self, fitness_fn, population_size, max_cycles):
        population = StateQueue(fitness_fn)

        while len(population) < population_size:
            state = self._generate_state()

            if state not in population:
                population.push(state)

        cycle = 0

        while cycle < max_cycles and population.peek()[0] != 0:
            x, y = self._select_states(population, 2)
            child = self._reproduce(x, y)
            population.push(child)
            cycle += 1

        return population.pop()
