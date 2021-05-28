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

            if len(match) > 1:
                parsed[match[0]] = sorted(match[1].split(","))
            else:
                parsed[match[0]] = []

        setattr(namespace, self.dest, parsed)


class GraphBuilder:
    def __init__(self, vertices):
        self.all_edges = {}
        self.unique_edges = []
        self.degree_sequence = {}

        # create a dictionary of all edges including duplicates
        for vertex, adjacency_list in vertices.items():
            adjacency_list = sorted(adjacency_list)

            # add each vertex to the dictionary
            if vertex not in self.all_edges:
                self.all_edges[vertex] = adjacency_list
            else:
                self.all_edges[vertex].extend(
                    a for a in adjacency_list if a not in self.all_edges[vertex])

            # loop through adjacent vertices and add to dictionary
            for adjacent in adjacency_list:
                if adjacent not in self.all_edges:
                    self.all_edges[adjacent] = [vertex]
                elif vertex not in self.all_edges[adjacent]:
                    self.all_edges[adjacent].append(vertex)

                edge = sorted([vertex, adjacent])

                if edge not in self.unique_edges:
                    self.unique_edges.append(edge)

        self.all_edges = dict(sorted(self.all_edges.items()))

        for vertex, adjacency_list in self.all_edges.items():
            self.degree_sequence[vertex] = len(adjacency_list)

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

    def is_tree(self, vertices, edges, cyclical):
        if 0 in self.degree_sequence:
            return False

        if edges == vertices - 1 and not cyclical:
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
        vertices = len(self.all_edges.keys())
        edges = len(self.unique_edges)
        cyclical = self.has_cycle()

        print(f"D=({', '.join(map(str,sorted(self.degree_sequence.values(), reverse=True)))})")
        print(f"V={{{', '.join(self.all_edges.keys())}}}")
        print(f"E={{{{{'}, {'.join([', '.join(e) for e in self.unique_edges])}}}}}")
        print()
        print(f"Vertices:  {vertices}")
        print(f"Edges:     {edges}")
        print(f"Cycle:     {'Yes' if cyclical else 'No'}")
        print(f"Tree:      {'Yes' if self.is_tree(vertices, edges, cyclical) else 'No'}")


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("vertices", nargs="+", type=str,
        help="vertices with adjacency list (this_vertex=adjacent_1,adjacent_2,...)",
        action=ListAction)

    args = parser.parse_args()
    builder = GraphBuilder(args.vertices)
    # builder = GraphBuilder({'a': ['b','c'], 'c': ['b','a']})
    builder.display_table()
    print()
    builder.display_info()


if __name__ == "__main__":
    main()
