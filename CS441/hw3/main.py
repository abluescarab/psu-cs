from environment import Environment

"""
function Q-Learning-Agent(percept) returns an action
    inputs: percept, a percept indicating current state s' and reward signal r'
    persistent: Q, table of action values indexed by state and action, init. 0
                N_sa, table of frequencies for state-action pairs, init. 0
                s, a, r, previous state, acction, and reward, init. null

    if Terminal?(s) then Q[s, None] <- r'
    if s is not null then
        increment N_sa[s, a]
        Q[s, a] <- Q[s, a] + α(N_sa[s, a])(r+γ max_a' Q[s', a'] - Q[s, a])
    s, a, r <- s', argmax_a' f(Q[s', a'], N_sa[s', a']), r'
    return a
"""


if __name__ == "__main__":
    env = Environment()
    env.run()
