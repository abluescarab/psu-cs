import activities.*;
import activities.collections.*;
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
                System.out.println("1) Manage collection");
                System.out.println("2) Add collection");
                System.out.println("3) Remove collection");
                System.out.println("4) Remove all collections");
                System.out.println("5) Remove all items in collections");
                System.out.println("6) Back");

                maxOption = 6;
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
                System.out.println("1) Manage project collection");
                System.out.println("2) Add project collection");
                System.out.println("3) Remove project collection");
                System.out.println("4) Remove all project collections");
                System.out.println("5) Remove all projects in collections");
                System.out.println("6) Back");

                maxOption = 6;
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

        while(!quit) {
            if(!currentCollection.isBlank())
                option = displayMenu(menu, currentCollection);
            else if(!currentCraft.isBlank())
                option = displayMenu(menu, currentCraft);
            else
                option = displayMenu(menu);

            switch(menu) {
                case ALL_COLLECTIONS:
                    switch(option) {
                        case 1: // manage collection
                            System.out.println("Collection name: ");
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
                        case 2: // add collection
                            newCollection = new CollectionActivity().create();

                            while(!manager.addCollection(newCollection)) {
                                System.out.println("Collection already exists. Please enter another name.");
                                newCollection.changeName(getStringInput(true, "Invalid name."));
                            }

                            System.out.println("Successfully added collection " + newCollection.toString());
                            break;
                        case 3: // remove collection
                            System.out.println("Collection name: ");
                            input = getStringInput(false, "Invalid collection name.");

                            if(input.isBlank())
                                break;

                            if(!manager.removeCollection(input))
                                System.out.println("Collection does not exist.");
                            else
                                System.out.println("Successfully removed collection " + input);
                            break;
                        case 4: // remove all collections
                            System.out.println("Removed " + manager.removeAllCollections() + " collection(s).");
                            break;
                        case 5: // remove all items in collections
                            System.out.println("Removed " + manager.removeAllItems() + " item(s) across " +
                                    manager.collectionCount() + " collection(s).");
                            break;
                        case 6: // back
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
                            if(!manager.displayCollection(currentCollection))
                                System.out.println("Collection does not exist.");
                            break;
                        case 2: // add item
                            collectionType = manager.collectionType(currentCollection);

                            if(collectionType == CollectionType.FOUNTAIN_PEN) {

                            }
                            else if(collectionType == CollectionType.KNIFE) {

                            }
                            else if(collectionType == CollectionType.TRADING_CARD) {

                            }
                            else {

                            }
                            break;
                        case 3: // remove item
                            break;
                        case 4: // remove all items
                            break;
                        case 5: // update knowledge level
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
                        case 1: // manage project collection
                            break;
                        case 2: // add project collection
                            break;
                        case 3: // remove project collection
                            break;
                        case 4: // remove all project collections
                            break;
                        case 5: // remove all projects in collections
                            break;
                        case 6: // back
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
                            break;
                        case 2: // manage crafts
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
