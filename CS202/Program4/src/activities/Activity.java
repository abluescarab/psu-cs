package activities;

public abstract class Activity {
    public enum ExperienceLevel {
        NOVICE,
        INTERMEDIATE,
        ADVANCED,
        EXPERT
    }

    private String name;
    private ExperienceLevel experienceLevel;

    public Activity() {
        name = "";
        experienceLevel = ExperienceLevel.NOVICE;
    }

    public Activity(String name, ExperienceLevel experienceLevel) {
        this.name = name;
        this.experienceLevel = experienceLevel;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeExperienceLevel(ExperienceLevel experienceLevel) {
        this.experienceLevel = experienceLevel;
    }
}
