package posts;

import activities.collections.*;
import activities.crafts.*;

public class PostManager {
    private ActivityPost posts;

    public PostManager() {
        posts = null;
    }

    public void add(ActivityPost post) {

    }

    public void removeAll() {
        posts = null;
    }

    public int display() {
        return display(posts);
    }

    private int display(ActivityPost current) {
        if(current == null)
            return 0;

        int count = display(current.getLeft());
        current.display();
        return count + display(current.getRight()) + 1;
    }

    public int displayByAuthor(String person) {
        return displayByAuthor(posts, person);
    }

    private int displayByAuthor(ActivityPost current, String person) {
        if(current == null)
            return 0;

        int count = displayByAuthor(current.getLeft(), person);

        if(current.authorMatches(person)) {
            current.display();
            ++count;
        }

        return count + displayByAuthor(current.getRight(), person);
    }

    public int displayByType(CollectionType collectionType) {
        return displayByType(posts, collectionType);
    }

    private int displayByType(ActivityPost current, CollectionType collectionType) {
        if(current == null)
            return 0;

        int count = displayByType(current.getLeft(), collectionType);

        if(current.isType(collectionType)) {
            current.display();
            ++count;
        }

        return count + displayByType(current.getRight(), collectionType);
    }

    public int displayByType(CraftType craftType) {
        return displayByType(posts, craftType);
    }

    private int displayByType(ActivityPost current, CraftType craftType) {
        if(current == null)
            return 0;

        int count = displayByType(current.getLeft(), craftType);

        if(current.isType(craftType)) {
            current.display();
            ++count;
        }

        return count + displayByType(current.getRight(), craftType);
    }

    public int displayByActivityName(String activity) {
        return displayByActivityName(posts, activity);
    }

    private int displayByActivityName(ActivityPost current, String activity) {
        if(current == null)
            return 0;

        int count = displayByActivityName(current.getLeft(), activity);

        if(current.activityMatches(activity)) {
            current.display();
            ++count;
        }

        return count + displayByActivityName(current.getRight(), activity);
    }

    private int getHeight(ActivityPost current) {
        if(current == null)
            return 0;

        return getHeight(current.getLeft()) - getHeight(current.getRight());
    }
}
