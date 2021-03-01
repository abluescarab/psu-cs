package activities.collections;

public class CollectionObject {
    private String name;
    private double price;
    private CollectionObject next;

    public CollectionObject() {
        name = "";
        price = 0.0;
    }

    public CollectionObject(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
