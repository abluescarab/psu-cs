/*
TODO: array of LLLs (Activity is node)
TODO: move ArrayList<ActivityItem> to CraftActivity & CollectionActivity & make more specific
 */


import activities.*;
import utils.Utils;

public class Main extends Utils {
    private enum MenuType {
        MAIN,
        ALL_COLLECTIONS,
        ALL_CRAFTS,
        SINGLE_COLLECTION,
        SINGLE_CRAFT
    }

    private static MenuType menu = MenuType.MAIN;
    /*private static CollectionType currentCollection = CollectionType.NONE;
    private static CraftType currentCraft = CraftType.NONE;

    private static String changeCollection(CollectionType collectionType) {
        String menuText = "";
        currentCollection = collectionType;

        if(collectionType == CollectionType.NONE)
            menu = MenuType.ALL_COLLECTIONS;
        else {
            menu = MenuType.SINGLE_COLLECTION;

            if(collectionType == CollectionType.FOUNTAIN_PEN)
                menuText = "Fountain Pen Collection";
            else if(collectionType == CollectionType.KNIFE)
                menuText = "Knife Collection";
            else
                menuText = "Trading Card Collection";
        }

        return menuText;
    }

    private static String changeCraft(CraftType craftType) {
        String menuText = "";
        currentCraft = craftType;

        if(craftType == CraftType.NONE)
            menu = MenuType.ALL_CRAFTS;
        else {
            menu = MenuType.SINGLE_CRAFT;

            if(craftType == CraftType.PAINTING)
                menuText = "Painting Projects";
            else if(craftType == CraftType.SCULPTURE)
                menuText = "Sculpture Projects";
            else
                menuText = "Woodworking Projects";
        }

        return menuText;
    }*/

    private static int displayMenu(String menuText) {
        int maxOption = 0;

        switch(menu) {
            case ALL_COLLECTIONS:
                System.out.println("Collections");
                System.out.println("-------------------------------------");
                System.out.println("1) View fountain pen collection");
                System.out.println("1) View knife collection");
                System.out.println("2) View trading card collection");
                System.out.println("4) Clear all collections");
                System.out.println("5) Back");

                maxOption = 5;
                break;
            case SINGLE_COLLECTION:
                System.out.println(menuText);
                System.out.println("-------------------------------------");
                System.out.println("1) Display collection");
                System.out.println("2) Add item");
                System.out.println("3) Remove item");
                System.out.println("4) Clear all items");
                System.out.println("5) Update knowledge level");
                System.out.println("6) Back");

                maxOption = 6;
                break;
            case ALL_CRAFTS:
                System.out.println("Craft Projects");
                System.out.println("-------------------------------------");
                System.out.println("1) Manage paintings");
                System.out.println("2) Manage sculptures");
                System.out.println("3) Manage woodworking projects");
                System.out.println("4) Clear all projects");
                System.out.println("5) Back");

                maxOption = 5;
                break;
            case SINGLE_CRAFT:
                System.out.println(menuText);
                System.out.println("-------------------------------------");
                System.out.println("1) Display projects");
                System.out.println("2) Mark a project complete");
                System.out.println("3) Add project");
                System.out.println("4) Remove project");
                System.out.println("5) Clear all projects");
                System.out.println("6) Update experience level");
                System.out.println("7) Back");

                maxOption = 7;
                break;
            case MAIN:
            default:
                System.out.println("Activity Manager");
                System.out.println("-------------------------------------");
                System.out.println("1) View collections");
                System.out.println("2) View craft projects");
                System.out.println("3) Quit");

                maxOption = 3;
                break;
        }

        return getNumericInput(maxOption);
    }

    public static void main(String[] args) {
        int option = 0;
        String input = "";
        String menuText = "";
        CollectionType currentCollection = CollectionType.NONE;
        CraftType currentCraft = CraftType.NONE;

        boolean quit = false;
        ActivityManager manager = new ActivityManager();

        while(!quit) {
            option = displayMenu(menuText);

            switch(menu) {
                case ALL_COLLECTIONS:
                    switch(option) {
                        case 1: // manage fountain pen collection
                            menuText = changeCollection(CollectionType.FOUNTAIN_PEN);
                            break;
                        case 2: // manage knife collection
                            menuText = changeCollection(CollectionType.KNIFE);
                            break;
                        case 3: // manage trading card collection
                            menuText = changeCollection(CollectionType.TRADING_CARD);
                            break;
                        case 4: // clear all collections
                            System.out.println("Are you sure you want to clear all collections? (y/n) ");

                            if(getConfirmation()) {
                                System.out.println("Cancelled clearing all collections.");
                                break;
                            }

                            manager.clearAllCollections();
                            System.out.println("Successfully cleared all collections.");
                            break;
                        case 5: // back
                            menu = MenuType.MAIN;
                            break;
                        default:
                            option = displayMenu(menuText);
                            break;
                    }
                    break;
                case SINGLE_COLLECTION:
                    switch(option) {
                        case 1: // display collection
                            manager.display(currentCollection);
                            break;
                        case 2: // add item
                            System.out.println("Item name: ");
                            input = getStringInput();

                            if(input.isBlank()) {
                                System.out.println("Cancelled adding new item.");
                                break;
                            }

                            if(currentCollection == CollectionType.FOUNTAIN_PEN) {
                            }
                            else if(currentCollection == CollectionType.KNIFE) {

                            }
                            else if(currentCollection == CollectionType.TRADING_CARD) {

                            }
                            break;
                        case 3: // remove item
                            break;
                        case 4: // remove all items
                            break;
                        case 5: // update knowledge level
                            break;
                        case 6: // back
                            menuText = changeCollection(CollectionType.NONE);
                            break;
                        default:
                            option = displayMenu(menuText);
                            break;
                    }
                    break;
                case ALL_CRAFTS:
                    switch(option) {
                        case 1: // manage paintings
                            menuText = changeCraft(CraftType.PAINTING);
                            break;
                        case 2: // manage sculptures
                            menuText = changeCraft(CraftType.SCULPTURE);
                            break;
                        case 3: // manage woodworking projects
                            menuText = changeCraft(CraftType.WOODWORKING);
                            break;
                        case 4: // clear all projects
                            System.out.println("Are you sure you want to clear all projects? (y/n) ");

                            if(!getConfirmation()) {
                                System.out.println("Cancelled clearing all projects.");
                                break;
                            }

                            manager.clearAllProjects();
                            System.out.println("Successfully cleared all projects.");
                            break;
                        case 5: // back
                            menu = MenuType.MAIN;
                            break;
                        default:
                            option = displayMenu(menuText);
                            break;
                    }
                    break;
                case SINGLE_CRAFT:
                    switch(option) {
                        case 1: // display projects
                            manager.display(currentCraft);
                            break;
                        case 2: // mark project complete
                            break;
                        case 3: // add project
                            break;
                        case 4: // remove project
                            break;
                        case 5: // clear all projects
                            break;
                        case 6: // update experience level
                            break;
                        case 7: // back
                            menuText = changeCraft(CraftType.NONE);
                            break;
                        default:
                            option = displayMenu(menuText);
                            break;
                    }
                    break;
                case MAIN:
                default:
                    switch(option) {
                        case 1: // manage collections
                            menu = MenuType.ALL_COLLECTIONS;
                            break;
                        case 2: // manage craft projects
                            menu = MenuType.ALL_CRAFTS;
                            break;
                        case 3: // quit
                            quit = true;
                            break;
                        default:
                            option = displayMenu(menuText);
                            break;
                    }
                    break;
            }

            System.out.println();
        }
    }
}
