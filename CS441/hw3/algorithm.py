import random
from enum import Enum
from environment import Environment


class Action(Enum):
    Grab = 0
    North = 1
    South = 2
    East = 3
    West = 4


class QLearning:
    def __init__(self):
        self.N = 5000       # number of episodes
        self.M = 200        # number of actions per episode
        self.eta = 0.2      # learning rate
        self.epsilon = 0.1  # probability of greedy action
        self.gamma = 0.9    # discount factor

        self.Q = []     # table of action values where rows = states
                        # and cols = actions
        # self.N_sa = []  # table of frequencies for state-action pairs
        self.s = None   # previous state
        self.a = None   # previous action
        self.r = None   # previous reward

    def run(self, env: Environment):
        """Runs the algorithm.

        Args:
            env (Environment): environment to run with
        """
        training_rewards = self._run(env, True)
        trained_rewards = self._run(env, False)

        print(training_rewards)
        print(trained_rewards)
        # TODO: plot rewards

    def _run(self, env: Environment, training: True):
        rewards = []

        for ep in range(0, self.N):
            env.generate()
            reward = 0

            for _ in range(0, self.M):
                reward += self._run_action(env, (env.state, env.reward_signal))

            if ep % 100 == 0:
                rewards.append(reward)

        return rewards

    def _run_action(self, env: Environment, percept: tuple):
        """Runs the algorithm.

        Args:
            env (Environment): environment to run with
                are actions
            percept (tuple): current state s' and reward signal r'

        Pseudocode:
            function Q-Learning-Agent(percept) returns an action
                inputs: percept, a percept indicating current state s' and reward signal r'
                persistent: Q, table of action values indexed by state and action, init. 0
                            N_sa, table of frequencies for state-action pairs, init. 0
                            s, a, r, previous state, action, and reward, init. null

                if Terminal?(s) then Q[s, None] <- r'
                if s is not null then
                    increment N_sa[s, a]
                    Q[s, a] <- Q[s, a] + α(N_sa[s, a])(r+γ max_a' Q[s', a'] - Q[s, a])
                s, a, r <- s', argmax_a' f(Q[s', a'], N_sa[s', a']), r'
                return a
        """
        s_, r_ = percept # equivalent to s', r'

        if env.is_terminal():
            # Q[s, None] <- r'
            self.Q[(self.s, None)] = r_(s_) # calculate reward from current state

        if self.s:
            if (self.s, self.a) not in self.N_sa:
                self.N_sa[(self.s, self.a)] = 1
            else:
                self.N_sa[(self.s, self.a)] += 1
            # Q[s, a] <- Q[s, a] + α(N_sa[s, a])(r+γ max_a' Q[s', a'] - Q[s, a])

        self.s = s_
        # a <- argmax_a' f(Q[s', a'], N_sa[s', a'])
        self.a = None
        self.r = r_(self.s)

        return self.a
