package com.example.dobra.myapplication;

public class Level {
    private int chapter;
    private boolean completed;
    private String content;
    private int level;
    private String type;

    public Level() {
    }

    public Level(int chapter, boolean completed, String content, int level, String type) {
        this.chapter = chapter;
        this.completed = completed;
        this.content = content;
        this.level = level;
        this.type = type;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
