class Test:
    def __init__(self, func, args, expected):
        self.func = func
        self.args = args
        self.expected = expected

    def run(self):
        try:
            result = self.func(self.args)

            assert result == self.expected, \
                f"fail: got {result}, expected {self.expected}"
        except AssertionError as e:
            print(e)
            return False
        else:
            print(f"pass: got {self.expected}")
            return True


class TestSuite:
    def __init__(self):
        self.tests = []
        self.performed = 0
        self.passed = 0

    def add_test(self, func, args, expected):
        self.tests.append(Test(func, args, expected))

    def _print_results(self):
        print(f"test results: {self.passed} passed, "
              f"{self.performed - self.passed} failed, {self.performed} run")

    def _reset(self):
        self.performed = 0
        self.passed = 0

    def _run_single(self, test):
        self.performed += 1

        if test.run():
            self.passed += 1

    def clear(self):
        self.tests.clear()
        self._reset()

    def run(self):
        self._reset()

        for t in self.tests:
            self._run_single(t)

        self._print_results()

    def run_by_function(self, func):
        self._reset()

        for t in self.tests:
            if t.func == func:
                self._run_single(t)

        self._print_results()

    def run_by_args(self, args):
        self._reset()

        for t in self.tests:
            if t.args == args:
                self._run_single(t)

        self._print_results()

    def run_by_expected(self, expected):
        self._reset()

        for t in self.tests:
            if t.expected == expected:
                self._run_single(t)

        self._print_results()
