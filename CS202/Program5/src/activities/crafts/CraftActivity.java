/* Alana Gilston - 3/5/21 - CS202 - Program 5
 * CraftActivity.java
 *
 * The CraftActivity class manages a crafting skill and its corresponding projects.
 */
package activities.crafts;

import activities.*;
import activities.crafts.items.*;

public class CraftActivity extends Activity {
    /**
     * The type of project stored in the craft.
     */
    private CraftType craftType;

    /**
     * Create a new CraftActivity.
     */
    public CraftActivity() {
        super();
        craftType = CraftType.PAINTING;
    }

    /**
     * Create a new CraftActivity from user input.
     * @return The new CraftActivity
     */
    @Override
    public CraftActivity create() {
        int option = 0;

        if(super.create() == null)
            return null;

        System.out.println("What craft projects are you working on?");
        System.out.println("1) Painting");
        System.out.println("2) Sculpture");
        System.out.println("3) Woodworking");

        option = getNumericInput(3);

        if(option == 1) {
            craftType = CraftType.PAINTING;
            addPaintingProject();
        }
        else if(option == 2) {
            craftType = CraftType.SCULPTURE;
            addSculptureProject();
        }
        else {
            craftType = CraftType.WOODWORKING;
            addWoodworkingProject();
        }

        return this;
    }

    /**
     * Get the type of project stored in the craft.
     * @return The type of project stored in the craft
     */
    public CraftType getType() {
        return craftType;
    }

    /**
     * Mark a project completed.
     * @param name Name of the project
     * @return Whether the project was marked complete or not
     */
    public boolean markCompleted(String name) {
        for(ActivityItem item : items) {
            if(item.matches(name)) {
                ((CraftProject)item).markCompleted();
                return true;
            }
        }

        return false;
    }

    /**
     * Add a new PaintingProject to the craft from user input.
     */
    private void addPaintingProject() {
        boolean quit = false;

        do {
            System.out.print("Do you want to add a painting project? (y/n) ");

            if(getConfirmation())
                add(new PaintingProject().create());
            else
                quit = true;
        } while(!quit);
    }

    /**
     * Add a new SculptureProject to the craft from user input.
     */
    private void addSculptureProject() {
        boolean quit = false;

        do {
            System.out.print("Do you want to add a sculpture project? (y/n) ");

            if(getConfirmation())
                add(new SculptureProject().create());
            else
                quit = true;
        } while(!quit);
    }

    /**
     * Add a new WoodworkingProject to the craft from user input.
     */
    private void addWoodworkingProject() {
        boolean quit = false;

        do {
            System.out.print("Do you want to add a woodworking project? (y/n) ");

            if(getConfirmation())
                add(new WoodworkingProject().create());
            else
                quit = true;
        } while(!quit);
    }
}
