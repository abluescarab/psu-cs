package activities.crafts;

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
}
