from heapq import heappush, heappop


class PriorityQueue:
    def __init__(self, *items):
        self.items = []

        if items:
            for item in items:
                self.push(item)

    def __str__(self):
        return self.items.__str__()

    def __len__(self):
        return len(self.items)

    def __contains__(self, item):
        return item in self.items

    def push(self, item):
        heappush(self.items, item)

    def pop(self):
        return heappop(self.items)
