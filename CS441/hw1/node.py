from board import Heuristic
from priority_queue import PriorityQueue


class Node:
    def __init__(self, state, path_cost=0, parent=None):
        self.state = state
        self.path_cost = path_cost
        self.parent = parent

    def __repr__(self):
        return self.__str__()

    def __str__(self):
        return f"{{'state': {self.state}, 'path_cost': {self.path_cost}}}"

    def __eq__(self, other):
        if isinstance(other, Node):
            return (self.state == other.state) and \
                    (self.path_cost == other.path_cost) and \
                    (self.parent == other.parent)

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

    def expand(self, board: Board):
        return [Node(board.result(self.state, action), self.path_cost + 1, self)
                for action in board.actions(self.state)]


class NodeQueue(PriorityQueue):
    def __contains__(self, item):
        return item in [t[1] for t in self._items]

    def __getitem__(self, key):
        for cost, item in self._items:
            if key == item:
                return (cost, item)

        raise KeyError(f"{key} is not in priority queue")

    def has_state(self, state):
        """
        Check if the queue has a node with a given state.

        Args:
            state: desired state

        Returns:
            bool: True if state exists in queue; False otherwise
        """
        for n in self._items:
            if n[1].state == state:
                return True

        return False

    def path_cost(self, node: Node):
        """
        Get the path cost of the given node in the queue.

        Args:
            node (Node): node to get

        Returns:
            int: path cost from node
        """
        for n in self._items:
            if n[1].state == node.state:
                return n[0]

        return 0

    def push(self, item: Node):
        """
        Push an item onto the queue.

        Args:
            item (Node): node to push
        """
        super().push((item.path_cost, item))

    def pop(self):
        """
        Pop an item off the queue.

        Returns:
            Node: popped node
        """
        path_cost, node = -1, None

        while not node and len(self._items) > 0:
            path_cost, node = super().pop()

        return node

    def update(self, node: Node):
        """
        Update a node in the queue based on its state.

        Args:
            node (Node): node to update
        """
        if not self.has_state(node):
            self.push(node)
            return

        for i in range(len(self._items)):
            if self._items[i][1] and self._items[i][1].state == node.state:
                self._items[i] = (self._items[i][0], None)
                self.push(node)
