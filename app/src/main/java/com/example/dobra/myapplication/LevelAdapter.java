package com.example.dobra.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.List;

public class LevelAdapter {
    private List<Level> mLevelList;
    private List<String> mKeys;
    private Context context;
    private LinearLayout mainLayout;
    private LinearLayout firstThirdLayout, secondThirdLayout, lastThirdLayout;

    public LevelAdapter(List<Level> mLevelList, List<String> mKeys, Context context, LinearLayout mainLayout) {
        this.mLevelList = mLevelList;
        this.mKeys = mKeys;
        this.context = context;
        this.mainLayout = mainLayout;
        this.firstThirdLayout = (LinearLayout) this.mainLayout.findViewById(R.id.firstThirdOfTheScreenLayout);
        this.secondThirdLayout = (LinearLayout) this.mainLayout.findViewById(R.id.secondThirdOfTheScreenLayout);
        this.lastThirdLayout = (LinearLayout) this.mainLayout.findViewById(R.id.lastThirdOfTheScreenLayout);
    }

    public void generateMap() {

        if(CurrentUserInformation.getInstance().getChapterSelected().equals(1)){
            ImageView chapterImage = mainLayout.findViewById(R.id.chapterName);
            chapterImage.setImageResource(R.drawable.variables);
        } else if(CurrentUserInformation.getInstance().getChapterSelected().equals(2)){
            ImageView chapterImage = mainLayout.findViewById(R.id.chapterName);
            chapterImage.setImageResource(R.drawable.ifworld);
        } else if(CurrentUserInformation.getInstance().getChapterSelected().equals(3)){
            ImageView chapterImage = mainLayout.findViewById(R.id.chapterName);
            chapterImage.setImageResource(R.drawable.whilewait);
        } else if(CurrentUserInformation.getInstance().getChapterSelected().equals(4)){
            ImageView chapterImage = mainLayout.findViewById(R.id.chapterName);
            chapterImage.setImageResource(R.drawable.forever);
        } else if(CurrentUserInformation.getInstance().getChapterSelected().equals(5)){
            ImageView chapterImage = mainLayout.findViewById(R.id.chapterName);
            chapterImage.setImageResource(R.drawable.arrays);
        } else if(CurrentUserInformation.getInstance().getChapterSelected().equals(6)){
            ImageView chapterImage = mainLayout.findViewById(R.id.chapterName);
            chapterImage.setImageResource(R.drawable.methods);
        } else if(CurrentUserInformation.getInstance().getChapterSelected().equals(7)){
            ImageView chapterImage = mainLayout.findViewById(R.id.chapterName);
            chapterImage.setImageResource(R.drawable.classes);
        }

        int countLevelsDrawn = 1;

        for (final Level level : mLevelList) {
            if (level.isBlue()) {

                ImageView blueTile = new ImageView(context);
                blueTile.setAdjustViewBounds(true);

                if(level.isCompleted()) {
                    blueTile.setImageResource(R.drawable.bluetile);

                } else if(level.getLevel().equals(CurrentUserInformation.getInstance().getCurrentActiveLevel())){
                    blueTile.setImageResource(R.drawable.bluetilecurrent);
                } else {
                    blueTile.setImageResource(R.drawable.bluetile);
                }

                if (countLevelsDrawn == 1) {
                    firstThirdLayout.addView(blueTile);
                } else if (countLevelsDrawn == 2) {
                    secondThirdLayout.addView(blueTile);
                } else if (countLevelsDrawn == 3) {
                    lastThirdLayout.addView(blueTile);
                }

                if (countLevelsDrawn == 3) {
                    countLevelsDrawn = 1;
                } else {
                    countLevelsDrawn++;
                }

            } else if (level.isGreen()) {
                ImageView greenTile = new ImageView(context);

                greenTile.setAdjustViewBounds(true);

                if(level.isCompleted() && !level.getLevel().equals(CurrentUserInformation.getInstance().getCurrentActiveLevel())) {
                    greenTile.setImageResource(R.drawable.greentile);
                    greenTile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CurrentUserInformation.getInstance().setLevelSelectedForPlay(level);
                            Intent startGameplay = new Intent("android.intent.action.GameplayScreen");
                            context.startActivity(startGameplay);
                        }
                    });

                }else if(level.getLevel().equals(CurrentUserInformation.getInstance().getCurrentActiveLevel())){
                    greenTile.setImageResource(R.drawable.greentilecurrent);

                    greenTile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CurrentUserInformation.getInstance().setLevelSelectedForPlay(level);
                            Intent startGameplay = new Intent("android.intent.action.GameplayScreen");
                            context.startActivity(startGameplay);
                        }
                    });
                } else {
                    greenTile.setImageResource(R.drawable.greentileactive);
                    if(level.getLevel().equals(CurrentUserInformation.getInstance().getCurrentActiveLevel()+1)){
                        greenTile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CurrentUserInformation.getInstance().setLevelSelectedForPlay(level);
                                Intent startGameplay = new Intent("android.intent.action.GameplayScreen");
                                context.startActivity(startGameplay);
                            }
                        });
                    } else {
                        greenTile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(context, "You need to complete previous levels!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                if (countLevelsDrawn == 1) {
                    firstThirdLayout.addView(greenTile);
                } else if (countLevelsDrawn == 2) {
                    secondThirdLayout.addView(greenTile);
                } else if (countLevelsDrawn == 3) {
                    lastThirdLayout.addView(greenTile);
                }

                if (countLevelsDrawn == 3) {
                    countLevelsDrawn = 1;
                } else {
                    countLevelsDrawn++;
                }

            } else if (level.isViolette()) {
                ImageView violetteTile = new ImageView(context);

                violetteTile.setAdjustViewBounds(true);

                if(level.isCompleted() && !level.getLevel().equals(CurrentUserInformation.getInstance().getCurrentActiveLevel())) {
                    violetteTile.setImageResource(R.drawable.violettile);
                    violetteTile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CurrentUserInformation.getInstance().setLevelSelectedForPlay(level);
                            Intent startGameplay = new Intent("android.intent.action.GameplayScreen");
                            context.startActivity(startGameplay);
                        }
                    });

                }else if(level.getLevel().equals(CurrentUserInformation.getInstance().getCurrentActiveLevel())){
                    violetteTile.setImageResource(R.drawable.violettilecurrent);
                    violetteTile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CurrentUserInformation.getInstance().setLevelSelectedForPlay(level);
                            Intent startGameplay = new Intent("android.intent.action.GameplayScreen");
                            context.startActivity(startGameplay);
                        }
                    });
                } else {
                    violetteTile.setImageResource(R.drawable.violettileactive);
                    if(level.getLevel().equals(CurrentUserInformation.getInstance().getCurrentActiveLevel()+1)){
                        violetteTile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CurrentUserInformation.getInstance().setLevelSelectedForPlay(level);
                                Intent startGameplay = new Intent("android.intent.action.GameplayScreen");
                                context.startActivity(startGameplay);
                            }
                        });
                    } else {
                        violetteTile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(context, "You need to complete previous levels!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                if (countLevelsDrawn == 1) {
                    firstThirdLayout.addView(violetteTile);
                } else if (countLevelsDrawn == 2) {
                    secondThirdLayout.addView(violetteTile);
                } else if (countLevelsDrawn == 3) {
                    lastThirdLayout.addView(violetteTile);
                }

                if (countLevelsDrawn == 3) {
                    countLevelsDrawn = 1;
                } else {
                    countLevelsDrawn++;
                }

            } else if (level.isRed()) {
                ImageView redTile = new ImageView(context);

                redTile.setAdjustViewBounds(true);

                if(level.isCompleted() && !level.getLevel().equals(CurrentUserInformation.getInstance().getCurrentActiveLevel())) {
                    redTile.setImageResource(R.drawable.redtile);

                    redTile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CurrentUserInformation.getInstance().setLevelSelectedForPlay(level);
                            Intent startGameplay = new Intent("android.intent.action.GameplayScreen");
                            context.startActivity(startGameplay);
                        }
                    });

                }else if(level.getLevel().equals(CurrentUserInformation.getInstance().getCurrentActiveLevel())){
                    redTile.setImageResource(R.drawable.redtilecurrent);
                    redTile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CurrentUserInformation.getInstance().setLevelSelectedForPlay(level);
                            Intent startGameplay = new Intent("android.intent.action.GameplayScreen");
                            context.startActivity(startGameplay);
                        }
                    });

                } else {
                    redTile.setImageResource(R.drawable.redtileactive);

                    if(level.getLevel().equals(CurrentUserInformation.getInstance().getCurrentActiveLevel()+1)){
                        redTile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CurrentUserInformation.getInstance().setLevelSelectedForPlay(level);
                                Intent startGameplay = new Intent("android.intent.action.GameplayScreen");
                                context.startActivity(startGameplay);
                            }
                        });
                    } else {
                        redTile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(context, "You need to complete previous levels!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                if (countLevelsDrawn == 1) {
                    firstThirdLayout.addView(redTile);
                } else if (countLevelsDrawn == 2) {
                    secondThirdLayout.addView(redTile);
                } else if (countLevelsDrawn == 3) {
                    lastThirdLayout.addView(redTile);
                }

                if (countLevelsDrawn == 3) {
                    countLevelsDrawn = 1;
                } else {
                    countLevelsDrawn++;
                }
            }
        }
    }

}
