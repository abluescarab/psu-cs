package utils;

import java.util.Scanner;

public abstract class Utils {
    private static final Scanner scanner = new Scanner(System.in);

    protected static int validateInput(int maxInput) {
        return validateInput(1, maxInput, true);
    }

    protected static int validateInput(int minInput, int maxInput) {
        return validateInput(minInput, maxInput, true);
    }

    protected static int validateInput(int minInput, int maxInput, boolean showPrompt) {
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

    protected static boolean validateYes(String prompt) {
        String input = "";

        System.out.print(prompt + " (y/n) ");
        input = scanner.nextLine().toLowerCase();

        return input.equals("y") || input.equals("yes");
    }
}
