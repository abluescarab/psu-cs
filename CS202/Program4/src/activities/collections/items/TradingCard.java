package activities.collections.items;

public class TradingCard extends CollectionItem {
    private String rarity;
    private String series;
    private String condition;

    public TradingCard() {
        rarity = "";
        series = "";
        condition = "";
    }

    public TradingCard(String name, double price, String rarity, String series, String condition) {
        super(name, price);
        this.rarity = rarity;
        this.series = series;
        this.condition = condition;
    }

    public void display() {
        // TODO
    }
}
