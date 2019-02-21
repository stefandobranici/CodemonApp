package com.example.dobra.myapplication;

public class Level {
    private Integer chapter;
    private boolean completed;
    private String content;
    private Integer level;
    private String type;

    public Level() {
    }

    public Level(Integer chapter, boolean completed, String content, Integer level, String type) {
        this.chapter = chapter;
        this.completed = completed;
        this.content = content;
        this.level = level;
        this.type = type;
    }

    public Integer getChapter() {
        return chapter;
    }

    public void setChapter(Integer chapter) {
        this.chapter = chapter;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isBlue(){
        return (type.equals("blue"));
    }

    public boolean isGreen(){
        return (type.equals("green"));
    }

    public boolean isViolette(){
        return (type.equals("violet"));
    }

    public boolean isRed(){
        return (type.equals("red"));
    }
}
