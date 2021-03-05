package activities.crafts.items;

public class WoodworkingProject extends CraftProject {
    private final String woodType;
    private final String dimensions;

    public WoodworkingProject() {
        woodType = "";
        dimensions = "";
    }

    public WoodworkingProject(String name, String description, String woodType, String dimensions) {
        super(name, description);
        this.woodType = woodType;
        this.dimensions = dimensions;
    }

    public void display() {
        // TODO
    }
}
