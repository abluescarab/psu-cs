package activities.crafts.projects;

public class WoodworkingProject extends CraftProject {
    private final String woodUsed;
    private final String dimensions;

    public WoodworkingProject() {
        woodUsed = "";
        dimensions = "";
    }

    public WoodworkingProject(String name, String description, boolean forSale, String woodUsed, String dimensions) {
        super(name, description);
        this.woodUsed = woodUsed;
        this.dimensions = dimensions;
    }
}
