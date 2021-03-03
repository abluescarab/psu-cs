package activities.collections;

import activities.Activity;

import java.util.ArrayList;
import java.util.Iterator;

public class Collection extends Activity {
    private final ArrayList<CollectionObject> collected;

    public Collection() {
        collected = new ArrayList<>();
    }

    public Collection(String name, ExperienceLevel experienceLevel) {
        super(experienceLevel);
        collected = new ArrayList<>();
    }

    public boolean contains(String collectedObject) {
        boolean contains = false;

        for(CollectionObject obj : collected) {
            if(obj.matches(collectedObject)) {
                return true;
            }
        }

        return false;
    }

    public boolean add(CollectionObject collectedObject) {
        return collected.add(collectedObject);
    }

    public boolean remove(String collectedObject) {
        int index = 0;
        Iterator<CollectionObject> iterator = collected.iterator();

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

    /*public int display() {
        // TODO
    }*/
}
