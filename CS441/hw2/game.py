import random
from state_queue import StateQueue


class Game:
    def _generate_state(self):
        return random.sample(range(8), 8)

    def _select_states(self, count):
        if count > len(self.states):
            count = len(self.states)

        return [self.states.peek(i)[1] for i in range(count)]

    def _reproduce(self, x, y):
        # ensure at least one element from x and y
        c = random.randint(1, 7)
        return x[:c] + y[c:]


    def run(self, fitness_fn, population, max_cycles):
        self.states = StateQueue(fitness_fn)

        while len(self.states) < population:
            state = self._generate_state()

            if state not in self.states:
                self.states.push(state)

        cycle = 0

        while cycle < max_cycles and self.states.peek()[0] != 0:
            x, y = self._select_states(2)
            child = self._reproduce(x, y)
            self.states.push(child)
            cycle += 1

        return self.states.pop()
