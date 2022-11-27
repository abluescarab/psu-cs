import random
from enum import Enum
from algorithm import QLearning


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

    def _get_next_square(self, square, action: Action):
        if action == Action.Grab or self._has_wall(square, action):
            return square
        elif action == Action.North:
            return square - 10
        elif action == Action.South:
            return square + 10
        elif action == Action.East:
            return square + 1
        elif action == Action.West:
            return square - 1

    def _calculate_reward(self, state, action: Action):
        square = self._get_next_square(self.robby, action)

        if action == Action.Grab:
            return 10 if state[square] else -1
        else:
            return -5 if self._has_wall(square, action) else 0

    def _is_terminal(self):
        return not any(self.state)

    def generate(self):
        self.state = []

        for _ in range(100):
            self.state.append(random.choices([0, 1], [0.5, 0.5])[0])

        self.robby = random.randint(0, 99)

    def reward_signal(self, state):
        actions = {}

        for action in Action:
            actions[action] = self._calculate_reward(state, action)

        return max(actions, key=actions.get)
