package activities.crafts.items;

public class SculptureProject extends CraftProject {
    public enum DryingType {
        NONE,
        OVEN,
        AIR,
        KILN
    }

    private String clayBrand;
    private DryingType dryingType;

    public SculptureProject() {
        clayBrand = "";
        dryingType = DryingType.NONE;
    }

    public SculptureProject(String name, String description, String clayBrand, DryingType dryingType) {
        super(name, description);
        this.clayBrand = clayBrand;
        this.dryingType = dryingType;
    }

    public void display() {
        // TODO
    }
}
