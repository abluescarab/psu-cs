import os
import re
import argparse


class ListAction(argparse.Action):
    def __init__(self, option_strings, dest, nargs="+", **kwargs):
        super().__init__(option_strings, dest, nargs, **kwargs)

    def __call__(self, parser, namespace, values, option_string=None):
        parsed = {}

        # split the values by equal sign
        for adjacent in values:
            match = re.split(r"\=", adjacent)

            if len(match) > 1:
                # then split each vertex list by comma
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

    def is_connected(self):
        return 0 not in self.degree_sequence.values()

    def is_complete(self):
        return all(degree == len(self.all_edges.keys()) for degree in self.degree_sequence.values())


    # modified from https://www.geeksforgeeks.org/detect-cycle-undirected-graph/
    def has_cycle(self):
        visited = {}

        for key in self.all_edges.keys():
            if key not in visited and self.is_cyclic(key, visited, ""):
                return True

        return False

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

    def is_tree(self, vertex_count, edge_count):
        if not self.is_connected():
            return False

        if edge_count == vertex_count - 1 and not self.has_cycle():
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

    def display_info(self):
        vertex_count = len(self.all_edges.keys())
        edge_count = len(self.unique_edges)

        print(f"D=({', '.join(map(str,sorted(self.degree_sequence.values(), reverse=True)))})")
        print(f"V={{{', '.join(self.all_edges.keys())}}}")
        print(f"E={{{{{'}, {'.join([', '.join(e) for e in self.unique_edges])}}}}}")
        print()
        print(f"Vertices:  {vertex_count}")
        print(f"Edges:     {edge_count}")
        print(f"Cycle:     {'Yes' if self.has_cycle() else 'No'}")
        print(f"Connected: {'Yes' if self.is_connected() else 'No'}")
        print(f"Complete:  {'Yes' if self.is_complete() else 'No'}")
        print(f"Tree:      {'Yes' if self.is_tree(vertex_count, edge_count) else 'No'}")


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
