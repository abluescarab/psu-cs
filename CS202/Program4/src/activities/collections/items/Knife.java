package activities.collections.items;

public class Knife extends CollectionItem {
    private double bladeLength;
    private String bladeMaterial;

    public Knife() {
        bladeLength = 0.0;
        bladeMaterial = "";
    }

    public Knife(String name, double price, double bladeLength, String bladeMaterial) {
        super(name, price);
        this.bladeLength = bladeLength;
        this.bladeMaterial = bladeMaterial;
    }

    @Override
    public Knife create() {
        super.create();

        System.out.print("Blade length: ");
        bladeLength = getDoubleInput();

        System.out.print("Blade material: ");
        bladeMaterial = getStringInput();

        return this;
    }

    public void display() {
        // TODO
    }
}
