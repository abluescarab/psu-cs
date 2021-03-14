/* Alana Gilston - 3/5/21 - CS202 - Program 4
 * FountainPen.java
 *
 * The FountainPen class manages a fountain pen in a collection.
 */
package activities.collections.items;

public class FountainPen extends CollectionItem {
    public enum FillingType {
        CARTRIDGE_CONVERTER,
        PISTON,
        VACUUM,
        EYEDROPPER
    }

    /**
     * Nib size installed in the pen.
     */
    private String nibSize;
    /**
     * Filling system used by the pen.
     */
    private FillingType fillingSystem;
    /**
     * Ink currently in the pen.
     */
    private String ink;

    /**
     * Create a new FountainPen.
     */
    public FountainPen() {
        nibSize = "";
        fillingSystem = FillingType.CARTRIDGE_CONVERTER;
        ink = "";
    }

    /**
     * Create a new FountainPen.
     * @param name The name of the pen
     * @param price Price paid for the pen
     * @param nibSize Nib size installed in the pen
     * @param fillingSystem Filling system the pen uses
     * @param ink Current ink in the pen
     */
    public FountainPen(String name, double price, String nibSize, FillingType fillingSystem, String ink) {
        super(name, price);
        this.nibSize = nibSize;
        this.fillingSystem = fillingSystem;
        this.ink = ink;
    }

    /**
     * Change the nib size installed in the pen.
     * @param nibSize New nib size
     */
    public void changeNibSize(String nibSize) {
        this.nibSize = nibSize;
    }

    /**
     * Change the filling system the pen uses
     * @param fillingSystem New filling system
     */
    public void changeFillingSystem(FillingType fillingSystem) {
        this.fillingSystem = fillingSystem;
    }

    /**
     * Change the current ink in the pen.
     * @param ink New ink
     */
    public void changeInk(String ink) {
        this.ink = ink;
    }

    /**
     * Create a new FountainPen from user input.
     * @return The object created
     */
    @Override
    public FountainPen create() {
        int option = 0;

        if(super.create() == null)
            return null;

        System.out.print("Nib size: ");
        nibSize = getStringInput();

        System.out.println("Filling system:");
        System.out.println("1) Cartridge/converter");
        System.out.println("2) Piston");
        System.out.println("3) Vacuum");
        System.out.println("4) Eyedropper");

        option = getNumericInput(4);

        if(option == 1)
            fillingSystem = FillingType.CARTRIDGE_CONVERTER;
        else if(option == 2)
            fillingSystem = FillingType.PISTON;
        else if(option == 3)
            fillingSystem = FillingType.VACUUM;
        else
            fillingSystem = FillingType.EYEDROPPER;

        System.out.print("Current ink: ");
        ink = getStringInput();

        return this;
    }

    /**
     * Display the FountainPen.
     */
    public void display() {
        super.display();
        System.out.println("Nib size: " + nibSize);
        System.out.print("Filling system: ");

        if(fillingSystem == FillingType.CARTRIDGE_CONVERTER)
            System.out.println("Cartridge/Converter");
        else if(fillingSystem == FillingType.PISTON)
            System.out.println("Piston");
        else if(fillingSystem == FillingType.VACUUM)
            System.out.println("Vacuum");
        else
            System.out.println("Eyedropper");

        System.out.println("Current ink: " + ink);
    }
}
