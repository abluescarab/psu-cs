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
    private String ink;
    private FillingType fillingSystem;
    private boolean isItalic;

    public FountainPen() {
        nibSize = "";
        ink = "";
        fillingSystem = FillingType.CARTRIDGE;
        isItalic = false;
    }
}
