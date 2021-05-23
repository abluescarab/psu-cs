import os
import re
import argparse


class ListAction(argparse.Action):
    def __init__(self, option_strings, dest, nargs="+", **kwargs):
        super().__init__(option_strings, dest, nargs, **kwargs)

    def __call__(self, parser, namespace, values, option_string=None):
        parsed = {}

        for adjacent in values:
            match = re.split(r"\=", adjacent)
            parsed[match[0]] = sorted(match[1].split(","))

        setattr(namespace, self.dest, parsed)


class GraphBuilder:
    def __init__(self, vertices):
        self.all_edges = {}
        self.unique_edges = []
        self.degree_sequence = {}

        # create a dictionary of all edges including duplicates
        for vertex, adjacency_list in vertices.items():
            if vertex in self.all_edges:
                self.all_edges[vertex].extend(adjacency_list)
            else:
                self.all_edges[vertex] = adjacency_list

            # loop through the adjacent vertices and add them to the list
            for adjacent in adjacency_list:
                edge = sorted([vertex, adjacent])

                if edge not in self.unique_edges:
                    self.unique_edges.append(edge)

                if adjacent not in self.all_edges.keys():
                    self.all_edges[adjacent] = [vertex]
                elif vertex not in self.all_edges[adjacent]:
                    self.all_edges[adjacent].append(vertex)

            self.all_edges[vertex] = sorted(self.all_edges[vertex])

        self.all_edges = dict(sorted(self.all_edges.items()))

        for vertex, adjacent in self.all_edges.items():
            self.degree_sequence[vertex] = len(adjacent)

        # create a sorted list of unique edges
        # for key, value in self.all_edges.items():
        #     self.degree_sequence[key] = len(value)

        #     for adjacent in value:
        #         to_add = sorted([key, adjacent])

        #         if to_add not in self.unique_edges:
        #             self.unique_edges.append(to_add)

    # modified from https://www.geeksforgeeks.org/detect-cycle-undirected-graph/
    def is_cyclic(self, key, visited, parent):
        visited[key] = True

        for vertex in self.all_edges[key]:
            if vertex not in visited:
                if self.is_cyclic(vertex, visited, key):
                    return True
            elif parent != vertex:
                return True

        return False

    # modified from https://www.geeksforgeeks.org/detect-cycle-undirected-graph/
    def has_cycle(self):
        visited = {}

        for key in self.all_edges.keys():
            if key not in visited and self.is_cyclic(key, visited, ""):
                return True

        return False

    def display_table(self):
        columns = [
            {
                "label": "Vertex",
                "width": max(len("Vertex"),
                             max(len(vertex) for vertex in self.all_edges))
            },
            {
                "label": "Degree",
                "width": max(len("Degree"),
                             max(len(str(degree)) for degree in self.degree_sequence))
            },
            {
                "label": "Adjacent",
                "width": max(len("Adjacent"),
                             max([len(", ".join(adjacent)) for adjacent in self.all_edges.values()]))
            }
        ]

        # print the padded list column headers
        for i in range(len(columns)):
            print(f"{columns[i]['label']:<{columns[i]['width']}}", end="")

            if i < len(columns) - 1:
                print(" | ", end="")

        print()

        # print the row separators
        for i in range(len(columns)):
            print("-" * columns[i]["width"], end="")

            if i < len(columns) - 1:
                print("-+-", end="")

        print()

        # print the values of the vertices
        for vertex, adjacent in self.all_edges.items():
            for i in range(len(columns)):
                if columns[i]["label"] == "Vertex":
                    print(f"{vertex:<{columns[i]['width']}}", end="")
                elif columns[i]["label"] == "Degree":
                    print(f"{self.degree_sequence[vertex]:<{columns[i]['width']}}", end="")
                elif columns[i]["label"] == "Adjacent":
                    print(f"{', '.join(adjacent):<{columns[i]['width']}}")

                if i < len(columns) - 1:
                    print(" | ", end="")

    def display(self):
        cyclical = self.has_cycle()

        print()
        print(f"V={{{', '.join(self.all_edges.keys())}}}")
        print(f"E={{{{{'}, {'.join([', '.join(e) for e in self.unique_edges])}}}}}")
        print(f"D=({', '.join(map(str,sorted(self.degree_sequence.values(), reverse=True)))})")
        print()
        print(f"Vertices:  {len(self.all_edges.keys())}")
        print(f"Edges:     {len(self.unique_edges)}")
        print(f"Cycle:     {'Yes' if cyclical else 'No'}")
        print(f"Connected: {'Yes' if 0 not in self.degree_sequence.values() else 'No'}")


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("vertices", nargs="+", type=str,
        help="vertices with adjacency list (this_vertex=adjacent_1,adjacent_2,...)",
        action=ListAction)
    parser.add_argument("-t", "--table", action="store_true", help="show full table of vertices")

    # args = parser.parse_args()
    # builder = GraphBuilder(args.vertices)
    builder = GraphBuilder({"a": ["c","d"], "b": [], "c": ["d"]})

    # if args.table:
    builder.display_table()

    builder.display()


if __name__ == "__main__":
    main()
