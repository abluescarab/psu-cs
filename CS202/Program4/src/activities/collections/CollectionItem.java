package activities.collections;

public abstract class CollectionItem {
    private String name;
    private double price;

    public CollectionItem() {
        name = "";
        price = 0.0;
    }

    public CollectionItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public boolean matches(String name) {
        return this.name.equals(name);
    }

    public boolean matches(String name, double price) {
        return this.name.equals(name) && this.price == price;
    }

    public void display() {
        // TODO
    }

    public String toString() {
        return name;
    }
}
