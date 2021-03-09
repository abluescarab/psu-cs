/* Alana Gilston - 3/5/21 - CS202 - Program 4
 * CollectionItem.java
 *
 * The CollectionItem class manages an item in a collection.
 */
package activities.collections.items;

import activities.*;

public abstract class CollectionItem extends ActivityItem {
    /**
     * Price paid for the item.
     */
    private double price;

    /**
     * Create a new CollectionItem.
     */
    public CollectionItem() {
        price = 0.0;
    }

    /**
     * Create a new CollectionItem.
     * @param name Name of the item
     * @param price Price paid for the item
     */
    public CollectionItem(String name, double price) {
        super(name);
        this.price = price;
    }

    /**
     * Create the item from user input.
     * @return The item created
     */
    @Override
    public ActivityItem create() {
        if(super.create() == null)
            return null;

        System.out.print("Price paid: ");
        price = getDoubleInput();
        return this;
    }

    /**
     * Display the item.
     */
    public void display() {
        super.display();
        System.out.println("Price: " + price);
    }
}
