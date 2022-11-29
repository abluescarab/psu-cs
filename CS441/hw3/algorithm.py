import random
import state.utils as su
import state.state as state
from state.environment import Environment
from math import sqrt
import matplotlib.pyplot as plt


def stdev(l):
    mean = sum(l) / len(l)
    return sqrt(sum((x - mean) ** 2 for x in l) / (len(l) - 1))


class QLearning:
    def __init__(self):
        # TODO: set to 5000
        self.N = 5000       # number of episodes
        self.M = 200        # number of actions per episode
        self.eta = 0.2      # learning rate
        self.epsilon = 0.1  # probability of greedy action
        self.gamma = 0.9    # discount factor
        self.Q = {}         # table of action values where keys = state
                            # and values = actions

    def add_Q(self, env: Environment):
        if env.state in self.Q:
            return

        self.Q[env.state] = su.all_rewards(env.state)

    def run(self):
        training_rewards = self._run(True, "Training agent...")
        print(f"Average reward:     {round(sum(training_rewards) / len(training_rewards), 1)}")
        print(f"Standard deviation: {round(stdev(training_rewards), 1)}")

        training_plot = training_rewards[::100]
        print(training_plot)
        plt.plot(range(len(training_plot)), training_plot)
        plt.show()

        self.epsilon = 0.1
        trained_rewards = self._run(False, "Testing trained agent...")
        print(f"Average reward:     {round(sum(trained_rewards) / len(trained_rewards), 1)}")
        print(f"Standard deviation: {round(stdev(trained_rewards), 1)}")
        # TODO: plot rewards

    def _run(self, decrease_epsilon, print_message):
        rewards = [0] * self.N

        for N in range(0, self.N):
            env = Environment(su.generate())
            self.add_Q(env)
            M = 0
            reward = 0

            while M < self.M and not env.state.is_terminal():
                print(f"\r{print_message} {str(N + 1).ljust(4, ' ')}/{self.N}", end="")
                result = self._run_action(env)
                env, reward = result, reward + result.reward
                M += 1

            if (N + 1) % 50 == 0 and decrease_epsilon:
                self.epsilon /= 2

                if self.epsilon < 0.000001:
                    self.epsilon = 0.0

            rewards[N] = reward

        print()
        return rewards

    def _run_action(self, env: Environment):
        curr_rewards = su.all_rewards(env.state)
        pick_rand = random.choices([False, True],
                                   [1 - self.epsilon,
                                   self.epsilon])[0]

        if pick_rand:
            index = random.randint(0, len(curr_rewards) - 1)
        else:
            index = max(range(len(curr_rewards)), key=curr_rewards.__getitem__)

        result = su.update_env(env, index)
        self.add_Q(result)

        reward = self.epsilon * (result.reward + self.gamma *
            max(self.Q[result.state]) - self.Q[env.state][env.action])
        self.Q[env.state][env.action] += reward
        return result
