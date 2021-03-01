package activities.collections;

public class FountainPen extends CollectionObject {
    public enum FillingType {
        CARTRIDGE,
        CONVERTER,
        PISTON,
        VACUUM,
        EYEDROPPER
    }

    private String nibSize;
    private FillingType fillingSystem;
    private String ink;

    public FountainPen() {
        nibSize = "";
        fillingSystem = FillingType.CARTRIDGE;
        ink = "";
    }

    public FountainPen(String name, double price, String nibSize,
                       FillingType fillingSystem, String ink) {
        super(name, price);
        this.nibSize = nibSize;
        this.fillingSystem = fillingSystem;
        this.ink = ink;
    }
}
