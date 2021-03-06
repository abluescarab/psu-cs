package activities.crafts.items;

import java.awt.color.*;

public class SculptureProject extends CraftProject {
    public enum DryingType {
        OVEN,
        AIR,
        KILN,
        OTHER
    }

    private String clayBrand;
    private DryingType dryingType;

    public SculptureProject() {
        clayBrand = "";
        dryingType = DryingType.OTHER;
    }

    public SculptureProject(String name, String description, String clayBrand, DryingType dryingType) {
        super(name, description);
        this.clayBrand = clayBrand;
        this.dryingType = dryingType;
    }

    @Override
    public SculptureProject create() {
        int option = 0;

        super.create();

        System.out.print("Clay brand: ");
        clayBrand = getStringInput();

        System.out.println("Drying type:");
        System.out.println("1) Oven bake");
        System.out.println("2) Air dry");
        System.out.println("3) Kiln fired");
        System.out.println("4) Other");

        option = getNumericInput(4);

        if(option == 1)
            dryingType = DryingType.OVEN;
        else if(option == 2)
            dryingType = DryingType.AIR;
        else if(option == 3)
            dryingType = DryingType.KILN;
        else
            dryingType = DryingType.OTHER;

        return this;
    }

    public void display() {
        // TODO
    }
}
