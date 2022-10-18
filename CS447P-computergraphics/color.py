def print_matrix(matrix):
    for r in range(len(matrix)):
        print("[", end="")
        for c in range(len(matrix[r])):
            print(matrix[r][c], end="")

            if c < len(matrix[r]) - 1:
                print(" ", end="")

        print("]")


def read_matrix(filename):
    try:
        with open(filename, "r") as file:
            matrix = [[int(num) for num in line.split(" ")] for line in file]

        return matrix
    except FileNotFoundError:
        print("No file found")
        return []


def rgb_to_xyz(r, g, b):
    xyz = [[0.4124, 0.3576, 0.1805],
           [0.2126, 0.7152, 0.0722],
           [0.0193, 0.1192, 0.9505]]
    rgb = (r, g, b)
    result = [0.0] * 3

    for i in range(3):
        sum = 0.0

        for j in range(3):
            sum += xyz[i][j] * rgb[j]

        result[i] = sum
        sum = 0.0

    result_tuple = tuple(result)
    return result_tuple


def rgb_to_lab(r, g, b, xn, yn, zn):
    xyz = rgb_to_xyz(r, g, b)
    x_xn = xyz[0] / xn
    y_yn = xyz[1] / yn
    z_zn = xyz[2] / zn
    l = 0.0
    a = 0.0
    b = 0.0

    f = lambda t: pow(t, 1 / 3) - 16 if t > 0.008856 else (7.787 * t) + (16 / 116)

    if y_yn > 0.008856:
        l = 116 * pow(y_yn, 1 / 3) - 16
    else:
        l = 903.3 * pow(y_yn, 1 / 3)

    a = 500 * (f(x_xn) - f(y_yn))
    b = 200 * (f(y_yn) - f(z_zn))

    return (round(l, 4), round(a, 4), round(b, 4))


def interpolate(u, c1, c2):
    if len(c1) != 3:
        return

    if len(c2) != 3:
        return

    result = [0.0] * 3

    for i in range(3):
        result[i] = round(((1 - u) * c1[i]) + (u * c2[i]), 4)

    return tuple(result)


def convolve(image, filter):
    matrix = [[int(c) for c in r] for r in image]

    result = []
    line = []
    row = 0
    col = 0

    while True:
        sum = 0
        section = [[col for col in row[col:col+3]] for row in matrix[row:row+3]]

        for i in range(len(filter)):
            for j in range(len(filter[i])):
                sum += section[i][j] * filter[i][j]

        line.append(sum)
        col += 1

        if col > 3:
            col = 0
            row += 1
            result.append(line)
            line = []

        if row > 3:
            break

    return result


def sobel(image, horizontal):
    if horizontal:
        return convolve(image,
            [[1, 0, -1],
            [2, 0, -2],
            [1, 0, -1]])
    else:
        return convolve(image,
            [[1, 2, 1],
            [0, 0, 0],
            [-1, -2, -1]])


if __name__ == "__main__":
    import argparse
    parser = argparse.ArgumentParser()
    parser.add_argument("--interpolate", "-i", type=float,
        metavar=("u", "r1", "g1", "b1", "r2", "g2", "b2"), nargs=7)
    parser.add_argument("--rgb-xyz", "-rx", type=float,
        metavar=("r", "g", "b"), nargs=3)
    parser.add_argument("--rgb-lab", "-rl", type=float,
        metavar=("r", "g", "b", "xn", "yn", "zn"), nargs=6)
    parser.add_argument("--sobel-horizontal", "-sh", nargs="+")
    parser.add_argument("--sobel-vertical", "-sv", nargs="+")
    parser.add_argument("--convolve", "-c", type=str,
        metavar=("matrix", "filter"), nargs=2)

    args = parser.parse_args()

    if args.interpolate:
        u = args.interpolate[0]
        r1 = args.interpolate[1]
        g1 = args.interpolate[2]
        b1 = args.interpolate[3]
        r2 = args.interpolate[4]
        g2 = args.interpolate[5]
        b2 = args.interpolate[6]
        c1 = (r1, g1, b1)
        c2 = (r2, g2, b2)

        result = interpolate(u, c1, c2)

        print(f"r({u}) = (1 - {u}) * {r1} + {u} * {r2} = {result[0]}")
        print(f"g({u}) = (1 - {u}) * {g1} + {u} * {g2} = {result[1]}")
        print(f"b({u}) = (1 - {u}) * {b1} + {u} * {b2} = {result[2]}")

    if args.rgb_xyz:
        r = args.rgb_xyz[0]
        g = args.rgb_xyz[1]
        b = args.rgb_xyz[2]

        print(f"RGB -> XYZ:    ({r}, {g}, {b}) -> {rgb_to_xyz(r, g, b)}")

    if args.rgb_lab:
        r = args.rgb_lab[0]
        g = args.rgb_lab[1]
        b = args.rgb_lab[2]
        xn = args.rgb_lab[3]
        yn = args.rgb_lab[4]
        zn = args.rgb_lab[5]

        print(f"RGB -> L*a*b*: ({r}, {g}, {b}) -> {rgb_to_lab(r, g, b, xn, yn, zn)}")

    if args.sobel_horizontal or args.sobel_vertical:
        s = args.sobel_horizontal

        if args.sobel_vertical:
            s = args.sobel_vertical

        if len(s) == 1:
            file = s[0]
            matrix = read_matrix(file)

            if matrix:
                print_matrix(sobel(matrix, args.sobel_horizontal))
        elif len(s) == 36:
            print_matrix(sobel([
                    [s[0], s[1], s[2], s[3], s[4], s[5]],
                    [s[0], s[1], s[2], s[3], s[4], s[5]],
                    [s[0], s[1], s[2], s[3], s[4], s[5]],
                    [s[0], s[1], s[2], s[3], s[4], s[5]],
                    [s[0], s[1], s[2], s[3], s[4], s[5]],
                    [s[0], s[1], s[2], s[3], s[4], s[5]]
                ], args.sobel_horizontal))

    if args.convolve:
        matrix_file = args.convolve[0]
        filter_file = args.convolve[1]

        matrix = read_matrix(matrix_file)
        filter = read_matrix(filter_file)

        if matrix and filter:
            print_matrix(convolve(matrix, filter))
        pass
