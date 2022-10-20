from priority_queue import PriorityQueue


class Node:
    def __init__(self, state, path_cost):
        self.state = state
        self.path_cost = path_cost


class NodeQueue:
    def __init__(self):
        self.queue = PriorityQueue()
        self.map = {}

    def __str__(self):
        return self.queue.__str__()

    def __len__(self):
        return len(self.queue)

    def __contains__(self, node):
        return node in self.queue

    def push(self, node: Node):
        item = (node.path_cost, node)
        self.map[node] = item
        self.queue.push(item)

    def pop(self):
        path_cost, node = self.queue.pop()
        del self.map[node]
        return node
