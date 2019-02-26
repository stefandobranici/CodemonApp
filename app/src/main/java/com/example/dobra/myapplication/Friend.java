package com.example.dobra.myapplication;

public class Friend {

    private String friendImage;
    private String friendName;
    private String friendLevel;
    private String friendProgress;
    private String friendActivity;

    public Friend() {
    }

    public Friend(String friendImage, String friendName, String friendLevel, String friendProgress, String friendActivity) {
        this.friendImage = friendImage;
        this.friendName = friendName;
        this.friendLevel = friendLevel;
        this.friendProgress = friendProgress;
        this.friendActivity = friendActivity;
    }

    public String getFriendImage() {
        return friendImage;
    }

    public void setFriendImage(String friendImage) {
        this.friendImage = friendImage;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendLevel() {
        return friendLevel;
    }

    public void setFriendLevel(String friendLevel) {
        this.friendLevel = friendLevel;
    }

    public String getFriendProgress() {
        return friendProgress;
    }

    public void setFriendProgress(String friendProgress) {
        this.friendProgress = friendProgress;
    }

    public String getFriendActivity() {
        return friendActivity;
    }

    public void setFriendActivity(String friendActivity) {
        this.friendActivity = friendActivity;
    }
}
