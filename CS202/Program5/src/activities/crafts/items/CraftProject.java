/* Alana Gilston - 3/5/21 - CS202 - Program 4
 * CraftProject.java
 *
 * The CraftProject class manages a craft project.
 */
package activities.crafts.items;

import activities.*;

public class CraftProject extends ActivityItem {
    /**
     * Description of the project.
     */
    private String description;
    /**
     * Whether the project is complete or in-progress.
     */
    private boolean complete;

    /**
     * Create a new CraftProject.
     */
    public CraftProject() {
        description = "";
        complete = false;
    }

    /**
     * Create a new CraftProject.
     * @param name Name of the project
     * @param description Description of the project
     */
    public CraftProject(String name, String description) {
        super(name);
        this.description = description;
        complete = false;
    }

    /**
     * Create a new CraftProject from user input.
     * @return The CraftProject created
     */
    @Override
    public CraftProject create() {
        if(super.create() == null)
            return null;

        System.out.print("Description: ");
        description = getStringInput();

        return this;
    }

    /**
     * Change the project description.
     * @param description New description
     */
    public void changeDescription(String description) {
        this.description = description;
    }

    /**
     * Get whether the project is complete or not.
     * @return Whether the project is complete or not
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     * Mark the project as complete.
     */
    public void markCompleted() {
        complete = true;
    }

    /**
     * Display the CraftProject.
     */
    public void display() {
        super.display();
        System.out.println("Description: " + description);
        System.out.println("Completed: " + (complete ? "Yes" : "No"));
    }
}
