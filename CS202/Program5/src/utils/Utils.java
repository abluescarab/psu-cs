/* Alana Gilston - 3/2/21 - CS202 - Program 5
 * Utils.java
 *
 * The Utils class manages a number of utility functions for other classes in the project, primarily user input.
 */
package utils;

import java.util.Scanner;

public abstract class Utils {
    /**
     * A scanner instance to get user input.
     */
    protected static Scanner scanner = new Scanner(System.in);

    /**
     * Get numeric user input bounded by a maximum value.
     * @param maxInput Maximum allowed input
     * @return The number provided by the user
     */
    protected static int getNumericInput(int maxInput) {
        return getNumericInput(1, maxInput, true);
    }

    /**
     * Get numeric user input bounded by a minimum and maximum value.
     * @param minInput Minimum allowed input
     * @param maxInput Maximum allowed input
     * @return The number provided by the user
     */
    protected static int getNumericInput(int minInput, int maxInput) {
        return getNumericInput(minInput, maxInput, true);
    }

    /**
     * Get numeric user input bounded by a minimum and maximum value.
     * @param minInput Minimum allowed input
     * @param maxInput Maximum allowed input
     * @param showPrompt Whether to show the caret prompt
     * @return The number provided by the user
     */
    protected static int getNumericInput(int minInput, int maxInput, boolean showPrompt) {
        int input = -1;

        do {
            if(showPrompt)
                System.out.print("> ");

            if(scanner.hasNextInt())
                input = scanner.nextInt();
            else
                System.out.println("Invalid input. Please try again.");

            clearScanner();
        } while(input < minInput || input > maxInput);

        return input;
    }

    /**
     * Get a double from user input.
     * @return The double provided by the user
     */
    protected static double getDoubleInput() {
        double input = 0.0;
        boolean success = false;

        do {
            if(scanner.hasNextDouble()) {
                input = scanner.nextDouble();
                success = true;
            }
            else
                System.out.println("Invalid input. Please try again.");

            clearScanner();
        } while(!success);

        return input;
    }

    /**
     * Get a string from user input.
     * @return The string provided by the user
     */
    protected static String getStringInput() {
        return getStringInput(false, "");
    }

    /**
     * Get a string from user input.
     * @param repeatIfBlank Whether to repeat the prompt if user input is blank
     * @param invalidMessage Message to display when user input is invalid
     * @return The string provided by the user
     */
    protected static String getStringInput(boolean repeatIfBlank, String invalidMessage) {
        String input = "";

        do {
            input = scanner.nextLine();

            if(repeatIfBlank && input.isBlank())
                System.out.println(invalidMessage);
        } while(repeatIfBlank && input.isBlank());

        clearScanner();
        return input;
    }

    /**
     * Get confirmation from the user to perform an action.
     * @return Whether the user confirmed or not
     */
    protected static boolean getConfirmation() {
        String input = scanner.nextLine().toLowerCase();
        clearScanner();
        return input.equals("y") || input.equals("yes");
    }

    /**
     * Clear the scanner instance.
     */
    protected static void clearScanner() {
        scanner = new Scanner(System.in);
    }
}
