package activities;

public abstract class Activity {
    public enum ExperienceLevel {
        NOVICE,
        INTERMEDIATE,
        ADVANCED,
        EXPERT
    }

    private ExperienceLevel experienceLevel;

    public Activity() {
        experienceLevel = ExperienceLevel.NOVICE;
    }

    public Activity(ExperienceLevel experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public void changeExperienceLevel(ExperienceLevel experienceLevel) {
        this.experienceLevel = experienceLevel;
    }
}
