package com.example.dobra.myapplication;

import java.util.HashMap;
import java.util.Map;

public class UserLevel {

    private static final UserLevel SINGLE_INSTANCE = new UserLevel();

    private Map<Integer, Integer> xpPerLevel;
    private Map<Integer, Integer> maxHealthPerLevel;

    private UserLevel(){
        xpPerLevel = new HashMap<>();

        maxHealthPerLevel = new HashMap<>();

        xpPerLevel.put(0, 0);
        xpPerLevel.put(1, 100);
        xpPerLevel.put(2, 250);
        xpPerLevel.put(3, 500);
        xpPerLevel.put(4, 1000);
        xpPerLevel.put(5, 1750);
        xpPerLevel.put(6, 3000);
        xpPerLevel.put(7, 4500);
        xpPerLevel.put(8, 7000);
        xpPerLevel.put(9, 10000);
        xpPerLevel.put(10,15000);
        xpPerLevel.put(11, 100000);

        maxHealthPerLevel.put(0, 0);
        maxHealthPerLevel.put(1, 10);
        maxHealthPerLevel.put(2, 11);
        maxHealthPerLevel.put(3, 12);
        maxHealthPerLevel.put(4, 13);
        maxHealthPerLevel.put(5, 14);
        maxHealthPerLevel.put(6, 15);
        maxHealthPerLevel.put(7, 16);
        maxHealthPerLevel.put(8, 17);
        maxHealthPerLevel.put(9, 18);
        maxHealthPerLevel.put(10, 19);
    }

    public static UserLevel getInstance() {
        return SINGLE_INSTANCE;
    }

    public Integer getXpNeedForCurrentLevel(Integer userCurrentLevel){
        return xpPerLevel.get(userCurrentLevel);
    }

    public boolean canUserLevelUp(Integer level, Integer currentXp, Integer newXp){
        if(xpPerLevel.containsKey(level)) {
            return (xpPerLevel.get(level) <= (currentXp + newXp));
        } else return false;
    }

    public Integer getMaxHealthAtLevel(Integer userCurrentLevel){
        if(maxHealthPerLevel.containsKey(userCurrentLevel)) {
            return maxHealthPerLevel.get(userCurrentLevel);
        } else return -1;
    }

}
