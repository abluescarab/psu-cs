package utils;

import java.util.Scanner;

public abstract class Utils {
    protected static final Scanner scanner = new Scanner(System.in);

    protected static int getNumericInput(int maxInput) {
        return getNumericInput(1, maxInput, true);
    }

    protected static int getNumericInput(int minInput, int maxInput) {
        return getNumericInput(minInput, maxInput, true);
    }

    protected static int getNumericInput(int minInput, int maxInput, boolean showPrompt) {
        int input = -1;

        do {
            if(showPrompt)
                System.out.print("> ");

            if(scanner.hasNextInt())
                input = scanner.nextInt();
            else
                System.out.println("Invalid input. Please try again.");

            scanner.nextLine();
        } while(input < minInput || input > maxInput);

        return input;
    }

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

            scanner.nextLine();
        } while(!success);

        return input;
    }

    protected static String getStringInput() {
        return getStringInput(false, "");
    }

    protected static String getStringInput(boolean repeatIfBlank, String invalidMessage) {
        String input = "";

        do {
            input = scanner.nextLine();

            if(repeatIfBlank && input.isBlank())
                System.out.println(invalidMessage);
        } while(repeatIfBlank && input.isBlank());

        return input;
    }

    protected static boolean getConfirmation() {
        String input = scanner.nextLine().toLowerCase();
        return input.equals("y") || input.equals("yes");
    }
}
