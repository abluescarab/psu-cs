from heapq import heappush, heappop


class PriorityQueue:
    def __init__(self, *items):
        self._items = []

        if items:
            for item in items:
                self.push(item)

    def __str__(self):
        return self._items.__str__()

    def __len__(self):
        return len(self._items)

    def __contains__(self, item):
        return item in self._items

    def __getitem__(self, index):
        return self._items[index]

    def push(self, item):
        """
        Push an item onto the queue.

        Args:
            item: item to push
        """
        heappush(self._items, item)

    def pop(self):
        """
        Pop an item from the queue.

        Returns:
            any: popped item
        """
        return heappop(self._items)

    def peek(self, skip=0):
        return self._items[skip]
