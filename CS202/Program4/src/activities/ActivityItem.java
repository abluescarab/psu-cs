package activities;

import utils.*;

public abstract class ActivityItem extends Utils {
    private String name;

    public ActivityItem() {
        name = "";
    }

    public ActivityItem(String name) {
        this.name = name;
    }

    public ActivityItem create() {
        String input = "";

        System.out.print("Name: ");
        input = getStringInput(false, "Invalid item name.");

        if(input.isBlank())
            return null;

        name = input;
        return this;
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
