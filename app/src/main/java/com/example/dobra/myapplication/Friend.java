package com.example.dobra.myapplication;

public class Friend implements Comparable<Friend>{

    private String friendImage;
    private String friendName;
    private String friendLevel;
    private String friendProgress;
    private String friendActivity;
    private String friendID;

    public Friend() {
    }

    public Friend(String friendImage, String friendName, String friendLevel, String friendProgress, String friendActivity, String friendID) {
        this.friendImage = friendImage;
        this.friendName = friendName;
        this.friendLevel = friendLevel;
        this.friendProgress = friendProgress;
        this.friendActivity = friendActivity;
        this.friendID = friendID;
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

    public String getFriendID() {
        return friendID;
    }

    public void setFriendID(String friendID) {
        this.friendID = friendID;
    }

    private Integer friendLevelsCompleted(){
        Integer levelsCompleted = 0;
        for(int i = 0; i < friendProgress.length(); i++){
            if(friendProgress.charAt(i) == '/'){
                levelsCompleted = Integer.parseInt(friendProgress.substring(0, i));
            }
        }

        return levelsCompleted;
    }

    @Override
    public int compareTo(Friend o) {
        return this.friendLevelsCompleted().compareTo(o.friendLevelsCompleted());
    }
}
