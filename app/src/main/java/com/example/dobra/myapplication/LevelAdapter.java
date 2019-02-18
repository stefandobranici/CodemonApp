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
import android.widget.Toast;

import java.util.List;

public class LevelAdapter {
   private List<Level> mLevelList;
   private List<String> mKeys;
   private Context context;
   private RelativeLayout layout;
   private float scale;
   private int countX, countY, widthPixels;
   private boolean goRight;

   public LevelAdapter(List<Level> mLevelList, List<String> mKeys, Context context, RelativeLayout layout) {
        this.mLevelList = mLevelList;
        this.mKeys = mKeys;
        this.context = context;
        this.layout = layout;

        scale = context.getResources().getDisplayMetrics().density;
        countX = 0;
        countY = 0;
        goRight = true;
        widthPixels = context.getResources().getDisplayMetrics().widthPixels;
   }

   public void generateMap() {
       for(final Level level:mLevelList){
           if(level.getType().equals("blue")){
               if(goRight) {
                   ImageView image = new ImageView(context);
                   image.setImageResource(R.drawable.bluetile);


                   int dpWidthInPx = (int) (120 * scale);
                   int dpHeightInPx = (int) (30 * scale);

                   if(countX == 0){
                       image.setX(widthPixels / (3 - countX) - dpWidthInPx);
                   } else if(countX == 1){
                       image.setX(widthPixels / (3 - countX) - dpWidthInPx/2);
                   } else {
                       image.setX(widthPixels / (3 - countX) - dpWidthInPx );
                   }
                   image.setY(180 + countY * dpWidthInPx -  countY * dpWidthInPx/4);

                   LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
                   image.setLayoutParams(layoutParams);

                   layout.addView(image);

                   ImageView image2 = new ImageView(context);
                   image2.setImageResource(R.drawable.chiuplus);


                   int dpWidthInPx2 = (int) (40 * scale);
                   int dpHeightInPx2 = (int) (60 * scale);


                   image2.setX(image.getX() + dpWidthInPx/3 );
                   image2.setY(image.getY() - dpHeightInPx2 + dpHeightInPx/3 + dpHeightInPx/5);

                   LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(dpWidthInPx2, dpHeightInPx2);
                   image2.setLayoutParams(layoutParams2);

                   layout.addView(image2);

                   if(countX+1<=2){
                       countX++;
                   } else {
                       countY++;
                       goRight = false;
                   }
               } else {
                   ImageView image = new ImageView(context);
                   image.setImageResource(R.drawable.bluetile);

                   int dpWidthInPx = (int) (120 * scale);
                   int dpHeightInPx = (int) (30 * scale);

                   if(countX == 0){
                       image.setX(widthPixels / (3 - countX) - dpWidthInPx);
                   } else if(countX == 1){
                       image.setX(widthPixels / (3 - countX) - dpWidthInPx/2);
                   } else {
                       image.setX(widthPixels / (3 - countX) - dpWidthInPx );
                   }
                   image.setY(180 + countY * dpWidthInPx -  countY * dpWidthInPx/4);

                   LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
                   image.setLayoutParams(layoutParams);

                   layout.addView(image);

                   ImageView image2 = new ImageView(context);
                   image2.setImageResource(R.drawable.chiuplus);


                   int dpWidthInPx2 = (int) (40 * scale);
                   int dpHeightInPx2 = (int) (60 * scale);


                   image2.setX(image.getX() + dpWidthInPx/3);
                   image2.setY(image.getY() - dpHeightInPx2 + dpHeightInPx/3 + dpHeightInPx/5);

                   LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(dpWidthInPx2, dpHeightInPx2);
                   image2.setLayoutParams(layoutParams2);

                   layout.addView(image2);

                   if(countX-1>=0){
                       countX--;
                   } else {
                       countY++;
                       goRight = true;
                   }
               }
           }  else if(level.getType().equals("green")){
               if(goRight) {
                   ImageView image = new ImageView(context);
                   image.setImageResource(R.drawable.greentile);

                   int dpWidthInPx = (int) (120 * scale);
                   int dpHeightInPx = (int) (30 * scale);

                   if(countX == 0){
                       image.setX(widthPixels / (3 - countX) - dpWidthInPx);
                   } else if(countX == 1){
                       image.setX(widthPixels / (3 - countX) - dpWidthInPx/2);
                   } else {
                       image.setX(widthPixels / (3 - countX) - dpWidthInPx );
                   }
                   image.setY(180 + countY * dpWidthInPx -  countY * dpWidthInPx/4);

                   LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
                   image.setLayoutParams(layoutParams);

                   layout.addView(image);

                   ImageButton image2 = new ImageButton(context);
                   image2.setBackgroundResource(R.drawable.trainingcap);


                   int dpWidthInPx2 = (int) (50 * scale);
                   int dpHeightInPx2 = (int) (30 * scale);


                   image2.setX(image.getX() + dpWidthInPx/3 - dpWidthInPx/12);
                   image2.setY(image.getY() - dpHeightInPx2 + dpHeightInPx/3 + dpHeightInPx/5);

                   LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(dpWidthInPx2, dpHeightInPx2);
                   image2.setLayoutParams(layoutParams2);

/*                   image2.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Intent intent = new Intent(context, GameplayScreen.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                           context.startActivity(intent);
                       }
                   });*/

                   layout.addView(image2);

                   if(countX+1<=2){
                       countX++;
                   } else {
                       countY++;
                       goRight = false;
                   }
               } else {
                   ImageView image = new ImageView(context);
                   image.setImageResource(R.drawable.greentile);

                   int dpWidthInPx = (int) (120 * scale);
                   int dpHeightInPx = (int) (30 * scale);

                   if(countX == 0){
                       image.setX(widthPixels / (3 - countX) - dpWidthInPx);
                   } else if(countX == 1){
                       image.setX(widthPixels / (3 - countX) - dpWidthInPx/2);
                   } else {
                       image.setX(widthPixels / (3 - countX) - dpWidthInPx );
                   }
                   image.setY(180 + countY * dpWidthInPx -  countY * dpWidthInPx/4);

                   LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
                   image.setLayoutParams(layoutParams);

                   layout.addView(image);

                   ImageButton image2 = new ImageButton(context);
                   image2.setBackgroundResource(R.drawable.trainingcap);


                   int dpWidthInPx2 = (int) (50 * scale);
                   int dpHeightInPx2 = (int) (30 * scale);


                   image2.setX(image.getX() + dpWidthInPx/3 - dpWidthInPx/12);
                   image2.setY(image.getY() - dpHeightInPx2 + dpHeightInPx/3 + dpHeightInPx/5);

                   LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(dpWidthInPx2, dpHeightInPx2);
                   image2.setLayoutParams(layoutParams2);

/*                   image2.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Intent intent = new Intent(context, GameplayScreen.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                           context.startActivity(intent);
                       }
                   });*/


                   layout.addView(image2);

                   if(countX-1>=0){
                       countX--;
                   } else {
                       countY++;
                       goRight = true;
                   }
               }
           } else if(level.getType().equals("violet")){
               if(goRight) {
                   ImageView image = new ImageView(context);
                   image.setImageResource(R.drawable.violettile);

                   int dpWidthInPx = (int) (120 * scale);
                   int dpHeightInPx = (int) (30 * scale);

                   if(countX == 0){
                       image.setX(widthPixels / (3 - countX) - dpWidthInPx);
                   } else if(countX == 1){
                       image.setX(widthPixels / (3 - countX) - dpWidthInPx/2);
                   } else {
                       image.setX(widthPixels / (3 - countX) - dpWidthInPx );
                   }
                   image.setY(180 + countY * dpWidthInPx -  countY * dpWidthInPx/4);

                   LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
                   image.setLayoutParams(layoutParams);

                   layout.addView(image);

                   ImageButton image2 = new ImageButton(context);
                   image2.setBackgroundResource(R.drawable.codebug);


                   int dpWidthInPx2 = (int) (50 * scale);
                   int dpHeightInPx2 = (int) (60 * scale);


                   image2.setX(image.getX() + dpWidthInPx/3 - dpWidthInPx/12);
                   image2.setY(image.getY() - dpHeightInPx2 + dpHeightInPx/3 + dpHeightInPx/5);

                   LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(dpWidthInPx2, dpHeightInPx2);
                   image2.setLayoutParams(layoutParams2);

/*                   image2.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Intent intent = new Intent(context, GameplayScreen.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                           context.startActivity(intent);
                       }
                   });*/


                   layout.addView(image2);

                   if(countX+1<=2){
                       countX++;
                   } else {
                       countY++;
                       goRight = false;
                   }
               } else {
                   ImageView image = new ImageView(context);
                   image.setImageResource(R.drawable.violettile);

                   int dpWidthInPx = (int) (120 * scale);
                   int dpHeightInPx = (int) (30 * scale);

                   if(countX == 0){
                       image.setX(widthPixels / (3 - countX) - dpWidthInPx);
                   } else if(countX == 1){
                       image.setX(widthPixels / (3 - countX) - dpWidthInPx/2);
                   } else {
                       image.setX(widthPixels / (3 - countX) - dpWidthInPx );
                   }
                   image.setY(180 + countY * dpWidthInPx -  countY * dpWidthInPx/4);

                   LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
                   image.setLayoutParams(layoutParams);

                   layout.addView(image);

                   ImageButton image2 = new ImageButton(context);
                   image2.setBackgroundResource(R.drawable.codebug);


                   int dpWidthInPx2 = (int) (50 * scale);
                   int dpHeightInPx2 = (int) (60 * scale);


                   image2.setX(image.getX() + dpWidthInPx/3 - dpWidthInPx/12);
                   image2.setY(image.getY() - dpHeightInPx2 + dpHeightInPx/3 + dpHeightInPx/5);

                   LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(dpWidthInPx2, dpHeightInPx2);
                   image2.setLayoutParams(layoutParams2);

/*                   image2.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Intent intent = new Intent(context, GameplayScreen.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                           context.startActivity(intent);
                       }
                   });*/


                   layout.addView(image2);

                   if(countX-1>=0){
                       countX--;
                   } else {
                       countY++;
                       goRight = true;
                   }
               }
           } else if(level.getType().equals("red")) {
               if (goRight) {
                   ImageView image = new ImageView(context);
                   image.setImageResource(R.drawable.redtile);

                   int dpWidthInPx = (int) (120 * scale);
                   int dpHeightInPx = (int) (30 * scale);

                   if (countX == 0) {
                       image.setX(widthPixels / (3 - countX) - dpWidthInPx);
                   } else if (countX == 1) {
                       image.setX(widthPixels / (3 - countX) - dpWidthInPx / 2);
                   } else {
                       image.setX(widthPixels / (3 - countX) - dpWidthInPx);
                   }
                   image.setY(180 + countY * dpWidthInPx - countY * dpWidthInPx / 4);

                   LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
                   image.setLayoutParams(layoutParams);

                   layout.addView(image);

                   ImageButton image2 = new ImageButton(context);
                   image2.setBackgroundResource(R.drawable.boss);


                   int dpWidthInPx2 = (int) (50 * scale);
                   int dpHeightInPx2 = (int) (80 * scale);


                   image2.setX(image.getX() + dpWidthInPx/3);
                   image2.setY(image.getY() - dpHeightInPx2 + dpHeightInPx/3 + dpHeightInPx/5);

                   LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(dpWidthInPx2, dpHeightInPx2);
                   image2.setLayoutParams(layoutParams2);

/*                   image2.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Intent intent = new Intent(context, GameplayScreen.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                           context.startActivity(intent);
                       }
                   });*/


                   layout.addView(image2);

                   if (countX + 1 <= 2) {
                       countX++;
                   } else {
                       countY++;
                       goRight = false;
                   }
               } else {
                   ImageView image = new ImageView(context);
                   image.setImageResource(R.drawable.redtile);

                   int dpWidthInPx = (int) (120 * scale);
                   int dpHeightInPx = (int) (30 * scale);

                   if (countX == 0) {
                       image.setX(widthPixels / (3 - countX) - dpWidthInPx);
                   } else if (countX == 1) {
                       image.setX(widthPixels / (3 - countX) - dpWidthInPx / 2);
                   } else {
                       image.setX(widthPixels / (3 - countX) - dpWidthInPx);
                   }
                   image.setY(180 + countY * dpWidthInPx - countY * dpWidthInPx / 4);

                   LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
                   image.setLayoutParams(layoutParams);

                   layout.addView(image);

                   ImageButton image2 = new ImageButton(context);
                   image2.setBackgroundResource(R.drawable.boss);


                   int dpWidthInPx2 = (int) (50 * scale);
                   int dpHeightInPx2 = (int) (80 * scale);


                   image2.setX(image.getX() + dpWidthInPx/3 );
                   image2.setY(image.getY() - dpHeightInPx2 + dpHeightInPx/3 + dpHeightInPx/5);

                   LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(dpWidthInPx2, dpHeightInPx2);
                   image2.setLayoutParams(layoutParams2);

/*                   image2.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Intent intent = new Intent(context, GameplayScreen.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                           context.startActivity(intent);
                       }
                   });*/


                   layout.addView(image2);

                   if (countX - 1 >= 0) {
                       countX--;
                   } else {
                       countY++;
                       goRight = true;
                   }
               }
           }
       }
   }
}
