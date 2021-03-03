package activities;

import activities.collections.*;
import activities.crafts.*;

public class ActivityManager {
    public enum CollectionType {
        FOUNTAIN_PEN,
        KNIFE,
        TRADING_CARD
    }

    public enum CraftType {
        PAINTING,
        SCULPTURE,
        WOODWORKING
    }

    private final Collection fountainPenCollection;
    private final Collection knifeCollection;
    private final Collection tradingCardCollection;
    private final Craft paintingSkill;
    private final Craft sculptureSkill;
    private final Craft woodworkingSkill;

    public ActivityManager() {
        fountainPenCollection = new Collection();
        knifeCollection = new Collection();
        tradingCardCollection = new Collection();
        paintingSkill = new Craft();
        sculptureSkill = new Craft();
        woodworkingSkill = new Craft();
    }

    public boolean add(FountainPen fountainPen) {
        return fountainPenCollection.add(fountainPen);
    }

    public boolean add(Knife knife) {
        return knifeCollection.add(knife);
    }

    public boolean add(TradingCard tradingCard) {
        return tradingCardCollection.add(tradingCard);
    }

    public boolean add(Painting painting) {
        return paintingSkill.add(painting);
    }

    public boolean add(Sculpture sculpture) {
        return sculptureSkill.add(sculpture);
    }

    public boolean add(WoodworkingProject woodworkingProject) {
        return woodworkingSkill.add(woodworkingProject);
    }

    public boolean remove(CollectionType collection, String name) {
        boolean result = false;

        if(collection == CollectionType.FOUNTAIN_PEN)
            result = fountainPenCollection.remove(name);
        else if(collection == CollectionType.KNIFE)
            result = knifeCollection.remove(name);
        else if(collection == CollectionType.TRADING_CARD)
            result = tradingCardCollection.remove(name);

        return result;
    }

    public boolean remove(CraftType craft, String name) {
        boolean result = false;

        if(craft == CraftType.PAINTING)
            result = paintingSkill.remove(name);
        else if(craft == CraftType.SCULPTURE)
            result = sculptureSkill.remove(name);
        else if(craft == CraftType.WOODWORKING)
            result = woodworkingSkill.remove(name);

        return result;
    }

    public int removeAll(CollectionType collection) {
        int result = 0;

        if(collection == CollectionType.FOUNTAIN_PEN)
            result = fountainPenCollection.removeAll();
        else if(collection == CollectionType.KNIFE)
            result = knifeCollection.removeAll();
        else if(collection == CollectionType.TRADING_CARD)
            result = tradingCardCollection.removeAll();

        return result;
    }

    public int removeAll(CraftType craft) {
        int result = 0;

        if(craft == CraftType.PAINTING)
            result = paintingSkill.removeAll();
        else if(craft == CraftType.SCULPTURE)
            result = sculptureSkill.removeAll();
        else if(craft == CraftType.WOODWORKING)
            result = woodworkingSkill.removeAll();

        return result;
    }

    public void clearAllCollections() {
        fountainPenCollection.removeAll();
        knifeCollection.removeAll();
        tradingCardCollection.removeAll();
    }

    public void clearAllProjects() {
        paintingSkill.removeAll();
        sculptureSkill.removeAll();
        woodworkingSkill.removeAll();
    }

    public void display(CollectionType collection) {
        if(collection == CollectionType.FOUNTAIN_PEN)
            fountainPenCollection.display();
        else if(collection == CollectionType.KNIFE)
            knifeCollection.display();
        else if(collection == CollectionType.TRADING_CARD)
            tradingCardCollection.display();
    }

    public void display(CraftType craft) {
        if(craft == CraftType.PAINTING)
            paintingSkill.display();
        else if(craft == CraftType.SCULPTURE)
            sculptureSkill.display();
        else if(craft == CraftType.WOODWORKING)
            woodworkingSkill.display();
    }
}
