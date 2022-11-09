import random
from state_queue import StateQueue


class Game:
    def __init__(self, fitness_fn):
        self.fitness_fn = fitness_fn
        self.states = StateQueue(fitness_fn)

    def _generate_state(self):
        return random.sample(range(8), 8)

    def _select_states(self, count):
        if count > len(self.states):
            count = len(self.states)

        return [self.states.pop() for _ in range(count)]

    def _reproduce(self, x, y):
        # ensure at least one element from x and y
        c = random.randint(1, 7)
        return x[:c] + y[c:]

    def populate(self, population):
        state = None

        while len(self.states) < population:
            state = self._generate_state()

            if state not in self.states:
                self.states.push(state)

    def run(self):
        x, y = self._select_states(2)
        self._reproduce(x, y)
