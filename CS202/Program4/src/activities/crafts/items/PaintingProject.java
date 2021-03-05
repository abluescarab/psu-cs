package activities.crafts.items;

public class PaintingProject extends CraftProject {
    private final String surfaceType;
    private final String dimensions;

    public PaintingProject() {
        surfaceType = "";
        dimensions = "";
    }

    public PaintingProject(String name, String description, String surfaceType, String dimensions) {
        super(name, description);
        this.surfaceType = surfaceType;
        this.dimensions = dimensions;
    }

    public void display() {
        // TODO
    }
}
