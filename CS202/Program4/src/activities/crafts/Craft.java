package activities.crafts;

import activities.Activity;
import activities.crafts.projects.*;

public class Craft extends Activity {
    private CraftProject projects;

    public Craft() {
        projects = null;
    }

    public Craft(ExperienceLevel experienceLevel) {
        super(experienceLevel);
        this.projects = null;
    }

    public boolean add(CraftProject project) {
        return add(projects, project);
    }

    private boolean add(CraftProject current, CraftProject project) {
        if(current.getNext() == null) {
            current.setNext(project);
            return true;
        }

        return add(current.getNext(), project);
    }

    public boolean remove(String name) {
        return remove(projects, name);
    }

    private boolean remove(CraftProject current, String name) {
        if(current == null)
            return false;

        if(current.getNext().toString().equals(name)) {
            current.setNext(null);
            return true;
        }

        return remove(current.getNext(), name);
    }

    public int removeAll() {
        int count = count();
        projects = null;
        return count;
    }

    public int count() {
        return count(projects);
    }

    private int count(CraftProject current) {
        if(current == null)
            return 0;

        return count(current.getNext()) + 1;
    }

    public void display() {
        // TODO
    }
}
