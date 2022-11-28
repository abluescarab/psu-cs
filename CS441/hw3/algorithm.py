from environment import Action, Environment


class QLearning:
    def __init__(self):
        self.N = 5000       # number of episodes
        self.M = 200        # number of actions per episode
        self.eta = 0.2      # learning rate
        self.epsilon = 0.1  # probability of greedy action
        self.gamma = 0.9    # discount factor
        self.Q = {}         # table of action values where keys = states
                            # and values = actions

    def _update_Q(self, state, action, reward):
        if state not in self.Q:
            self.Q[state] = [0] * (len(Action) + 1) # 0 = None

        self.Q[state][0 if not action else action] = reward

    def run(self):
        training_rewards = self._run(True)
        self.epsilon = 0.1
        trained_rewards = self._run(False)

        print(training_rewards)
        print(trained_rewards)
        # TODO: plot rewards

    def _run(self, decrease_epsilon):
        rewards = []

        for episode in range(0, self.N):
            env = Environment.generate()
            M = 0

            while M < self.M and not env.is_terminal():
                reward = self._run_action(env, 0)
                M += 1

            if (episode + 1) % 50 == 0 and decrease_epsilon:
                self.epsilon /= 2

                if self.epsilon < 0.000001:
                    self.epsilon = 0.0

            if (episode + 1) % 100 == 0:
                rewards.append(reward)

        return rewards

    def _run_action(self, env: Environment):
        # 1. Observe Robby's current state.
        # 2. Choose an action a_t using ε-greedy action selection.
        # 3. Perform the action.
        # 4. Receive reward r_t.
        # 5. Observe Robby's new state s_(t+1).
        # 6. Update Q(s_t, a_t) = Q(s_t, a_t) +
        #       ŋ(r_t + γ max_(a') Q(s_(t+1), a') - Q(s_t, a_t)).
        new_env = Environment.do_best_action(env, self.epsilon)



#     def run(self, env: Environment):
#         """Runs the algorithm.

#         Args:
#             env (Environment): environment to run with
#         """
#         training_rewards = self._run(env, True)
#         trained_rewards = self._run(env, False)

#         print(training_rewards)
#         print(trained_rewards)
#         # TODO: plot rewards

#     def update_q(self, state, action, reward):
#         if state not in self.Q:
#             self.Q[state] = [0] * (len(Action) + 1)

#         # 0 = None
#         self.Q[state][0 if action == None else action] = reward

#     def _run(self, env: Environment, training: True):
#         rewards = []

#         for ep in range(0, self.N):
#             env.generate()
#             reward = 0

#             for _ in range(0, self.M):
#                 reward += self._run_action(env, (env.state, env.reward_signal))

#             if ep % 100 == 0:
#                 rewards.append(reward)

#         return rewards

#     def _run_action(self, env: Environment, percept: tuple):
#         """Runs the algorithm.

#         Args:
#             env (Environment): environment to run with
#             percept (tuple): current state s' and reward signal r'

#         Pseudocode:
#             function Q-Learning-Agent(percept) returns an action
#                 inputs: percept, a percept indicating current state s' and reward signal r'
#                 persistent: Q, table of action values indexed by state and action, init. 0
#                             N_sa, table of frequencies for state-action pairs, init. 0
#                             s, a, r, previous state, action, and reward, init. null

#                 if Terminal?(s) then Q[s, None] <- r'
#                 if s is not null then
#                     increment N_sa[s, a]
#                     Q[s, a] <- Q[s, a] + α(N_sa[s, a])(r+γ max_a' Q[s', a'] - Q[s, a])
#                 s, a, r <- s', argmax_a' f(Q[s', a'], N_sa[s', a']), r'
#                 return a

#             repeat (for each episode):
#                 init. s
#                 repeat (for each step):
#                     choose a from s using policy derived from Q
#                     take action a, observe r, s'
#                     Q(s, a) <- Q(s, a) +
#         """

#         # s_, r_ = percept # equivalent to s', r'

#         # if env.is_terminal():
#         #     # Q[s, None] <- r'
#         #     self.update_q(self.s, None, r_(s_)) # calculate reward from current state

#         # if self.s:
#         #     self.update_n(self.s, self.a)
#         #     # Q[s, a] <- Q[s, a] + α(N_sa[s, a])(r+γ max_a' Q[s', a'] - Q[s, a])

#         # self.s = s_
#         # # a <- argmax_a' f(Q[s', a'], N_sa[s', a'])
#         # self.a = None
#         # self.r = r_(self.s)

#         # return self.a
