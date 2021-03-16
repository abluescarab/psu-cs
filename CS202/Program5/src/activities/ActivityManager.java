/* Alana Gilston - 3/5/21 - CS202 - Program 5
 * ActivityManager.java
 *
 * The ActivityManager class manages user-defined activities and their corresponding item and project collections.
 */
package activities;

import activities.collections.*;
import activities.collections.items.*;
import activities.crafts.*;
import activities.crafts.items.*;
import utils.*;

import java.util.*;

public class ActivityManager extends Utils {
    /**
     * Linear linked list of collections.
     */
    private CollectionActivity collections;
    /**
     * Linear linked list of crafts.
     */
    private CraftActivity crafts;

    /**
     * Create a new ActivityManager.
     */
    public ActivityManager() {
        collections = null;
        crafts = null;
    }

    /**
     * Change the knowledge level of a collection.
     * @param collection Collection to change
     * @param experienceLevel New knowledge level
     */
    public void changeCollectionKnowledgeLevel(String collection, ExperienceLevel experienceLevel) {
        CollectionActivity activity = getActivity(collections, collection);

        if(activity == null)
            return;

        activity.changeExperienceLevel(experienceLevel);
    }

    /**
     * Change the experience level of a craft.
     * @param craft Craft to change
     * @param experienceLevel New experience level
     */
    public void changeCraftExperienceLevel(String craft, ExperienceLevel experienceLevel) {
        CraftActivity activity = getActivity(crafts, craft);

        if(activity == null)
            return;

        activity.changeExperienceLevel(experienceLevel);
    }

    /**
     * Mark a project complete.
     * @param craft Craft with project
     * @param name Project name
     * @return Whether the project was marked complete
     */
    public boolean markCompleted(String craft, String name) {
        CraftActivity activity = getActivity(crafts, craft);

        if(activity == null)
            return false;

        return activity.markCompleted(name);
    }

    /**
     * Get the type of item stored in a collection.
     * @param name Name of the collection
     * @return The type of the collection
     */
    public CollectionType getCollectionType(String name) {
        CollectionActivity collection = getActivity(collections, name);

        if(collection == null)
            return CollectionType.NONE;

        return collection.getType();
    }

    /**
     * Get the type of project stored in a craft.
     * @param name Name of the craft
     * @return The type of projects
     */
    public CraftType getCraftType(String name) {
        CraftActivity craft = getActivity(crafts, name);

        if(craft == null)
            return CraftType.NONE;

        return craft.getType();
    }

    /**
     * Add a collection.
     * @param activity Collection to add
     * @return Whether the collection was added
     */
    public boolean addCollection(CollectionActivity activity) {
        if(activity == null || hasCollection(activity.toString()))
            return false;

        if(collections == null) {
            collections = activity;
            return true;
        }

        return addCollectionOrCraft(collections, activity);
    }

    /**
     * Add a craft.
     * @param activity Craft to add
     * @return Whether the craft was added
     */
    public boolean addCraft(CraftActivity activity) {
        if(activity == null || hasCraft(activity.toString()))
            return false;

        if(crafts == null) {
            crafts = activity;
            return true;
        }

        return addCollectionOrCraft(crafts, activity);
    }

    /**
     * Add a collection or craft.
     * @param current Current node in the LLL
     * @param activity Activity to add
     * @return Whether the activity was added
     */
    private boolean addCollectionOrCraft(Activity current, Activity activity) {
        if(current.getNext() == null) {
            current.setNext(activity);
            return true;
        }

        return addCollectionOrCraft(current.getNext(), activity);
    }

    /**
     * Add a FountainPen to a collection.
     * @param collection Collection to add to
     * @param fountainPen FountainPen to add
     * @return Whether the item was added
     */
    public boolean addItem(String collection, FountainPen fountainPen) {
        CollectionActivity activity = getActivity(collections, collection);

        if(activity == null || activity.getType() != CollectionType.FOUNTAIN_PEN)
            return false;

        return activity.add(fountainPen);
    }

    /**
     * Add a Knife to a collection.
     * @param collection Collection to add to
     * @param knife Knife to add
     * @return Whether the item was added
     */
    public boolean addItem(String collection, Knife knife) {
        CollectionActivity activity = getActivity(collections, collection);

        if(activity == null || activity.getType() != CollectionType.KNIFE)
            return false;

        return activity.add(knife);
    }

    /**
     * Add a TradingCard to a collection.
     * @param collection Collection to add to
     * @param tradingCard TradingCard to add
     * @return Whether the item was added
     */
    public boolean addItem(String collection, TradingCard tradingCard) {
        CollectionActivity activity = getActivity(collections, collection);

        if(activity == null || activity.getType() != CollectionType.TRADING_CARD)
            return false;

        return activity.add(tradingCard);
    }

    /**
     * Add a PaintingProject to a craft.
     * @param craft Craft to add to
     * @param paintingProject PaintingProject to add
     * @return Whether the project was added
     */
    public boolean addProject(String craft, PaintingProject paintingProject) {
        CraftActivity activity = getActivity(crafts, craft);

        if(activity == null || activity.getType() != CraftType.PAINTING)
            return false;

        return activity.add(paintingProject);
    }

    /**
     * Add a SculptureProject to a craft.
     * @param craft Craft to add to
     * @param sculptureProject SculptureProject to add
     * @return Whether the project was added
     */
    public boolean addProject(String craft, SculptureProject sculptureProject) {
        CraftActivity activity = getActivity(crafts, craft);

        if(activity == null || activity.getType() != CraftType.SCULPTURE)
            return false;

        return activity.add(sculptureProject);
    }

    /**
     * Add a WoodworkingProject to a craft.
     * @param craft Craft to add to
     * @param woodworkingProject WoodworkingProject to add
     * @return Whether the project was added
     */
    public boolean addProject(String craft, WoodworkingProject woodworkingProject) {
        CraftActivity activity = getActivity(crafts, craft);

        if(activity == null || activity.getType() != CraftType.WOODWORKING)
            return false;

        return activity.add(woodworkingProject);
    }

    /**
     * Remove a collection.
     * @param name Collection to remove
     * @return Whether the collection was removed
     */
    public boolean removeCollection(String name) {
        if(!hasCollection(name))
            return false;

        if(collections.matches(name)) {
            collections = (CollectionActivity)collections.getNext();
            return true;
        }

        return removeCollectionOrCraft(collections, name);
    }

    /**
     * Remove a craft.
     * @param name Craft to remove
     * @return Whether the craft was removed
     */
    public boolean removeCraft(String name) {
        if(!hasCraft(name))
            return false;

        if(crafts.matches(name)) {
            crafts = (CraftActivity)crafts.getNext();
            return true;
        }

        return removeCollectionOrCraft(crafts, name);
    }

    /**
     * Remove a collection or craft.
     * @param current Current collection or craft
     * @param name Name of the collection or craft to remove
     * @return Whether the collection or craft was removed
     */
    private boolean removeCollectionOrCraft(Activity current, String name) {
        if(current == null)
            return false;

        if(current.getNext() != null && current.getNext().matches(name)) {
            current.setNext(current.getNext().getNext());
            return true;
        }

        return removeCollectionOrCraft(current.getNext(), name);
    }

    /**
     * Remove item from a collection.
     * @param collection Collection to remove from
     * @param name Item to remove
     * @return Whether the item was removed
     */
    public boolean removeItem(String collection, String name) {
        CollectionActivity activity = getActivity(collections, collection);

        if(activity == null)
            return false;

        return activity.removeFirst(name);
    }

    /**
     * Remove all items with a specific name.
     * @param collection Collection to remove from
     * @param name Name of the items to remove
     * @return How many items were removed
     */
    public int removeAllItemsWithName(String collection, String name) {
        CollectionActivity activity = getActivity(collections, collection);

        if(activity == null)
            return 0;

        return activity.removeAll(name);
    }

    /**
     * Choose the item to remove.
     * @param collection Collection to remove from
     * @param name Name of the item to remove
     * @return Whether the item was removed
     */
    public boolean chooseItemToRemove(String collection, String name) {
        CollectionActivity activity = getActivity(collections, collection);
        return chooseItemToRemove(activity, name);
    }

    /**
     * Choose the project to remove.
     * @param craft Craft to remove from
     * @param name Name of the project to remove
     * @return Whether the project was removed
     */
    public boolean chooseProjectToRemove(String craft, String name) {
        CraftActivity activity = getActivity(crafts, craft);
        return chooseItemToRemove(activity, name);
    }

    /**
     * Remove a project.
     * @param craft Craft to remove from
     * @param name Name of the project
     * @return Whether the project was removed
     */
    public boolean removeProject(String craft, String name) {
        CraftActivity activity = getActivity(crafts, craft);

        if(activity == null)
            return false;

        return activity.removeFirst(name);
    }

    /**
     * Remove all projects with a name.
     * @param craft Craft to remove from
     * @param name Name of the proejcts
     * @return How many projects were removed
     */
    public int removeAllProjectsWithName(String craft, String name) {
        CraftActivity activity = getActivity(crafts, craft);

        if(activity == null)
            return 0;

        return activity.removeAll(name);
    }

    /**
     * Remove all items from a collection.
     * @param collection Collection to remove from
     * @return How many items were removed
     */
    public int removeAllItems(String collection) {
        CollectionActivity activity = getActivity(collections, collection);

        if(activity == null)
            return 0;

        return activity.removeAll();
    }

    /**
     * Remove all projects from a craft.
     * @param craft Craft to remove from
     * @return How many projects were removed
     */
    public int removeAllProjects(String craft) {
        CraftActivity activity = getActivity(crafts, craft);

        if(activity == null)
            return 0;

        return activity.removeAll();
    }

    /**
     * Remove all items from all collections.
     * @return How many items were removed
     */
    public int removeAllItems() {
        return removeAllItemsOrProjects(collections);
    }

    /**
     * Remove all projects from all crafts.
     * @return How many projects were removed
     */
    public int removeAllProjects() {
        return removeAllItemsOrProjects(crafts);
    }

    /**
     * Remove all items or projects.
     * @param activity Activity to remove from
     * @return How many items or projects were removed
     */
    private int removeAllItemsOrProjects(Activity activity) {
        if(activity == null)
            return 0;

        return activity.removeAll() + removeAllItemsOrProjects(activity.getNext());
    }

    /**
     * Remove all collections.
     */
    public void removeAllCollections() {
        collections = null;
    }

    /**
     * Remove all crafts.
     */
    public void removeAllCrafts() {
        crafts = null;
    }

    /**
     * Check if a collection exists.
     * @param name Collection name
     * @return Whether the collection exists
     */
    public boolean hasCollection(String name) {
        return getActivity(collections, name) != null;
    }

    /**
     * Check if a craft exists.
     * @param name Craft name
     * @return Whether the craft exists
     */
    public boolean hasCraft(String name) {
        return getActivity(crafts, name) != null;
    }

    /**
     * Check if a collection has an item.
     * @param collection Collection to check in
     * @param name Name of the item
     * @return Whether the item exists
     */
    public boolean hasItem(String collection, String name) {
        CollectionActivity activity = getActivity(collections, collection);

        if(activity == null)
            return false;

        return activity.contains(name);
    }

    /**
     * Count the number of items with a specific name.
     * @param collection Collection to look in
     * @param name Name to find
     * @return Number of items with that name
     */
    public int countOfItemsWithName(String collection, String name) {
        CollectionActivity activity = getActivity(collections, collection);

        if(activity == null)
            return 0;

        return activity.containsCount(name);
    }

    /**
     * Check if a craft has a project.
     * @param craft Craft to check
     * @param name Name of the project to check for
     * @return Whether the craft has a project
     */
    public boolean hasProject(String craft, String name) {
        CraftActivity activity = getActivity(crafts, craft);

        if(activity == null)
            return false;

        return activity.contains(name);
    }

    /**
     * Count the number of projects with a specific name.
     * @param craft Craft to look in
     * @param name Name to find
     * @return Number of projects with that name
     */
    public int countOfProjectsWithName(String craft, String name) {
        CraftActivity activity = getActivity(crafts, craft);

        if(activity == null)
            return 0;

        return activity.containsCount(name);
    }

    /**
     * Display a collection.
     * @param name Name of the collection
     * @param displayItems Whether to display items
     * @return Whether the collection was displayed
     */
    public boolean displayCollection(String name, boolean displayItems) {
        CollectionActivity activity = getActivity(collections, name);

        if(activity == null)
            return false;

        activity.display(displayItems);
        return true;
    }

    /**
     * Display a craft.
     * @param name Name of the craft
     * @param displayProjects Whether to display projects
     * @return Whether the craft was displayed
     */
    public boolean displayCraft(String name, boolean displayProjects) {
        CraftActivity activity = getActivity(crafts, name);

        if(activity == null)
            return false;

        activity.display(displayProjects);
        return true;
    }

    /**
     * Display all collections.
     * @return Number of collections displayed
     */
    public int displayAllCollections() {
        return displayAllCollectionsOrCrafts(collections);
    }

    /**
     * Display all crafts.
     * @return Number of crafts displayed
     */
    public int displayAllCrafts() {
        return displayAllCollectionsOrCrafts(crafts);
    }

    /**
     * Display all collections or crafts.
     * @param activity Activity to begin at
     * @return Number of crafts or collections displayed
     */
    private int displayAllCollectionsOrCrafts(Activity activity) {
        if(activity == null)
            return 0;

        activity.display(false);
        return displayAllCollectionsOrCrafts(activity.getNext()) + 1;
    }

    /**
     * Choose an item to remove.
     * @param activity Activity to remove from
     * @param name Name of the item to remove
     * @return Whether the item was removed
     */
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

    /**
     * Get a collection by name.
     * @param name Collection to find
     * @return The found collection
     */
    public CollectionActivity getCollection(String name) {
        return getActivity(collections, name);
    }

    /**
     * Get an activity by name.
     * @param activity Activity to begin at
     * @param name Name to find
     * @return The found activity
     */
    private CollectionActivity getActivity(CollectionActivity activity, String name) {
        if(activity == null)
            return null;

        if(activity.matches(name))
            return activity;

        return getActivity((CollectionActivity)activity.getNext(), name);
    }

    /**
     * Get a craft by name.
     * @param name Craft to find
     * @return The found craft
     */
    public CraftActivity getCraft(String name) {
        return getActivity(crafts, name);
    }

    /**
     * Get an activity by name.
     * @param activity Activity to begin at
     * @param name Name to find
     * @return The found activity
     */
    private CraftActivity getActivity(CraftActivity activity, String name) {
        if(activity == null)
            return null;

        if(activity.matches(name))
            return activity;

        return getActivity((CraftActivity)activity.getNext(), name);
    }
}
