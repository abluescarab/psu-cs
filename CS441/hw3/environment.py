import random
from enum import Enum


class Action(Enum):
    Grab = 0
    North = 1
    South = 2
    East = 3
    West = 4


class Environment:
    def _has_wall(self, square: int, action: Action):
        if action == Action.North:
            return square < 10
        elif action == Action.South:
            return square > 89
        elif action == Action.East:
            return (square - 9) % 10 == 0
        elif action == Action.West:
            return square % 10 == 0

    def _calculate_reward(self, state, square: int, action: Action):
        if action == Action.Grab:
            return 10 if state[square] else -1
        else:
            return -5 if self._has_wall(square, action) else 0

    def _generate(self):
        state = []

        for _ in range(100):
            state.append(random.choices([0, 1], [0.5, 0.5])[0])

        return state, random.randint(0, 99)

    def run(self):
        reward = 0
        state, robby = self._generate()
