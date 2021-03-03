package activities.crafts;

public class CraftProject {
    private String name;
    private boolean forSale;
    private boolean complete;
    private CraftProject next;

    public CraftProject() {
        name = "";
        forSale = false;
        complete = false;
        next = null;
    }

    public CraftProject(String name, boolean forSale) {
        this.name = name;
        this.forSale = forSale;
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
