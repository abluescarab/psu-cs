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

    public void display(String post) {

    }

    public int findPosts(String person) {

    }

    public int findPosts(CollectionType collectionType) {

    }

    public int findPosts(CraftType craftType) {

    }

    public void removeAll() {
        posts = null;
    }
}
