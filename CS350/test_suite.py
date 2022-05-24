class Test:
    def __init__(self, func, expected, *args, **kwargs):
        self.func = func
        self.args = args
        self.kwargs = kwargs
        self.expected = expected

    def run(self):
        try:
            result = self.func(*self.args, **self.kwargs)

            assert result == self.expected, \
                f"[{self.func.__name__}] fail: got {result}, expected {self.expected}"
        except AssertionError as e:
            print(e)
            return False
        else:
            print(f"[{self.func.__name__}] pass: got {self.expected}")
            return True


class TestSuite:
    def __init__(self, separate_results_by_function=False):
        self._tests = []
        self._performed = 0
        self._passed = 0
        self.separate_results_by_function = separate_results_by_function

    def add_test(self, func, expected, *args, **kwargs):
        self._tests.append(Test(func, expected, *args, **kwargs))

    def _print_results(self, func_name=None):
        if len(self._tests) > 0:
            print()

        print(f"[{func_name if func_name is not None else 'all'}] result:", end=" ")

        if len(self._tests) > 0:
            print(f"{self._passed} passed, "
                  f"{self._performed - self._passed} failed, {self._performed} run")
        else:
            print(f"no tests performed")

        print()

    def _reset(self):
        self._performed = 0
        self._passed = 0

    def _run_single(self, test):
        self._performed += 1

        if test.run():
            self._passed += 1

    def run(self):
        self._reset()
        last_func = self._tests[0].func.__name__

        for t in self._tests:
            if self.separate_results_by_function and t.func.__name__ != last_func:
                self._print_results(last_func)
                self._reset()

            self._run_single(t)
            last_func = t.func.__name__

        self._print_results(last_func if self.separate_results_by_function
                            else None)

    def run_by_function(self, func):
        self._reset()

        for t in self._tests:
            if t.func == func:
                self._run_single(t)

        self._print_results()

    def run_by_args(self, args):
        self._reset()

        for t in self._tests:
            if t.args == args:
                self._run_single(t)

        self._print_results()

    def run_by_expected(self, expected):
        self._reset()

        for t in self._tests:
            if t.expected == expected:
                self._run_single(t)

        self._print_results()

    def run_by_index(self, index):
        self._reset()

        if index < len(self._tests):
            self._run_single(self._tests[index])

        self._print_results()

    def remove_by_function(self, func):
        self._tests = [t for t in self._tests if t.func != func]

    def remove_by_args(self, args):
        self._tests = [t for t in self._tests if t.args != args]

    def remove_by_expected(self, expected):
        self._tests = [t for t in self._tests if t.expected != expected]

    def remove_by_index(self, index):
        self._tests.pop(index)

    def clear(self):
        self._tests.clear()
        self._reset()
