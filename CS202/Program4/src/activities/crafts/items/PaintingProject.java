package activities.crafts.items;

public class PaintingProject extends CraftProject {
    private String surfaceType;
    private String dimensions;

    public PaintingProject() {
        surfaceType = "";
        dimensions = "";
    }

    public PaintingProject(String name, String description, String surfaceType, String dimensions) {
        super(name, description);
        this.surfaceType = surfaceType;
        this.dimensions = dimensions;
    }

    @Override
    public PaintingProject create() {
        super.create();

        System.out.print("Surface type: ");
        surfaceType = getStringInput();

        System.out.print("Dimensions: ");
        dimensions = getStringInput();

        return this;
    }

    public void display() {
        // TODO
    }
}
