/* Alana Gilston - 3/5/21 - CS202 - Program 4
 * CollectionActivity.java
 *
 * The CollectionActivity class manages a collection and its corresponding items.
 */
package activities.collections;

import activities.*;
import activities.collections.items.*;

public class CollectionActivity extends Activity {
    /**
     * The type of item stored in the collection.
     */
    private CollectionType collectionType;

    /**
     * Create a new CollectionActivity.
     */
    public CollectionActivity() {
        super();
        collectionType = CollectionType.FOUNTAIN_PEN;
    }

    /**
     * Create a new CollectionActivity from user input.
     * @return The CollectionActivity created
     */
    @Override
    public CollectionActivity create() {
        int option = 0;

        if(super.create() == null)
            return null;

        System.out.println("What type of collection is this?");
        System.out.println("1) Fountain pen");
        System.out.println("2) Knife");
        System.out.println("3) Trading card");

        option = getNumericInput(3);

        if(option == 1) {
            collectionType = CollectionType.FOUNTAIN_PEN;
            addFountainPen();
        }
        else if(option == 2) {
            collectionType = CollectionType.KNIFE;
            addKnife();
        }
        else {
            collectionType = CollectionType.TRADING_CARD;
            addTradingCard();
        }

        return this;
    }

    /**
     * Get the type of item stored in the collection.
     * @return The type of item stored in the collection
     */
    public CollectionType getType() {
        return collectionType;
    }

    /**
     * Add a new FountainPen to the collection from user input.
     */
    private void addFountainPen() {
        boolean quit = false;

        do {
            System.out.print("Do you want to add a fountain pen? (y/n) ");

            if(getConfirmation())
                add(new FountainPen().create());
            else
                quit = true;
        } while(!quit);
    }

    /**
     * Add a new Knife to the collection from user input.
     */
    private void addKnife() {
        boolean quit = false;

        do {
            System.out.print("Do you want to add a knife? (y/n) ");

            if(getConfirmation())
                add(new Knife().create());
            else
                quit = true;
        } while(!quit);
    }

    /**
     * Add a new TradingCard to the collection from user input.
     */
    private void addTradingCard() {
        boolean quit = false;

        do {
            System.out.print("Do you want to add a trading card? (y/n) ");

            if(getConfirmation())
                add(new TradingCard().create());
            else
                quit = true;
        } while(!quit);
    }
}
