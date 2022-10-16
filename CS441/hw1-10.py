from enum import Enum
from random import sample, choice, choices


def get_square(index):
    if 0 <= index <= 2:
        return 0, index

    if 3 <= index <= 5:
        return 1, index - 3

    if 6 <= index <= 8:
        return 2, index - 6

    return -1


class Action(Enum):
    suck = 0
    left = 1
    down = 2
    right = 3
    up = 4


class Room:
    def __init__(self, dirt):
        # True = dirt
        self.environment = [[False, False, False],
                            [False, False, False],
                            [False, False, False]]
        self.randomize(dirt)

    def randomize(self, dirt):
        indices = sample(range(8), dirt)

        for i in range(len(indices)):
            row, col = get_square(indices[i])
            self.environment[row][col] = True

    def is_dirty(self, row, col):
        return self.environment[row][col]

    def make_dirty(self, row, col):
        self.environment[row][col] = True

    def clean(self, row, col):
        if not self.environment[row][col]:
            return False

        self.environment[row][col] = False
        return True

    def is_clean(self):
        for i in range(3):
            for j in range(3):
                if self.environment[i][j]:
                    return False

        return True


class Agent:
    def __init__(self):
        self.row = -1
        self.col = -1

    def _get_action(self, room):
        return None

    def _can_move(self, action: Action):
        if action == Action.left:
            return self.col != 0
        elif action == Action.down:
            return self.row != 2
        elif action == Action.right:
            return self.col != 2
        elif action == Action.up:
            return self.row != 0
        else:
            return False

    def move(self, action: Action):
        if action == Action.left:
            self.col -= 1
        elif action == Action.down:
            self.row += 1
        elif action == Action.right:
            self.col += 1
        elif action == Action.up:
            self.row -= 1

    def in_square(self, row, col):
        return row == self.row and col == self.col

    def spawn(self, row = -1, col = -1):
        if row > -1 and col > -1:
            self.row = row
            self.col = col
        else:
            self.row, self.col = get_square(choice(range(8)))

    def do_action(self, room: Room, murphy=False):
        action = self._get_action(room, murphy)

        if action == Action.suck:
            success = choices([True, False], weights=[0.75, 0.25])[0] \
                if murphy else True

            if success:
                room.clean(self.row, self.col)
            elif not room.is_dirty(self.row, self.col):
                room.make_dirty(self.row, self.col)
        else:
            self.move(action)


class SimpleReflexAgent(Agent):
    def __init__(self):
        super().__init__()

    def _get_action(self, room, murphy=False):
        dirty = room.is_dirty(self.row, self.col)

        # 25% of the time, the suck action fails to clean if the floor is
        # dirty and deposits dirt if the floor is clean; the dirt sensor gives
        # the wrong answer 10% of the time
        if murphy:
            if choices([dirty, not dirty], weights=[0.9, 0.1])[0]:
                return Action.suck
        elif dirty:
            return Action.suck

        actions = []

        if self._can_move(Action.left):
            actions.append(Action.left)

        if self._can_move(Action.right):
            actions.append(Action.right)

        if self._can_move(Action.up):
            actions.append(Action.up)

        if self._can_move(Action.down):
            actions.append(Action.down)

        return choice(actions)


class RandomizedAgent(Agent):
    def __init__(self):
        super().__init__()

    def _get_action(self, room, murphy=False):
        actions = [Action.suck]

        if self._can_move(Action.left):
            actions.append(Action.left)

        if self._can_move(Action.right):
            actions.append(Action.right)

        if self._can_move(Action.up):
            actions.append(Action.up)

        if self._can_move(Action.down):
            actions.append(Action.down)

        return choice(actions)


def draw_row(row, col, agent: Agent, room: Room):
    clean = " "
    dirty = "*"
    clean_agent = "c"
    dirty_agent = "d"

    if agent.in_square(row, col):
        return dirty_agent if room.is_dirty(row, col) else clean_agent
    else:
        return dirty if room.is_dirty(row, col) else clean


def draw(agent, room, prefix=""):
    spaces = " " * (len(prefix) + 1)
    grid = f"{spaces}+---------+\n" \
            f"{spaces}|"
    line = ""

    for i in range(3):
        for j in range(3):
            line += f" {draw_row(i, j, agent, room)} "

        grid += line + "|\n"
        line = ""

        if i == 0:
            line += prefix + " |"
        elif i < 2:
            grid += f"{spaces}|"

    grid += f"{spaces}+---------+\n"
    print(grid)


def run(dirt_amount, randomized=False, murphy=False):
    max_steps = 10000
    clean = False
    step = 0
    room = Room(dirt_amount)
    agent = SimpleReflexAgent() if not randomized else RandomizedAgent()

    agent.spawn()

    while not clean:
        if step == max_steps:
            return step

        agent.do_action(room, murphy)
        step += 1

        if room.is_clean():
            clean = True

    return step


def run_multi(count, dirt_amount, randomized=False, murphy=False):
    sum = 0

    for i in range(count):
        sum += run(dirt_amount, randomized, murphy)

    return sum // count


if __name__ == "__main__":
    tests = [
        {   "randomized": False,
            "dirt": 1,
            "murphy": False,
            "average": -1 },
        {   "randomized": False,
            "dirt": 3,
            "murphy": False,
            "average": -1 },
        {   "randomized": False,
            "dirt": 5,
            "murphy": False,
            "average": -1 },
        {   "randomized": True,
            "dirt": 1,
            "murphy": False,
            "average": -1 },
        {   "randomized": True,
            "dirt": 3,
            "murphy": False,
            "average": -1 },
        {   "randomized": True,
            "dirt": 5,
            "murphy": False,
            "average": -1 },
        {   "randomized": False,
            "dirt": 1,
            "murphy": True,
            "average": -1 },
        {   "randomized": False,
            "dirt": 3,
            "murphy": True,
            "average": -1 },
        {   "randomized": False,
            "dirt": 5,
            "murphy": True,
            "average": -1 },
        {   "randomized": True,
            "dirt": 1,
            "murphy": True,
            "average": -1 },
        {   "randomized": True,
            "dirt": 3,
            "murphy": True,
            "average": -1 },
        {   "randomized": True,
            "dirt": 5,
            "murphy": True,
            "average": -1 }
    ]

    for test in tests:
        dirt = test["dirt"]
        random = test["randomized"]
        murphy = test["murphy"]

        test["average"] = run_multi(100, dirt, random, murphy)

        print(f"{'Reflex' if not random else 'Random'} {dirt}"
              f"{' (murphy)' if murphy else ''}: {test['average']}")
