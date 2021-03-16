/* Alana Gilston - 3/5/21 - CS202 - Program 5
 * Activity.java
 *
 * The Activity class manages a user-defined activity and its corresponding items.
 */
package activities;

import utils.*;

import java.util.*;

public abstract class Activity extends Utils {
    /**
     * Items stored in the Activity.
     */
    protected final ArrayList<ActivityItem> items;
    /**
     * Name of the Activity.
     */
    private String name;
    /**
     * Experience with the Activity.
     */
    private ExperienceLevel experienceLevel;
    /**
     * Next Activity in the list.
     */
    private Activity next;

    /**
     * Create a new Activity.
     */
    public Activity() {
        name = "";
        experienceLevel = ExperienceLevel.NOVICE;
        items = new ArrayList<>();
        next = null;
    }

    /**
     * Create a new Activity.
     * @param name Name of the Activity
     * @param experienceLevel Experience level of the Activity
     */
    public Activity(String name, ExperienceLevel experienceLevel) {
        this.name = name;
        this.experienceLevel = experienceLevel;
        items = new ArrayList<>();
        next = null;
    }

    /**
     * Create a new Activity from user input.
     * @return The new Activity
     */
    public Activity create() {
        String input = "";
        int option = 0;

        System.out.print("Name: ");
        input = getStringInput(false, "Invalid name.");

        if(input.isBlank())
            return null;

        System.out.println("Experience level:");
        System.out.println("1) Novice");
        System.out.println("2) Intermediate");
        System.out.println("3) Advanced");
        System.out.println("4) Expert");
        option = getNumericInput(4);

        changeName(input);

        if(option == 0)
            changeExperienceLevel(ExperienceLevel.NOVICE);
        else if(option == 1)
            changeExperienceLevel(ExperienceLevel.INTERMEDIATE);
        else if(option == 3)
            changeExperienceLevel(ExperienceLevel.ADVANCED);
        else
            changeExperienceLevel(ExperienceLevel.EXPERT);

        return this;
    }

    /**
     * Change the Activity name.
     * @param name New name
     */
    public void changeName(String name) {
        this.name = name;
    }

    /**
     * Change the Activity experience level.
     * @param experienceLevel New experience level
     */
    public void changeExperienceLevel(ExperienceLevel experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    /**
     * Check if the Activity name matches a provided name.
     * @param name Name to compare to
     * @return Whether the names match
     */
    public boolean matches(String name) {
        return this.name.equals(name);
    }

    /**
     * Get the next Activity.
     * @return Next Activity
     */
    protected Activity getNext() {
        return next;
    }

    /**
     * Set the next Activity.
     * @param next New next Activity
     */
    protected void setNext(Activity next) {
        this.next = next;
    }

    /**
     * Display the Activity.
     * @param displayItems Whether to display the items in the Activity
     */
    public void display(boolean displayItems) {
        System.out.print(name + " (");

        if(experienceLevel == ExperienceLevel.NOVICE)
            System.out.print("Novice");
        else if(experienceLevel == ExperienceLevel.INTERMEDIATE)
            System.out.print("Intermediate");
        else if(experienceLevel == ExperienceLevel.ADVANCED)
            System.out.print("Advanced");
        else
            System.out.print("Expert");

        System.out.println(")");

        if(displayItems)
            displayAll();
    }

    /**
     * Display all items in the Activity.
     * @return Number of items displayed
     */
    public int displayAll() {
        for(ActivityItem item : items) {
            System.out.println();
            item.display();
        }

        return items.size();
    }

    /**
     * Get the number of items in the Activity.
     * @return Number of items in the Activity
     */
    public int count() {
        return items.size();
    }

    /**
     * Check if the Activity contains an item.
     * @param name Name of the item
     * @return Whether the Activity contains an item
     */
    public boolean contains(String name) {
        for(ActivityItem item : items) {
            if(item.matches(name))
                return true;
        }

        return false;
    }

    /**
     * Get the number of items with a name in the Activity.
     * @param name Name to find
     * @return Number of items with that name
     */
    public int containsCount(String name) {
        int count = 0;

        for(ActivityItem item : items) {
            if(item.matches(name))
                ++count;
        }

        return count;
    }

    /**
     * Add a new item to the Activity.
     * @param item ActivityItem to add
     * @return Whether the item was added
     */
    public boolean add(ActivityItem item) {
        if(item == null)
            return false;

        return items.add(item);
    }

    /**
     * Remove the item from the Activity.
     * @param item ActivityItem to remove
     * @return Whether the item was removed
     */
    public boolean remove(ActivityItem item) {
        if(items.contains(item))
            return items.remove(item);

        return false;
    }

    /**
     * Remove the first instance of an item with a name.
     * @param name Name to find
     * @return Whether the item was removed
     */
    public boolean removeFirst(String name) {
        Iterator<ActivityItem> iterator = items.iterator();

        while(iterator.hasNext()) {
            if(iterator.next().matches(name)) {
                iterator.remove();
                return true;
            }
        }

        return false;
    }

    /**
     * Remove all instances of items with a name.
     * @param name Name to find
     * @return Number of items removed
     */
    public int removeAll(String name) {
        int removed = 0;
        Iterator<ActivityItem> iterator = items.iterator();

        while(iterator.hasNext()) {
            if(iterator.next().matches(name)) {
                iterator.remove();
                ++removed;
            }
        }

        return removed;
    }

    /**
     * Remove all items from the Activity.
     * @return Number of items removed
     */
    public int removeAll() {
        int count = count();
        items.clear();
        return count;
    }

    /**
     * Convert the Activity to a string.
     * @return The Activity as a string
     */
    public String toString() {
        return name;
    }

    /**
     * Display all items with a name.
     * @param name Name to find
     * @return Array of items with that name
     */
    ArrayList<ActivityItem> displayItemsWithName(String name) {
        int count = 0;
        ArrayList<ActivityItem> foundItems = new ArrayList<>();

        for(ActivityItem item : items) {
            if(item.matches(name)) {
                foundItems.add(item);
                System.out.print(++count + ") ");
                item.display();
                System.out.println();
            }
        }

        return foundItems;
    }
}
