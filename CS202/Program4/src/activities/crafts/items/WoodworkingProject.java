package activities.crafts.items;

public class WoodworkingProject extends CraftProject {
    private String woodType;
    private String dimensions;

    public WoodworkingProject() {
        woodType = "";
        dimensions = "";
    }

    public WoodworkingProject(String name, String description, String woodType, String dimensions) {
        super(name, description);
        this.woodType = woodType;
        this.dimensions = dimensions;
    }

    @Override
    public WoodworkingProject create() {
        super.create();

        System.out.print("Wood type: ");
        woodType = getStringInput();

        System.out.print("Dimensions: ");
        dimensions = getStringInput();

        return this;
    }

    public void display() {
        // TODO
    }
}
