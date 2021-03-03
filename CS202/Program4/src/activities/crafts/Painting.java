package activities.crafts;

public class Painting extends CraftProject  {
    private String surfaceType;
    private String dimensions;

    public Painting() {
        surfaceType = "";
        dimensions = "";
    }

    public Painting(String name, String description, String surfaceType, String dimensions) {
        super(name, description);
        this.surfaceType = surfaceType;
        this.dimensions = dimensions;
    }
}
