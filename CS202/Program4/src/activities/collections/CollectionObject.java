package activities.collections;

public abstract class CollectionObject {
    private String name;
    private double price;

    public CollectionObject() {
        name = "";
        price = 0.0;
    }

    public CollectionObject(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public boolean matches(String name) {
        return this.name.equals(name);
    }

    public boolean matches(String name, double price) {
        return this.name.equals(name) && this.price == price;
    }

    /*public String toString() {
        // TODO
    }*/
}
