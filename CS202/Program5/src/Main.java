/* Alana Gilston - 3/15/21 - CS202 - Program 5
 * Main.java
 *
 * This is the main file to manage the PostManager class.
 */
import activities.*;
import activities.collections.*;
import activities.collections.items.*;
import activities.crafts.*;
import activities.crafts.items.*;
import posts.*;
import utils.Utils;

public class Main extends Utils {
    private enum MenuType {
        MAIN,
        ALL_COLLECTIONS,
        ALL_CRAFTS,
        SINGLE_COLLECTION,
        SINGLE_CRAFT,
        POSTS
    }

    /**
     * Display a menu.
     * @param menu Type of menu to display
     * @return The option entered by the user
     */
    private static int displayMenu(MenuType menu) {
        return displayMenu(menu, "");
    }

    /**
     * Display a menu.
     * @param menu Type of menu to display
     * @param current Current collection or craft
     * @return The option entered by the user
     */
    private static int displayMenu(MenuType menu, String current) {
        int maxOption = 0;

        switch(menu) {
            case POSTS:
                System.out.println("Posts");
                System.out.println("-------------------------------------");
                System.out.println("1) Write new post");
                System.out.println("2) View all posts");
                System.out.println("3) View posts by author");
                System.out.println("4) View posts by activity type");
                System.out.println("5) View posts by activity name");
                System.out.println("6) Back");

                maxOption = 6;
                break;
            case ALL_COLLECTIONS:
                System.out.println("Manage Collections");
                System.out.println("-------------------------------------");
                System.out.println("1) Display all collections");
                System.out.println("2) Manage collection");
                System.out.println("3) Add collection");
                System.out.println("4) Remove collection");
                System.out.println("5) Remove all collections");
                System.out.println("6) Remove all items in collections");
                System.out.println("7) Back");

                maxOption = 7;
                break;
            case SINGLE_COLLECTION:
                System.out.println("Collection: " + current);
                System.out.println("-------------------------------------");
                System.out.println("1) Display collection");
                System.out.println("2) Add item");
                System.out.println("3) Remove item");
                System.out.println("4) Remove all items");
                System.out.println("5) Update knowledge level");
                System.out.println("6) Back");

                maxOption = 6;
                break;
            case ALL_CRAFTS:
                System.out.println("Crafts");
                System.out.println("-------------------------------------");
                System.out.println("1) Display all crafts");
                System.out.println("2) Manage crafts");
                System.out.println("3) Add craft");
                System.out.println("4) Remove crafts");
                System.out.println("5) Remove all crafts");
                System.out.println("6) Remove all craft projects");
                System.out.println("7) Back");

                maxOption = 7;
                break;
            case SINGLE_CRAFT:
                System.out.println("Craft: " + current);
                System.out.println("-------------------------------------");
                System.out.println("1) Display all projects");
                System.out.println("2) Mark a project complete");
                System.out.println("3) Add project");
                System.out.println("4) Remove project");
                System.out.println("5) Remove all projects");
                System.out.println("6) Update experience level");
                System.out.println("7) Back");

                maxOption = 7;
                break;
            case MAIN:
            default:
                System.out.println("Activity Manager");
                System.out.println("-------------------------------------");
                System.out.println("1) Manage posts");
                System.out.println("2) Manage collections");
                System.out.println("3) Manage crafts");
                System.out.println("4) Quit");

                maxOption = 4;
                break;
        }

        return getNumericInput(maxOption);
    }

    public static void main(String[] args) {
        ActivityManager manager = new ActivityManager();
        PostManager posts = new PostManager();
        // loop variables
        int result = 0;
        MenuType menu = MenuType.MAIN;
        String currentCollection = "";
        String currentCraft = "";
        CollectionType collectionType;
        CraftType craftType;
        boolean quit = false;
        // input variables
        int option = 0;
        String input = "";
        CollectionActivity newCollection = null;
        CraftActivity newCraft = null;
        ActivityPost newPost = null;

        while(!quit) {
            if(!currentCollection.isBlank())
                option = displayMenu(menu, currentCollection);
            else if(!currentCraft.isBlank())
                option = displayMenu(menu, currentCraft);
            else
                option = displayMenu(menu);

            System.out.println();

            switch(menu) {
                case POSTS:
                    switch(option) {
                        case 1: // write new post
                            newPost = new ActivityPost().create(manager);

                            if(newPost == null) {
                                System.out.println("Cancelled adding new post.");
                                break;
                            }

                            posts.add(newPost);
                            System.out.println("Successfully added new post.");
                            break;
                        case 2: // view all posts
                            result = posts.display();

                            if(result == 0)
                                System.out.println("No posts to display.");
                            else
                                System.out.println("Found " + result + " post(s).");
                            break;
                        case 3: // view posts by author
                            System.out.print("Author: ");
                            input = getStringInput(false, "Invalid name.");

                            if(input.isBlank())
                                break;

                            result = posts.displayByAuthor(input);

                            if(result == 0)
                                System.out.println("No posts by that author.");
                            else
                                System.out.println("Found " + result + " post(s) by that author.");
                            break;
                        case 4: // view posts by activity type
                            System.out.println("What type of activity do you want to view posts about?");
                            System.out.println("1) Collection");
                            System.out.println("2) Craft");
                            option = getNumericInput(2);

                            if(option == 1) {
                                System.out.println("What type of collection do you want to view posts about?");
                                System.out.println("1) All collections");
                                System.out.println("2) Fountain pens");
                                System.out.println("3) Knives");
                                System.out.println("4) Trading cards");
                                option = getNumericInput(4);

                                if(option == 1)
                                    collectionType = CollectionType.NONE;
                                else if(option == 2)
                                    collectionType = CollectionType.FOUNTAIN_PEN;
                                else if(option == 3)
                                    collectionType = CollectionType.KNIFE;
                                else
                                    collectionType = CollectionType.TRADING_CARD;

                                result = posts.displayByActivityType(collectionType);
                            }
                            else {
                                System.out.println("What type of craft do you want to view posts about?");
                                System.out.println("1) All crafts");
                                System.out.println("2) Paintings");
                                System.out.println("3) Sculptures");
                                System.out.println("4) Woodworking");
                                option = getNumericInput(4);

                                if(option == 1)
                                    craftType = CraftType.NONE;
                                else if(option == 2)
                                    craftType = CraftType.PAINTING;
                                else if(option == 3)
                                    craftType = CraftType.SCULPTURE;
                                else
                                    craftType = CraftType.WOODWORKING;

                                result = posts.displayByActivityType(craftType);
                            }

                            if(result == 0)
                                System.out.println("No posts for that activity type.");
                            else
                                System.out.println("Found " + result + " post(s) for that activity type.");
                            break;
                        case 5: // view posts by activity name
                            System.out.print("Activity: ");
                            input = getStringInput(false, "Invalid name.");

                            if(input.isBlank())
                                break;

                            result = posts.displayByActivityName(input);

                            if(result == 0)
                                System.out.println("No posts for that activity name.");
                            else
                                System.out.println("Found " + result + " post(s) for that activity name.");
                            break;
                        case 6:
                            menu = MenuType.MAIN;
                            break;
                        default:
                            option = displayMenu(menu);
                            break;
                    }
                    break;
                case ALL_COLLECTIONS:
                    switch(option) {
                        case 1: // display all collections
                            result = manager.displayAllCollections();

                            if(result == 0)
                                System.out.println("No collections to display.");
                            else {
                                System.out.println();
                                System.out.println("Displayed " + result + " collection(s).");
                            }
                            break;
                        case 2: // manage collection
                            System.out.print("Collection name: ");
                            input = getStringInput(false, "Invalid collection name.");

                            if(input.isBlank())
                                break;

                            if(!manager.hasCollection(input)) {
                                System.out.println("Collection does not exist.");
                                break;
                            }

                            currentCollection = input;
                            menu = MenuType.SINGLE_COLLECTION;
                            break;
                        case 3: // add collection
                            newCollection = new CollectionActivity().create();

                            if(newCollection == null) {
                                System.out.println("Cancelled adding new collection.");
                                break;
                            }

                            while(!manager.addCollection(newCollection)) {
                                System.out.println("Collection with that name exists. Enter another name: ");
                                newCollection.changeName(getStringInput(true, "Invalid name. Try again: "));
                            }

                            System.out.println("Successfully added collection " + newCollection.toString() + ".");
                            break;
                        case 4: // remove collection
                            System.out.print("Collection name: ");
                            input = getStringInput(false, "Invalid name.");

                            if(input.isBlank())
                                break;

                            if(!manager.removeCollection(input))
                                System.out.println("Collection does not exist.");
                            else
                                System.out.println("Successfully removed collection " + input + ".");
                            break;
                        case 5: // remove all collections
                            System.out.println("Are you sure you want to remove all collections? (y/n) ");

                            if(getConfirmation()) {
                                manager.removeAllCollections();
                                System.out.println("Removed all collections.");
                            }
                            else
                                System.out.println("Cancelled removing all collections.");
                            break;
                        case 6: // remove all items in collections
                            System.out.println("Are you sure you want to remove all collection items? (y/n) ");

                            if(getConfirmation())
                                System.out.println("Removed " + manager.removeAllItems() + " item(s).");
                            else
                                System.out.println("Cancelled removing all collection items.");
                            break;
                        case 7: // back
                            menu = MenuType.MAIN;
                            break;
                        default:
                            option = displayMenu(menu);
                            break;
                    }
                    break;
                case SINGLE_COLLECTION:
                    switch(option) {
                        case 1: // display collection
                            manager.displayCollection(currentCollection, true);
                            break;
                        case 2: // add item
                            collectionType = manager.getCollectionType(currentCollection);

                            if(collectionType == CollectionType.FOUNTAIN_PEN) {
                                if(!manager.addItem(currentCollection, new FountainPen().create()))
                                    System.out.println("Could not add new fountain pen.");
                                else
                                    System.out.println("Successfully added new fountain pen.");
                            }
                            else if(collectionType == CollectionType.KNIFE) {
                                if(!manager.addItem(currentCollection, new Knife().create()))
                                    System.out.println("Could not add new knife.");
                                else
                                    System.out.println("Successfully added new knife.");
                            }
                            else if(collectionType == CollectionType.TRADING_CARD) {
                                if(!manager.addItem(currentCollection, new TradingCard().create()))
                                    System.out.println("Could not add new trading card.");
                                else
                                    System.out.println("Successfully added new trading card.");
                            }

                            break;
                        case 3: // remove item
                            System.out.print("Name: ");
                            input = getStringInput(false, "Invalid name.");

                            if(input.isBlank())
                                break;

                            if(manager.countOfItemsWithName(currentCollection, input) > 1) {
                                if(!manager.chooseItemToRemove(currentCollection, input))
                                    System.out.println("Cancelled removing an item.");
                                else
                                    System.out.println("Successfully removed " + input + ".");
                            }
                            else
                            if(!manager.removeItem(currentCollection, input))
                                System.out.println("Could not remove " + input + ".");
                            else
                                System.out.println("Successfully removed " + input + ".");
                            break;
                        case 4: // remove all items
                            result = manager.removeAllItems(currentCollection);

                            if(result == 0)
                                System.out.println("No items to remove.");
                            else {
                                System.out.println();
                                System.out.println("Successfully removed " + result + " item(s).");
                            }
                            break;
                        case 5: // update knowledge level
                            System.out.println("What knowledge level do you want to change to?");
                            System.out.println("1) Novice");
                            System.out.println("2) Intermediate");
                            System.out.println("3) Advanced");
                            System.out.println("4) Expert");

                            option = getNumericInput(4);

                            if(option == 1) {
                                manager.changeCollectionKnowledgeLevel(currentCollection, ExperienceLevel.NOVICE);
                                System.out.println("Successfully changed the knowledge level to Novice.");
                            }
                            else if(option == 2) {
                                manager.changeCollectionKnowledgeLevel(currentCollection, ExperienceLevel.INTERMEDIATE);
                                System.out.println("Successfully changed the knowledge level to Intermediate.");
                            }
                            else if(option == 3) {
                                manager.changeCollectionKnowledgeLevel(currentCollection, ExperienceLevel.ADVANCED);
                                System.out.println("Successfully changed the knowledge level to Advanced.");
                            }
                            else {
                                manager.changeCollectionKnowledgeLevel(currentCollection, ExperienceLevel.EXPERT);
                                System.out.println("Successfully changed the knowledge level to Expert.");
                            }

                            break;
                        case 6: // back
                            menu = MenuType.ALL_COLLECTIONS;
                            currentCollection = "";
                            break;
                        default:
                            option = displayMenu(menu);
                            break;
                    }
                    break;
                case ALL_CRAFTS:
                    switch(option) {
                        case 1: // display all crafts
                            result = manager.displayAllCrafts();

                            if(result == 0)
                                System.out.println("No crafts to display.");
                            else {
                                System.out.println();
                                System.out.println("Displayed " + result + " craft(s).");
                            }
                            break;
                        case 2: // manage craft
                            System.out.print("Craft name: ");
                            input = getStringInput(false, "Invalid name.");

                            if(input.isBlank())
                                break;

                            if(!manager.hasCraft(input)) {
                                System.out.println("Craft does not exist.");
                                break;
                            }

                            currentCraft = input;
                            menu = MenuType.SINGLE_CRAFT;
                            break;
                        case 3: // add craft
                            newCraft = new CraftActivity().create();

                            if(newCraft == null) {
                                System.out.println("Cancelled adding new craft.");
                                break;
                            }

                            while(!manager.addCraft(newCraft)) {
                                System.out.println("Craft with that name already exists. Enter another name: ");
                                newCraft.changeName(getStringInput(true, "Invalid name. Try again: "));
                            }

                            System.out.println("Successfully added craft " + newCraft.toString() + ".");
                            break;
                        case 4: // remove craft
                            System.out.print("Craft name: ");
                            input = getStringInput(false, "Invalid name.");

                            if(input.isBlank())
                                break;

                            if(!manager.removeCraft(input))
                                System.out.println("Craft does not exist.");
                            else
                                System.out.println("Successfully removed craft " + input + ".");
                            break;
                        case 5: // remove all crafts
                            System.out.print("Are you sure you want to remove all crafts? (y/n) ");

                            if(getConfirmation()) {
                                manager.removeAllCrafts();
                                System.out.println("Removed all crafts.");
                            }
                            else
                                System.out.println("Cancelled removing all crafts.");
                            break;
                        case 6: // remove all craft projects
                            System.out.print("Are you sure you want to remove all craft projects? (y/n) ");

                            if(getConfirmation())
                                System.out.println("Removed " + manager.removeAllProjects() + " craft project(s).");
                            else
                                System.out.println("Cancelled removing all craft projects.");

                            break;
                        case 7: // back
                            menu = MenuType.MAIN;
                            break;
                        default:
                            option = displayMenu(menu);
                            break;
                    }
                    break;
                case SINGLE_CRAFT:
                    switch(option) {
                        case 1: // display all projects
                            manager.displayCraft(currentCraft, true);
                            break;
                        case 2: // mark a project complete
                            System.out.print("Name: ");
                            input = getStringInput(false, "Invalid name.");

                            if(input.isBlank())
                                break;

                            if(!manager.markCompleted(currentCraft, input))
                                System.out.println("Could not mark " + input + " complete.");
                            else
                                System.out.println("Successfully marked " + input + " complete.");
                            break;
                        case 3: // add project
                            craftType = manager.getCraftType(currentCraft);

                            if(craftType == CraftType.PAINTING) {
                                if(!manager.addProject(currentCraft, new PaintingProject().create()))
                                    System.out.println("Could not add new painting project.");
                                else
                                    System.out.println("Successfully added new painting project.");
                            }
                            else if(craftType == CraftType.SCULPTURE) {
                                if(!manager.addProject(currentCraft, new SculptureProject().create()))
                                    System.out.println("Could not add new sculpture project.");
                                else
                                    System.out.println("Successfully added new sculpture project.");
                            }
                            else if(craftType == CraftType.WOODWORKING) {
                                if(!manager.addProject(currentCraft, new WoodworkingProject().create()))
                                    System.out.println("Could not add new woodworking project.");
                                else
                                    System.out.println("Successfully added new woodworking project.");
                            }

                            break;
                        case 4: // remove project
                            System.out.print("Name: ");
                            input = getStringInput(false, "Invalid name.");

                            if(input.isBlank())
                                break;

                            if(manager.countOfProjectsWithName(currentCraft, input) > 1) {
                                if(!manager.chooseProjectToRemove(currentCraft, input))
                                    System.out.println("Cancelled removing a project.");
                                else
                                    System.out.println("Successfully removed " + input + ".");
                            }
                            else {
                                if(!manager.removeProject(currentCraft, input))
                                    System.out.println("Could not remove " + input + ".");
                                else
                                    System.out.println("Successfully removed " + input + ".");
                            }

                            break;
                        case 5: // remove all projects
                            result = manager.removeAllProjects(currentCraft);

                            if(result == 0)
                                System.out.println("No projects to remove.");
                            else {
                                System.out.println();
                                System.out.println("Successfully removed " + result + " item(s).");
                            }
                            break;
                        case 6: // update experience level
                            System.out.println("What experience level do you want to change to?");
                            System.out.println("1) Novice");
                            System.out.println("2) Intermediate");
                            System.out.println("3) Advanced");
                            System.out.println("4) Expert");

                            option = getNumericInput(4);

                            if(option == 1) {
                                manager.changeCraftExperienceLevel(currentCraft, ExperienceLevel.NOVICE);
                                System.out.println("Successfully changed the experience level to Novice.");
                            }
                            else if(option == 2) {
                                manager.changeCraftExperienceLevel(currentCraft, ExperienceLevel.INTERMEDIATE);
                                System.out.println("Successfully changed the experience level to Intermediate.");
                            }
                            else if(option == 3) {
                                manager.changeCraftExperienceLevel(currentCraft, ExperienceLevel.ADVANCED);
                                System.out.println("Successfully changed the experience level to Advanced.");
                            }
                            else {
                                manager.changeCraftExperienceLevel(currentCraft, ExperienceLevel.EXPERT);
                                System.out.println("Successfully changed the experience level to Expert.");
                            }
                            break;
                        case 7: // back
                            menu = MenuType.ALL_CRAFTS;
                            currentCraft = "";
                            break;
                        default:
                            option = displayMenu(menu);
                            break;
                    }
                    break;
                case MAIN:
                default:
                    switch(option) {
                        case 1: // view and manage posts
                            menu = MenuType.POSTS;
                            break;
                        case 2: // manage collections
                            menu = MenuType.ALL_COLLECTIONS;
                            break;
                        case 3: // manage crafts
                            menu = MenuType.ALL_CRAFTS;
                            break;
                        case 4: // quit
                            quit = true;
                            break;
                        default:
                            option = displayMenu(menu);
                            break;
                    }
                    break;
            }

            System.out.println();
        }
    }
}
