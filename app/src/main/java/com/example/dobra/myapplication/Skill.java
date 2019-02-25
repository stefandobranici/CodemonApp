package com.example.dobra.myapplication;

public class Skill {
    private String Skillname;
    private Integer UnlockableAtLevel;
    private Boolean Unlocked;

    public Skill() {
    }

    public Skill(String skillname, Integer unlockableAtLevel, Boolean unlocked) {
        Skillname = skillname;
        UnlockableAtLevel = unlockableAtLevel;
        Unlocked = unlocked;
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
}
