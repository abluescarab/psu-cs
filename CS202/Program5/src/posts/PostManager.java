/* Alana Gilston - 3/15/21 - CS202 - Program 5
 * PostManager.java
 *
 * The PostManager class manages posts made by the user about an activity.
 */
package posts;

import activities.collections.*;
import activities.crafts.*;

public class PostManager {
    /**
     * The posts written about each activity.
     */
    private ActivityPost posts;

    /**
     * Create a new PostManager.
     */
    public PostManager() {
        posts = null;
    }

    /**
     * Add a new post.
     * @param post Post to add
     */
    public void add(ActivityPost post) {
        posts = add(posts, post);
    }

    /**
     * Add a new post.
     * @param current Current post in the list
     * @param post Post to add
     * @return Tree with post added
     */
    private ActivityPost add(ActivityPost current, ActivityPost post) {
        if(current == null)
            return post;

        if(current.getKey() < post.getKey()) // if the current key is < the new one, start from right subtree
            current.setRight(add(current.getRight(), post));
        else if(current.getKey() > post.getKey()) // if the current key is > the new one, start from left subtree
            current.setLeft(add(current.getLeft(), post));
        else
            return current; // if the keys match, don't allow duplicates

        current.updateHeight();
        int balance = getBalance(current);

        // if the tree is weighted toward the left and the new post is < the current, perform a right rotation
        if(balance > 1 && post.getKey() < current.getLeft().getKey())
            return rotateRight(current);

        // if the tree is weighted toward the right and the new post is > the current, perform a left rotation
        if(balance < -1 && post.getKey() > current.getRight().getKey()) // if the balance of the right tree is higher
            return rotateLeft(current);

        // if the tree is weighted toward the left and the new post is > the current, rotate current node's left subtree
        // then perform a right rotation
        if(balance > 1 && post.getKey() > current.getLeft().getKey()) { // if the balance
            current.setLeft(rotateLeft(current.getLeft()));
            return rotateRight(current);
        }

        // if the tree is weighted toward the right and the new post is < the current, rotate current node's right subtree
        // then perform a left rotation
        if(balance < -1 && post.getKey() < current.getRight().getKey()) {
            current.setRight(rotateRight(current.getRight()));
            return rotateLeft(current);
        }

        return current;
    }

    /**
     * Get the balance of the tree.
     * @param current Current vertex to check balance for
     * @return Balance of the provided subtree
     */
    private int getBalance(ActivityPost current) {
        if(current == null)
            return 0;

        return getBalance(current.getLeft()) - getBalance(current.getRight());
    }

    /**
     * Perform a left rotation.
     * @param root Vertex to rotate at
     * @return Rotated vertex
     */
    private ActivityPost rotateLeft(ActivityPost root) {
        ActivityPost newRoot = root.getLeft();
        ActivityPost newLeft = root.getRight();

        newRoot.setRight(root);
        root.setLeft(newLeft);
        root.updateHeight();
        newRoot.updateHeight();

        return newRoot;
    }

    /**
     * Perform a right rotation.
     * @param root Vertex to rotate at
     * @return Rotated vertex
     */
    private ActivityPost rotateRight(ActivityPost root) {
        ActivityPost newRoot = root.getRight();
        ActivityPost newRight = root.getLeft();

        newRoot.setLeft(root);
        root.setRight(newRight);
        root.updateHeight();
        newRoot.updateHeight();

        return newRoot;
    }

    /**
     * Remove all posts.
     */
    public void removeAll() {
        posts = null;
    }

    /**
     * Display all posts.
     * @return Number of posts displayed
     */
    public int display() {
        return display(posts);
    }

    /**
     * Display all posts.
     * @param current Current post to display
     * @return Number of posts displayed
     */
    private int display(ActivityPost current) {
        if(current == null)
            return 0;

        int count = display(current.getLeft());
        current.display();
        return count + display(current.getRight()) + 1;
    }

    /**
     * Display posts by a specific author.
     * @param author Author to find
     * @return Number of posts displayed
     */
    public int displayByAuthor(String author) {
        return displayByAuthor(posts, author);
    }

    /**
     * Display posts by a specific author.
     * @param current Current post
     * @param author Author to find
     * @return Number of posts displayed
     */
    private int displayByAuthor(ActivityPost current, String author) {
        if(current == null)
            return 0;

        int count = displayByAuthor(current.getLeft(), author);

        if(current.authorMatches(author)) {
            current.display();
            ++count;
        }

        return count + displayByAuthor(current.getRight(), author);
    }

    /**
     * Display posts by collection type.
     * @param collectionType Collection type to find
     * @return Number of posts displayed
     */
    public int displayByActivityType(CollectionType collectionType) {
        return displayByActivityType(posts, collectionType);
    }

    /**
     * Display posts by collection type.
     * @param current Current post
     * @param collectionType Collection type to find
     * @return Number of posts displayed
     */
    private int displayByActivityType(ActivityPost current, CollectionType collectionType) {
        if(current == null)
            return 0;

        int count = displayByActivityType(current.getLeft(), collectionType);

        if(collectionType == CollectionType.NONE || current.isType(collectionType)) {
            current.display();
            ++count;
        }

        return count + displayByActivityType(current.getRight(), collectionType);
    }

    /**
     * Display posts by craft type.
     * @param craftType Craft type to find
     * @return Number of posts displayed
     */
    public int displayByActivityType(CraftType craftType) {
        return displayByActivityType(posts, craftType);
    }

    /**
     * Display posts by craft type.
     * @param current Current post
     * @param craftType Craft type to find
     * @return Number of posts displayed
     */
    private int displayByActivityType(ActivityPost current, CraftType craftType) {
        if(current == null)
            return 0;

        int count = displayByActivityType(current.getLeft(), craftType);

        if(craftType == CraftType.NONE || current.isType(craftType)) {
            current.display();
            ++count;
        }

        return count + displayByActivityType(current.getRight(), craftType);
    }

    /**
     * Display posts by activity name.
     * @param activity Activity name to find
     * @return Number of posts displayed
     */
    public int displayByActivityName(String activity) {
        return displayByActivityName(posts, activity);
    }

    /**
     * Display posts by activity name.
     * @param current Current post
     * @param activity Activity name to find
     * @return Number of posts displayed
     */
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
}
