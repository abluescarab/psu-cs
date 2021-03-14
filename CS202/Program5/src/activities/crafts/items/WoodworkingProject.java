/* Alana Gilston - 3/5/21 - CS202 - Program 4
 * WoodworkingProject.java
 *
 * The WoodworkingProject class manages a woodworking project.
 */
package activities.crafts.items;

public class WoodworkingProject extends CraftProject {
    /**
     * The type of wood uses, e.g. oak, ash, etc.
     */
    private String woodType;
    /**
     * The dimensions of the final piece.
     */
    private String dimensions;

    /**
     * Create a new WoodworkingProject.
     */
    public WoodworkingProject() {
        woodType = "";
        dimensions = "";
    }

    /**
     * Create a new WoodworkingProject.
     * @param name Name of the project
     * @param description Description of the project
     * @param woodType Wood type used, e.g. oak, ash, etc.
     * @param dimensions Dimensions of the final project
     */
    public WoodworkingProject(String name, String description, String woodType, String dimensions) {
        super(name, description);
        this.woodType = woodType;
        this.dimensions = dimensions;
    }

    /**
     * Create a new WoodworkingProject from user input.
     * @return The WoodworkingProject created
     */
    @Override
    public WoodworkingProject create() {
        if(super.create() == null)
            return null;

        System.out.print("Wood type: ");
        woodType = getStringInput();

        System.out.print("Dimensions: ");
        dimensions = getStringInput();

        return this;
    }

    /**
     * Display the WoodworkingProject.
     */
    public void display() {
        super.display();
        System.out.println("Wood type: " + woodType);
        System.out.println("Dimensions: " + dimensions);
    }
}
