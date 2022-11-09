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

    def __getitem__(self, key):
        for item in self._items:
            if key == item:
                return item

        raise KeyError(f"{key} is not in priority queue")

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
