package activities;

import activities.collections.*;
import activities.collections.items.*;
import activities.crafts.*;
import activities.crafts.items.*;

import java.util.*;

public class ActivityManager {
    private final ArrayList<CollectionActivity> collections;
    private final ArrayList<CraftActivity> crafts;

    public ActivityManager() {
        collections = new ArrayList<>();
        crafts = new ArrayList<>();
    }

    public int collectionCount() {
        return collections.size();
    }

    public int craftCount() {
        return crafts.size();
    }

    public CollectionType collectionType(String name) {
        CollectionActivity collection = getActivity(collections, name);

        if(collection == null)
            return CollectionType.NONE;

        return collection.getType();
    }

    public CraftType craftType(String name) {
        CraftActivity craft = getActivity(crafts, name);

        if(craft == null)
            return CraftType.NONE;

        return craft.getType();
    }

    public boolean addCollection(CollectionActivity activity) {
        if(hasCollection(activity.toString()))
            return false;

        return collections.add(activity);
    }

    public boolean addCraft(CraftActivity activity) {
        if(hasCraft(activity.toString()))
            return false;

        return crafts.add(activity);
    }

    public boolean addItem(String collection, FountainPen fountainPen) {
        CollectionActivity activity = getActivity(collections, collection);

        if(activity != null)
            return activity.add(fountainPen);

        return false;
    }

    public boolean addItem(String collection, Knife knife) {
        CollectionActivity activity = getActivity(collections, collection);

        if(activity != null)
            return activity.add(knife);

        return false;
    }

    public boolean addItem(String collection, TradingCard tradingCard) {
        CollectionActivity activity = getActivity(collections, collection);

        if(activity != null)
            return activity.add(tradingCard);

        return false;
    }

    public boolean addProject(String craft, PaintingProject paintingProject) {
        CraftActivity activity = getActivity(crafts, craft);

        if(activity != null)
            return activity.add(paintingProject);

        return false;
    }

    public boolean addProject(String craft, SculptureProject sculptureProject) {
        CraftActivity activity = getActivity(crafts, craft);

        if(activity != null)
            return activity.add(sculptureProject);

        return false;
    }

    public boolean addProject(String craft, WoodworkingProject woodworkingProject) {
        CraftActivity activity = getActivity(crafts, craft);

        if(activity != null)
            return activity.add(woodworkingProject);

        return false;
    }

    public boolean removeCollection(String name) {
        if(!hasCollection(name))
            return false;

        Iterator<CollectionActivity> iterator = collections.iterator();

        while(iterator.hasNext()) {
            if(iterator.next().matches(name)) {
                iterator.remove();
                return true;
            }
        }

        return false;
    }

    public boolean removeCraft(String name) {
        if(!hasCraft(name))
            return false;

        Iterator<CraftActivity> iterator = crafts.iterator();

        while(iterator.hasNext()) {
            if(iterator.next().matches(name)) {
                iterator.remove();
                return true;
            }
        }

        return false;
    }

    public boolean removeItem(String collection, String name) {
        CollectionActivity activity = getActivity(collections, collection);

        if(activity != null)
            return activity.remove(name);

        return false;
    }

    public boolean removeProject(String craft, String name) {
        CraftActivity activity = getActivity(crafts, craft);

        if(activity != null)
            return activity.remove(name);

        return false;
    }

    public int removeItems(String collection) {
        CollectionActivity activity = getActivity(collections, collection);

        if(activity != null)
            return activity.removeAll();

        return 0;
    }

    public int removeProjects(String craft) {
        CraftActivity activity = getActivity(crafts, craft);

        if(activity != null)
            return activity.removeAll();

        return 0;
    }

    public int removeAllItems() {
        int count = 0;

        for(CollectionActivity activity : collections)
            count += activity.removeAll();

        return count;
    }

    public int removeAllProjects() {
        int count = 0;

        for(CraftActivity activity : crafts)
            count += activity.removeAll();

        return count;
    }

    public int removeAllCollections() {
        int count = collections.size();
        collections.clear();
        return count;
    }

    public int removeAllCrafts() {
        int count = crafts.size();
        crafts.clear();
        return count;
    }

    public boolean hasCollection(String name) {
        for(CollectionActivity activity : collections) {
            if(activity.matches(name))
                return true;
        }

        return false;
    }

    public boolean hasCraft(String name) {
        for(CraftActivity activity : crafts) {
            if(activity.matches(name))
                return true;
        }

        return false;
    }

    public boolean displayCollection(String name) {
        CollectionActivity activity = getActivity(collections, name);

        if(activity != null) {
            activity.display();
            return true;
        }

        return false;
    }

    public boolean displayCraft(String name) {
        CraftActivity activity = getActivity(crafts, name);

        if(activity != null) {
            activity.display();
            return true;
        }

        return false;
    }

    public int displayItems(String collection) {
        CollectionActivity activity = getActivity(collections, collection);

        if(activity != null)
            return activity.displayAll();

        return 0;
    }

    public int displayProjects(String craft) {
        CraftActivity activity = getActivity(crafts, craft);

        if(activity != null)
            return activity.displayAll();

        return 0;
    }

    private <T extends Activity> T getActivity(ArrayList<T> list, String name) {
        for(T activity : list) {
            if(activity.matches(name)) {
                return activity;
            }
        }

        return null;
    }
}
