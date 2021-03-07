import activities.*;
import activities.collections.*;
import activities.collections.items.*;
import activities.crafts.*;
import utils.Utils;

public class Main extends Utils {
    private enum MenuType {
        MAIN,
        ALL_COLLECTIONS,
        ALL_CRAFTS,
        SINGLE_COLLECTION,
        SINGLE_CRAFT
    }

    private static int displayMenu(MenuType menu) {
        return displayMenu(menu, "");
    }

    private static int displayMenu(MenuType menu, String current) {
        int maxOption = 0;

        switch(menu) {
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
                System.out.println("1) Display all project collections");
                System.out.println("2) Manage project collection");
                System.out.println("3) Add project collection");
                System.out.println("4) Remove project collection");
                System.out.println("5) Remove all project collections");
                System.out.println("6) Remove all projects in collections");
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
                System.out.println("1) Manage collections");
                System.out.println("2) Manage crafts");
                System.out.println("3) Quit");

                maxOption = 3;
                break;
        }

        return getNumericInput(maxOption);
    }

    public static void main(String[] args) {
        ActivityManager manager = new ActivityManager();
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


        boolean debug = true;
        if(debug) {
            newCollection = new CollectionActivity();
            newCollection.changeName("Test");
            newCollection.add(new FountainPen("Test FP", 1, "M", FountainPen.FillingType.CARTRIDGE_CONVERTER, "Diamine"));
            newCollection.add(new FountainPen("Test FP", 2, "F", FountainPen.FillingType.PISTON, "Parker"));
            manager.addCollection(newCollection);
            currentCollection = "Test";
            menu = MenuType.SINGLE_COLLECTION;
        }


        while(!quit) {
            if(!currentCollection.isBlank())
                option = displayMenu(menu, currentCollection);
            else if(!currentCraft.isBlank())
                option = displayMenu(menu, currentCraft);
            else
                option = displayMenu(menu);

            System.out.println();

            switch(menu) {
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

                            System.out.println("Successfully added collection " + newCollection.toString());
                            break;
                        case 4: // remove collection
                            System.out.print("Collection name: ");
                            input = getStringInput(false, "Invalid collection name.");

                            if(input.isBlank())
                                break;

                            if(!manager.removeCollection(input))
                                System.out.println("Collection does not exist.");
                            else
                                System.out.println("Successfully removed collection " + input + ".");
                            break;
                        case 5: // remove all collections
                            System.out.println("Removed " + manager.removeAllCollections() + " collection(s).");
                            break;
                        case 6: // remove all items in collections
                            System.out.println("Removed " + manager.removeAllItems() + " item(s) across " +
                                    manager.collectionCount() + " collection(s).");
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
                            System.out.println("What experience level do you want to change to?");
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
                        case 1: // display all project collections
                            break;
                        case 2: // manage project collection
                            break;
                        case 3: // add project collection
                            break;
                        case 4: // remove project collection
                            break;
                        case 5: // remove all project collections
                            break;
                        case 6: // remove all projects in collections
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
                            break;
                        case 2: // mark a project complete
                            break;
                        case 3: // add project
                            break;
                        case 4: // remove project
                            break;
                        case 5: // remove all projects
                            break;
                        case 6: // update experience level
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
                        case 1: // manage collections
                            menu = MenuType.ALL_COLLECTIONS;
                            break;
                        case 2: // manage crafts
                            menu = MenuType.ALL_CRAFTS;
                            break;
                        case 3: // quit
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
