package com.example.dobra.myapplication;

public class Skill {
    private String ImageURL;
    private String Skillname;
    private Integer UnlockableAtLevel;
    private Boolean Unlocked;

    public Skill() {
    }

    public Skill(String imageURL, String skillname, Integer unlockableAtLevel, Boolean unlocked) {
        Skillname = skillname;
        UnlockableAtLevel = unlockableAtLevel;
        Unlocked = unlocked;
        ImageURL = imageURL;
    }

    public String getSkillname() {
        return Skillname;
    }

    public void setSkillname(String skillname) {
        Skillname = skillname;
    }

    public Integer getUnlockableAtLevel() {
        return UnlockableAtLevel;
    }

    public void setUnlockableAtLevel(Integer unlockableAtLevel) {
        UnlockableAtLevel = unlockableAtLevel;
    }

    public Boolean getUnlocked() {
        return Unlocked;
    }

    public void setUnlocked(Boolean unlocked) {
        Unlocked = unlocked;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }
}
