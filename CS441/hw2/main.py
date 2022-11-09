from game import Game

# function Genetic-Algorithm(population, Fitness-Fn) returns an individual
#     inputs: population, a set of individuals
#             Fitness-Fn, a function that measures fitness of individual

#     repeat
#         new_population <- empty set
#         for i = 1 to Size(population) do
#             x <- Random-Selection(population, Fitness-Fn)
#             y <- Random-Selection(population, Fitness-Fn)
#             child <- Reproduce(x, y)
#             if small random probability then child <- Mutate(child)
#             add child to new_population
#         population <- new_population
#     until some individual is fit enough, or enough time has elapsed
#     return the best individual in population, according to Fitness-Fn


if __name__ == "__main__":
    game = Game()
    game.populate(10)
    game.run()
