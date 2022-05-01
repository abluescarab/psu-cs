def asrt(given, expected):
    try:
        assert given == expected, f"result incorrect: should be {expected}, " \
            f"got {given}"
    except AssertionError as e:
        print(e)
    else:
        print(f"result correct:   {expected}")
