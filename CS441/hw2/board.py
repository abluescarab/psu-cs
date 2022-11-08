import math
import random


class Board:
    def __init__(self):
        self.states = []

    def _generate_state(self):
        # 0 = no queen present, 1 = queen present
        state = [0, 0, 0, 0, 0, 0, 0, 0,
                 0, 0, 0, 0, 0, 0, 0, 0,
                 0, 0, 0, 0, 0, 0, 0, 0,
                 0, 0, 0, 0, 0, 0, 0, 0,
                 0, 0, 0, 0, 0, 0, 0, 0,
                 0, 0, 0, 0, 0, 0, 0, 0,
                 0, 0, 0, 0, 0, 0, 0, 0,
                 0, 0, 0, 0, 0, 0, 0, 0]
        queens = random.sample(range(64), 8)

        for queen in queens:
            state[queen] = 1

        return state

    # function Reproduce(x, y) returns an individual
    #     inputs: x, y, parent individuals

    #     n <- Length(x); c <- random number from 1 to n
    #     return Append(Substring(x, 1, c), Substring(y, c + 1, n))
    def _reproduce(self, x, y):
        x_count = 0

        # ensure that there is at least one queen from x and y in the result
        while x_count == 0 or x_count == 8:
            c = random.choice(range(64))
            result = x[:c]
            x_count = sum(result)

        # x_count = 0
        # result = []
        # tries = 100

        # # ensure that 8 queens are always present after slicing; if unable to
        # # find a solution after multiple slices, return failure
        # while sum(result) < 8 and tries > 0:
        #     # ensure that there is at least one queen from x in the result
        #     while x_count == 0 or x_count == 8:
        #         c = random.choice(range(64))
        #         result = x[:c] + [0] * (64 - c)
        #         x_count = sum(result)

        #     # get the positions of queens from y
        #     y_queens = [i for i, v in enumerate(y) if v == 1]

        #     # apply queen positions from y to result until 8 queens total
        #     for i in range(x_count, 8):
        #         result[y_queens[i]] = 1

        #     tries -= 1

        # print(sum(result))
        # return result

    def populate(self, population):
        state = None

        while len(self.states) < population:
            state = self._generate_state()

            if state not in self.states:
                self.states.append(state)

    def run(self):
        x, y = random.sample(self.states, 2)
        self._reproduce(x, y)
