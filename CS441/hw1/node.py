from priority_queue import PriorityQueue


class Node:
    def __init__(self, state, path_cost):
        self.state = state
        self.path_cost = path_cost

    def __repr__(self):
        return self.__str__()

    def __str__(self):
        return f"{{'state': {self.state}, 'path_cost': {self.path_cost}}}"

    def __eq__(self, other):
        if isinstance(other, Node):
            return (self.state == other.state) and \
                    (self.path_cost == other.path_cost)

        return NotImplemented

    def __ne__(self, other):
        return not self.__eq__(other)

    def __lt__(self, other):
        return self.path_cost < other.path_cost

    def __le__(self, other):
        return self.path_cost <= other.path_cost

    def __gt__(self, other):
        return self.path_cost > other.path_cost

    def __ge__(self, other):
        return self.path_cost >= other.path_cost


class NodeQueue:
    def __init__(self, *nodes):
        self.queue = PriorityQueue()

        if nodes:
            for node in nodes:
                self.push(node)

    def __str__(self):
        return self.queue.__str__()

    def __len__(self):
        return len(self.queue)

    def __contains__(self, node):
        return node in self.queue

    def push(self, node: Node):
        self.queue.push(node)

    def pop(self):
        return self.queue.pop()
