from algorithm import QLearning
from environment import Environment


if __name__ == "__main__":
    env = Environment()
    q = QLearning()
    q.run(env)
