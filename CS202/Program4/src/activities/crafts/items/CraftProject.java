package activities.crafts.items;

import activities.*;

public class CraftProject extends ActivityItem {
    private String description;
    private boolean complete;

    public CraftProject() {
        description = "";
        complete = false;
    }

    public CraftProject(String name, String description) {
        super(name);
        this.description = description;
        complete = false;
    }

    @Override
    public CraftProject create() {
        super.create();

        System.out.print("Description: ");
        description = getStringInput();

        return this;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public boolean isComplete() {
        return complete;
    }

    public void markCompleted() {
        complete = true;
    }

    public void display() {
        // TODO
    }
}
