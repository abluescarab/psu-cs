package posts;

import activities.*;
import activities.collections.*;
import activities.crafts.*;
import utils.*;

public class ActivityPost extends Utils {
    private String author;
    private String message;
    private Activity activity;
    private ActivityPost left;
    private ActivityPost right;

    public ActivityPost() {
        author = "";
        message = "";
        activity = null;
        left = null;
        right = null;
    }

    public ActivityPost(String author, String message) {
        this.author = author;
        this.message = message;
        activity = null;
        left = null;
        right = null;
    }

    public ActivityPost(String author, String message, Activity activity) {
        this.author = author;
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

    public boolean authorMatches(String person) {
        return this.author.equals(person);
    }

    public boolean activityMatches(String activity) {
        if(this.activity == null)
            return false;

        return this.activity.matches(activity);
    }

    public void display() {
        System.out.println(author + ": " + message);
    }

    public ActivityPost create(ActivityManager activities) {
        int option = 0;
        String activityName = "";

        System.out.print("Author: ");
        author = getStringInput(false, "Invalid name.");

        if(author.isBlank())
            return null;

        System.out.print("Message: ");
        message = getStringInput();

        System.out.println("Activity type: ");
        System.out.println("1) Collection");
        System.out.println("2) Craft");
        option = getNumericInput(2);

        System.out.println("Activity name: ");
        activityName = getStringInput();

        if(option == 1) {
            activity = activities.getCollection(activityName);

            if(activity == null) {
                System.out.print("Collection does not exist. Do you want to to create it? (y/n) ");

                if(getConfirmation()) {
                    activity = new CollectionActivity().create();

                    if(activity != null)
                        // TODO: does this work byref?
                        activities.addCollection((CollectionActivity)activity);
                }
            }
        }
        else {
            activity = activities.getCraft(activityName);

            if(activity == null) {
                System.out.println("Craft does not exist. Do you want to create it? (y/n) ");

                if(getConfirmation()) {
                    activity = new CraftActivity().create();

                    if(activity != null)
                        // TODO: does this work byref?
                        activities.addCraft((CraftActivity)activity);
                }
            }
        }

        if(activity == null)
            return null;

        return this;
    }
}
