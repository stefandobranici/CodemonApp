package com.example.dobra.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;
import static com.example.dobra.myapplication.MapViewActivity.mapViewActivity;

public class GameplayScreen extends AppCompatActivity {
    private Level level;

    private final String ENEMY_CODEBUG = "Codebug";

    private final String ENEMY_CODEBOSS = "Fatal Error";

    private Typeface consolasFont;

    private LinearLayout topScreenLayout, middleScreenLayout;

    private ImageView gameScreenPlayerImage, gameScreenEnemyImage;

    private TextView gameScreenPlayerHealth, gameScreenPlayerLevel, gameScreenPlayerName, gameScreenEnemyName, gameScreenEnemyLevel, gameScreenEnemyHealth;

    //All menu layouts from third part of the screen
    private LinearLayout mainMenuBarLayout, mainSkillBarLayout, variablesSkillBarLayout, conditionalsSkillBarLayout, loopsSkillBarLayout, itemsSkillBarLayout;

    //Main menu bar buttons;
    private TextView openSkillsBarButton, openItemsBarButton, openNotebookButton, runAwayButton;

    //Skill menu buttons;
    private TextView openVariablesBarButton, openConditionalsBarButton, openLoopsBarButton, goBackToMainMenuButtonFromSkillsBar;

    //Variables skill bar buttons;
    private TextView intButton, charButton, floatButton, doubleButton, stringButton, toArrayButton, to2dArrayButton, goBackToSkillBarButtonFromVariables;

    //Conditionals skill bar buttons;
    private TextView ifButton, elseButton, elseIfButton, goBackToSkillBarButtonFromConditionals;

    //Loops skill bar buttons;
    private TextView whileButton, doButton, forButton, goBackToSkillBarButtonFromLoops;

    //Items bar buttons;
    private TextView useHealthPotionButton, useRevealingPotionButton, useFixerElixirButton, goBackToMainMenuButtonFromItemsBar;

    //Text buttons
    private ImageView forwardButtonForText, backButtonForText;

    //Attack animations
    private GifImageView chiuplusattackanimation, enemyattackanimation;

    private TextView currentTextViewClicked;

    List<ContentParser.ContentType> listOfWords;

    private ContentParser.ContentType currentContentTypeProcessed;

    private ArrayList<TextView> allErrorsInContent;

    private ArrayList<ContentParser.ContentType> errorsInContent;

    private ArrayList<LinearLayout> pagesOfContent;

    private int currentContentLayoutDisplayed;

    private int lineCounter;

    private int rewardNumber;

    private Integer enemyMaxHealth;

    private ImageView chestBoxOne, chestBoxOneReward, chestBoxTwo, chestBoxTwoReward, chestBoxThree, chestBoxThreeReward;

    private TextView chestBoxOneRewardText, chestBoxTwoRewardText, chestBoxThreeRewardText, continueToMapView, firstClearRewardText;

    private TextView skillUnlocked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay_screen);

        level = CurrentUserInformation.getInstance().getLevelSelectedForPlay();

        consolasFont = Typeface.createFromAsset(getAssets(), "font/Consolas.ttf");

        currentTextViewClicked = null;

        currentContentTypeProcessed = null;

        allErrorsInContent = new ArrayList<>();

        errorsInContent = new ArrayList<>();

        listOfWords = ContentParser.getInstance().processedWords(level);

        if (level.isGreen()) {
            setLearningScreenOn();
        } else if (level.isViolette()) {
            setTrainingScreenOn();
        } else if (level.isRed()) {
            setBossScreenOn();
        }
    }

    private void setLearningScreenOn() {
        ImageView trainerView = (ImageView) findViewById(R.id.trainerView);

        trainerView.setVisibility(View.VISIBLE);

        loadLearningContentOnScreen();

        setUpSkillBar();
    }

    private void setTrainingScreenOn() {

        LinearLayout topScreenLayoutFirstHalf = (LinearLayout) findViewById(R.id.topScreenLayoutFirstHalf);

        topScreenLayoutFirstHalf.setVisibility(View.VISIBLE);

        LinearLayout topScreenLayoutSecondHalf = (LinearLayout) findViewById(R.id.topScreenLayoutSecondHalf);

        topScreenLayoutSecondHalf.setVisibility(View.VISIBLE);

        setUserInformationOnScreen();

        loadContentOnScreen();

        setEnemyInformationOnScreen(ENEMY_CODEBUG);

        setUpSkillBar();

    }

    private void setBossScreenOn() {

        LinearLayout topScreenLayoutFirstHalf = (LinearLayout) findViewById(R.id.topScreenLayoutFirstHalf);

        topScreenLayoutFirstHalf.setVisibility(View.VISIBLE);

        LinearLayout topScreenLayoutSecondHalf = (LinearLayout) findViewById(R.id.topScreenLayoutSecondHalf);

        topScreenLayoutSecondHalf.setVisibility(View.VISIBLE);

        setUserInformationOnScreen();

        loadContentOnScreen();

        setEnemyInformationOnScreen(ENEMY_CODEBOSS);

        setUpSkillBar();
    }

    private void setUserInformationOnScreen() {
        gameScreenPlayerImage = (ImageView) findViewById(R.id.gameScreenPlayerImage);

        gameScreenPlayerImage.setImageResource(R.drawable.chiuplusback);

        gameScreenPlayerHealth = (TextView) findViewById(R.id.gameScreenPlayerHealth);

        gameScreenPlayerLevel = (TextView) findViewById(R.id.gameScreenPlayerLevel);

        gameScreenPlayerName = (TextView) findViewById(R.id.gameScreenPlayerName);

        chiuplusattackanimation = (GifImageView) findViewById(R.id.chiuplusattackanimation);

        gameScreenPlayerHealth.setText("HP  " + String.format(CurrentUserInformation.getInstance().getUserHealth().toString()) + "/" + String.format(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));

        gameScreenPlayerName.setText(CurrentUserInformation.getInstance().getUserName());

        gameScreenPlayerLevel.setText("Lv. " + String.format(CurrentUserInformation.getInstance().getUserLevel().toString()));
    }

    private void setEnemyInformationOnScreen(String enemy) {
        gameScreenEnemyImage = (ImageView) findViewById(R.id.gameScreenEnemyImage);

        gameScreenEnemyName = (TextView) findViewById(R.id.gameScreenEnemyName);

        gameScreenEnemyLevel = (TextView) findViewById(R.id.gameScreenEnemyLevel);

        gameScreenEnemyHealth = (TextView) findViewById(R.id.gameScreenEnemyHealth);

        enemyattackanimation = (GifImageView) findViewById(R.id.enemyattackanimation);

        enemyMaxHealth = allErrorsInContent.size();

        gameScreenEnemyName.setText(enemy);

        gameScreenEnemyLevel.setText("Lv.  " + String.format(level.getLevel().toString()));

        gameScreenEnemyHealth.setText("HP  " + allErrorsInContent.size() + "/" + enemyMaxHealth);

        if (enemy.equals(ENEMY_CODEBUG)) {
            gameScreenEnemyImage.setImageResource(R.drawable.codebug);
        } else if (enemy.equals(ENEMY_CODEBOSS)) {
            gameScreenEnemyImage.setImageResource(R.drawable.boss);
        }
    }

    private void loadLearningContentOnScreen() {
        pagesOfContent = new ArrayList<>();

        currentContentLayoutDisplayed = 0;

        lineCounter = 0;

        middleScreenLayout = (LinearLayout) findViewById(R.id.middleScreenLayout);

        forwardButtonForText = (ImageView) findViewById(R.id.forwardButtonForText);

        forwardButtonForText.setVisibility(View.VISIBLE);

        backButtonForText = (ImageView) findViewById(R.id.backButtonForText);

        backButtonForText.setVisibility(View.VISIBLE);

        LinearLayout currentProcessedPage = new LinearLayout(getApplicationContext());

        currentProcessedPage.setOrientation(LinearLayout.VERTICAL);

        LinearLayout lineOfWords = new LinearLayout(getApplicationContext());

        lineOfWords.setPadding(15, 15, 0, 0);

        int contentTypeCounter = 0;

        for (final ContentParser.ContentType contentType : listOfWords) {
            contentTypeCounter++;

            final TextView currentProcessedWord = new TextView(getApplicationContext());

            currentProcessedWord.setTypeface(consolasFont);

            currentProcessedWord.setTextSize(16);

            if (contentType.isKeyword()) {
                currentProcessedWord.setTextColor(getResources().getColor(R.color.colorAccent));

                currentProcessedWord.setText(contentType.wordContent);

                currentProcessedWord.setPadding(0, 0, 20, 0);

                lineOfWords.addView(currentProcessedWord);

            } else if (contentType.isRegularWord()) {
                currentProcessedWord.setTextColor(WHITE);

                currentProcessedWord.setText(contentType.wordContent);

                currentProcessedWord.setPadding(0, 0, 20, 0);

                lineOfWords.addView(currentProcessedWord);
            } else if (contentType.isNewLine()) {
                lineCounter++;

                currentProcessedPage.addView(lineOfWords);

                lineOfWords = new LinearLayout(getApplicationContext());

                lineOfWords.setPadding(15, 15, 0, 0);
            }

            if (lineCounter == 8 || contentTypeCounter == listOfWords.size()) {

                lineCounter = 0;

                if (contentTypeCounter == listOfWords.size()) {
                    currentProcessedPage.addView(lineOfWords);

                    lineOfWords = new LinearLayout(getApplicationContext());

                    lineOfWords.setPadding(15, 15, 0, 0);

                    TextView completeLevel = new TextView(getApplicationContext());

                    completeLevel.setTypeface(consolasFont);

                    completeLevel.setTextSize(20);

                    completeLevel.setTextColor(getResources().getColor(R.color.colorAccent));

                    completeLevel.setText("Click to Complete!");

                    completeLevel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            endGameScreenVictory();
                        }
                    });

                    lineOfWords.addView(completeLevel);

                    currentProcessedPage.addView(lineOfWords);


                }

                pagesOfContent.add(currentProcessedPage);

                currentProcessedPage = new LinearLayout(getApplicationContext());

                currentProcessedPage.setOrientation(LinearLayout.VERTICAL);
            }
        }

        backButtonForText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentContentLayoutDisplayed > 0) {
                    middleScreenLayout.removeAllViewsInLayout();
                    middleScreenLayout.addView(pagesOfContent.get(--currentContentLayoutDisplayed));
                }
            }
        });

        forwardButtonForText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentContentLayoutDisplayed < pagesOfContent.size() - 1) {
                    middleScreenLayout.removeAllViewsInLayout();
                    middleScreenLayout.addView(pagesOfContent.get(++currentContentLayoutDisplayed));
                }
            }
        });


        currentContentLayoutDisplayed = 0;

        middleScreenLayout.addView(pagesOfContent.get(currentContentLayoutDisplayed));

    }


    //Endgame Functionality
    private void endGameScreenVictory() {
        if (!level.isCompleted()) {
            final LinearLayout pickTheRewardLayout = (LinearLayout) findViewById(R.id.pickTheRewardLayout);
            pickTheRewardLayout.setVisibility(View.VISIBLE);

            firstClearRewardText = (TextView) findViewById(R.id.firstClearRewardText);

            chestBoxOne = (ImageView) findViewById(R.id.chestBoxOne);

            chestBoxOneReward = (ImageView) findViewById(R.id.chestBoxOneReward);

            chestBoxOneRewardText = (TextView) findViewById(R.id.chestBoxOneRewardText);

            chestBoxTwo = (ImageView) findViewById(R.id.chestBoxTwo);

            chestBoxTwoReward = (ImageView) findViewById(R.id.chestBoxTwoReward);

            chestBoxTwoRewardText = (TextView) findViewById(R.id.chestBoxTwoRewardText);

            chestBoxThree = (ImageView) findViewById(R.id.chestBoxThree);

            chestBoxThreeReward = (ImageView) findViewById(R.id.chestBoxThreeReward);

            chestBoxThreeRewardText = (TextView) findViewById(R.id.chestBoxThreeRewardText);

            continueToMapView = (TextView) findViewById(R.id.continueToMapView);

            chestBoxOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firstChestRewards();
                }
            });

            chestBoxTwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    secondChestRewards();
                }
            });

            chestBoxThree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    thirdChestRewards();
                }
            });

            continueToMapView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout secondRewardPage = (LinearLayout) findViewById(R.id.secondRewardPage);

                    pickTheRewardLayout.setVisibility(View.GONE);

                    secondRewardPage.setVisibility(View.VISIBLE);

                    TextView acceptRewards = (TextView) findViewById(R.id.acceptRewards);

                    skillUnlocked = (TextView) findViewById(R.id.skillUnlocked);

                    TextView xpGained = (TextView) findViewById(R.id.xpGained);

                    TextView leveledUp = (TextView) findViewById(R.id.leveledUp);

                    Integer xpGainedFromLevel = level.getLevel() * 5 + level.getChapter() * 100;

                    Integer currentUserXp = CurrentUserInformation.getInstance().getUserXp();

                    xpGained.setText("You have received: " + xpGainedFromLevel.toString() + " XP!");

                    Integer currentUserLevel = CurrentUserInformation.getInstance().getUserLevel();

                    Boolean userLeveledUp = false;

                    while (UserLevel.getInstance().canUserLevelUp(currentUserLevel, currentUserXp, xpGainedFromLevel)) {
                        userLeveledUp = true;

                        xpGainedFromLevel -= UserLevel.getInstance().getXpNeedForCurrentLevel(currentUserLevel) - currentUserXp;

                        currentUserXp = 0;

                        currentUserLevel++;
                    }

                    if (userLeveledUp) {
                        CurrentUserInformation.getInstance().setUserLevel(currentUserLevel);
                        CurrentUserInformation.getInstance().setUserHealth(UserLevel.getInstance().getMaxHealthAtLevel(currentUserLevel));
                        leveledUp.setText("Leveled up to " + currentUserLevel.toString() + "!");
                        CurrentUserInformation.getInstance().setUserXp(xpGainedFromLevel);

                    } else {
                        CurrentUserInformation.getInstance().increaseUserXp(xpGainedFromLevel);
                    }


                    unlockSkills();

                    if (level.isRed()) {
                        CurrentUserInformation.getInstance().setCurrentActiveLevel(level.getLevel() + 1);
                        CurrentUserInformation.getInstance().setCurrentActiveChapter(level.getChapter() + 1);
                    } else {
                        CurrentUserInformation.getInstance().setCurrentActiveLevel(level.getLevel());
                    }

                    CurrentUserInformation.getInstance().unlockCurrentLevel(level);

                    acceptRewards.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mapViewActivity.finish();
                            Intent mapview_intent = new Intent("android.intent.action.MapViewActivity");
                            startActivity(mapview_intent);
                            finish();
                        }
                    });
                }
            });


        } else {

            if(CurrentUserInformation.getInstance().getCurrentActiveLevel()<= level.getLevel()){
                CurrentUserInformation.getInstance().setCurrentActiveLevel(level.getLevel());
            }

            LinearLayout pickTheRewardLayout = (LinearLayout) findViewById(R.id.pickTheRewardLayout);
            pickTheRewardLayout.setVisibility(View.VISIBLE);

            firstClearRewardText = (TextView) findViewById(R.id.firstClearRewardText);
            firstClearRewardText.setText("Rewards already collected!");

            LinearLayout chestBoxesLayout = (LinearLayout) findViewById(R.id.chestBoxesLayout);
            chestBoxesLayout.setVisibility(View.GONE);

            continueToMapView = (TextView) findViewById(R.id.continueToMapView);
            continueToMapView.setVisibility(View.VISIBLE);

            continueToMapView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mapViewActivity.finish();
                    Intent mapview_intent = new Intent("android.intent.action.MapViewActivity");
                    startActivity(mapview_intent);
                    finish();
                }
            });


        }
    }

    private void endGameScreenDefeat() {
        LinearLayout defeatPopupLayout = (LinearLayout) findViewById(R.id.defeatPopupLayout);
        defeatPopupLayout.setVisibility(View.VISIBLE);

        TextView acceptDefeat = (TextView) findViewById(R.id.acceptDefeat);

        acceptDefeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(level.isCompleted()){
                    CurrentUserInformation.getInstance().setUserHealth(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()));
                } else {
                    CurrentUserInformation.getInstance().setUserHealth(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()));

                    Integer modulusOfLevel = level.getLevel()%21;

                    if(modulusOfLevel > 4){
                        CurrentUserInformation.getInstance().setCurrentActiveLevel(level.getLevel()-3);
                    }

                }
                mapViewActivity.finish();
                Intent mapview_intent = new Intent("android.intent.action.MapViewActivity");
                startActivity(mapview_intent);
                finish();
            }
        });
    }

    private void firstChestRewards() {
        continueToMapView.setVisibility(View.VISIBLE);

        Random randomDrop = new Random();

        rewardNumber = randomDrop.nextInt(100) + 1;

        if (rewardNumber == 1) {
            chestBoxOne.setImageResource(R.drawable.openchest2);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.bandana);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("The Bandana");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("NinjaHat");
        } else if (rewardNumber == 2) {
            chestBoxOne.setImageResource(R.drawable.openchest2);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.captainhat);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Captain's Hat");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("CaptainHat");
        } else if (rewardNumber == 3) {
            chestBoxOne.setImageResource(R.drawable.openchest2);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.crown);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("King's Crown");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("KingCrown");
        } else if (rewardNumber == 4) {
            chestBoxOne.setImageResource(R.drawable.openchest2);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.farmerhat);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Farmer's Hat");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("FarmerHat");
        } else if (rewardNumber == 5) {
            chestBoxOne.setImageResource(R.drawable.openchest2);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.fedora);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("The Fedora");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("CowboyHat");
        } else if (rewardNumber == 6) {
            chestBoxOne.setImageResource(R.drawable.openchest2);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.headphones);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Headphones");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("Headphones");
        } else if (rewardNumber == 7) {
            chestBoxOne.setImageResource(R.drawable.openchest2);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.piratehat);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Pirate Hat");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("PirateHat");
        } else if (rewardNumber == 8) {
            chestBoxOne.setImageResource(R.drawable.openchest2);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.spacehelmet);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Space Helmet");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("SpaceHelmet");
        } else if (rewardNumber == 9) {
            chestBoxOne.setImageResource(R.drawable.openchest2);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.tophat);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Top Hat");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("TopHat");
        } else if (rewardNumber == 10) {
            chestBoxOne.setImageResource(R.drawable.openchest2);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.wizardhat);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Wizard's Hat");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("WizardHat");
        } else if (rewardNumber > 11 && rewardNumber <= 20) {
            chestBoxOne.setImageResource(R.drawable.openchest2);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.healthpotion);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("x1 H. Potion");

            CurrentUserInformation.getInstance().updateConsumableQuantity("HealingPotion", 1);
        } else if (rewardNumber > 21 && rewardNumber <= 30) {
            chestBoxOne.setImageResource(R.drawable.openchest2);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.revealingpotion);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("x1 R. Potion");

            CurrentUserInformation.getInstance().updateConsumableQuantity("RevealingPotion", 1);
        } else if (rewardNumber > 31 && rewardNumber <= 40) {
            chestBoxOne.setImageResource(R.drawable.openchest2);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.fixerelixer);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("x1 Fixer Elixer");

            CurrentUserInformation.getInstance().updateConsumableQuantity("FixerElixer", 1);
        } else if (rewardNumber > 41 && rewardNumber <= 50) {
            chestBoxOne.setImageResource(R.drawable.openchest2);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.cybercoins);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Coins x5");

            CurrentUserInformation.getInstance().setUserCoins(5);
        } else if (rewardNumber > 51 && rewardNumber <= 60) {
            chestBoxOne.setImageResource(R.drawable.openchest2);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.cybercoins);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Coins x10");

            CurrentUserInformation.getInstance().setUserCoins(10);
        } else if (rewardNumber > 61 && rewardNumber <= 70) {
            chestBoxOne.setImageResource(R.drawable.openchest2);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.cybercoins);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Coins x15");

            CurrentUserInformation.getInstance().setUserCoins(15);
        } else if (rewardNumber > 71 && rewardNumber <= 80) {
            chestBoxOne.setImageResource(R.drawable.openchest2);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.cybercoins);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Coins x20");

            CurrentUserInformation.getInstance().setUserCoins(20);
        } else if (rewardNumber > 81 && rewardNumber <= 90) {
            chestBoxOne.setImageResource(R.drawable.openchest2);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.cybercoins);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Coins x25");

            CurrentUserInformation.getInstance().setUserCoins(25);
        } else {
            chestBoxOne.setImageResource(R.drawable.openchest2);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.cybercoins);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Coins x30");

            CurrentUserInformation.getInstance().setUserCoins(30);
        }

        rewardNumber = randomDrop.nextInt(100) + 1;

        if (rewardNumber == 1) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.bandana);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("The Bandana");

        } else if (rewardNumber == 2) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.captainhat);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Captain's Hat");
        } else if (rewardNumber == 3) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.crown);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("King's Crown");
        } else if (rewardNumber == 4) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.farmerhat);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Farmer's Hat");
        } else if (rewardNumber == 5) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.fedora);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("The Fedora");
        } else if (rewardNumber == 6) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.headphones);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Headphones");
        } else if (rewardNumber == 7) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.piratehat);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Pirate Hat");

        } else if (rewardNumber == 8) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.spacehelmet);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Space Helmet");

        } else if (rewardNumber == 9) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.tophat);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Top Hat");

        } else if (rewardNumber == 10) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.wizardhat);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Wizard's Hat");
        } else if (rewardNumber > 11 && rewardNumber <= 20) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.healthpotion);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("x1 H. Potion");
        } else if (rewardNumber > 21 && rewardNumber <= 30) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.revealingpotion);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("x1 R. Potion");
        } else if (rewardNumber > 31 && rewardNumber <= 40) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.cybercoins);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("x1 Fixer Elixer");
        } else if (rewardNumber > 41 && rewardNumber <= 50) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.cybercoins);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Coins x5");
        } else if (rewardNumber > 51 && rewardNumber <= 60) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.cybercoins);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Coins x10");
        } else if (rewardNumber > 61 && rewardNumber <= 70) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.cybercoins);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Coins x15");
        } else if (rewardNumber > 71 && rewardNumber <= 80) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.cybercoins);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Coins x20");
        } else if (rewardNumber > 81 && rewardNumber <= 90) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.cybercoins);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Coins x25");
        } else {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.cybercoins);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Coins x30");
        }

        rewardNumber = randomDrop.nextInt(100) + 1;

        if (rewardNumber == 1) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.bandana);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("The Bandana");

        } else if (rewardNumber == 2) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.captainhat);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Captain's Hat");
        } else if (rewardNumber == 3) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.crown);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("King's Crown");
        } else if (rewardNumber == 4) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.farmerhat);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Farmer's Hat");
        } else if (rewardNumber == 5) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.fedora);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("The Fedora");
        } else if (rewardNumber == 6) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.headphones);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Headphones");
        } else if (rewardNumber == 7) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.piratehat);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Pirate Hat");

        } else if (rewardNumber == 8) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.spacehelmet);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Space Helmet");

        } else if (rewardNumber == 9) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.tophat);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Top Hat");

        } else if (rewardNumber == 10) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.wizardhat);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Wizard's Hat");
        } else if (rewardNumber > 11 && rewardNumber <= 20) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.healthpotion);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("x1 H. Potion");
        } else if (rewardNumber > 21 && rewardNumber <= 30) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.revealingpotion);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("x1 R. Potion");
        } else if (rewardNumber > 31 && rewardNumber <= 40) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.cybercoins);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("x1 Fixer Elixer");
        } else if (rewardNumber > 41 && rewardNumber <= 50) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.cybercoins);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Coins x5");
        } else if (rewardNumber > 51 && rewardNumber <= 60) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.cybercoins);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Coins x10");
        } else if (rewardNumber > 61 && rewardNumber <= 70) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.cybercoins);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Coins x15");
        } else if (rewardNumber > 71 && rewardNumber <= 80) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.cybercoins);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Coins x20");
        } else if (rewardNumber > 81 && rewardNumber <= 90) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.cybercoins);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Coins x25");
        } else {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.cybercoins);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Coins x30");
        }
    }

    private void secondChestRewards() {
        continueToMapView.setVisibility(View.VISIBLE);

        Random randomDrop = new Random();

        rewardNumber = randomDrop.nextInt(100) + 1;

        if (rewardNumber == 1) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.bandana);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("The Bandana");
        } else if (rewardNumber == 2) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.captainhat);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Captain's Hat");
        } else if (rewardNumber == 3) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.crown);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("King's Crown");
        } else if (rewardNumber == 4) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.farmerhat);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Farmer's Hat");
        } else if (rewardNumber == 5) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.fedora);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("The Fedora");
        } else if (rewardNumber == 6) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.headphones);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Headphones");
        } else if (rewardNumber == 7) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.piratehat);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Pirate Hat");
        } else if (rewardNumber == 8) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.spacehelmet);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Space Helmet");
        } else if (rewardNumber == 9) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.tophat);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Top Hat");
        } else if (rewardNumber == 10) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.wizardhat);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Wizard's Hat");
        } else if (rewardNumber > 11 && rewardNumber <= 20) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.healthpotion);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("x1 H. Potion");
        } else if (rewardNumber > 21 && rewardNumber <= 30) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.revealingpotion);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("x1 R. Potion");
        } else if (rewardNumber > 31 && rewardNumber <= 40) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.fixerelixer);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("x1 Fixer Elixer");
        } else if (rewardNumber > 41 && rewardNumber <= 50) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.cybercoins);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Coins x5");
        } else if (rewardNumber > 51 && rewardNumber <= 60) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.cybercoins);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Coins x10");
        } else if (rewardNumber > 61 && rewardNumber <= 70) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.cybercoins);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Coins x15");
        } else if (rewardNumber > 71 && rewardNumber <= 80) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.cybercoins);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Coins x20");
        } else if (rewardNumber > 81 && rewardNumber <= 90) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.cybercoins);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Coins x25");
        } else {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.cybercoins);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Coins x30");
        }

        rewardNumber = randomDrop.nextInt(100) + 1;

        if (rewardNumber == 1) {
            chestBoxTwo.setImageResource(R.drawable.openchest2);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.bandana);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("The Bandana");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("NinjaHat");
        } else if (rewardNumber == 2) {
            chestBoxTwo.setImageResource(R.drawable.openchest2);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.captainhat);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Captain's Hat");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("CaptainHat");
        } else if (rewardNumber == 3) {
            chestBoxTwo.setImageResource(R.drawable.openchest2);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.crown);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("King's Crown");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("KingCrown");
        } else if (rewardNumber == 4) {
            chestBoxTwo.setImageResource(R.drawable.openchest2);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.farmerhat);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Farmer's Hat");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("FarmerHat");
        } else if (rewardNumber == 5) {
            chestBoxTwo.setImageResource(R.drawable.openchest2);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.fedora);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("The Fedora");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("CowboyHat");
        } else if (rewardNumber == 6) {
            chestBoxTwo.setImageResource(R.drawable.openchest2);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.headphones);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Headphones");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("Headphones");
        } else if (rewardNumber == 7) {
            chestBoxTwo.setImageResource(R.drawable.openchest2);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.piratehat);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Pirate Hat");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("PirateHat");

        } else if (rewardNumber == 8) {
            chestBoxTwo.setImageResource(R.drawable.openchest2);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.spacehelmet);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Space Helmet");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("SpaceHelmet");

        } else if (rewardNumber == 9) {
            chestBoxTwo.setImageResource(R.drawable.openchest2);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.tophat);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Top Hat");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("TopHat");

        } else if (rewardNumber == 10) {
            chestBoxTwo.setImageResource(R.drawable.openchest2);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.wizardhat);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Wizard's Hat");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("WizardHat");
        } else if (rewardNumber > 11 && rewardNumber <= 20) {
            chestBoxTwo.setImageResource(R.drawable.openchest2);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.healthpotion);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("x1 H. Potion");

            CurrentUserInformation.getInstance().updateConsumableQuantity("HealingPotion", 1);
        } else if (rewardNumber > 21 && rewardNumber <= 30) {
            chestBoxTwo.setImageResource(R.drawable.openchest2);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.revealingpotion);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("x1 R. Potion");

            CurrentUserInformation.getInstance().updateConsumableQuantity("RevealingPotion", 1);
        } else if (rewardNumber > 31 && rewardNumber <= 40) {
            chestBoxTwo.setImageResource(R.drawable.openchest2);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.cybercoins);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("x1 Fixer Elixer");

            CurrentUserInformation.getInstance().updateConsumableQuantity("FixerElixer", 1);
        } else if (rewardNumber > 41 && rewardNumber <= 50) {
            chestBoxTwo.setImageResource(R.drawable.openchest2);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.cybercoins);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Coins x5");

            CurrentUserInformation.getInstance().setUserCoins(5);
        } else if (rewardNumber > 51 && rewardNumber <= 60) {
            chestBoxTwo.setImageResource(R.drawable.openchest2);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.cybercoins);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Coins x10");

            CurrentUserInformation.getInstance().setUserCoins(10);
        } else if (rewardNumber > 61 && rewardNumber <= 70) {
            chestBoxTwo.setImageResource(R.drawable.openchest2);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.cybercoins);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Coins x15");

            CurrentUserInformation.getInstance().setUserCoins(15);
        } else if (rewardNumber > 71 && rewardNumber <= 80) {
            chestBoxTwo.setImageResource(R.drawable.openchest2);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.cybercoins);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Coins x20");

            CurrentUserInformation.getInstance().setUserCoins(20);
        } else if (rewardNumber > 81 && rewardNumber <= 90) {
            chestBoxTwo.setImageResource(R.drawable.openchest2);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.cybercoins);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Coins x25");

            CurrentUserInformation.getInstance().setUserCoins(25);
        } else {
            chestBoxTwo.setImageResource(R.drawable.openchest2);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.cybercoins);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Coins x30");

            CurrentUserInformation.getInstance().setUserCoins(30);
        }

        rewardNumber = randomDrop.nextInt(100) + 1;

        if (rewardNumber == 1) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.bandana);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("The Bandana");

        } else if (rewardNumber == 2) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.captainhat);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Captain's Hat");
        } else if (rewardNumber == 3) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.crown);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("King's Crown");
        } else if (rewardNumber == 4) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.farmerhat);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Farmer's Hat");
        } else if (rewardNumber == 5) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.fedora);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("The Fedora");
        } else if (rewardNumber == 6) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.headphones);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Headphones");
        } else if (rewardNumber == 7) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.piratehat);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Pirate Hat");

        } else if (rewardNumber == 8) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.spacehelmet);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Space Helmet");

        } else if (rewardNumber == 9) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.tophat);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Top Hat");

        } else if (rewardNumber == 10) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.wizardhat);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Wizard's Hat");
        } else if (rewardNumber > 11 && rewardNumber <= 20) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.healthpotion);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("x1 H. Potion");
        } else if (rewardNumber > 21 && rewardNumber <= 30) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.revealingpotion);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("x1 R. Potion");
        } else if (rewardNumber > 31 && rewardNumber <= 40) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.cybercoins);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("x1 Fixer Elixer");
        } else if (rewardNumber > 41 && rewardNumber <= 50) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.cybercoins);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Coins x5");
        } else if (rewardNumber > 51 && rewardNumber <= 60) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.cybercoins);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Coins x10");
        } else if (rewardNumber > 61 && rewardNumber <= 70) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.cybercoins);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Coins x15");
        } else if (rewardNumber > 71 && rewardNumber <= 80) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.cybercoins);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Coins x20");
        } else if (rewardNumber > 81 && rewardNumber <= 90) {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.cybercoins);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Coins x25");
        } else {
            chestBoxThree.setImageResource(R.drawable.openchest);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.cybercoins);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Coins x30");
        }
    }

    private void thirdChestRewards() {
        continueToMapView.setVisibility(View.VISIBLE);

        Random randomDrop = new Random();

        rewardNumber = randomDrop.nextInt(100) + 1;

        if (rewardNumber == 1) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.bandana);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("The Bandana");
        } else if (rewardNumber == 2) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.captainhat);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Captain's Hat");
        } else if (rewardNumber == 3) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.crown);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("King's Crown");
        } else if (rewardNumber == 4) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.farmerhat);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Farmer's Hat");
        } else if (rewardNumber == 5) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.fedora);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("The Fedora");
        } else if (rewardNumber == 6) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.headphones);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Headphones");
        } else if (rewardNumber == 7) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.piratehat);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Pirate Hat");
        } else if (rewardNumber == 8) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.spacehelmet);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Space Helmet");
        } else if (rewardNumber == 9) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.tophat);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Top Hat");
        } else if (rewardNumber == 10) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.wizardhat);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Wizard's Hat");
        } else if (rewardNumber > 11 && rewardNumber <= 20) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.healthpotion);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("x1 H. Potion");
        } else if (rewardNumber > 21 && rewardNumber <= 30) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.revealingpotion);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("x1 R. Potion");
        } else if (rewardNumber > 31 && rewardNumber <= 40) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.fixerelixer);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("x1 Fixer Elixer");
        } else if (rewardNumber > 41 && rewardNumber <= 50) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.cybercoins);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Coins x5");
        } else if (rewardNumber > 51 && rewardNumber <= 60) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.cybercoins);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Coins x10");
        } else if (rewardNumber > 61 && rewardNumber <= 70) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.cybercoins);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Coins x15");
        } else if (rewardNumber > 71 && rewardNumber <= 80) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.cybercoins);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Coins x20");
        } else if (rewardNumber > 81 && rewardNumber <= 90) {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.cybercoins);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Coins x25");
        } else {
            chestBoxOne.setImageResource(R.drawable.openchest);

            chestBoxOneReward.setVisibility(View.VISIBLE);

            chestBoxOneReward.setImageResource(R.drawable.cybercoins);

            chestBoxOneRewardText.setVisibility(View.VISIBLE);

            chestBoxOneRewardText.setText("Coins x30");
        }

        rewardNumber = randomDrop.nextInt(100) + 1;

        if (rewardNumber == 1) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.bandana);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("The Bandana");
        } else if (rewardNumber == 2) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.captainhat);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Captain's Hat");
        } else if (rewardNumber == 3) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.crown);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("King's Crown");
        } else if (rewardNumber == 4) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.farmerhat);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Farmer's Hat");
        } else if (rewardNumber == 5) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.fedora);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("The Fedora");
        } else if (rewardNumber == 6) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.headphones);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Headphones");
        } else if (rewardNumber == 7) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.piratehat);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Pirate Hat");

        } else if (rewardNumber == 8) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.spacehelmet);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Space Helmet");

        } else if (rewardNumber == 9) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.tophat);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Top Hat");

        } else if (rewardNumber == 10) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.wizardhat);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Wizard's Hat");
        } else if (rewardNumber > 11 && rewardNumber <= 20) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.healthpotion);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("x1 H. Potion");
        } else if (rewardNumber > 21 && rewardNumber <= 30) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.revealingpotion);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("x1 R. Potion");
        } else if (rewardNumber > 31 && rewardNumber <= 40) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.cybercoins);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("x1 Fixer Elixer");
        } else if (rewardNumber > 41 && rewardNumber <= 50) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.cybercoins);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Coins x5");
        } else if (rewardNumber > 51 && rewardNumber <= 60) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.cybercoins);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Coins x10");
        } else if (rewardNumber > 61 && rewardNumber <= 70) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.cybercoins);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Coins x15");
        } else if (rewardNumber > 71 && rewardNumber <= 80) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.cybercoins);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Coins x20");
        } else if (rewardNumber > 81 && rewardNumber <= 90) {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.cybercoins);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Coins x25");
        } else {
            chestBoxTwo.setImageResource(R.drawable.openchest);

            chestBoxTwoReward.setVisibility(View.VISIBLE);

            chestBoxTwoReward.setImageResource(R.drawable.cybercoins);

            chestBoxTwoRewardText.setVisibility(View.VISIBLE);

            chestBoxTwoRewardText.setText("Coins x30");
        }

        rewardNumber = randomDrop.nextInt(100) + 1;

        if (rewardNumber == 1) {
            chestBoxThree.setImageResource(R.drawable.openchest2);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.bandana);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("The Bandana");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("NinjaHat");

        } else if (rewardNumber == 2) {
            chestBoxThree.setImageResource(R.drawable.openchest2);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.captainhat);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Captain's Hat");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("CaptainHat");
        } else if (rewardNumber == 3) {
            chestBoxThree.setImageResource(R.drawable.openchest2);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.crown);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("King's Crown");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("KingCrown");
        } else if (rewardNumber == 4) {
            chestBoxThree.setImageResource(R.drawable.openchest2);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.farmerhat);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Farmer's Hat");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("FarmerHat");
        } else if (rewardNumber == 5) {
            chestBoxThree.setImageResource(R.drawable.openchest2);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.fedora);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("The Fedora");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("CowboyHat");
        } else if (rewardNumber == 6) {
            chestBoxThree.setImageResource(R.drawable.openchest2);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.headphones);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Headphones");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("Headphones");
        } else if (rewardNumber == 7) {
            chestBoxThree.setImageResource(R.drawable.openchest2);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.piratehat);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Pirate Hat");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("PirateHat");

        } else if (rewardNumber == 8) {
            chestBoxThree.setImageResource(R.drawable.openchest2);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.spacehelmet);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Space Helmet");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("SpaceHelmet");

        } else if (rewardNumber == 9) {
            chestBoxThree.setImageResource(R.drawable.openchest2);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.tophat);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Top Hat");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("TopHat");

        } else if (rewardNumber == 10) {
            chestBoxThree.setImageResource(R.drawable.openchest2);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.wizardhat);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Wizard's Hat");

            CurrentUserInformation.getInstance().unlockUserAppearanceItem("WizardHat");
        } else if (rewardNumber > 11 && rewardNumber <= 20) {
            chestBoxThree.setImageResource(R.drawable.openchest2);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.healthpotion);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("x1 H. Potion");

            CurrentUserInformation.getInstance().updateConsumableQuantity("HealingPotion", 1);
        } else if (rewardNumber > 21 && rewardNumber <= 30) {
            chestBoxThree.setImageResource(R.drawable.openchest2);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.revealingpotion);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("x1 R. Potion");

            CurrentUserInformation.getInstance().updateConsumableQuantity("RevealingPotion", 1);
        } else if (rewardNumber > 31 && rewardNumber <= 40) {
            chestBoxThree.setImageResource(R.drawable.openchest2);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.cybercoins);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("x1 Fixer Elixer");

            CurrentUserInformation.getInstance().updateConsumableQuantity("FixerElixer", 1);
        } else if (rewardNumber > 41 && rewardNumber <= 50) {
            chestBoxThree.setImageResource(R.drawable.openchest2);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.cybercoins);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Coins x5");

            CurrentUserInformation.getInstance().setUserCoins(5);
        } else if (rewardNumber > 51 && rewardNumber <= 60) {
            chestBoxThree.setImageResource(R.drawable.openchest2);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.cybercoins);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Coins x10");

            CurrentUserInformation.getInstance().setUserCoins(10);
        } else if (rewardNumber > 61 && rewardNumber <= 70) {
            chestBoxThree.setImageResource(R.drawable.openchest2);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.cybercoins);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Coins x15");

            CurrentUserInformation.getInstance().setUserCoins(15);
        } else if (rewardNumber > 71 && rewardNumber <= 80) {
            chestBoxThree.setImageResource(R.drawable.openchest2);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.cybercoins);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Coins x20");

            CurrentUserInformation.getInstance().setUserCoins(20);
        } else if (rewardNumber > 81 && rewardNumber <= 90) {
            chestBoxThree.setImageResource(R.drawable.openchest2);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.cybercoins);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Coins x25");

            CurrentUserInformation.getInstance().setUserCoins(25);
        } else {
            chestBoxThree.setImageResource(R.drawable.openchest2);

            chestBoxThreeReward.setVisibility(View.VISIBLE);

            chestBoxThreeReward.setImageResource(R.drawable.cybercoins);

            chestBoxThreeRewardText.setVisibility(View.VISIBLE);

            chestBoxThreeRewardText.setText("Coins x30");

            CurrentUserInformation.getInstance().setUserCoins(30);
        }
    }

    private void unlockSkills() {
        if (level.getLevel() == 2) {
            skillUnlocked.setText("Unlocked the int Skill!");

            CurrentUserInformation.getInstance().unlockUserSkill("int");
        } else if (level.getLevel() == 4) {
            skillUnlocked.setText("Unlocked the char Skill!");

            CurrentUserInformation.getInstance().unlockUserSkill("char");
        } else if (level.getLevel() == 7) {
            skillUnlocked.setText("Unlocked the float Skill!");

            CurrentUserInformation.getInstance().unlockUserSkill("float");
        } else if (level.getLevel() == 10) {
            skillUnlocked.setText("Unlocked the double Skill!");

            CurrentUserInformation.getInstance().unlockUserSkill("double");
        } else if (level.getLevel() == 13) {
            skillUnlocked.setText("Unlocked the string Skill!");

            CurrentUserInformation.getInstance().unlockUserSkill("string");
        } else if (level.getLevel() == 23) {
            skillUnlocked.setText("Unlocked the if Skill!");

            CurrentUserInformation.getInstance().unlockUserSkill("if");
        } else if (level.getLevel() == 25) {
            skillUnlocked.setText("Unlocked the else Skill!");

            CurrentUserInformation.getInstance().unlockUserSkill("else");
        } else if (level.getLevel() == 28) {
            skillUnlocked.setText("Unlocked the else if Skill!");

            CurrentUserInformation.getInstance().unlockUserSkill("else if");
        } else if (level.getLevel() == 44) {
            skillUnlocked.setText("Unlocked the while Skill!");

            CurrentUserInformation.getInstance().unlockUserSkill("while");
        } else if (level.getLevel() == 46) {
            skillUnlocked.setText("Unlocked the do Skill!");

            CurrentUserInformation.getInstance().unlockUserSkill("do");
        } else if (level.getLevel() == 65) {
            skillUnlocked.setText("Unlocked the for Skill!");

            CurrentUserInformation.getInstance().unlockUserSkill("for");
        } else if (level.getLevel() == 86) {
            skillUnlocked.setText("Unlocked the array Skill!");

            CurrentUserInformation.getInstance().unlockUserSkill("array");
        } else if (level.getLevel() == 95) {
            skillUnlocked.setText("Unlocked the 2dArray Skill!");

            CurrentUserInformation.getInstance().unlockUserSkill("2dArray");
        }
    }

    //Load content
    private void loadContentOnScreen() {
        middleScreenLayout = (LinearLayout) findViewById(R.id.middleScreenLayout);

        LinearLayout lineOfWords = new LinearLayout(getApplicationContext());

        lineOfWords.setPadding(15, 15, 0, 0);

        for (final ContentParser.ContentType contentType : listOfWords) {
            final TextView currentProcessedWord = new TextView(getApplicationContext());

            currentProcessedWord.setTypeface(consolasFont);

            currentProcessedWord.setTextSize(16);

            if (contentType.isKeyword()) {
                currentProcessedWord.setTextColor(getResources().getColor(R.color.colorAccent));

                currentProcessedWord.setText(contentType.wordContent);

                currentProcessedWord.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentTextViewClicked != null) {
                            if (currentTextViewClicked == currentProcessedWord) {
                                currentProcessedWord.setTextColor(getResources().getColor(R.color.colorAccent));
                                currentTextViewClicked = null;
                                currentContentTypeProcessed = null;
                            } else {
                                currentTextViewClicked.setTextColor(getResources().getColor(R.color.colorAccent));
                                currentTextViewClicked = currentProcessedWord;
                                currentContentTypeProcessed = contentType;
                                currentTextViewClicked.setTextColor(Color.parseColor("#FF4500"));
                            }
                        } else {

                            currentTextViewClicked = currentProcessedWord;
                            currentContentTypeProcessed = contentType;
                            currentTextViewClicked.setTextColor(Color.parseColor("#FF4500"));


                        }
                    }
                });

                currentProcessedWord.setPadding(0, 0, 20, 0);

                if (contentType.isSecretWord()) {
                    allErrorsInContent.add(currentProcessedWord);
                    errorsInContent.add(contentType);
                }

                lineOfWords.addView(currentProcessedWord);

            } else if (contentType.isRegularWord()) {
                currentProcessedWord.setTextColor(WHITE);

                currentProcessedWord.setText(contentType.wordContent);

                currentProcessedWord.setPadding(0, 0, 20, 0);

                lineOfWords.addView(currentProcessedWord);
            } else if (contentType.isNewLine()) {
                middleScreenLayout.addView(lineOfWords);

                lineOfWords = new LinearLayout(getApplicationContext());

                lineOfWords.setPadding(15, 15, 0, 0);
            }
        }

    }

    //Setup bars

    private void setUpSkillBar() {
        setUpMainMenuBarLayout();
        setUpMainSkillBarLayout();
        setUpVariablesSkillBarLayout();
        setUpConditionalsSkillBarLayout();
        setUpLoopsSkillBarLayout();
        setUpItemsSkillBarLayout();
    }

    private void setUpMainMenuBarLayout() {
        mainMenuBarLayout = (LinearLayout) findViewById(R.id.mainMenuBarLayout);
        mainMenuBarLayout.setVisibility(View.VISIBLE);

        setOpenSkillsBarButtonOnClickListener();
        setOpenItemsBarButtonOnClickListener();
        setOpenNotebookButtonOnClickListener();
        setRunAwayButtonOnClickListener();
    }

    private void setUpMainSkillBarLayout() {
        mainSkillBarLayout = (LinearLayout) findViewById(R.id.mainSkillBarLayout);
        mainSkillBarLayout.setVisibility(View.GONE);

        setOpenVariablesBarButtonOnClickListener();
        setOpenConditionalsBarButtonOnClickListener();
        setOpenLoopsBarButtonOnClickListener();
        setGoBackToMainMenuButtonFromSkillsBaOnClickListener();
    }

    private void setUpVariablesSkillBarLayout() {
        variablesSkillBarLayout = (LinearLayout) findViewById(R.id.variablesSkillBarLayout);
        variablesSkillBarLayout.setVisibility(View.GONE);

        setIntButtonOnClickListener();
        setCharButtonOnClickListener();
        setFloatButtonOnClickListener();
        setDoubleButtonOnClickListener();
        setStringButtonOnClickListener();
        setToArrayButtonOnClickListener();
        setTo2dArrayButtonOnClickListener();
        setGoBackToSkillBarButtonFromVariablesOnClickListener();
    }

    private void setUpConditionalsSkillBarLayout() {
        conditionalsSkillBarLayout = (LinearLayout) findViewById(R.id.conditionalsSkillBarLayout);
        conditionalsSkillBarLayout.setVisibility(View.GONE);

        setIfButtonOnClickListener();
        setElseButtonOnClickListener();
        setElseIfButtonOnClickListener();
        setGoBackToSkillBarButtonFromConditionals();
    }

    private void setUpLoopsSkillBarLayout() {
        loopsSkillBarLayout = (LinearLayout) findViewById(R.id.loopsSkillBarLayout);
        loopsSkillBarLayout.setVisibility(View.GONE);

        setWhileButtonOnClickListener();
        setDoButtonOnClickListener();
        setForButtonOnClickListener();
        setGoBackToSkillBarButtonFromLoops();
    }

    private void setUpItemsSkillBarLayout() {
        itemsSkillBarLayout = (LinearLayout) findViewById(R.id.itemsSkillBarLayout);
        itemsSkillBarLayout.setVisibility(View.GONE);

        setUseHealthPotionButtonOnClickListener();
        setUseRevealingPotionButtonOnClickListener();
        setUseFixerElixirButtonOnClickListener();
        setGoBackToMainMenuButtonFromItemsBar();
    }


    //Main menu buttons on click listeners; openSkillsBarButton, openItemsBarButton, openNotebookButton, runAwayButton
    private void setOpenSkillsBarButtonOnClickListener() {
        openSkillsBarButton = (TextView) findViewById(R.id.openSkillsBarButton);

        openSkillsBarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainMenuBarLayout.setVisibility(View.GONE);
                mainSkillBarLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setOpenItemsBarButtonOnClickListener() {
        openItemsBarButton = (TextView) findViewById(R.id.openItemsBarButton);
        openItemsBarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainMenuBarLayout.setVisibility(View.GONE);
                itemsSkillBarLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setOpenNotebookButtonOnClickListener() {
        openNotebookButton = (TextView) findViewById(R.id.openNotebookButton);
        openNotebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Notebook not yet implemented!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRunAwayButtonOnClickListener() {
        runAwayButton = (TextView) findViewById(R.id.runAwayButton);
        runAwayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //Main skills bar menu buttons on click listeners; openVariablesBarButton, openConditionalsBarButton, openLoopsBarButton, goBackToMainMenuButtonFromSkillsBar;
    private void setOpenVariablesBarButtonOnClickListener() {
        openVariablesBarButton = (TextView) findViewById(R.id.openVariablesBarButton);
        openVariablesBarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                variablesSkillBarLayout.setVisibility(View.VISIBLE);
                mainSkillBarLayout.setVisibility(View.GONE);
            }
        });
    }

    private void setOpenConditionalsBarButtonOnClickListener() {
        openConditionalsBarButton = (TextView) findViewById(R.id.openConditionalsBarButton);
        openConditionalsBarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conditionalsSkillBarLayout.setVisibility(View.VISIBLE);
                mainSkillBarLayout.setVisibility(View.GONE);
            }
        });
    }

    private void setOpenLoopsBarButtonOnClickListener() {
        openLoopsBarButton = (TextView) findViewById(R.id.openLoopsBarButton);
        openLoopsBarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loopsSkillBarLayout.setVisibility(View.VISIBLE);
                mainSkillBarLayout.setVisibility(View.GONE);
            }
        });
    }

    private void setGoBackToMainMenuButtonFromSkillsBaOnClickListener() {
        goBackToMainMenuButtonFromSkillsBar = (TextView) findViewById(R.id.goBackToMainMenuButtonFromSkillsBar);
        goBackToMainMenuButtonFromSkillsBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainSkillBarLayout.setVisibility(View.GONE);
                mainMenuBarLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    //Variables bar menu buttons on click listener;  intButton, charButton, floatButton, doubleButton, stringButton, toArrayButton, to2dArrayButton, goBackToSkillBarButtonFromVariables;

    private void setIntButtonOnClickListener() {
        intButton = (TextView) findViewById(R.id.intButton);

        if (CurrentUserInformation.getInstance().userSkillsCollection.get("int").getUnlocked()) {
            intButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentTextViewClicked != null) {
                        if (currentContentTypeProcessed.isSecretWord()) {
                            if (currentContentTypeProcessed.correctWord.equals("int")) {
                                chiuplusattackanimation.setImageResource(R.drawable.chiuplusattack);
                                chiuplusattackanimation.clearAnimation();


                                chiuplusattackanimation.setVisibility(View.VISIBLE);

                                allErrorsInContent.remove(currentTextViewClicked);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        chiuplusattackanimation.setVisibility(View.GONE);
                                        chiuplusattackanimation.clearAnimation();

                                        gameScreenEnemyHealth.setText("HP  " + allErrorsInContent.size() + "/" + enemyMaxHealth);

                                        if(allErrorsInContent.size()==0){
                                            endGameScreenVictory();
                                        }
                                    }
                                }, 2300);

                                currentTextViewClicked.setText("int");
                                currentTextViewClicked.setTextColor(Color.parseColor("#8FBC8F"));

                                currentTextViewClicked = null;
                                currentContentTypeProcessed = null;
                            } else {
                                enemyattackanimation.setImageResource(R.drawable.enemyattack);
                                enemyattackanimation.clearAnimation();

                                CurrentUserInformation.getInstance().increaseUserHealth(-1);

                                enemyattackanimation.setVisibility(View.VISIBLE);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        enemyattackanimation.setVisibility(View.GONE);
                                        enemyattackanimation.clearAnimation();

                                        gameScreenPlayerHealth.setText("HP  " + String.format(CurrentUserInformation.getInstance().getUserHealth().toString()) + "/" + String.format(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));

                                        if(CurrentUserInformation.getInstance().getUserHealth() == 0){
                                            endGameScreenDefeat();
                                        }
                                    }
                                }, 2300);

                                currentTextViewClicked.setTextColor(getResources().getColor(R.color.colorAccent));
                                currentTextViewClicked = null;
                                currentContentTypeProcessed = null;
                            }
                        } else {
                            enemyattackanimation.setImageResource(R.drawable.enemyattack);
                            enemyattackanimation.clearAnimation();

                            CurrentUserInformation.getInstance().increaseUserHealth(-1);

                            enemyattackanimation.setVisibility(View.VISIBLE);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    enemyattackanimation.setVisibility(View.GONE);
                                    enemyattackanimation.clearAnimation();

                                    gameScreenPlayerHealth.setText("HP  " + String.format(CurrentUserInformation.getInstance().getUserHealth().toString()) + "/" + String.format(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));

                                    if(CurrentUserInformation.getInstance().getUserHealth() == 0){
                                        endGameScreenDefeat();
                                    }
                                }
                            }, 2300);

                            currentTextViewClicked.setTextColor(getResources().getColor(R.color.colorAccent));
                            currentTextViewClicked = null;
                            currentContentTypeProcessed = null;
                        }
                    }
                }
            });
        } else {
            intButton.setText("Locked");

            intButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "This skill will be unlocked at level " + CurrentUserInformation.getInstance().userSkillsCollection.get("int").getUnlockableAtLevel().toString() + "!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setCharButtonOnClickListener() {
        charButton = (TextView) findViewById(R.id.charButton);

        if (CurrentUserInformation.getInstance().userSkillsCollection.get("char").getUnlocked()) {
            charButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentTextViewClicked != null) {
                        if (currentContentTypeProcessed.isSecretWord()) {
                            if (currentContentTypeProcessed.correctWord.equals("char")) {
                                chiuplusattackanimation.setImageResource(R.drawable.chiuplusattack);
                                chiuplusattackanimation.clearAnimation();


                                chiuplusattackanimation.setVisibility(View.VISIBLE);

                                allErrorsInContent.remove(currentTextViewClicked);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        chiuplusattackanimation.setVisibility(View.GONE);
                                        chiuplusattackanimation.clearAnimation();

                                        gameScreenEnemyHealth.setText("HP  " + allErrorsInContent.size() + "/" + enemyMaxHealth);

                                        if(allErrorsInContent.size()==0){
                                            endGameScreenVictory();
                                        }
                                    }
                                }, 2300);

                                currentTextViewClicked.setText("char");
                                currentTextViewClicked.setTextColor(Color.parseColor("#8FBC8F"));

                                currentTextViewClicked = null;
                                currentContentTypeProcessed = null;
                            } else {
                                enemyattackanimation.setImageResource(R.drawable.enemyattack);
                                enemyattackanimation.clearAnimation();

                                CurrentUserInformation.getInstance().increaseUserHealth(-1);

                                enemyattackanimation.setVisibility(View.VISIBLE);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        enemyattackanimation.setVisibility(View.GONE);
                                        enemyattackanimation.clearAnimation();

                                        gameScreenPlayerHealth.setText("HP  " + String.format(CurrentUserInformation.getInstance().getUserHealth().toString()) + "/" + String.format(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));

                                        if(CurrentUserInformation.getInstance().getUserHealth() == 0){
                                            endGameScreenDefeat();
                                        }
                                    }
                                }, 2300);

                                currentTextViewClicked.setTextColor(getResources().getColor(R.color.colorAccent));
                                currentTextViewClicked = null;
                                currentContentTypeProcessed = null;
                            }
                        } else {
                            enemyattackanimation.setImageResource(R.drawable.enemyattack);
                            enemyattackanimation.clearAnimation();

                            CurrentUserInformation.getInstance().increaseUserHealth(-1);

                            enemyattackanimation.setVisibility(View.VISIBLE);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    enemyattackanimation.setVisibility(View.GONE);
                                    enemyattackanimation.clearAnimation();

                                    gameScreenPlayerHealth.setText("HP  " + String.format(CurrentUserInformation.getInstance().getUserHealth().toString()) + "/" + String.format(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));

                                    if(CurrentUserInformation.getInstance().getUserHealth() == 0){
                                        endGameScreenDefeat();
                                    }
                                }
                            }, 2300);

                            currentTextViewClicked.setTextColor(getResources().getColor(R.color.colorAccent));
                            currentTextViewClicked = null;
                            currentContentTypeProcessed = null;
                        }
                    }
                }
            });
        } else {
            charButton.setText("Locked");

            charButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "This skill will be unlocked at level " + CurrentUserInformation.getInstance().userSkillsCollection.get("char").getUnlockableAtLevel().toString() + "!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setFloatButtonOnClickListener() {
        floatButton = (TextView) findViewById(R.id.floatButton);

        if (CurrentUserInformation.getInstance().userSkillsCollection.get("float").getUnlocked()) {
            floatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentTextViewClicked != null) {
                        if (currentContentTypeProcessed.isSecretWord()) {
                            if (currentContentTypeProcessed.correctWord.equals("float")) {
                                chiuplusattackanimation.setImageResource(R.drawable.chiuplusattack);
                                chiuplusattackanimation.clearAnimation();


                                chiuplusattackanimation.setVisibility(View.VISIBLE);

                                allErrorsInContent.remove(currentTextViewClicked);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        chiuplusattackanimation.setVisibility(View.GONE);
                                        chiuplusattackanimation.clearAnimation();

                                        gameScreenEnemyHealth.setText("HP  " + allErrorsInContent.size() + "/" + enemyMaxHealth);

                                        if(allErrorsInContent.size()==0){
                                            endGameScreenVictory();
                                        }
                                    }
                                }, 2300);

                                currentTextViewClicked.setText("float");
                                currentTextViewClicked.setTextColor(Color.parseColor("#8FBC8F"));

                                currentTextViewClicked = null;
                                currentContentTypeProcessed = null;
                            } else {
                                enemyattackanimation.setImageResource(R.drawable.enemyattack);
                                enemyattackanimation.clearAnimation();

                                CurrentUserInformation.getInstance().increaseUserHealth(-1);

                                enemyattackanimation.setVisibility(View.VISIBLE);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        enemyattackanimation.setVisibility(View.GONE);
                                        enemyattackanimation.clearAnimation();

                                        gameScreenPlayerHealth.setText("HP  " + String.format(CurrentUserInformation.getInstance().getUserHealth().toString()) + "/" + String.format(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));

                                        if(CurrentUserInformation.getInstance().getUserHealth() == 0){
                                            endGameScreenDefeat();
                                        }
                                    }
                                }, 2300);

                                currentTextViewClicked.setTextColor(getResources().getColor(R.color.colorAccent));
                                currentTextViewClicked = null;
                                currentContentTypeProcessed = null;
                            }
                        } else {
                            enemyattackanimation.setImageResource(R.drawable.enemyattack);
                            enemyattackanimation.clearAnimation();

                            CurrentUserInformation.getInstance().increaseUserHealth(-1);

                            enemyattackanimation.setVisibility(View.VISIBLE);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    enemyattackanimation.setVisibility(View.GONE);
                                    enemyattackanimation.clearAnimation();

                                    gameScreenPlayerHealth.setText("HP  " + String.format(CurrentUserInformation.getInstance().getUserHealth().toString()) + "/" + String.format(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));

                                    if(CurrentUserInformation.getInstance().getUserHealth() == 0){
                                        endGameScreenDefeat();
                                    }
                                }
                            }, 2300);

                            currentTextViewClicked.setTextColor(getResources().getColor(R.color.colorAccent));
                            currentTextViewClicked = null;
                            currentContentTypeProcessed = null;
                        }
                    }
                }
            });
        } else {
            floatButton.setText("Locked");

            floatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "This skill will be unlocked at level " + CurrentUserInformation.getInstance().userSkillsCollection.get("float").getUnlockableAtLevel().toString() + "!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setDoubleButtonOnClickListener() {
        doubleButton = (TextView) findViewById(R.id.doubleButton);

        if (CurrentUserInformation.getInstance().userSkillsCollection.get("double").getUnlocked()) {
            doubleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentTextViewClicked != null) {
                        if (currentContentTypeProcessed.isSecretWord()) {
                            if (currentContentTypeProcessed.correctWord.equals("double")) {
                                chiuplusattackanimation.setImageResource(R.drawable.chiuplusattack);
                                chiuplusattackanimation.clearAnimation();


                                chiuplusattackanimation.setVisibility(View.VISIBLE);

                                allErrorsInContent.remove(currentTextViewClicked);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        chiuplusattackanimation.setVisibility(View.GONE);
                                        chiuplusattackanimation.clearAnimation();

                                        gameScreenEnemyHealth.setText("HP  " + allErrorsInContent.size() + "/" + enemyMaxHealth);

                                        if(allErrorsInContent.size()==0){
                                            endGameScreenVictory();
                                        }
                                    }
                                }, 2300);

                                currentTextViewClicked.setText("double");
                                currentTextViewClicked.setTextColor(Color.parseColor("#8FBC8F"));

                                currentTextViewClicked = null;
                                currentContentTypeProcessed = null;
                            } else {
                                enemyattackanimation.setImageResource(R.drawable.enemyattack);
                                enemyattackanimation.clearAnimation();

                                CurrentUserInformation.getInstance().increaseUserHealth(-1);

                                enemyattackanimation.setVisibility(View.VISIBLE);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        enemyattackanimation.setVisibility(View.GONE);
                                        enemyattackanimation.clearAnimation();

                                        gameScreenPlayerHealth.setText("HP  " + String.format(CurrentUserInformation.getInstance().getUserHealth().toString()) + "/" + String.format(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));

                                        if(CurrentUserInformation.getInstance().getUserHealth() == 0){
                                            endGameScreenDefeat();
                                        }
                                    }
                                }, 2300);

                                currentTextViewClicked.setTextColor(getResources().getColor(R.color.colorAccent));
                                currentTextViewClicked = null;
                                currentContentTypeProcessed = null;
                            }
                        } else {
                            enemyattackanimation.setImageResource(R.drawable.enemyattack);
                            enemyattackanimation.clearAnimation();

                            CurrentUserInformation.getInstance().increaseUserHealth(-1);

                            enemyattackanimation.setVisibility(View.VISIBLE);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    enemyattackanimation.setVisibility(View.GONE);
                                    enemyattackanimation.clearAnimation();

                                    gameScreenPlayerHealth.setText("HP  " + String.format(CurrentUserInformation.getInstance().getUserHealth().toString()) + "/" + String.format(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));

                                    if(CurrentUserInformation.getInstance().getUserHealth() == 0){
                                        endGameScreenDefeat();
                                    }
                                }
                            }, 2300);

                            currentTextViewClicked.setTextColor(getResources().getColor(R.color.colorAccent));
                            currentTextViewClicked = null;
                            currentContentTypeProcessed = null;
                        }
                    }
                }
            });
        } else {
            doubleButton.setText("Locked");

            doubleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "This skill will be unlocked at level " + CurrentUserInformation.getInstance().userSkillsCollection.get("double").getUnlockableAtLevel().toString() + "!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setStringButtonOnClickListener() {
        stringButton = (TextView) findViewById(R.id.stringButton);

        if (CurrentUserInformation.getInstance().userSkillsCollection.get("string").getUnlocked()) {
            stringButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentTextViewClicked != null) {
                        if (currentContentTypeProcessed.isSecretWord()) {
                            if (currentContentTypeProcessed.correctWord.equals("string")) {
                                chiuplusattackanimation.setImageResource(R.drawable.chiuplusattack);
                                chiuplusattackanimation.clearAnimation();


                                chiuplusattackanimation.setVisibility(View.VISIBLE);

                                allErrorsInContent.remove(currentTextViewClicked);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        chiuplusattackanimation.setVisibility(View.GONE);
                                        chiuplusattackanimation.clearAnimation();

                                        gameScreenEnemyHealth.setText("HP  " + allErrorsInContent.size() + "/" + enemyMaxHealth);

                                        if(allErrorsInContent.size()==0){
                                            endGameScreenVictory();
                                        }
                                    }
                                }, 2300);

                                currentTextViewClicked.setText("string");
                                currentTextViewClicked.setTextColor(Color.parseColor("#8FBC8F"));

                                currentTextViewClicked = null;
                                currentContentTypeProcessed = null;
                            } else {
                                enemyattackanimation.setImageResource(R.drawable.enemyattack);
                                enemyattackanimation.clearAnimation();

                                CurrentUserInformation.getInstance().increaseUserHealth(-1);

                                enemyattackanimation.setVisibility(View.VISIBLE);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        enemyattackanimation.setVisibility(View.GONE);
                                        enemyattackanimation.clearAnimation();

                                        gameScreenPlayerHealth.setText("HP  " + String.format(CurrentUserInformation.getInstance().getUserHealth().toString()) + "/" + String.format(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));

                                        if(CurrentUserInformation.getInstance().getUserHealth() == 0){
                                            endGameScreenDefeat();
                                        }
                                    }
                                }, 2300);

                                currentTextViewClicked.setTextColor(getResources().getColor(R.color.colorAccent));
                                currentTextViewClicked = null;
                                currentContentTypeProcessed = null;
                            }
                        } else {
                            enemyattackanimation.setImageResource(R.drawable.enemyattack);
                            enemyattackanimation.clearAnimation();

                            CurrentUserInformation.getInstance().increaseUserHealth(-1);

                            enemyattackanimation.setVisibility(View.VISIBLE);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    enemyattackanimation.setVisibility(View.GONE);
                                    enemyattackanimation.clearAnimation();

                                    gameScreenPlayerHealth.setText("HP  " + String.format(CurrentUserInformation.getInstance().getUserHealth().toString()) + "/" + String.format(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));

                                    if(CurrentUserInformation.getInstance().getUserHealth() == 0){
                                        endGameScreenDefeat();
                                    }
                                }
                            }, 2300);

                            currentTextViewClicked.setTextColor(getResources().getColor(R.color.colorAccent));
                            currentTextViewClicked = null;
                            currentContentTypeProcessed = null;
                        }
                    }
                }
            });
        } else {
            stringButton.setText("Locked");

            stringButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "This skill will be unlocked at level " + CurrentUserInformation.getInstance().userSkillsCollection.get("string").getUnlockableAtLevel().toString() + "!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setToArrayButtonOnClickListener() {
        toArrayButton = (TextView) findViewById(R.id.toArrayButton);

        if (CurrentUserInformation.getInstance().userSkillsCollection.get("array").getUnlocked()) {
            toArrayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentTextViewClicked != null) {
                        if (currentContentTypeProcessed.isSecretWord()) {
                            if (currentContentTypeProcessed.correctWord.equals("array")) {
                                chiuplusattackanimation.setImageResource(R.drawable.chiuplusattack);
                                chiuplusattackanimation.clearAnimation();


                                chiuplusattackanimation.setVisibility(View.VISIBLE);

                                allErrorsInContent.remove(currentTextViewClicked);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        chiuplusattackanimation.setVisibility(View.GONE);
                                        chiuplusattackanimation.clearAnimation();

                                        gameScreenEnemyHealth.setText("HP  " + allErrorsInContent.size() + "/" + enemyMaxHealth);

                                        if(allErrorsInContent.size()==0){
                                            endGameScreenVictory();
                                        }
                                    }
                                }, 2300);

                                currentTextViewClicked.setText(currentContentTypeProcessed.wordContent + "[]");
                                currentTextViewClicked.setTextColor(Color.parseColor("#8FBC8F"));

                                currentTextViewClicked = null;
                                currentContentTypeProcessed = null;
                            } else {
                                enemyattackanimation.setImageResource(R.drawable.enemyattack);
                                enemyattackanimation.clearAnimation();

                                CurrentUserInformation.getInstance().increaseUserHealth(-1);

                                enemyattackanimation.setVisibility(View.VISIBLE);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        enemyattackanimation.setVisibility(View.GONE);
                                        enemyattackanimation.clearAnimation();

                                        gameScreenPlayerHealth.setText("HP  " + String.format(CurrentUserInformation.getInstance().getUserHealth().toString()) + "/" + String.format(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));

                                        if(CurrentUserInformation.getInstance().getUserHealth() == 0){
                                            endGameScreenDefeat();
                                        }
                                    }
                                }, 2300);

                                currentTextViewClicked.setTextColor(getResources().getColor(R.color.colorAccent));
                                currentTextViewClicked = null;
                                currentContentTypeProcessed = null;
                            }
                        } else {
                            enemyattackanimation.setImageResource(R.drawable.enemyattack);
                            enemyattackanimation.clearAnimation();

                            CurrentUserInformation.getInstance().increaseUserHealth(-1);

                            enemyattackanimation.setVisibility(View.VISIBLE);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    enemyattackanimation.setVisibility(View.GONE);
                                    enemyattackanimation.clearAnimation();

                                    gameScreenPlayerHealth.setText("HP  " + String.format(CurrentUserInformation.getInstance().getUserHealth().toString()) + "/" + String.format(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));

                                    if(CurrentUserInformation.getInstance().getUserHealth() == 0){
                                        endGameScreenDefeat();
                                    }
                                }
                            }, 2300);

                            currentTextViewClicked.setTextColor(getResources().getColor(R.color.colorAccent));
                            currentTextViewClicked = null;
                            currentContentTypeProcessed = null;
                        }
                    }
                }
            });
        } else {
            toArrayButton.setText("Locked");

            toArrayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "This skill will be unlocked at level " + CurrentUserInformation.getInstance().userSkillsCollection.get("array").getUnlockableAtLevel().toString() + "!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setTo2dArrayButtonOnClickListener() {
        to2dArrayButton = (TextView) findViewById(R.id.to2dArrayButton);

        if (CurrentUserInformation.getInstance().userSkillsCollection.get("2dArray").getUnlocked()) {
            to2dArrayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentTextViewClicked != null) {
                        if (currentContentTypeProcessed.isSecretWord()) {
                            if (currentContentTypeProcessed.correctWord.equals("2dArray")) {
                                chiuplusattackanimation.setImageResource(R.drawable.chiuplusattack);
                                chiuplusattackanimation.clearAnimation();


                                chiuplusattackanimation.setVisibility(View.VISIBLE);

                                allErrorsInContent.remove(currentTextViewClicked);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        chiuplusattackanimation.setVisibility(View.GONE);
                                        chiuplusattackanimation.clearAnimation();

                                        gameScreenEnemyHealth.setText("HP  " + allErrorsInContent.size() + "/" + enemyMaxHealth);

                                        if(allErrorsInContent.size()==0){
                                            endGameScreenVictory();
                                        }
                                    }
                                }, 2300);

                                currentTextViewClicked.setText(currentContentTypeProcessed.wordContent+"[][]");
                                currentTextViewClicked.setTextColor(Color.parseColor("#8FBC8F"));

                                currentTextViewClicked = null;
                                currentContentTypeProcessed = null;
                            } else {
                                enemyattackanimation.setImageResource(R.drawable.enemyattack);
                                enemyattackanimation.clearAnimation();

                                CurrentUserInformation.getInstance().increaseUserHealth(-1);

                                enemyattackanimation.setVisibility(View.VISIBLE);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        enemyattackanimation.setVisibility(View.GONE);
                                        enemyattackanimation.clearAnimation();

                                        gameScreenPlayerHealth.setText("HP  " + String.format(CurrentUserInformation.getInstance().getUserHealth().toString()) + "/" + String.format(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));

                                        if(CurrentUserInformation.getInstance().getUserHealth() == 0){
                                            endGameScreenDefeat();
                                        }
                                    }
                                }, 2300);

                                currentTextViewClicked.setTextColor(getResources().getColor(R.color.colorAccent));
                                currentTextViewClicked = null;
                                currentContentTypeProcessed = null;
                            }
                        } else {
                            enemyattackanimation.setImageResource(R.drawable.enemyattack);
                            enemyattackanimation.clearAnimation();

                            CurrentUserInformation.getInstance().increaseUserHealth(-1);

                            enemyattackanimation.setVisibility(View.VISIBLE);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    enemyattackanimation.setVisibility(View.GONE);
                                    enemyattackanimation.clearAnimation();

                                    gameScreenPlayerHealth.setText("HP  " + String.format(CurrentUserInformation.getInstance().getUserHealth().toString()) + "/" + String.format(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));

                                    if(CurrentUserInformation.getInstance().getUserHealth() == 0){
                                        endGameScreenDefeat();
                                    }
                                }
                            }, 2300);

                            currentTextViewClicked.setTextColor(getResources().getColor(R.color.colorAccent));
                            currentTextViewClicked = null;
                            currentContentTypeProcessed = null;
                        }
                    }
                }
            });
        } else {
            to2dArrayButton.setText("Locked");

            to2dArrayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "This skill will be unlocked at level " + CurrentUserInformation.getInstance().userSkillsCollection.get("2dArray").getUnlockableAtLevel().toString() + "!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setGoBackToSkillBarButtonFromVariablesOnClickListener() {
        goBackToSkillBarButtonFromVariables = (TextView) findViewById(R.id.goBackToSkillBarButtonFromVariables);
        goBackToSkillBarButtonFromVariables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                variablesSkillBarLayout.setVisibility(View.GONE);
                mainSkillBarLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    //Conditionals bar menu buttons on click listener; ifButton, elseButton, elseIfButton, goBackToSkillBarButtonFromConditionals;
    private void setIfButtonOnClickListener() {
        ifButton = (TextView) findViewById(R.id.ifButton);

        if (CurrentUserInformation.getInstance().userSkillsCollection.get("if").getUnlocked()) {
            ifButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentTextViewClicked != null) {
                        if (currentContentTypeProcessed.isSecretWord()) {
                            if (currentContentTypeProcessed.correctWord.equals("if")) {
                                chiuplusattackanimation.setImageResource(R.drawable.chiuplusattack);
                                chiuplusattackanimation.clearAnimation();


                                chiuplusattackanimation.setVisibility(View.VISIBLE);

                                allErrorsInContent.remove(currentTextViewClicked);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        chiuplusattackanimation.setVisibility(View.GONE);
                                        chiuplusattackanimation.clearAnimation();

                                        gameScreenEnemyName.setText("HP  " + allErrorsInContent.size() + "/" + enemyMaxHealth);

                                        if(allErrorsInContent.size()==0){
                                            endGameScreenVictory();
                                        }
                                    }
                                }, 2300);

                                currentTextViewClicked.setText("if");
                                currentTextViewClicked.setTextColor(Color.parseColor("#8FBC8F"));

                                currentTextViewClicked = null;
                                currentContentTypeProcessed = null;
                            } else {
                                enemyattackanimation.setImageResource(R.drawable.enemyattack);
                                enemyattackanimation.clearAnimation();

                                CurrentUserInformation.getInstance().increaseUserHealth(-1);

                                enemyattackanimation.setVisibility(View.VISIBLE);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        enemyattackanimation.setVisibility(View.GONE);
                                        enemyattackanimation.clearAnimation();

                                        gameScreenEnemyHealth.setText("HP  " + String.format(CurrentUserInformation.getInstance().getUserHealth().toString()) + "/" + String.format(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));

                                        if(CurrentUserInformation.getInstance().getUserHealth() == 0){
                                            endGameScreenDefeat();
                                        }
                                    }
                                }, 2300);

                                currentTextViewClicked.setTextColor(getResources().getColor(R.color.colorAccent));
                                currentTextViewClicked = null;
                                currentContentTypeProcessed = null;
                            }
                        } else {
                            enemyattackanimation.setImageResource(R.drawable.enemyattack);
                            enemyattackanimation.clearAnimation();

                            CurrentUserInformation.getInstance().increaseUserHealth(-1);

                            enemyattackanimation.setVisibility(View.VISIBLE);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    enemyattackanimation.setVisibility(View.GONE);
                                    enemyattackanimation.clearAnimation();

                                    gameScreenPlayerHealth.setText("HP  " + String.format(CurrentUserInformation.getInstance().getUserHealth().toString()) + "/" + String.format(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));

                                    if(CurrentUserInformation.getInstance().getUserHealth() == 0){
                                        endGameScreenDefeat();
                                    }
                                }
                            }, 2300);

                            currentTextViewClicked.setTextColor(getResources().getColor(R.color.colorAccent));
                            currentTextViewClicked = null;
                            currentContentTypeProcessed = null;
                        }
                    }
                }
            });
        } else {
            ifButton.setText("Locked");

            ifButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "This skill will be unlocked at level " + CurrentUserInformation.getInstance().userSkillsCollection.get("if").getUnlockableAtLevel().toString() + "!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setElseButtonOnClickListener() {
        elseButton = (TextView) findViewById(R.id.elseButton);

        if (CurrentUserInformation.getInstance().userSkillsCollection.get("else").getUnlocked()) {
            elseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentTextViewClicked != null) {
                        if (currentContentTypeProcessed.isSecretWord()) {
                            if (currentContentTypeProcessed.correctWord.equals("else")) {
                                chiuplusattackanimation.setImageResource(R.drawable.chiuplusattack);
                                chiuplusattackanimation.clearAnimation();


                                chiuplusattackanimation.setVisibility(View.VISIBLE);

                                allErrorsInContent.remove(currentTextViewClicked);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        chiuplusattackanimation.setVisibility(View.GONE);
                                        chiuplusattackanimation.clearAnimation();

                                        gameScreenEnemyHealth.setText("HP  " + allErrorsInContent.size() + "/" + enemyMaxHealth);

                                        if(allErrorsInContent.size()==0){
                                            endGameScreenVictory();
                                        }
                                    }
                                }, 2300);

                                currentTextViewClicked.setText("else");
                                currentTextViewClicked.setTextColor(Color.parseColor("#8FBC8F"));

                                currentTextViewClicked = null;
                                currentContentTypeProcessed = null;
                            } else {
                                enemyattackanimation.setImageResource(R.drawable.enemyattack);
                                enemyattackanimation.clearAnimation();

                                CurrentUserInformation.getInstance().increaseUserHealth(-1);

                                enemyattackanimation.setVisibility(View.VISIBLE);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        enemyattackanimation.setVisibility(View.GONE);
                                        enemyattackanimation.clearAnimation();

                                        gameScreenPlayerHealth.setText("HP  " + String.format(CurrentUserInformation.getInstance().getUserHealth().toString()) + "/" + String.format(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));

                                        if(CurrentUserInformation.getInstance().getUserHealth() == 0){
                                            endGameScreenDefeat();
                                        }
                                    }
                                }, 2300);

                                currentTextViewClicked.setTextColor(getResources().getColor(R.color.colorAccent));
                                currentTextViewClicked = null;
                                currentContentTypeProcessed = null;
                            }
                        } else {
                            enemyattackanimation.setImageResource(R.drawable.enemyattack);
                            enemyattackanimation.clearAnimation();

                            CurrentUserInformation.getInstance().increaseUserHealth(-1);

                            enemyattackanimation.setVisibility(View.VISIBLE);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    enemyattackanimation.setVisibility(View.GONE);
                                    enemyattackanimation.clearAnimation();

                                    gameScreenPlayerHealth.setText("HP  " + String.format(CurrentUserInformation.getInstance().getUserHealth().toString()) + "/" + String.format(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));

                                    if(CurrentUserInformation.getInstance().getUserHealth() == 0){
                                        endGameScreenDefeat();
                                    }
                                }
                            }, 2300);

                            currentTextViewClicked.setTextColor(getResources().getColor(R.color.colorAccent));
                            currentTextViewClicked = null;
                            currentContentTypeProcessed = null;
                        }
                    }
                }
            });
        } else {
            elseButton.setText("Locked");

            elseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "This skill will be unlocked at level " + CurrentUserInformation.getInstance().userSkillsCollection.get("else").getUnlockableAtLevel().toString() + "!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setElseIfButtonOnClickListener() {
        elseIfButton = (TextView) findViewById(R.id.elseIfButton);

        if (CurrentUserInformation.getInstance().userSkillsCollection.get("else if").getUnlocked()) {
            elseIfButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentTextViewClicked != null) {
                        if (currentContentTypeProcessed.isSecretWord()) {
                            if (currentContentTypeProcessed.correctWord.equals("else if")) {
                                chiuplusattackanimation.setImageResource(R.drawable.chiuplusattack);
                                chiuplusattackanimation.clearAnimation();


                                chiuplusattackanimation.setVisibility(View.VISIBLE);

                                allErrorsInContent.remove(currentTextViewClicked);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        chiuplusattackanimation.setVisibility(View.GONE);
                                        chiuplusattackanimation.clearAnimation();

                                        gameScreenEnemyHealth.setText("HP  " + allErrorsInContent.size() + "/" + enemyMaxHealth);

                                        if(allErrorsInContent.size()==0){
                                            endGameScreenVictory();
                                        }
                                    }
                                }, 2300);

                                currentTextViewClicked.setText("else if");
                                currentTextViewClicked.setTextColor(Color.parseColor("#8FBC8F"));

                                currentTextViewClicked = null;
                                currentContentTypeProcessed = null;
                            } else {
                                enemyattackanimation.setImageResource(R.drawable.enemyattack);
                                enemyattackanimation.clearAnimation();

                                CurrentUserInformation.getInstance().increaseUserHealth(-1);

                                enemyattackanimation.setVisibility(View.VISIBLE);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        enemyattackanimation.setVisibility(View.GONE);
                                        enemyattackanimation.clearAnimation();

                                        gameScreenPlayerHealth.setText("HP  " + String.format(CurrentUserInformation.getInstance().getUserHealth().toString()) + "/" + String.format(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));

                                        if(CurrentUserInformation.getInstance().getUserHealth() == 0){
                                            endGameScreenDefeat();
                                        }
                                    }
                                }, 2300);

                                currentTextViewClicked.setTextColor(getResources().getColor(R.color.colorAccent));
                                currentTextViewClicked = null;
                                currentContentTypeProcessed = null;
                            }
                        } else {
                            enemyattackanimation.setImageResource(R.drawable.enemyattack);
                            enemyattackanimation.clearAnimation();

                            CurrentUserInformation.getInstance().increaseUserHealth(-1);

                            enemyattackanimation.setVisibility(View.VISIBLE);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    enemyattackanimation.setVisibility(View.GONE);
                                    enemyattackanimation.clearAnimation();

                                    gameScreenPlayerHealth.setText("HP  " + String.format(CurrentUserInformation.getInstance().getUserHealth().toString()) + "/" + String.format(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));

                                    if(CurrentUserInformation.getInstance().getUserHealth() == 0){
                                        endGameScreenDefeat();
                                    }
                                }
                            }, 2300);

                            currentTextViewClicked.setTextColor(getResources().getColor(R.color.colorAccent));
                            currentTextViewClicked = null;
                            currentContentTypeProcessed = null;
                        }
                    }
                }
            });
        } else {
            elseIfButton.setText("Locked");

            elseIfButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "This skill will be unlocked at level " + CurrentUserInformation.getInstance().userSkillsCollection.get("else if").getUnlockableAtLevel().toString() + "!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setGoBackToSkillBarButtonFromConditionals() {
        goBackToSkillBarButtonFromConditionals = (TextView) findViewById(R.id.goBackToSkillBarButtonFromConditionals);
        goBackToSkillBarButtonFromConditionals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conditionalsSkillBarLayout.setVisibility(View.GONE);
                mainSkillBarLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    //Loops bar menu buttons on click listener; whileButton, doButton, forButton, goBackToSkillBarButtonFromLoops;

    private void setWhileButtonOnClickListener() {
        whileButton = (TextView) findViewById(R.id.whileButton);

        if (CurrentUserInformation.getInstance().userSkillsCollection.get("while").getUnlocked()) {
            whileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentTextViewClicked != null) {
                        if (currentContentTypeProcessed.isSecretWord()) {
                            if (currentContentTypeProcessed.correctWord.equals("while")) {
                                chiuplusattackanimation.setImageResource(R.drawable.chiuplusattack);
                                chiuplusattackanimation.clearAnimation();


                                chiuplusattackanimation.setVisibility(View.VISIBLE);

                                allErrorsInContent.remove(currentTextViewClicked);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        chiuplusattackanimation.setVisibility(View.GONE);
                                        chiuplusattackanimation.clearAnimation();

                                        gameScreenEnemyHealth.setText("HP  " + allErrorsInContent.size() + "/" + enemyMaxHealth);

                                        if(allErrorsInContent.size()==0){
                                            endGameScreenVictory();
                                        }
                                    }
                                }, 2300);

                                currentTextViewClicked.setText("while");
                                currentTextViewClicked.setTextColor(Color.parseColor("#8FBC8F"));

                                currentTextViewClicked = null;
                                currentContentTypeProcessed = null;
                            } else {
                                enemyattackanimation.setImageResource(R.drawable.enemyattack);
                                enemyattackanimation.clearAnimation();

                                CurrentUserInformation.getInstance().increaseUserHealth(-1);

                                enemyattackanimation.setVisibility(View.VISIBLE);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        enemyattackanimation.setVisibility(View.GONE);
                                        enemyattackanimation.clearAnimation();

                                        gameScreenPlayerHealth.setText("HP  " + String.format(CurrentUserInformation.getInstance().getUserHealth().toString()) + "/" + String.format(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));

                                        if(CurrentUserInformation.getInstance().getUserHealth() == 0){
                                            endGameScreenDefeat();
                                        }
                                    }
                                }, 2300);

                                currentTextViewClicked.setTextColor(getResources().getColor(R.color.colorAccent));
                                currentTextViewClicked = null;
                                currentContentTypeProcessed = null;
                            }
                        } else {
                            enemyattackanimation.setImageResource(R.drawable.enemyattack);
                            enemyattackanimation.clearAnimation();

                            CurrentUserInformation.getInstance().increaseUserHealth(-1);

                            enemyattackanimation.setVisibility(View.VISIBLE);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    enemyattackanimation.setVisibility(View.GONE);
                                    enemyattackanimation.clearAnimation();

                                    gameScreenPlayerHealth.setText("HP  " + String.format(CurrentUserInformation.getInstance().getUserHealth().toString()) + "/" + String.format(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));

                                    if(CurrentUserInformation.getInstance().getUserHealth() == 0){
                                        endGameScreenDefeat();
                                    }
                                }
                            }, 2300);

                            currentTextViewClicked.setTextColor(getResources().getColor(R.color.colorAccent));
                            currentTextViewClicked = null;
                            currentContentTypeProcessed = null;
                        }
                    }
                }
            });
        } else {
            whileButton.setText("Locked");

            whileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "This skill will be unlocked at level " + CurrentUserInformation.getInstance().userSkillsCollection.get("while").getUnlockableAtLevel().toString() + "!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setDoButtonOnClickListener() {
        doButton = (TextView) findViewById(R.id.doButton);

        if (CurrentUserInformation.getInstance().userSkillsCollection.get("do").getUnlocked()) {
            doButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentTextViewClicked != null) {
                        if (currentContentTypeProcessed.isSecretWord()) {
                            if (currentContentTypeProcessed.correctWord.equals("do")) {
                                chiuplusattackanimation.setImageResource(R.drawable.chiuplusattack);
                                chiuplusattackanimation.clearAnimation();


                                chiuplusattackanimation.setVisibility(View.VISIBLE);

                                allErrorsInContent.remove(currentTextViewClicked);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        chiuplusattackanimation.setVisibility(View.GONE);
                                        chiuplusattackanimation.clearAnimation();

                                        gameScreenEnemyHealth.setText("HP  " + allErrorsInContent.size() + "/" + enemyMaxHealth);

                                        if(allErrorsInContent.size()==0){
                                            endGameScreenVictory();
                                        }
                                    }
                                }, 2300);

                                currentTextViewClicked.setText("do");
                                currentTextViewClicked.setTextColor(Color.parseColor("#8FBC8F"));

                                currentTextViewClicked = null;
                                currentContentTypeProcessed = null;
                            } else {
                                enemyattackanimation.setImageResource(R.drawable.enemyattack);
                                enemyattackanimation.clearAnimation();

                                CurrentUserInformation.getInstance().increaseUserHealth(-1);

                                enemyattackanimation.setVisibility(View.VISIBLE);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        enemyattackanimation.setVisibility(View.GONE);
                                        enemyattackanimation.clearAnimation();

                                        gameScreenPlayerHealth.setText("HP  " + String.format(CurrentUserInformation.getInstance().getUserHealth().toString()) + "/" + String.format(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));

                                        if(CurrentUserInformation.getInstance().getUserHealth() == 0){
                                            endGameScreenDefeat();
                                        }
                                    }
                                }, 2300);

                                currentTextViewClicked.setTextColor(getResources().getColor(R.color.colorAccent));
                                currentTextViewClicked = null;
                                currentContentTypeProcessed = null;
                            }
                        } else {
                            enemyattackanimation.setImageResource(R.drawable.enemyattack);
                            enemyattackanimation.clearAnimation();

                            CurrentUserInformation.getInstance().increaseUserHealth(-1);

                            enemyattackanimation.setVisibility(View.VISIBLE);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    enemyattackanimation.setVisibility(View.GONE);
                                    enemyattackanimation.clearAnimation();

                                    gameScreenPlayerHealth.setText("HP  " + String.format(CurrentUserInformation.getInstance().getUserHealth().toString()) + "/" + String.format(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));

                                    if(CurrentUserInformation.getInstance().getUserHealth() == 0){
                                        endGameScreenDefeat();
                                    }
                                }
                            }, 2300);

                            currentTextViewClicked.setTextColor(getResources().getColor(R.color.colorAccent));
                            currentTextViewClicked = null;
                            currentContentTypeProcessed = null;
                        }
                    }
                }
            });
        } else {
            doButton.setText("Locked");

            doButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "This skill will be unlocked at level " + CurrentUserInformation.getInstance().userSkillsCollection.get("do").getUnlockableAtLevel().toString() + "!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setForButtonOnClickListener() {
        forButton = (TextView) findViewById(R.id.forButton);

        if (CurrentUserInformation.getInstance().userSkillsCollection.get("for").getUnlocked()) {
            forButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentTextViewClicked != null) {
                        if (currentContentTypeProcessed.isSecretWord()) {
                            if (currentContentTypeProcessed.correctWord.equals("for")) {
                                chiuplusattackanimation.setImageResource(R.drawable.chiuplusattack);
                                chiuplusattackanimation.clearAnimation();


                                chiuplusattackanimation.setVisibility(View.VISIBLE);

                                allErrorsInContent.remove(currentTextViewClicked);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        chiuplusattackanimation.setVisibility(View.GONE);
                                        chiuplusattackanimation.clearAnimation();

                                        gameScreenEnemyHealth.setText("HP  " + allErrorsInContent.size() + "/" + enemyMaxHealth);

                                        if(allErrorsInContent.size()==0){
                                            endGameScreenVictory();
                                        }
                                    }
                                }, 2300);

                                currentTextViewClicked.setText("for");
                                currentTextViewClicked.setTextColor(Color.parseColor("#8FBC8F"));

                                currentTextViewClicked = null;
                                currentContentTypeProcessed = null;
                            } else {
                                enemyattackanimation.setImageResource(R.drawable.enemyattack);
                                enemyattackanimation.clearAnimation();

                                CurrentUserInformation.getInstance().increaseUserHealth(-1);

                                enemyattackanimation.setVisibility(View.VISIBLE);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        enemyattackanimation.setVisibility(View.GONE);
                                        enemyattackanimation.clearAnimation();

                                        gameScreenPlayerHealth.setText("HP  " + String.format(CurrentUserInformation.getInstance().getUserHealth().toString()) + "/" + String.format(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));

                                        if(CurrentUserInformation.getInstance().getUserHealth() == 0){
                                            endGameScreenDefeat();
                                        }
                                    }
                                }, 2300);

                                currentTextViewClicked.setTextColor(getResources().getColor(R.color.colorAccent));
                                currentTextViewClicked = null;
                                currentContentTypeProcessed = null;
                            }
                        } else {
                            enemyattackanimation.setImageResource(R.drawable.enemyattack);
                            enemyattackanimation.clearAnimation();

                            CurrentUserInformation.getInstance().increaseUserHealth(-1);

                            enemyattackanimation.setVisibility(View.VISIBLE);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    enemyattackanimation.setVisibility(View.GONE);
                                    enemyattackanimation.clearAnimation();

                                    gameScreenPlayerHealth.setText("HP  " + String.format(CurrentUserInformation.getInstance().getUserHealth().toString()) + "/" + String.format(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));

                                    if(CurrentUserInformation.getInstance().getUserHealth() == 0){
                                        endGameScreenDefeat();
                                    }
                                }
                            }, 2300);

                            currentTextViewClicked.setTextColor(getResources().getColor(R.color.colorAccent));
                            currentTextViewClicked = null;
                            currentContentTypeProcessed = null;
                        }
                    }
                }
            });
        } else {
            forButton.setText("Locked");

            forButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "This skill will be unlocked at level " + CurrentUserInformation.getInstance().userSkillsCollection.get("for").getUnlockableAtLevel().toString() + "!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setGoBackToSkillBarButtonFromLoops() {
        goBackToSkillBarButtonFromLoops = (TextView) findViewById(R.id.goBackToSkillBarButtonFromLoops);
        goBackToSkillBarButtonFromLoops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loopsSkillBarLayout.setVisibility(View.GONE);
                mainSkillBarLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    //Items bar menu buttons on click listener; useHealthPotionButton, useRevealingPotionButton, useFixerElixirButton, goBackToMainMenuButtonFromItemsBar;

    private void setUseHealthPotionButtonOnClickListener() {
        useHealthPotionButton = (TextView) findViewById(R.id.useHealthPotionButton);

        useHealthPotionButton.setText(String.format("Healing Potion (" + CurrentUserInformation.getInstance().userConsumablesCollection.get("HealingPotion").getQuantity().toString() + ")"));

        useHealthPotionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CurrentUserInformation.getInstance().userConsumablesCollection.get("HealingPotion").getQuantity() > 0) {
                    if(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()) == CurrentUserInformation.getInstance().getUserHealth()){
                        Toast.makeText(getApplicationContext(), "Health is currently at max value!", Toast.LENGTH_SHORT).show();
                    } else {
                        CurrentUserInformation.getInstance().updateConsumableQuantity("HealingPotion", -1);
                        CurrentUserInformation.getInstance().increaseUserHealth(1);

                        Toast.makeText(getApplicationContext(), "Healing Potion used! 1 HP has been restored!", Toast.LENGTH_SHORT).show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                useHealthPotionButton.setText(String.format("Healing Potion (" + CurrentUserInformation.getInstance().userConsumablesCollection.get("HealingPotion").getQuantity().toString() + ")"));
                                gameScreenPlayerHealth.setText("HP  " + String.format(CurrentUserInformation.getInstance().getUserHealth().toString()) + "/" + String.format(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));
                            }
                        }, 1000);

                        useHealthPotionButton.setText(String.format("Healing Potion (" + CurrentUserInformation.getInstance().userConsumablesCollection.get("HealingPotion").getQuantity().toString() + ")"));
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No Healing Potion(s) available!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setUseRevealingPotionButtonOnClickListener() {
        useRevealingPotionButton = (TextView) findViewById(R.id.useRevealingPotionButton);

        useRevealingPotionButton.setText(String.format("Revealing Potion (" + CurrentUserInformation.getInstance().userConsumablesCollection.get("RevealingPotion").getQuantity().toString() + ")"));

        useRevealingPotionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CurrentUserInformation.getInstance().userConsumablesCollection.get("RevealingPotion").getQuantity() > 0) {
                    if(allErrorsInContent.size()>0) {

                        CurrentUserInformation.getInstance().updateConsumableQuantity("RevealingPotion", -1);

                        Random rand = new Random();

                        int randNumber = rand.nextInt(allErrorsInContent.size());

                        currentTextViewClicked = allErrorsInContent.get(randNumber);

                        currentTextViewClicked.setTextColor(Color.parseColor("#FF4500"));

                        currentContentTypeProcessed = errorsInContent.get(randNumber);

                        useRevealingPotionButton.setText(String.format("Revealing Potion (" + CurrentUserInformation.getInstance().userConsumablesCollection.get("RevealingPotion").getQuantity().toString() + ")"));

                        Toast.makeText(getApplicationContext(), "Revealing Potion used! One error has been revealed!", Toast.LENGTH_SHORT).show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                useRevealingPotionButton.setText(String.format("Revealing Potion (" + CurrentUserInformation.getInstance().userConsumablesCollection.get("RevealingPotion").getQuantity().toString() + ")"));
                            }
                        }, 1000);

                    } else {
                        Toast.makeText(getApplicationContext(), "No errors to be revealed!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No Revealing Potion(s) available!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setUseFixerElixirButtonOnClickListener() {
        useFixerElixirButton = (TextView) findViewById(R.id.useFixerElixirButton);

        useFixerElixirButton.setText(String.format("Fixer Elixer (" + CurrentUserInformation.getInstance().userConsumablesCollection.get("FixerElixer").getQuantity().toString() + ")"));

        useFixerElixirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CurrentUserInformation.getInstance().userConsumablesCollection.get("FixerElixer").getQuantity() > 0) {
                    if(allErrorsInContent.size()>0) {
                        chiuplusattackanimation.setImageResource(R.drawable.chiuplusattack);
                        chiuplusattackanimation.clearAnimation();


                        chiuplusattackanimation.setVisibility(View.VISIBLE);


                        CurrentUserInformation.getInstance().updateConsumableQuantity("FixerElixer", -1);

                        Random rand = new Random();

                        int randNumber = rand.nextInt(allErrorsInContent.size());

                        currentTextViewClicked = allErrorsInContent.get(randNumber);

                        currentContentTypeProcessed = errorsInContent.get(randNumber);

                        currentTextViewClicked.setTextColor(Color.parseColor("#8FBC8F"));

                        if(currentContentTypeProcessed.correctWord.equals("array")) {
                            currentTextViewClicked.setText(currentContentTypeProcessed.wordContent + "[]");
                        } else if(currentContentTypeProcessed.correctWord.equals("2dArray")) {
                            currentTextViewClicked.setText(currentContentTypeProcessed.wordContent + "[][]");
                        } else {
                            currentTextViewClicked.setText(currentContentTypeProcessed.correctWord);
                        }

                        currentTextViewClicked.setText(currentContentTypeProcessed.correctWord);

                        allErrorsInContent.remove(currentTextViewClicked);
                        errorsInContent.remove(currentContentTypeProcessed);

                        currentTextViewClicked = null;
                        currentContentTypeProcessed = null;


                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                chiuplusattackanimation.setVisibility(View.GONE);
                                chiuplusattackanimation.clearAnimation();

                                gameScreenEnemyHealth.setText("HP  " + allErrorsInContent.size() + "/" + enemyMaxHealth);

                                if(allErrorsInContent.size()==0){
                                    endGameScreenVictory();
                                }
                                Toast.makeText(getApplicationContext(), "Fixer Elixer used! One error has been fixed!", Toast.LENGTH_SHORT).show();
                                useFixerElixirButton.setText(String.format("Fixer Elixer (" + CurrentUserInformation.getInstance().userConsumablesCollection.get("FixerElixer").getQuantity().toString() + ")"));
                            }
                        }, 2300);
                    } else {
                        Toast.makeText(getApplicationContext(), "No errors to be fixed!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No Fixer Elixer(s) available!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setGoBackToMainMenuButtonFromItemsBar() {
        goBackToMainMenuButtonFromItemsBar = (TextView) findViewById(R.id.goBackToMainMenuButtonFromItemsBar);
        goBackToMainMenuButtonFromItemsBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsSkillBarLayout.setVisibility(View.GONE);
                mainMenuBarLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mainSkillBarLayout.getVisibility() == View.VISIBLE) {
            mainSkillBarLayout.setVisibility(View.GONE);
            mainMenuBarLayout.setVisibility(View.VISIBLE);
        } else if (variablesSkillBarLayout.getVisibility() == View.VISIBLE) {
            variablesSkillBarLayout.setVisibility(View.GONE);
            mainSkillBarLayout.setVisibility(View.VISIBLE);
        } else if (conditionalsSkillBarLayout.getVisibility() == View.VISIBLE) {
            conditionalsSkillBarLayout.setVisibility(View.GONE);
            mainSkillBarLayout.setVisibility(View.VISIBLE);
        } else if (loopsSkillBarLayout.getVisibility() == View.VISIBLE) {
            loopsSkillBarLayout.setVisibility(View.GONE);
            mainSkillBarLayout.setVisibility(View.VISIBLE);
        } else if (itemsSkillBarLayout.getVisibility() == View.VISIBLE) {
            itemsSkillBarLayout.setVisibility(View.GONE);
            mainMenuBarLayout.setVisibility(View.VISIBLE);
        }
    }
}
