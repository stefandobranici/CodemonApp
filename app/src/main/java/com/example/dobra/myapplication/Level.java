package com.example.dobra.myapplication;

public class Level {
    public int chapter_id;
    public int colour_label;
    public int mission_id;
    public boolean completed;

    Level(){

    }

    public void setValues(Level level){
        chapter_id = level.chapter_id;
        colour_label = level.colour_label;
        mission_id = level.mission_id;
        completed = level.completed;
    }
}
