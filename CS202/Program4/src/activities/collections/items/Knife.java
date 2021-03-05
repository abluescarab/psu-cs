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

    public void display() {
        // TODO
    }
}
