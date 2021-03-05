package activities.crafts.items;

import activities.*;

public class CraftProject extends ActivityItem {
    private final String description;
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
