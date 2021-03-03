package activities.crafts;

import activities.Activity;

public class Craft extends Activity {
    public CraftProject projects;

    public Craft() {
        projects = null;
    }

    public Craft(String name, ExperienceLevel experienceLevel) {
        super(experienceLevel);
        this.projects = null;
    }
}
