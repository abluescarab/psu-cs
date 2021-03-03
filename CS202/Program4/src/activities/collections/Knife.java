package activities.collections;

public class Knife extends CollectionObject {
    private double bladeLength;
    private String bladeMaterial;

    public Knife() {
        bladeMaterial = "";
        bladeLength = 0.0;
    }

    public Knife(String name, double price, double bladeLength, String bladeMaterial) {
        super(name, price);
        this.bladeLength = bladeLength;
        this.bladeMaterial = bladeMaterial;
    }
}