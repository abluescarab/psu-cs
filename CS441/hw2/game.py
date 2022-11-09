import math
import random


class Game:
    def __init__(self):
        self.states = []

    def _generate_state(self):
        return random.sample(range(8), 8)

    # function Reproduce(x, y) returns an individual
    #     inputs: x, y, parent individuals

    #     n <- Length(x); c <- random number from 1 to n
    #     return Append(Substring(x, 1, c), Substring(y, c + 1, n))
    def _reproduce(self, x, y):
        # ensure at least one element from x and y
        c = random.randint(1, 7)
        return x[:c] + y[c:]

    def populate(self, population):
        state = None

        while len(self.states) < population:
            state = self._generate_state()

            if state not in self.states:
                self.states.append(state)

    def run(self, fitness_fn):
        x, y = random.sample(self.states, 2)
        self._reproduce(x, y)
