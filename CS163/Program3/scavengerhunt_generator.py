import random
import sys
import os

locations = [
        "82nd Av.",
        "Alberta St.",
        "Burnside St.",
        "Canyon Rd.",
        "Cesar Chavez Blvd.",
        "Division St.",
        "Hawthorne Blvd.",
        "Klickitat St.",
        "Lombard St.",
        "Naito Pkwy.",
        "NE 116th Ave.",
        "NE 13th Ave.",
        "NE 84th Ave.",
        "NE Russell St.",
        "NE Skidmore St.",
        "NW 107th Ave.",
        "NW 108th Ave.",
        "NW 109th Ave.",
        "NW 110th Ave.",
        "NW 112th Ave.",
        "NW 13th Ave.",
        "NW 14th Ave.",
        "NW 15th Ave.",
        "NW 16th Ave.",
        "NW 17th Ave.",
        "NW 18th Ave.",
        "NW 19th Ave.",
        "NW 1st St.",
        "NW 20th Ave.",
        "NW 20th Pl.",
        "NW 21st Ave.",
        "NW 21st Pl.",
        "NW 22nd Ave.",
        "NW 22nd Pl.",
        "NW 23rd Ave.",
        "NW 23rd Pl.",
        "NW 24th Ave.",
        "NW 24th Pl.",
        "NW 25th Ave.",
        "NW 25th Pl.",
        "NW 26th Ave.",
        "NW 27th Ave.",
        "NW 28th Ave.",
        "NW 29th Ave.",
        "NW 2nd St.",
        "NW 30th Ave.",
        "NW 31st Ave.",
        "NW 32nd Ave.",
        "NW 33rd Ave.",
        "NW 34th Ave.",
        "NW 35th Ave.",
        "NW 3rd Ct.",
        "NW 3rd St.",
        "NW 4th St.",
        "NW 53rd Dr.",
        "NW 81st Pl.",
        "NW 83rd Pl.",
        "NW 87th Ave.",
        "NW Abbey Rd.",
        "NW Alder Grove Ln.",
        "NW Aspen Ave.",
        "NW Bailey St.",
        "NW Barnsley Ct.",
        "NW Bartholomew Dr.",
        "NW Belgrave Ave.",
        "NW Benson Ct.",
        "NW Benson Ln.",
        "NW Benson St.",
        "NW Birkendene St.",
        "NW Blue Pointe Ln.",
        "NW Brewer St.",
        "NW Brittney Ct.",
        "NW Broadway",
        "NW Burkhardt Ct.",
        "NW Bush St.",
        "NW Caxton Ct.",
        "NW Chapin Dr.",
        "NW Cornell Rd.",
        "NW Country Woods Ln.",
        "NW Creston Rd.",
        "NW Crestview Way.",
        "NW Dunbar Ln.",
        "NW Elva Ave.",
        "NW Engleman St.",
        "NW Express Ave.",
        "NW Finzer Ct.",
        "NW Foley Ct.",
        "NW Franklin Ct.",
        "NW Frazier Ct.",
        "NW Front Ave.",
        "NW Gordon St.",
        "NW Groce St.",
        "NW Guam St.",
        "NW Harbor Blvd.",
        "NW Hardy Ave.",
        "NW Hazeltine St.",
        "NW Herrin Ct.",
        "NW Hoge Ave.",
        "NW Hopedale Ct.",
        "NW Industrial St.",
        "NW Johnson St.",
        "NW Kelly Cir.",
        "NW Lake St.",
        "NW Lambert St.",
        "NW Langley Ct.",
        "NW Lightning Ridge Dr.",
        "NW Luray Ter.",
        "NW Luzon St.",
        "NW Mackay Ave.",
        "NW Marshall St.",
        "NW Mayer Ct.",
        "NW Mayfield Rd.",
        "NW Mckenna Dr.",
        "NW Midway Ave.",
        "NW Mill Pond Rd.",
        "NW Miller Hill Ct.",
        "NW Miller Hill Dr.",
        "NW Miller Rd.",
        "NW Mills St.",
        "NW Mountain View Rd.",
        "NW New Hope Ct.",
        "NW Nicolai St.",
        "NW Norfolk Ct.",
        "NW Northrup St.",
        "NW Ogden St.",
        "NW Old Skyline Blvd.",
        "NW Overton St.",
        "NW Parkridge Ln.",
        "NW Paxton Ct.",
        "NW Pettygrove St.",
        "NW Pinnacle Ct.",
        "NW Pinnacle Dr.",
        "NW Potters Ct.",
        "NW Quimby St.",
        "NW Rainmont Rd.",
        "NW Raleigh St.",
        "NW Ramsey Dr.",
        "NW Reed Dr.",
        "NW Reed St.",
        "NW Roosevelt St.",
        "NW Rosaria Ave.",
        "NW Rosewa Ave.",
        "NW Roseway Ave.",
        "NW Savier St.",
        "NW Sawyer Ct.",
        "NW Shepard St.",
        "NW Sherlock Ave.",
        "NW Silver Ridge Loop.",
        "NW Skyline Blvd.",
        "NW Skyview Dr.",
        "NW Slocum Way.",
        "NW Spencer St.",
        "NW Springville Ct.",
        "NW Springville Rd.",
        "NW St Helens Rd.",
        "NW Suffolk St.",
        "NW Summit Ave.",
        "NW Thompson Rd.",
        "NW Thurman St.",
        "NW Timber Ridge Ct.",
        "NW Unnamed Rd.",
        "NW Upshur St.",
        "NW Vaughn St.",
        "NW Walmar Dr.",
        "NW Westover Rd.",
        "NW Whitney St.",
        "NW Wilark Ave.",
        "NW Wiley Ln.",
        "NW Willbridge Ave.",
        "NW Wilson St.",
        "NW Wood St.",
        "NW Yeon Ave.",
        "NW York St.",
        "NW Yorkshire Ln.",
        "SE 115th Ave.",
        "SE 89th Ave.",
        "SE Clatsop St.",
        "SE Steele St.",
        "Stark St.",
        "SW 47th Ave.",
        "SW 66th Ave.",
        "SW 66th Ct.",
        "SW 68th Ave.",
        "SW Barnes Rd.",
        "SW Canby Ln.",
        "SW Canby St.",
        "SW Garden Home Rd.",
        "SW Locust St.",
        "SW Oak St.",
        "SW Railroad St.",
        "SW Terri Ct.",
        "SW Vesta St.",
        "Terwilliger Blvd.",
        "Williams Av."
        ]

hint_1 = [
        "behind",
        "in front of",
        "to the left of",
        "to the right of",
        "above",
        "below",
        "around",
        "past",
        "near",
        "under"
        ]

hint_2 = [
        "backyard",
        "car",
        "doghouse",
        "driveway",
        "fence",
        "fire hydrant",
        "front door",
        "front yard",
        "garden",
        "gate",
        "house",
        "lamppost",
        "planter box",
        "porch",
        "side yard",
        "sidewalk",
        "tree"
        ]

adjectives = [
        "big",
        "black",
        "blue",
        "bloody",
        "costumed",
        "creepy",
        "cursed",
        "dark",
        "decayed",
        "demonic",
        "dirty",
        "gray",
        "green",
        "gruesome",
        "haunted",
        "horrible",
        "horrifying",
        "inflatable",
        "light",
        "mummified",
        "new",
        "old",
        "orange",
        "possessed",
        "pretty",
        "red",
        "scary",
        "small",
        "spooky",
        "terrifying",
        "ugly",
        "white",
        "yellow"
        ]

nouns = [
        "alien",
        "bat",
        "beast",
        "bogeyman",
        "brain",
        "broomstick",
        "bug",
        "candle",
        "cat",
        "cauldron",
        "clown",
        "coffin",
        "corpse",
        "crow",
        "demon",
        "devil",
        "dog",
        "doll",
        "eyeball",
        "ghost",
        "ghoul",
        "goblin",
        "graveyard",
        "Grim Reaper",
        "headless horseman",
        "horse",
        "house",
        "insect",
        "mad scientist",
        "mansion",
        "mask",
        "monster",
        "mummy",
        "ogre",
        "ouija board",
        "owl",
        "pirate",
        "pumpkin",
        "raven",
        "scarecrow",
        "skeleton",
        "spider",
        "spiderweb",
        "tarantula",
        "toad",
        "tombstone",
        "troll",
        "vampire",
        "werewolf",
        "warlock",
        "witch",
        "wizard",
        "zombie"
        ]

def print_progress(iteration, total: float, decimals = 1):
    percent = ("{0:.1f}").format(100 * (iteration / total))
    complete = int(50 * iteration / total)
    bar = ("-" * complete) + (" " * (50 - complete))

    print(f"\rProgress: |{bar}| {percent}% Complete", end = "\r")

    if iteration == total:
        print()


def write_to_file(filename: str, results: []):
    with open(filename, 'w') as file:
        file.write("\n".join(results) + "\n")

    print(f"Wrote {len(results)} items to {filename}.")
    print()


def generate_items(filename: str, count: int):
    names = []
    results = []

    while len(results) < count:
        name = random.choice(adjectives).capitalize() + " " + random.choice(nouns)

        if name not in names:
            result = (f"{name}|{random.randint(1, 101)} {random.choice(locations)}|"
                    f"{random.choice(hint_1).capitalize()} the {random.choice(hint_2)}")

            names.append(name)
            results.append(result)
            print_progress(len(results), count)

    write_to_file(filename, results)
    return names


def generate_queue(filename: str, count: int, names: []):
    selected_names = []

    while len(selected_names) < count:
        name = random.choice(names)

        if name not in selected_names:
            selected_names.append(random.choice(names))
            print_progress(len(selected_names), count)

    write_to_file(filename, selected_names)


def print_usage():
    print("Usage:  python3 scavengerhunt_generator.py <number to generate> <filename> [queue amount <queue filename>] [--yes/-y=say yes to file overwrite prompts]")


def main():
    # skip the whole program if the arguments are incorrect
    if len(sys.argv) < 3 or len(sys.argv) == 4 or len(sys.argv) > 6:
        print_usage()
        return

    skip_prompts = True

    # allow "yes" anywhere in the arguments
    if "--yes" in sys.argv:
        sys.argv.remove("--yes")
    elif "-y" in sys.argv:
        sys.argv.remove("-y")
    else:
        skip_prompts = False

    # set some default variables
    max_number = len(adjectives) * len(nouns)
    queue_amount = 0

    # get the generation amount from the arguments
    try:
        generate_amount = int(sys.argv[1])
        if generate_amount > max_number or generate_amount < 1:
            raise ValueError
    except ValueError:
        print(f"Invalid number. Number should be between 1 and {max_number}.")
        return

    # get the filename from the arguments
    filename = sys.argv[2]

    # get the queue values from the arguments
    if len(sys.argv) > 4:
        try:
            queue_amount = int(sys.argv[3])
            if queue_amount > generate_amount or queue_amount < 1:
                raise ValueError
        except ValueError:
            print(f"Invalid number. Number should be between 1 and {generate_amount}.")
            return

        queue_filename = sys.argv[4]

    # check if the filename exists -- if yes, ask to overwrite
    if os.path.exists(filename) and not skip_prompts:
        yes_no = input("File already exists. Overwrite? (y/n) ").lower()

        if yes_no != "yes" and yes_no != "y":
            return

    # generate the items
    names = generate_items(filename, generate_amount)

    # generate a queue file if the arguments are provided
    if queue_amount > 0:
        if os.path.exists(queue_filename) and not skip_prompts:
            yes_no = input("Queue file already exists. Overwrite? (y/n) ").lower()

            if yes_no != "yes" and yes_no != "y":
                return

        generate_queue(queue_filename, queue_amount, names)


if __name__ == "__main__":
    main()
