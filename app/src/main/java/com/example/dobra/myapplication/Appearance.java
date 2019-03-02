package com.example.dobra.myapplication;

public class Appearance {
    private String ImageURL;
    private String Name;
    private Boolean Unlocked;

    public Appearance() {
    }

    public Appearance(String imageURL, String name, Boolean unlocked) {
        Name = name;
        Unlocked = unlocked;
        ImageURL = imageURL;
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

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }
}
