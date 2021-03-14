/* Alana Gilston - 3/5/21 - CS202 - Program 4
 * PaintingProject.java
 *
 * The PaintingProject class manages a painting project.
 */
package activities.crafts.items;

public class PaintingProject extends CraftProject {
    /**
     * Surface type of the PaintingProject, e.g. canvas, wood, etc.
     */
    private String surfaceType;
    /**
     * Dimensions of the PaintingProject.
     */
    private String dimensions;

    /**
     * Create a new PaintingProject.
     */
    public PaintingProject() {
        surfaceType = "";
        dimensions = "";
    }

    /**
     * Create a new PaintingProject.
     * @param name Name of the project
     * @param description Description of the project
     * @param surfaceType Surface type used for the painting
     * @param dimensions Dimensions of the final painting
     */
    public PaintingProject(String name, String description, String surfaceType, String dimensions) {
        super(name, description);
        this.surfaceType = surfaceType;
        this.dimensions = dimensions;
    }

    /**
     * Create a new PaintingProject from user input.
     * @return The PaintingProject created
     */
    @Override
    public PaintingProject create() {
        if(super.create() == null)
            return null;

        System.out.print("Surface type: ");
        surfaceType = getStringInput();

        System.out.print("Dimensions: ");
        dimensions = getStringInput();

        return this;
    }

    /**
     * Display the PaintingProject.
     */
    public void display() {
        super.display();
        System.out.println("Surface type: " + surfaceType);
        System.out.println("Dimensions: " + dimensions);
    }
}
