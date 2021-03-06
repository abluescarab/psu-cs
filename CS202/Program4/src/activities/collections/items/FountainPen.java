package activities.collections.items;

public class FountainPen extends CollectionItem {
    public enum FillingType {
        CARTRIDGE_CONVERTER,
        PISTON,
        VACUUM,
        EYEDROPPER
    }

    private String nibSize;
    private FillingType fillingSystem;
    private String ink;

    public FountainPen() {
        nibSize = "";
        fillingSystem = FillingType.CARTRIDGE_CONVERTER;
        ink = "";
    }

    public FountainPen(String name, double price, String nibSize, FillingType fillingSystem, String ink) {
        super(name, price);
        this.nibSize = nibSize;
        this.fillingSystem = fillingSystem;
        this.ink = ink;
    }

    public void changeNibSize(String nibSize) {
        this.nibSize = nibSize;
    }

    public void changeFillingSystem(FillingType fillingSystem) {
        this.fillingSystem = fillingSystem;
    }

    public void changeInk(String ink) {
        this.ink = ink;
    }

    @Override
    public FountainPen create() {
        int option = 0;

        super.create();

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

    public void display() {
        // TODO
    }
}
