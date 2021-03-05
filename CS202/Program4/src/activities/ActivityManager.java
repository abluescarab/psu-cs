package activities;

import activities.collections.*;
import activities.crafts.*;

import java.util.*;

public class ActivityManager {
    public enum ActivityType {
        COLLECTION,
        CRAFT
    }

    private final ArrayList<CollectionActivity> collections;
    private final ArrayList<CraftActivity> crafts;

    public ActivityManager() {
        collections = new ArrayList<>();
        crafts = new ArrayList<>();
    }
}
