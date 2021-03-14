/* Alana Gilston - 3/5/21 - CS202 - Program 4
 * ActivityItem.java
 *
 * The ActivityItem class manages an item stored in an activity, either a collection item or craft project.
 */
package activities;

import utils.*;

public abstract class ActivityItem extends Utils {
    /**
     * Name of the ActivityItem.
     */
    private String name;

    /**
     * Create a new ActivityItem.
     */
    public ActivityItem() {
        name = "";
    }

    /**
     * Create a new ActivityItem.
     * @param name Name of the ActivityItem
     */
    public ActivityItem(String name) {
        this.name = name;
    }

    /**
     * Create a new ActivityItem from user input.
     * @return ActivityItem created
     */
    public ActivityItem create() {
        String input = "";

        System.out.print("Name: ");
        input = getStringInput(false, "Invalid item name.");

        if(input.isBlank())
            return null;

        name = input;
        return this;
    }

    /**
     * Whether the ActivityItem's name matches the provided name.
     * @param name Name to compare to
     * @return Whether the names match.
     */
    public boolean matches(String name) {
        return this.name.equals(name);
    }

    /**
     * Display the ActivityItem.
     */
    public void display() {
        System.out.println(name);
    }

    /**
     * Convert the ActivityItem to a string.
     * @return The ActivityItem as a string
     */
    public String toString() {
        return name;
    }
}
