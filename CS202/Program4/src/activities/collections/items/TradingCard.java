/* Alana Gilston - 3/5/21 - CS202 - Program 4
 * TradingCard.java
 *
 * The TradingCard class manages a trading card in a collection.
 */
package activities.collections.items;

public class TradingCard extends CollectionItem {
    /**
     * Rarity of the TradingCard.
     */
    private String rarity;
    /**
     * Printed series of the TradingCard.
     */
    private String series;
    /**
     * Condition the TradingCard is in.
     */
    private String condition;

    /**
     * Create a new TradingCard.
     */
    public TradingCard() {
        rarity = "";
        series = "";
        condition = "";
    }

    /**
     * Create a new TradingCard.
     * @param name Name of the card
     * @param price Price paid for the card
     * @param rarity Rarity of the card
     * @param series Printed series of the card
     * @param condition Condition the card is in
     */
    public TradingCard(String name, double price, String rarity, String series, String condition) {
        super(name, price);
        this.rarity = rarity;
        this.series = series;
        this.condition = condition;
    }

    /**
     * Create a new TradingCard from user input.
     * @return The TradingCard created
     */
    @Override
    public TradingCard create() {
        if(super.create() == null)
            return null;

        System.out.print("Rarity: ");
        rarity = getStringInput();

        System.out.print("Series: ");
        series = getStringInput();

        System.out.print("Condition: ");
        condition = getStringInput();

        return this;
    }

    /**
     * Display the TradingCard.
     */
    public void display() {
        super.display();
        System.out.println("Series: " + series);
        System.out.println("Rarity: " + rarity);
        System.out.println("Condition: " + condition);
    }
}
