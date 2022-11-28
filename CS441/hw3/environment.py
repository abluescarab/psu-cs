import random
from enum import IntEnum


class Action(IntEnum):
    Grab = 1
    North = 2
    South = 3
    East = 4
    West = 5


class Environment:
    def __init__(self, state, position, action=None, reward=0):
        self.state = state
        self.position = position
        self.action = action
        self.reward = reward

    def __str__(self):
        return str(self.__dict__)

    def is_terminal(self):
        return not any(self.state)

    @staticmethod
    def has_wall(square, action):
        if action == Action.North:
            return square < 10
        elif action == Action.South:
            return square > 89
        elif action == Action.East:
            return (square - 9) % 10 == 0
        elif action == Action.West:
            return square % 10 == 0

    @staticmethod
    def next_square(square, action):
        if action == Action.Grab or Environment.has_wall(square, action):
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
    def generate():
        state = []

        for _ in range(100):
            state.append(random.choices([0, 1], [0.5, 0.5])[0])

        return Environment(state, random.randint(0, 99))

    @staticmethod
    def do_best_action(env, epsilon):
        actions = [0] * len(Action)
        new_state, new_pos = env.state.copy(), env.position

        for action in Action:
            square = Environment.next_square(new_pos, action)
            reward = 0

            if action == Action.Grab:
                reward = 10 if new_state[square] else -1
            else:
                reward = -5 if Environment.has_wall(square, action) else 0

            actions[int(action) - 1] = reward

        pick_rand = random.choices([False, True], [1 - epsilon, epsilon])[0]

        if pick_rand:
            index = random.randint(0, len(actions) - 1)
        else:
            index = max(range(len(actions)), key=actions.__getitem__)

        action = Action(index + 1)

        if action == Action.Grab:
            new_state[square] = 0
        else:
            new_pos = square

        return Environment(new_state, new_pos, action, actions[index])

    # @staticmethod
    # def result(state, position, action):
    #     square = Environment.next_square(position, action)
    #     reward = 0

    #     if action == Action.Grab:
    #         reward = 10 if state[square] else -1
    #     else:
    #         reward = -5 if Environment.has_wall(square, action) else 0

    #     return reward

    # def best_action(self, epsilon):
    #     actions = [None] * len(Action)

    #     for action in Action:
    #         actions[int(action) - 1] = \
    #             Environment.result(self.state, self.position, action)

    #     pick_rand = random.choices([False, True], [1 - epsilon, epsilon])[0]

    #     if pick_rand:
    #         return random.choice(actions)
    #     else:
    #         return max(actions, key=lambda a: a[0])


#     @staticmethod
#     def _has_wall(self, square: int, action: Action):
#         if action == Action.North:
#             return square < 10
#         elif action == Action.South:
#             return square > 89
#         elif action == Action.East:
#             return (square - 9) % 10 == 0
#         elif action == Action.West:
#             return square % 10 == 0

#     @staticmethod
#     def _get_next_square(self, square, action: Action):
#         if action == Action.Grab or self._has_wall(square, action):
#             return square
#         elif action == Action.North:
#             return square - 10
#         elif action == Action.South:
#             return square + 10
#         elif action == Action.East:
#             return square + 1
#         elif action == Action.West:
#             return square - 1

#     @staticmethod
#     def best_action(self, state):
#         actions = {}

#         for action in Action:
#             actions[action] = self._calculate_reward(state, action)

#         return max(actions, key=actions.get)

#     def _is_terminal(self):
#         return not any(self.state)

#     def _calculate_reward(self, state, action: Action):
#         square = self._get_next_square(self.robby, action)

#         if action == Action.Grab:
#             return 10 if state[square] else -1
#         else:
#             return -5 if self._has_wall(square, action) else 0

#     def generate(self):
#         self.state = []

#         for _ in range(100):
#             self.state.append(random.choices([0, 1], [0.5, 0.5])[0])

#         self.robby = random.randint(0, 99)

#     def result(self, action):
