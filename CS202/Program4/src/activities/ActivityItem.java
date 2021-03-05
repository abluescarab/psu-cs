package activities;

public abstract class ActivityItem {
    private final String name;

    public ActivityItem() {
        name = "";
    }

    public ActivityItem(String name) {
        this.name = name;
    }

    public boolean matches(String name) {
        return this.name.equals(name);
    }

    public void display() {
        System.out.println(name);
    }

    public String toString() {
        return name;
    }
}
