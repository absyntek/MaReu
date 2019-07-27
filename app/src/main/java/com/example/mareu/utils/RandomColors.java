package com.example.mareu.utils;

import android.content.Context;

import com.example.mareu.R;

import java.util.Collections;
import java.util.Stack;

public class RandomColors {


    private Stack<Integer> recycle, colors;

    public RandomColors(Context context) {
        colors = new Stack<>();
        recycle =new Stack<>();
        int[] colorSet = context.getResources().getIntArray(R.array.color_List);
        for (int color : colorSet){
            recycle.add(color);
        }
    }

    public int getColor() {
        if (colors.size()==0) {
            while(!recycle.isEmpty())
                colors.push(recycle.pop());
            Collections.shuffle(colors);
        }
        Integer c= colors.pop();
        recycle.push(c);
        return c;
    }
}