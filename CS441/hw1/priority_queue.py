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

    def push(self, item):
        heappush(self._items, item)

    def pop(self):
        return heappop(self._items)
