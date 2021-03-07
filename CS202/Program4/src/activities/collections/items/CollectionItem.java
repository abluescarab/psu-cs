package activities.collections.items;

import activities.*;

public abstract class CollectionItem extends ActivityItem {
    private double price;

    public CollectionItem() {
        price = 0.0;
    }

    public CollectionItem(String name, double price) {
        super(name);
        this.price = price;
    }

    @Override
    public ActivityItem create() {
        if(super.create() == null)
            return null;

        System.out.print("Price paid: ");
        price = getDoubleInput();
        return this;
    }

    public void display() {
        super.display();
        System.out.println("Price: " + price);
    }
}
