import random
from state import State
from state import Action
from math import sqrt
import matplotlib.pyplot as plt


SHOW_PLOTS = 0 # whether to display plots to user


def stdev(l):
    mean = sum(l) / len(l)
    return sqrt(sum((x - mean) ** 2 for x in l) / (len(l) - 1))


class QLearning:
    def __init__(self):
        self.N = 5000       # number of episodes
        self.M = 200        # number of actions per episode
        self.eta = 0.2      # learning rate
        self.gamma = 0.9    # discount factor
        self.epsilon = 0.1  # probability of greedy action
        self._Q = {}        # table of action values where keys = state
                            # and values = actions

    def _plot(self, data):
        plt.plot([(x + 1) * 100 for x in range(len(data))], data)
        plt.xlabel("Episode")
        plt.ylabel("Reward")
        plt.grid(True)
        plt.show()

    def _get_Q(self, state: State, action: Action=None):
        if action is None:
            return self._Q[tuple(state.state)]

        return self._Q[tuple(state.state)][action]

    def _update_Q(self, state: State, action: Action=None, reward: int=0):
        t = tuple(state.state)

        if t not in self._Q:
            self._Q[t] = State.all_rewards(state)

        if action is not None:
            self._Q[t][action] += reward

    def run(self):
        training_rewards = self._run(True, "Training agent...")
        print(f"Average reward:     {round(sum(training_rewards) / len(training_rewards), 1)}")
        print(f"Standard deviation: {round(stdev(training_rewards), 1)}")

        if SHOW_PLOTS:
            training_plot = training_rewards[::100]
            self._plot(training_plot)

        self.epsilon = 0.1
        trained_rewards = self._run(False, "Testing trained agent...")
        print(f"Average reward:     {round(sum(trained_rewards) / len(trained_rewards), 1)}")
        print(f"Standard deviation: {round(stdev(trained_rewards), 1)}")

        if SHOW_PLOTS:
            trained_plot = trained_rewards[::100]
            self._plot(trained_plot)

    def _run(self, decrease_epsilon, print_message):
        rewards = [0] * self.N

        for N in range(0, self.N):
            state = State.generate()
            self._update_Q(state)
            M = 0
            reward = 0

            while M < self.M and not state.is_terminal():
                print(f"\r{print_message} {str(N + 1).ljust(4, ' ')}/{self.N}", end="")

                possible_rewards = State.all_rewards(state)
                pick_rand = random.choices([False, True],
                                        [1 - self.epsilon,
                                        self.epsilon])[0]

                if pick_rand:
                    index = random.randint(0, len(possible_rewards) - 1)
                else:
                    index = max(range(len(possible_rewards)), key=possible_rewards.__getitem__)

                result = state.do(index, possible_rewards[index])
                self._update_Q(result)

                q_reward = \
                    self.eta * (result.reward
                    + (self.gamma * max(self._get_Q(result)))
                    - self._get_Q(state, state.action))
                self._update_Q(state, state.action, q_reward)
                state = result
                reward += result.reward

                M += 1

            if (N + 1) % 50 == 0 and decrease_epsilon:
                self.epsilon /= 2

                if self.epsilon < 0.000001:
                    self.epsilon = 0.0

            rewards[N] = reward

        print()
        return rewards
