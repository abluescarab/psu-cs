from .state import State
from .action import Action


class Environment:
    def __init__(self, state: State, action=Action.Nothing, reward=0):
        self.state = state
        self.action = action
        self.reward = reward

    def __str__(self):
        return (f"{{'state': {self.state}, "
                f"'action': '{str(Action(self.action))}', "
                f"'reward': {self.reward}}}")

    def copy(self):
        return Environment(self.state.copy(), self.action, self.reward)
