package com.example.dobra.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ShopActivity extends AppCompatActivity {

    private TextView purchaseChestButton, chestBoxOneRewardTextShop, chestBoxTwoRewardTextShop, chestBoxThreeRewardTextShop, closePurchasedChestLayout;

    private ImageView chestBoxOneShop, chestBoxOneRewardShop, chestBoxTwoShop, chestBoxTwoRewardShop, chestBoxThreeShop, chestBoxThreeRewardShop;

    private ImageView storyModeButton, multiplayerModeButton, practiceModeButton, profileModeButton, friendsModeButton, inventoryModeButton, shopModeButton, settingsModeButton, backButton;

    private LinearLayout chestBoughtLayout;

    private Map<Integer, String> rewardPool;

    private Integer dropChest1, dropChest2, dropChest3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        chestBoughtLayout = (LinearLayout) findViewById(R.id.chestBoughtLayout);

        setPurchaseChestButtonOnClickListener();

        dropChest1 = 0;
        dropChest2 = 0;
        dropChest3 = 0;

        rewardPool = new HashMap<>();

        setRewardPool();

        findAllViewsInLayout();

        setUpButtons();
    }


    private void setPurchaseChestButtonOnClickListener(){
        purchaseChestButton = (TextView) findViewById(R.id.purchaseChestButton);

        purchaseChestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CurrentUserInformation.getInstance().getUserCoins()>=50) {
                    chestBoughtLayout.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getApplicationContext(), "Not enough CyberCoins!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setRewardPool(){
        rewardPool.put(1, "CaptainHat");
        rewardPool.put(2, "CowboyHat");
        rewardPool.put(3, "FarmerHat");
        rewardPool.put(4, "Headphones");
        rewardPool.put(5, "KingCrown");
        rewardPool.put(6, "NinjaHat");
        rewardPool.put(7, "PirateHat");
        rewardPool.put(8, "SpaceHelmet");
        rewardPool.put(9, "TopHat");
        rewardPool.put(10, "WizardHat");

        for(int i = 11; i <= 20; i++){
            rewardPool.put(i, "FixerElixer");
        }

        for(int i = 21; i <= 50; i++){
            rewardPool.put(i, "RevealingPotion");
        }

        for(int i = 51; i <= 100; i++){
            rewardPool.put(i, "HealingPotion");
        }
    }

    private void generateDropReward(){
        Random rand = new Random();

        dropChest1 =(Integer) rand.nextInt(100)+1;

        dropChest2 =(Integer) rand.nextInt(100)+1;

        dropChest3 =(Integer) rand.nextInt(100)+1;
    }

    private void findAllViewsInLayout(){
        chestBoxOneShop = (ImageView) findViewById(R.id.chestBoxOneShop);

        chestBoxOneRewardShop = (ImageView) findViewById(R.id.chestBoxOneRewardShop);

        chestBoxTwoShop = (ImageView) findViewById(R.id.chestBoxTwoShop);

        chestBoxTwoRewardShop = (ImageView) findViewById(R.id.chestBoxTwoRewardShop);

        chestBoxThreeShop = (ImageView) findViewById(R.id.chestBoxThreeShop);

        chestBoxThreeRewardShop = (ImageView) findViewById(R.id.chestBoxThreeRewardShop);

        chestBoxOneRewardTextShop = (TextView) findViewById(R.id.chestBoxOneRewardTextShop);

        chestBoxTwoRewardTextShop = (TextView) findViewById(R.id.chestBoxTwoRewardTextShop);

        chestBoxThreeRewardTextShop = (TextView) findViewById(R.id.chestBoxThreeRewardTextShop);

        closePurchasedChestLayout = (TextView) findViewById(R.id.closePurchasedChestLayout);

        setClosePurchasedChestLayoutOnClickListener();

        setChestBoxOneOnClickListener();

        setChestBoxTwoOnClickListener();

        setChestBoxThreeOnClickListener();
    }

    private void setRewardLayoutVisible(){
        chestBoxOneRewardShop.setVisibility(View.VISIBLE);

        chestBoxTwoRewardShop.setVisibility(View.VISIBLE);

        chestBoxThreeRewardShop.setVisibility(View.VISIBLE);

        chestBoxOneRewardTextShop.setVisibility(View.VISIBLE);

        chestBoxTwoRewardTextShop.setVisibility(View.VISIBLE);

        chestBoxThreeRewardTextShop.setVisibility(View.VISIBLE);

        closePurchasedChestLayout.setVisibility(View.VISIBLE);
    }

    private void resetPurchaseLayout(){
        chestBoxOneRewardShop.setVisibility(View.GONE);

        chestBoxTwoRewardShop.setVisibility(View.GONE);

        chestBoxThreeRewardShop.setVisibility(View.GONE);

        chestBoxOneRewardTextShop.setVisibility(View.GONE);

        chestBoxTwoRewardTextShop.setVisibility(View.GONE);

        chestBoxThreeRewardTextShop.setVisibility(View.GONE);

        closePurchasedChestLayout.setVisibility(View.GONE);

        chestBoxOneShop.setImageResource(R.drawable.chest2);

        chestBoxTwoShop.setImageResource(R.drawable.chest2);

        chestBoxThreeShop.setImageResource(R.drawable.chest2);

        chestBoughtLayout.setVisibility(View.GONE);

        setChestBoxOneOnClickListener();

        setChestBoxTwoOnClickListener();

        setChestBoxThreeOnClickListener();
    }

    private void setChestBoxOneOnClickListener(){

        chestBoxOneShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CurrentUserInformation.getInstance().getUserCoins()>=50) {
                    CurrentUserInformation.getInstance().setUserCoins(-50);

                    generateDropReward();

                    setRewardLayoutVisible();

                    chestBoxOneShop.setImageResource(R.drawable.openchest2);

                    chestBoxTwoShop.setImageResource(R.drawable.openchest);

                    chestBoxThreeShop.setImageResource(R.drawable.openchest);

                    chestBoxOneRewardTextShop.setText(rewardPool.get(dropChest1));

                    chestBoxTwoRewardTextShop.setText(rewardPool.get(dropChest2));

                    chestBoxThreeRewardTextShop.setText(rewardPool.get(dropChest3));
                    if (rewardPool.get(dropChest1).equals("CaptainHat")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.captainhat);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest1));
                    } else if (rewardPool.get(dropChest1).equals("CowboyHat")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.fedora);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest1));
                    } else if (rewardPool.get(dropChest1).equals("FarmerHat")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.farmerhat);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest1));
                    } else if (rewardPool.get(dropChest1).equals("Headphones")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.headphones);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest1));
                    } else if (rewardPool.get(dropChest1).equals("KingCrown")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.crown);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest1));
                    } else if (rewardPool.get(dropChest1).equals("NinjaHat")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.bandana);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest1));
                    } else if (rewardPool.get(dropChest1).equals("PirateHat")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.piratehat);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest1));
                    } else if (rewardPool.get(dropChest1).equals("SpaceHelmet")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.spacehelmet);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest1));
                    } else if (rewardPool.get(dropChest1).equals("TopHat")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.tophat);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest1));
                    } else if (rewardPool.get(dropChest1).equals("WizardHat")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.wizardhat);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest1));
                    } else if (rewardPool.get(dropChest1).equals("FixerElixer")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.fixerelixer);

                        CurrentUserInformation.getInstance().updateConsumableQuantity(rewardPool.get(dropChest1), 1);
                    } else if (rewardPool.get(dropChest1).equals("RevealingPotion")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.revealingpotion);

                        CurrentUserInformation.getInstance().updateConsumableQuantity(rewardPool.get(dropChest1), 1);
                    } else if (rewardPool.get(dropChest1).equals("HealingPotion")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.healthpotion);

                        CurrentUserInformation.getInstance().updateConsumableQuantity(rewardPool.get(dropChest1), 1);
                    }

                    if (rewardPool.get(dropChest2).equals("CaptainHat")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.captainhat);
                    } else if (rewardPool.get(dropChest2).equals("CowboyHat")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.fedora);
                    } else if (rewardPool.get(dropChest2).equals("FarmerHat")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.farmerhat);
                    } else if (rewardPool.get(dropChest2).equals("Headphones")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.headphones);
                    } else if (rewardPool.get(dropChest2).equals("KingCrown")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.crown);
                    } else if (rewardPool.get(dropChest2).equals("NinjaHat")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.bandana);
                    } else if (rewardPool.get(dropChest2).equals("PirateHat")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.piratehat);
                    } else if (rewardPool.get(dropChest2).equals("SpaceHelmet")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.spacehelmet);
                    } else if (rewardPool.get(dropChest2).equals("TopHat")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.tophat);
                    } else if (rewardPool.get(dropChest2).equals("WizardHat")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.wizardhat);
                    } else if (rewardPool.get(dropChest2).equals("FixerElixer")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.fixerelixer);
                    } else if (rewardPool.get(dropChest2).equals("RevealingPotion")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.revealingpotion);
                    } else if (rewardPool.get(dropChest2).equals("HealingPotion")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.healthpotion);
                    }

                    if (rewardPool.get(dropChest3).equals("CaptainHat")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.captainhat);
                    } else if (rewardPool.get(dropChest3).equals("CowboyHat")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.fedora);
                    } else if (rewardPool.get(dropChest3).equals("FarmerHat")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.farmerhat);
                    } else if (rewardPool.get(dropChest3).equals("Headphones")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.headphones);
                    } else if (rewardPool.get(dropChest3).equals("KingCrown")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.crown);
                    } else if (rewardPool.get(dropChest3).equals("NinjaHat")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.bandana);
                    } else if (rewardPool.get(dropChest3).equals("PirateHat")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.piratehat);
                    } else if (rewardPool.get(dropChest3).equals("SpaceHelmet")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.spacehelmet);
                    } else if (rewardPool.get(dropChest3).equals("TopHat")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.tophat);
                    } else if (rewardPool.get(dropChest3).equals("WizardHat")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.wizardhat);
                    } else if (rewardPool.get(dropChest3).equals("FixerElixer")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.fixerelixer);
                    } else if (rewardPool.get(dropChest3).equals("RevealingPotion")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.revealingpotion);
                    } else if (rewardPool.get(dropChest3).equals("HealingPotion")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.healthpotion);
                    }

                    chestBoxOneShop.setOnClickListener(null);
                    chestBoxTwoShop.setOnClickListener(null);
                    chestBoxThreeShop.setOnClickListener(null);
                } else {
                    Toast.makeText(getApplicationContext(), "Not enough CyberCoins!", Toast.LENGTH_SHORT).show();

                    resetPurchaseLayout();
                }
            }
        });
    }

    private void setChestBoxTwoOnClickListener(){

        chestBoxTwoShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CurrentUserInformation.getInstance().getUserCoins()>=50) {
                    CurrentUserInformation.getInstance().setUserCoins(-50);

                    generateDropReward();

                    setRewardLayoutVisible();

                    chestBoxOneShop.setImageResource(R.drawable.openchest);

                    chestBoxTwoShop.setImageResource(R.drawable.openchest2);

                    chestBoxThreeShop.setImageResource(R.drawable.openchest);

                    chestBoxOneRewardTextShop.setText(rewardPool.get(dropChest1));

                    chestBoxTwoRewardTextShop.setText(rewardPool.get(dropChest2));

                    chestBoxThreeRewardTextShop.setText(rewardPool.get(dropChest3));

                    if (rewardPool.get(dropChest1).equals("CaptainHat")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.captainhat);
                    } else if (rewardPool.get(dropChest1).equals("CowboyHat")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.fedora);
                    } else if (rewardPool.get(dropChest1).equals("FarmerHat")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.farmerhat);
                    } else if (rewardPool.get(dropChest1).equals("Headphones")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.headphones);
                    } else if (rewardPool.get(dropChest1).equals("KingCrown")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.crown);
                    } else if (rewardPool.get(dropChest1).equals("NinjaHat")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.bandana);
                    } else if (rewardPool.get(dropChest1).equals("PirateHat")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.piratehat);
                    } else if (rewardPool.get(dropChest1).equals("SpaceHelmet")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.spacehelmet);
                    } else if (rewardPool.get(dropChest1).equals("TopHat")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.tophat);
                    } else if (rewardPool.get(dropChest1).equals("WizardHat")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.wizardhat);
                    } else if (rewardPool.get(dropChest1).equals("FixerElixer")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.fixerelixer);
                    } else if (rewardPool.get(dropChest1).equals("RevealingPotion")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.revealingpotion);
                    } else if (rewardPool.get(dropChest1).equals("HealingPotion")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.healthpotion);
                    }

                    if (rewardPool.get(dropChest2).equals("CaptainHat")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.captainhat);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest2));
                    } else if (rewardPool.get(dropChest2).equals("CowboyHat")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.fedora);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest2));
                    } else if (rewardPool.get(dropChest2).equals("FarmerHat")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.farmerhat);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest2));
                    } else if (rewardPool.get(dropChest2).equals("Headphones")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.headphones);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest2));
                    } else if (rewardPool.get(dropChest2).equals("KingCrown")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.crown);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest2));
                    } else if (rewardPool.get(dropChest2).equals("NinjaHat")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.bandana);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest2));
                    } else if (rewardPool.get(dropChest2).equals("PirateHat")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.piratehat);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest2));
                    } else if (rewardPool.get(dropChest2).equals("SpaceHelmet")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.spacehelmet);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest2));
                    } else if (rewardPool.get(dropChest2).equals("TopHat")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.tophat);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest2));
                    } else if (rewardPool.get(dropChest2).equals("WizardHat")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.wizardhat);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest2));
                    } else if (rewardPool.get(dropChest2).equals("FixerElixer")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.fixerelixer);

                        CurrentUserInformation.getInstance().updateConsumableQuantity(rewardPool.get(dropChest2), 1);
                    } else if (rewardPool.get(dropChest2).equals("RevealingPotion")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.revealingpotion);

                        CurrentUserInformation.getInstance().updateConsumableQuantity(rewardPool.get(dropChest2), 1);
                    } else if (rewardPool.get(dropChest2).equals("HealingPotion")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.healthpotion);

                        CurrentUserInformation.getInstance().updateConsumableQuantity(rewardPool.get(dropChest2), 1);
                    }

                    if (rewardPool.get(dropChest3).equals("CaptainHat")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.captainhat);
                    } else if (rewardPool.get(dropChest3).equals("CowboyHat")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.fedora);
                    } else if (rewardPool.get(dropChest3).equals("FarmerHat")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.farmerhat);
                    } else if (rewardPool.get(dropChest3).equals("Headphones")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.headphones);
                    } else if (rewardPool.get(dropChest3).equals("KingCrown")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.crown);
                    } else if (rewardPool.get(dropChest3).equals("NinjaHat")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.bandana);
                    } else if (rewardPool.get(dropChest3).equals("PirateHat")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.piratehat);
                    } else if (rewardPool.get(dropChest3).equals("SpaceHelmet")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.spacehelmet);
                    } else if (rewardPool.get(dropChest3).equals("TopHat")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.tophat);
                    } else if (rewardPool.get(dropChest3).equals("WizardHat")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.wizardhat);
                    } else if (rewardPool.get(dropChest3).equals("FixerElixer")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.fixerelixer);
                    } else if (rewardPool.get(dropChest3).equals("RevealingPotion")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.revealingpotion);
                    } else if (rewardPool.get(dropChest3).equals("HealingPotion")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.healthpotion);
                    }

                    chestBoxOneShop.setOnClickListener(null);
                    chestBoxTwoShop.setOnClickListener(null);
                    chestBoxThreeShop.setOnClickListener(null);
                } else {
                    Toast.makeText(getApplicationContext(), "Not enough CyberCoins!", Toast.LENGTH_SHORT).show();

                    resetPurchaseLayout();
                }
            }
        });
    }

    private void setChestBoxThreeOnClickListener(){

        chestBoxThreeShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CurrentUserInformation.getInstance().getUserCoins()>=50) {
                    CurrentUserInformation.getInstance().setUserCoins(-50);

                    generateDropReward();

                    setRewardLayoutVisible();

                    chestBoxOneShop.setImageResource(R.drawable.openchest);

                    chestBoxTwoShop.setImageResource(R.drawable.openchest);

                    chestBoxThreeShop.setImageResource(R.drawable.openchest2);

                    chestBoxOneRewardTextShop.setText(rewardPool.get(dropChest1));

                    chestBoxTwoRewardTextShop.setText(rewardPool.get(dropChest2));

                    chestBoxThreeRewardTextShop.setText(rewardPool.get(dropChest3));

                    if (rewardPool.get(dropChest1).equals("CaptainHat")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.captainhat);

                    } else if (rewardPool.get(dropChest1).equals("CowboyHat")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.fedora);

                    } else if (rewardPool.get(dropChest1).equals("FarmerHat")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.farmerhat);

                    } else if (rewardPool.get(dropChest1).equals("Headphones")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.headphones);

                    } else if (rewardPool.get(dropChest1).equals("KingCrown")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.crown);

                    } else if (rewardPool.get(dropChest1).equals("NinjaHat")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.bandana);

                    } else if (rewardPool.get(dropChest1).equals("PirateHat")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.piratehat);

                    } else if (rewardPool.get(dropChest1).equals("SpaceHelmet")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.spacehelmet);

                    } else if (rewardPool.get(dropChest1).equals("TopHat")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.tophat);

                    } else if (rewardPool.get(dropChest1).equals("WizardHat")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.wizardhat);

                    } else if (rewardPool.get(dropChest1).equals("FixerElixer")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.fixerelixer);
                    } else if (rewardPool.get(dropChest1).equals("RevealingPotion")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.revealingpotion);
                    } else if (rewardPool.get(dropChest1).equals("HealingPotion")) {
                        chestBoxOneRewardShop.setImageResource(R.drawable.healthpotion);
                    }

                    if (rewardPool.get(dropChest2).equals("CaptainHat")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.captainhat);
                    } else if (rewardPool.get(dropChest2).equals("CowboyHat")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.fedora);
                    } else if (rewardPool.get(dropChest2).equals("FarmerHat")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.farmerhat);
                    } else if (rewardPool.get(dropChest2).equals("Headphones")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.headphones);
                    } else if (rewardPool.get(dropChest2).equals("KingCrown")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.crown);
                    } else if (rewardPool.get(dropChest2).equals("NinjaHat")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.bandana);
                    } else if (rewardPool.get(dropChest2).equals("PirateHat")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.piratehat);
                    } else if (rewardPool.get(dropChest2).equals("SpaceHelmet")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.spacehelmet);
                    } else if (rewardPool.get(dropChest2).equals("TopHat")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.tophat);
                    } else if (rewardPool.get(dropChest2).equals("WizardHat")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.wizardhat);
                    } else if (rewardPool.get(dropChest2).equals("FixerElixer")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.fixerelixer);
                    } else if (rewardPool.get(dropChest2).equals("RevealingPotion")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.revealingpotion);
                    } else if (rewardPool.get(dropChest2).equals("HealingPotion")) {
                        chestBoxTwoRewardShop.setImageResource(R.drawable.healthpotion);
                    }

                    if (rewardPool.get(dropChest3).equals("CaptainHat")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.captainhat);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest3));
                    } else if (rewardPool.get(dropChest3).equals("CowboyHat")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.fedora);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest3));
                    } else if (rewardPool.get(dropChest3).equals("FarmerHat")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.farmerhat);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest3));
                    } else if (rewardPool.get(dropChest3).equals("Headphones")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.headphones);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest3));
                    } else if (rewardPool.get(dropChest3).equals("KingCrown")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.crown);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest3));
                    } else if (rewardPool.get(dropChest3).equals("NinjaHat")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.bandana);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest3));
                    } else if (rewardPool.get(dropChest3).equals("PirateHat")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.piratehat);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest3));
                    } else if (rewardPool.get(dropChest3).equals("SpaceHelmet")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.spacehelmet);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest3));
                    } else if (rewardPool.get(dropChest3).equals("TopHat")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.tophat);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest3));
                    } else if (rewardPool.get(dropChest3).equals("WizardHat")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.wizardhat);

                        CurrentUserInformation.getInstance().unlockUserAppearanceItem(rewardPool.get(dropChest3));
                    } else if (rewardPool.get(dropChest3).equals("FixerElixer")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.fixerelixer);

                        CurrentUserInformation.getInstance().updateConsumableQuantity(rewardPool.get(dropChest3), 1);
                    } else if (rewardPool.get(dropChest3).equals("RevealingPotion")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.revealingpotion);

                        CurrentUserInformation.getInstance().updateConsumableQuantity(rewardPool.get(dropChest3), 1);
                    } else if (rewardPool.get(dropChest3).equals("HealingPotion")) {
                        chestBoxThreeRewardShop.setImageResource(R.drawable.healthpotion);

                        CurrentUserInformation.getInstance().updateConsumableQuantity(rewardPool.get(dropChest3), 1);
                    }

                    chestBoxOneShop.setOnClickListener(null);
                    chestBoxTwoShop.setOnClickListener(null);
                    chestBoxThreeShop.setOnClickListener(null);
                } else {
                    Toast.makeText(getApplicationContext(), "Not enough CyberCoins!", Toast.LENGTH_SHORT).show();

                    resetPurchaseLayout();
                }
            }
        });
    }

    private void setClosePurchasedChestLayoutOnClickListener(){

        closePurchasedChestLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chestBoughtLayout.setVisibility(View.GONE);
                closePurchasedChestLayout.setVisibility(View.GONE);
                resetPurchaseLayout();
            }
        });
    }

    private void setUpButtons() {
        //Sub menu setup
        setStoryModeButtonOnClickListener();
        setMultiplayerModeButtonOnClickListener();
        setPracticeModeButtonOnClickListener();
        setProfileButtonOnClickListener();
        setFriendsButtonOnClickListener();
        setInventoryModeButtonOnClickListener();
        setShopModeButtonOnClickListener();
        setSettingsButtonOnClickListener();
        setBackButtonOnClickListener();
    }


    //Set submenu button click listeners

    private void setStoryModeButtonOnClickListener() {
        storyModeButton = (ImageView) findViewById(R.id.storybutton);
        storyModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent storyMode = new Intent("android.intent.action.ChapterSelectorActivity");
                startActivity(storyMode);
                finish();
            }
        });
    }

    private void setMultiplayerModeButtonOnClickListener() {
        multiplayerModeButton = (ImageView) findViewById(R.id.multiplayerbutton);
        multiplayerModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Mode not yet implemented! Work In Progress!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setPracticeModeButtonOnClickListener() {
        practiceModeButton = (ImageView) findViewById(R.id.practicebutton);
        practiceModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Mode not yet implemented! Work In Progress!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setProfileButtonOnClickListener() {
        profileModeButton = (ImageView) findViewById(R.id.profilebutton);
        profileModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileMode = new Intent("android.intent.action.ProfileViewActivity");
                startActivity(profileMode);
                finish();
            }
        });
    }

    private void setFriendsButtonOnClickListener() {
        friendsModeButton = (ImageView) findViewById(R.id.friendsbutton);
        friendsModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent friendsMode = new Intent("android.intent.action.FriendsView");
                startActivity(friendsMode);
                finish();
            }
        });
    }

    private void setInventoryModeButtonOnClickListener() {
        inventoryModeButton = (ImageView) findViewById(R.id.itemsbutton);
        inventoryModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inventory_intent = new Intent("android.intent.action.InventoryActivity");
                startActivity(inventory_intent);
            }
        });
    }

    private void setShopModeButtonOnClickListener() {
        shopModeButton = (ImageView) findViewById(R.id.shopbutton);
        shopModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shop_intent = new Intent("android.intent.action.ShopActivity");
                startActivity(shop_intent);
                finish();
            }
        });
    }

    private void setSettingsButtonOnClickListener() {
        settingsModeButton = (ImageView) findViewById(R.id.settingsbutton);
        settingsModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settings_intent = new Intent("android.intent.action.SettingsScreen");
                startActivity(settings_intent);
                finish();
            }
        });
    }

    private void setBackButtonOnClickListener() {
        backButton = (ImageView) findViewById(R.id.backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
