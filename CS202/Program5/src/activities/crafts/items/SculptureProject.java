/* Alana Gilston - 3/5/21 - CS202 - Program 5
 * SculptureProject.java
 *
 * The SculptureProject class manages a sculpture project.
 */
package activities.crafts.items;

public class SculptureProject extends CraftProject {
    public enum DryingType {
        OVEN,
        AIR,
        KILN,
        OTHER
    }

    /**
     * The brand of clay used.
     */
    private String clayBrand;
    /**
     * The method used to make the clay dry.
     */
    private DryingType dryingType;

    /**
     * Create a new SculptureProject.
     */
    public SculptureProject() {
        clayBrand = "";
        dryingType = DryingType.OTHER;
    }

    /**
     * Create a new SculptureProject.
     * @param name Name of the project
     * @param description Description of the project
     * @param clayBrand Brand of clay used
     * @param dryingType How the clay dries
     */
    public SculptureProject(String name, String description, String clayBrand, DryingType dryingType) {
        super(name, description);
        this.clayBrand = clayBrand;
        this.dryingType = dryingType;
    }

    /**
     * Create a new SculptureProject from user input.
     * @return The SculptureProject created
     */
    @Override
    public SculptureProject create() {
        int option = 0;

        if(super.create() == null)
            return null;

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

    /**
     * Display the SculptureProject.
     */
    public void display() {
        super.display();
        System.out.println("Clay brand: " + clayBrand);
        System.out.print("Drying type: ");

        switch(dryingType) {
            case OVEN:
                System.out.println("Oven bake");
                break;
            case AIR:
                System.out.println("Air dry");
                break;
            case KILN:
                System.out.println("Kiln fire");
                break;
            case OTHER:
                System.out.println("Other");
                break;
        }
    }
}
