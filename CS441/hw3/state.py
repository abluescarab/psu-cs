import random
from enum import IntEnum


class Action(IntEnum):
    Grab = 0
    North = 1
    South = 2
    East = 3
    West = 4
    Nothing = 5


class State:
    def __init__(self, state, position, action: Action=Action.Nothing, reward: int=0):
        self._cans = 0
        self.state = state
        self.position = position
        self.action = action
        self.reward = reward

    def __str__(self):
        return str(self.__dict__)

    def __getitem__(self, index):
        if index is not None:
            return self.state[index]

    def __setitem__(self, index: int, value):
        self.state[index] = value

    def is_terminal(self):
        return self._cans == 0

    def copy(self):
        state = State(self.state.copy(), self.position, self.action, self.reward)
        state._cans = self._cans
        return state

    def do(self, action: Action, reward: int):
        result = self.copy()

        if action == Action.Grab:
            result[result.position] = 0
            result._cans -= 1
        else:
            square = State.next_square(result.position, action)

            if 0 <= square <= 99:
                result.position = square

        result.action = action
        result.reward = reward
        return result

    @staticmethod
    def generate():
        state = State([], random.randint(0, 99))

        for _ in range(100):
            has_can = random.randint(0, 1)
            state.state.append(has_can)
            state._cans += has_can

        return state

    @staticmethod
    def has_wall(square: int, action: Action):
        if action == Action.North:
            return square < 10
        elif action == Action.South:
            return square > 89
        elif action == Action.East:
            return (square - 9) % 10 == 0
        elif action == Action.West:
            return square % 10 == 0

    @staticmethod
    def next_square(square: int, action: Action):
        if action == Action.Nothing or \
            action == Action.Grab:
            return square
        elif action == Action.North:
            return square - 10
        elif action == Action.South:
            return square + 10
        elif action == Action.East:
            return square + 1
        elif action == Action.West:
            return square - 1

    @staticmethod
    def reward_for(state, action: Action, square: int=-1):
        if square == -1:
            square = State.next_square(state.position, action)

        reward = 0

        if action == Action.Grab and (0 <= square <= 99):
            reward = 10 if state[square] else -1
        else:
            reward = -5 if State.has_wall(square, action) else 0

        return reward

    @staticmethod
    def all_rewards(state):
        actions = [0] * len(Action)

        for act in Action:
            actions[act] = State.reward_for(state, act)

        return actions
