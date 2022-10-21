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
        if other:
            return self.path_cost < other.path_cost
        else:
            return False

    def __le__(self, other):
        if other:
            return self.path_cost <= other.path_cost
        else:
            return False

    def __gt__(self, other):
        if other:
            return self.path_cost > other.path_cost
        else:
            return True

    def __ge__(self, other):
        if other:
            return self.path_cost >= other.path_cost
        else:
            return True


class NodeQueue(PriorityQueue):
    def has_state(self, node: Node):
        for n in self._items:
            if n[1].state == node.state:
                return True

        return False

    def push(self, item: Node):
        super().push((item.path_cost, item))

    def pop(self):
        path_cost, node = -1, None

        while not node and len(self._items) > 0:
            path_cost, node = super().pop()

        return node

    def update(self, node: Node):
        if not self.has_state(node):
            self.push(node)
            return

        for i in range(len(self._items)):
            if self._items[i][1] and self._items[i][1].state == node.state:
                self._items[i] = (self._items[i][0], None)
                self.push(node)
