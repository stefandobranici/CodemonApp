package com.example.dobra.myapplication;

public class Medal {
    private String Name;
    private Boolean Unlocked;

    public Medal(){

    }

    public Medal(String name, Boolean unlocked) {
        Name = name;
        Unlocked = unlocked;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Boolean getUnlocked() {
        return Unlocked;
    }

    public void setUnlocked(Boolean unlocked) {
        Unlocked = unlocked;
    }
}
