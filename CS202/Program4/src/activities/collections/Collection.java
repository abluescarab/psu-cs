package activities.collections;

import activities.Activity;
import activities.collections.items.*;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Collection extends Activity {
    private final ArrayList<CollectionItem> collected;

    public abstract void create();

    public Collection() {
        collected = new ArrayList<>();
    }

    public Collection(ExperienceLevel experienceLevel) {
        super(experienceLevel);
        collected = new ArrayList<>();
    }

    public boolean contains(String collectedObject) {
        boolean contains = false;

        for(CollectionItem obj : collected) {
            if(obj.matches(collectedObject)) {
                return true;
            }
        }

        return false;
    }

    public boolean add(CollectionItem collectedObject) {
        return collected.add(collectedObject);
    }

    public boolean remove(String collectedObject) {
        int index = 0;
        Iterator<CollectionItem> iterator = collected.iterator();

        while(iterator.hasNext()) {
            if(iterator.next().matches(collectedObject)) {
                iterator.remove();
                return true;
            }
        }

        return false;
    }

    public int removeAll() {
        int count = collected.size();
        collected.clear();
        return count;
    }

    public int display() {
        for(CollectionItem item : collected) {
            item.display();
        }

        return collected.size();
    }
}
