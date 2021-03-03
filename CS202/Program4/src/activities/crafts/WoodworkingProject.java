package activities.crafts;

import java.util.ArrayList;

public class WoodworkingProject extends CraftProject {
    private String woodUsed;
    private String dimensions;

    public WoodworkingProject() {
        woodUsed = "";
        dimensions = "";
    }

    public WoodworkingProject(String name, boolean forSale, String woodUsed, String dimensions) {
        super(name, forSale);
        this.woodUsed = woodUsed;
        this.dimensions = dimensions;
    }
}
