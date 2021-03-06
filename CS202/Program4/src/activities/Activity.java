package activities;

import utils.*;

import java.util.*;

public abstract class Activity extends Utils {
    public enum ExperienceLevel {
        NOVICE,
        INTERMEDIATE,
        ADVANCED,
        EXPERT
    }

    private String name;
    private ExperienceLevel experienceLevel;
    private final ArrayList<ActivityItem> items;
    private Activity next;

    public Activity() {
        name = "";
        experienceLevel = ExperienceLevel.NOVICE;
        items = new ArrayList<>();
        next = null;
    }

    public Activity(String name, ExperienceLevel experienceLevel) {
        this.name = name;
        this.experienceLevel = experienceLevel;
        items = new ArrayList<>();
        next = null;
    }

    public Activity create() {
        String input = "";
        int option = 0;

        System.out.println("Name: ");
        input = getStringInput(false, "Invalid name.");

        if(input.isBlank())
            return null;

        System.out.println("Experience level:");
        System.out.println("1) Novice");
        System.out.println("2) Intermediate");
        System.out.println("3) Advanced");
        System.out.println("4) Expert");
        option = getNumericInput(4);

        changeName(input);

        if(option == 0)
            changeExperienceLevel(ExperienceLevel.NOVICE);
        else if(option == 1)
            changeExperienceLevel(ExperienceLevel.INTERMEDIATE);
        else if(option == 3)
            changeExperienceLevel(ExperienceLevel.ADVANCED);
        else
            changeExperienceLevel(ExperienceLevel.EXPERT);

        return this;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeExperienceLevel(ExperienceLevel experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public boolean matches(String name) {
        return this.name.equals(name);
    }

    Activity getNext() {
        return next;
    }

    void setNext(Activity next) {
        this.next = next;
    }

    public void display() {
        System.out.println(name);
        System.out.print("Experience level: ");

        if(experienceLevel == ExperienceLevel.NOVICE)
            System.out.println("Novice");
        else if(experienceLevel == ExperienceLevel.INTERMEDIATE)
            System.out.println("Intermediate");
        else if(experienceLevel == ExperienceLevel.ADVANCED)
            System.out.println("Advanced");
        else
            System.out.println("Expert");
    }

    public int displayAll() {
        for(ActivityItem item : items)
            item.display();

        return items.size();
    }

    public int count() {
        return items.size();
    }

    public boolean contains(String name) {
        for(ActivityItem item : items) {
            if(item.matches(name))
                return true;
        }

        return false;
    }

    public boolean add(ActivityItem item) {
        if(contains(item.toString()))
            return false;

        return items.add(item);
    }

    public boolean remove(String name) {
        Iterator<ActivityItem> iterator = items.iterator();

        while(iterator.hasNext()) {
            if(iterator.next().matches(name)) {
                iterator.remove();
                return true;
            }
        }

        return false;
    }

    public int removeAll() {
        int count = count();
        items.clear();
        return count;
    }

    public String toString() {
        return name;
    }
}
