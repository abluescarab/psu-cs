package activities;

import java.util.*;

public abstract class Activity {
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

    public abstract void create();

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

    public void displayAll() {
        for(ActivityItem item : items)
            item.display();
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
