package activities.collections.items;

import activities.*;

public abstract class CollectionItem extends ActivityItem {
    private final double price;

    public CollectionItem() {
        price = 0.0;
    }

    public CollectionItem(String name, double price) {
        super(name);
        this.price = price;
    }
}
