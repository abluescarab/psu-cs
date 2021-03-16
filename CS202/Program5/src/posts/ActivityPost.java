/* Alana Gilston - 3/15/21 - CS202 - Program 5
 * ActivityPost.java
 *
 * The ActivityPost class manages a post made about an activity.
 */
package posts;

import activities.*;
import activities.collections.*;
import activities.crafts.*;
import utils.*;

public class ActivityPost extends Utils {
    /**
     * Stores the last created unique key.
     */
    private static int lastKey = 0;
    /**
     * Current post's unique key.
     */
    private final int key;
    /**
     * Author who wrote the post.
     */
    private String author;
    /**
     * Message in the post.
     */
    private String message;
    /**
     * Activity the post is about.
     */
    private Activity activity;
    /**
     * Stored height for the balanced search tree vertex.
     */
    private int height;
    /**
     * Root of the left subtree.
     */
    private ActivityPost left;
    /**
     * Root of the right subtree.
     */
    private ActivityPost right;

    /**
     * Create a new ActivityPost.
     */
    public ActivityPost() {
        key = ++lastKey;
        author = "";
        message = "";
        activity = null;
        height = 1;
        left = null;
        right = null;
    }

    /**
     * Create a new ActivityPost.
     * @param author Author who wrote the post
     * @param message Message of the post
     */
    public ActivityPost(String author, String message) {
        key = ++lastKey;
        this.author = author;
        this.message = message;
        activity = null;
        height = 1;
        left = null;
        right = null;
    }

    /**
     * Create a new ActivityPost.
     * @param author Author who wrote the post
     * @param message Message of the post
     * @param activity Activity post was written about
     */
    public ActivityPost(String author, String message, Activity activity) {
        key = ++lastKey;
        this.author = author;
        this.message = message;
        this.activity = activity;
        height = 1;
        left = null;
        right = null;
    }

    /**
     * Get the left subtree.
     * @return Left vertex
     */
    ActivityPost getLeft() {
        return left;
    }

    /**
     * Set the left subtree.
     * @param left New left vertex
     */
    void setLeft(ActivityPost left) {
        this.left = left;
    }

    /**
     * Get the right subtree.
     * @return Right vertex
     */
    ActivityPost getRight() {
        return right;
    }

    /**
     * Set the right subtree.
     * @param right New right vertex
     */
    void setRight(ActivityPost right) {
        this.right = right;
    }

    /**
     * Update the vertex's height for the balanced search tree insertion.
     */
    void updateHeight() {
        int leftHeight = 0;
        int rightHeight = 0;

        if(left != null)
            leftHeight = left.getHeight();

        if(right != null)
            rightHeight = right.getHeight();

        height = 1 + Math.max(leftHeight, rightHeight);
    }

    /**
     * Get the height of the tallest subtree.
     * @return Height of the tallest subtree
     */
    int getHeight() {
        return height;
    }

    /**
     * Get the unique key.
     * @return Unique key
     */
    int getKey() {
        return key;
    }

    /**
     * Check if the post is about a collection type.
     * @param collectionType Collection type to check
     * @return Whether the post is about the collection type
     */
    public boolean isType(CollectionType collectionType) {
        if(activity == null)
            return collectionType == CollectionType.NONE;

        return ((CollectionActivity)activity).getType() == collectionType;
    }

    /**
     * Check if the post is about a craft type.
     * @param craftType Craft type to check
     * @return Whether the post is about the craft type
     */
    public boolean isType(CraftType craftType) {
        if(activity == null)
            return craftType == CraftType.NONE;

        return ((CraftActivity)activity).getType() == craftType;
    }

    /**
     * Check if the author matches another author.
     * @param author Author to compare to
     * @return Whether the authors match
     */
    public boolean authorMatches(String author) {
        return this.author.equals(author);
    }

    /**
     * Compare the author to another post and return the value of compareTo().
     * @param post Post to compare
     * @return Result of the compareTo() function
     */
    public int compareAuthor(ActivityPost post) {
        return this.author.compareTo(post.author);
    }

    /**
     * Check if the activity matches a provided name.
     * @param activity Activity name to compare to
     * @return Whether the activity names match
     */
    public boolean activityMatches(String activity) {
        if(this.activity == null)
            return false;

        return this.activity.matches(activity);
    }

    /**
     * Display the post.
     */
    public void display() {
        System.out.println("(" + activity.toString() + ") " + author + ": " + message);
    }

    /**
     * Create a new ActivityPost from user input.
     * @param activities Activity manager with activities to post about
     * @return New ActivityPost instance
     */
    public ActivityPost create(ActivityManager activities) {
        int option = 0;
        String activityName = "";

        System.out.println("Activity type: ");
        System.out.println("1) Collection");
        System.out.println("2) Craft");
        option = getNumericInput(2);

        System.out.print("Activity name: ");
        activityName = getStringInput();

        if(option == 1)
            activity = activities.getCollection(activityName);
        else
            activity = activities.getCraft(activityName);

        if(activity == null) {
            System.out.println("Activity with that name does not exist.");
            return null;
        }

        System.out.print("Author: ");
        author = getStringInput();

        System.out.print("Message: ");
        message = getStringInput();

        return this;
    }
}
