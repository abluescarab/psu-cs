package activities;

import activities.collections.*;
import activities.collections.items.*;
import activities.crafts.*;
import activities.crafts.items.*;
import utils.*;

import java.util.*;

public class ActivityManager extends Utils {
    private final ArrayList<CollectionActivity> collections;
    private final ArrayList<CraftActivity> crafts;

    public ActivityManager() {
        collections = new ArrayList<>();
        crafts = new ArrayList<>();
    }

    public void changeCollectionKnowledgeLevel(String collection, ExperienceLevel experienceLevel) {
        CollectionActivity activity = getActivity(collections, collection);

        if(activity == null)
            return;

        activity.changeExperienceLevel(experienceLevel);
    }

    public void changeCraftExperienceLevel(String craft, ExperienceLevel experienceLevel) {
        CraftActivity activity = getActivity(crafts, craft);

        if(activity == null)
            return;

        activity.changeExperienceLevel(experienceLevel);
    }

    public boolean markCompleted(String craft, String name) {
        CraftActivity activity = getActivity(crafts, craft);

        if(activity == null)
            return false;

        return activity.markCompleted(name);
    }

    public int collectionCount() {
        return collections.size();
    }

    public int craftCount() {
        return crafts.size();
    }

    public CollectionType getCollectionType(String name) {
        CollectionActivity collection = getActivity(collections, name);

        if(collection == null)
            return CollectionType.NONE;

        return collection.getType();
    }

    public CraftType getCraftType(String name) {
        CraftActivity craft = getActivity(crafts, name);

        if(craft == null)
            return CraftType.NONE;

        return craft.getType();
    }

    public boolean addCollection(CollectionActivity activity) {
        if(activity == null || hasCollection(activity.toString()))
            return false;

        return collections.add(activity);
    }

    public boolean addCraft(CraftActivity activity) {
        if(activity == null || hasCraft(activity.toString()))
            return false;

        return crafts.add(activity);
    }

    // TODO: update return value to int? -1 = wrong type, 0 = could not add?
    public boolean addItem(String collection, FountainPen fountainPen) {
        CollectionActivity activity = getActivity(collections, collection);

        if(activity == null || activity.getType() != CollectionType.FOUNTAIN_PEN)
            return false;

        return activity.add(fountainPen);
    }

    public boolean addItem(String collection, Knife knife) {
        CollectionActivity activity = getActivity(collections, collection);

        if(activity == null || activity.getType() != CollectionType.KNIFE)
            return false;

        return activity.add(knife);
    }

    public boolean addItem(String collection, TradingCard tradingCard) {
        CollectionActivity activity = getActivity(collections, collection);

        if(activity == null || activity.getType() != CollectionType.TRADING_CARD)
            return false;

        return activity.add(tradingCard);
    }

    public boolean addProject(String craft, PaintingProject paintingProject) {
        CraftActivity activity = getActivity(crafts, craft);

        if(activity == null || activity.getType() != CraftType.PAINTING)
            return false;

        return activity.add(paintingProject);
    }

    public boolean addProject(String craft, SculptureProject sculptureProject) {
        CraftActivity activity = getActivity(crafts, craft);

        if(activity == null || activity.getType() != CraftType.SCULPTURE)
            return false;

        return activity.add(sculptureProject);
    }

    public boolean addProject(String craft, WoodworkingProject woodworkingProject) {
        CraftActivity activity = getActivity(crafts, craft);

        if(activity == null || activity.getType() != CraftType.WOODWORKING)
            return false;

        return activity.add(woodworkingProject);
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

        if(activity == null)
            return false;

        return activity.removeFirst(name);
    }

    public int removeAllItemsWithName(String collection, String name) {
        CollectionActivity activity = getActivity(collections, collection);

        if(activity == null)
            return 0;

        return activity.removeAll(name);
    }

    public boolean chooseItemToRemove(String collection, String name) {
        CollectionActivity activity = getActivity(collections, collection);
        return chooseItemToRemove(activity, name);
    }

    public boolean chooseProjectToRemove(String craft, String name) {
        CraftActivity activity = getActivity(crafts, craft);
        return chooseItemToRemove(activity, name);
    }

    public boolean removeProject(String craft, String name) {
        CraftActivity activity = getActivity(crafts, craft);

        if(activity == null)
            return false;

        return activity.removeFirst(name);
    }

    public int removeAllProjectsWithName(String craft, String name) {
        CraftActivity activity = getActivity(crafts, craft);

        if(activity == null)
            return 0;

        return activity.removeAll(name);
    }

    public int removeAllItems(String collection) {
        CollectionActivity activity = getActivity(collections, collection);

        if(activity == null)
            return 0;

        return activity.removeAll();
    }

    public int removeAllProjects(String craft) {
        CraftActivity activity = getActivity(crafts, craft);

        if(activity == null)
            return 0;

        return activity.removeAll();
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

    public boolean hasItem(String collection, String name) {
        CollectionActivity activity = getActivity(collections, collection);

        if(activity == null)
            return false;

        return activity.contains(name);
    }

    public int countOfItemsWithName(String collection, String name) {
        CollectionActivity activity = getActivity(collections, collection);

        if(activity == null)
            return 0;

        return activity.containsCount(name);
    }

    public boolean hasCraft(String name) {
        for(CraftActivity activity : crafts) {
            if(activity.matches(name))
                return true;
        }

        return false;
    }

    public boolean hasProject(String craft, String name) {
        CraftActivity activity = getActivity(crafts, craft);

        if(activity == null)
            return false;

        return activity.contains(name);
    }

    public int countOfProjectsWithName(String craft, String name) {
        CraftActivity activity = getActivity(crafts, craft);

        if(activity == null)
            return 0;

        return activity.containsCount(name);
    }

    public boolean displayCollection(String name, boolean displayItems) {
        CollectionActivity activity = getActivity(collections, name);

        if(activity == null)
            return false;

        activity.display(displayItems);
        return true;
    }

    public int displayAllCollections() {
        for(CollectionActivity activity : collections) {
            activity.display(false);
        }

        return collections.size();
    }

    public boolean displayCraft(String name, boolean displayProjects) {
        CraftActivity activity = getActivity(crafts, name);

        if(activity == null)
            return false;

        activity.display(displayProjects);
        return true;
    }

    public int displayItems(String collection) {
        CollectionActivity activity = getActivity(collections, collection);

        if(activity == null)
            return 0;

        return activity.displayAll();
    }

    public int displayProjects(String craft) {
        CraftActivity activity = getActivity(crafts, craft);

        if(activity == null)
            return 0;

        return activity.displayAll();
    }

    private boolean chooseItemToRemove(Activity activity, String name) {
        int option = 0;
        ArrayList<ActivityItem> items = null;

        if(activity == null)
            return false;

        System.out.println("There are multiple items with that name. Enter the number of the item you want to delete, or 0 to cancel.");
        System.out.println();
        items = activity.displayItemsWithName(name);
        option = getNumericInput(0, items.size());

        if(option == 0)
            return false;

        return activity.remove(items.get(option - 1));
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
