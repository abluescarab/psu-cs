import random
from .action import Action
from .environment import Environment
from .state import State


def has_wall(square, action):
    if action == Action.North:
        return square < 10
    elif action == Action.South:
        return square > 89
    elif action == Action.East:
        return (square - 9) % 10 == 0
    elif action == Action.West:
        return square % 10 == 0


def next_square(square, action):
    if action == Action.Nothing or \
        action == Action.Grab or \
        has_wall(square, action):
        return square
    elif action == Action.North:
        return square - 10
    elif action == Action.South:
        return square + 10
    elif action == Action.East:
        return square + 1
    elif action == Action.West:
        return square - 1


def do(state, action):
    result = state.copy()

    if action == Action.Grab:
        result[result.position] = 0
        result._cans -= 1
    else:
        result.position = next_square(result.position, action)

    return result


def generate():
    state = State([], random.randint(0, 99))

    for i in range(100):
        has_can = random.randint(0, 1)
        state._state.append(has_can)
        state._cans += has_can

    return state


def reward_for(state, action: Action):
    square = next_square(state.position, action)
    reward = 0

    if action == Action.Grab:
        reward = 10 if state[square] else -1
    else:
        reward = -5 if has_wall(square, action) else 0

    return reward


def all_rewards(state):
    actions = [0] * len(Action)

    for act in Action:
        actions[act] = reward_for(state, act)

    return actions


def update_env(env, action):
    return Environment(do(env.state, action),
                       Action(action),
                       reward_for(env.state, action))

