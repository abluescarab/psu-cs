import java.util.Scanner;

public class Main extends Utils {
    private enum MenuType {
        MAIN,
        MAIN_COLLECTIONS,
        MAIN_CRAFTS,
        SINGLE_COLLECTION,
        SINGLE_CRAFT
    }

    private static MenuType menu = MenuType.MAIN;

    public static int displayMenu() {
        int maxOption = 0;

        switch(menu) {
            case MAIN_COLLECTIONS:
                System.out.println("Collections:");
                System.out.println("-------------------------------------");
                System.out.println("1) Manage a collection");
                System.out.println("2) Add a new collection");
                System.out.println("3) Remove a collection");
                System.out.println("4) Clear all collections");
                System.out.println("5) Back");

                maxOption = 5;
                break;
            case SINGLE_COLLECTION:
                break;
            case MAIN_CRAFTS:
                System.out.println("Craft projects:");
                System.out.println("-------------------------------------");
                System.out.println("1) Manage a project");
                System.out.println("2) Add a new project");
                System.out.println("3) Remove a project");
                System.out.println("4) Clear all projects");
                System.out.println("5) Back");

                maxOption = 5;
                break;
            case SINGLE_CRAFT:
                break;
            case MAIN:
            default:
                System.out.println("Hobby Manager");
                System.out.println("-------------------------------------");
                System.out.println("1) Manage collections");
                System.out.println("2) Manage craft projects");
                System.out.println("3) Quit");

                maxOption = 3;
                break;
        }

        return validateInput(1, maxOption);
    }

    public static void main(String[] args) {
        int option = 0;
        String input;
        boolean quit = false;

        while(!quit) {
            option = displayMenu();

            switch(menu) {
                case MAIN_COLLECTIONS:
                    switch(option) {
                        case 1: // manage
                            break;
                        case 2: // add
                            break;
                        case 3: // remove
                            break;
                        case 4: // clear
                            break;
                        default:
                            option = displayMenu();
                            break;
                    }
                    break;
                case SINGLE_COLLECTION:
                    break;
                case MAIN_CRAFTS:
                    switch(option) {
                        case 1: // manage
                            break;
                        case 2: // add
                            break;
                        case 3: // remove
                            break;
                        case 4: // clear
                            break;
                        default:
                            option = displayMenu();
                            break;
                    }
                    break;
                case SINGLE_CRAFT:
                    break;
                case MAIN:
                default:
                    switch(option) {
                        case 1: // manage collections
                            menu = MenuType.MAIN_COLLECTIONS;
                            break;
                        case 2: // manage craft projects
                            menu = MenuType.MAIN_CRAFTS;
                            break;
                        case 3: // quit
                            quit = true;
                            break;
                        default:
                            option = validateInput(1, 3);
                            break;
                    }
                    break;
            }
        }
    }
}
