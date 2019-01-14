package com.example.dobra.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.view.View;

public class Character extends View {

    private Bitmap chiuplus;

    public Character(Context context){
        super(context);

        chiuplus = BitmapFactory.decodeResource(getResources(), R.drawable.chiuplus);

    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        canvas.drawBitmap(chiuplus, 0, 0, null);
    }


}
