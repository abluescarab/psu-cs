package posts;

import activities.*;
import activities.collections.*;
import activities.crafts.*;

public abstract class ActivityPost {
    private final String person;
    private final String message;
    private final Activity activity;
    private ActivityPost left;
    private ActivityPost right;

    public ActivityPost() {
        person = "";
        message = "";
        activity = null;
        left = null;
        right = null;
    }

    public ActivityPost(String person, String message) {
        this.person = person;
        this.message = message;
        activity = null;
        left = null;
        right = null;
    }

    public ActivityPost(String person, String message, Activity activity) {
        this.person = person;
        this.message = message;
        this.activity = activity;
        left = null;
        right = null;
    }

    public ActivityPost getLeft() {
        return left;
    }

    public void setLeft(ActivityPost left) {
        this.left = left;
    }

    public ActivityPost getRight() {
        return right;
    }

    public void setRight(ActivityPost right) {
        this.right = right;
    }

    public boolean isType(CollectionType collectionType) {
        if(activity == null)
            return collectionType == CollectionType.NONE;

        return ((CollectionActivity)activity).getType() == collectionType;
    }

    public boolean isType(CraftType craftType) {
        if(activity == null)
            return craftType == CraftType.NONE;

        return ((CraftActivity)activity).getType() == craftType;
    }

    public void display() {
        System.out.println(person + ": " + message);
    }

    public ActivityPost create() {
        // TODO
        return null;
    }
}
