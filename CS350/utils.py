def asrt(given, expected):
    try:
        assert given == expected, f"fail: got {given}, should be {expected}"
    except AssertionError as e:
        print(e)
    else:
        print(f"pass: got {expected}")
