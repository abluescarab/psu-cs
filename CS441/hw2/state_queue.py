from priority_queue import PriorityQueue


class StateQueue(PriorityQueue):
    def __init__(self, fitness_fn, *states):
        super().__init__()
        self.fitness_fn = fitness_fn

        if states:
            for state in states:
                self.push(state)

    def __contains__(self, item):
        return item in [t[1] for t in self._items]

    def push(self, item):
        """
        Push an item onto the queue.

        Args:
            item: item to push
        """
        super().push((self.fitness_fn(item), item))

    def pop(self):
        """
        Pop an item off the queue.

        Returns:
            item: popped item
        """
        val, item = -1, None

        while not item and len(self._items) > 0:
            val, item = super().pop()

        return (val, item)

    def update(self, item):
        """
        Update an item in the queue based on its value.

        Args:
            item: item to update
        """
        if not self.__contains__(item):
            self.push(item)
            return

        for i in range(len(self._items)):
            if self._items[i][1] == item:
                self._items[i] = (self._items[i][0], None)
                self.push(item)
