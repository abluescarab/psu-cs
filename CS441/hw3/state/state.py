class State:
    def __init__(self, state, position):
        self._cans = 0
        self._state = state
        self.position = position

    def __str__(self):
        return str(self.__dict__)

    def __getitem__(self, index):
        if index is not None:
            return self._state[index]

    def __setitem__(self, index, value):
        self._state[index] = value

    def __contains__(self, state):
        return state in self._state

    def is_terminal(self):
        return self._cans == 0

    def copy(self):
        state = State(self._state.copy(), self.position)
        state._cans = self._cans
        return state
