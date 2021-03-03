package activities.crafts;

public class CraftProject {
    private String name;
    private String description;
    private boolean complete;
    private CraftProject next;

    public CraftProject() {
        name = "";
        description = "";
        complete = false;
        next = null;
    }

    public CraftProject(String name, String description) {
        this.name = name;
        this.description = description;
        complete = false;
        next = null;
    }

    public CraftProject getNext() {
        return next;
    }

    public void setNext(CraftProject next) {
        this.next = next;
    }

    public void display() {
        // TODO
    }

    public String toString() {
        return name;
    }
}
