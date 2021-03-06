package activities.collections;

import activities.*;
import activities.collections.*;
import activities.collections.items.*;

public class CollectionActivity extends Activity {
    private CollectionType collectionType;

    public CollectionActivity() {
        super();
        collectionType = CollectionType.FOUNTAIN_PEN;
    }

    @Override
    public CollectionActivity create() {
        int option = 0;

        super.create();

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

    public CollectionType getType() {
        return collectionType;
    }

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
