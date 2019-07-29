package com.example.mareu.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class RandomColorsTest {


    private Context mContext;
    private Resources mResources;

    private RandomColors mRandomColors;

    Integer[] test = {1,2,3,4,5};

    @Test
    public void getColor() {
        int[] test2 = Arrays.stream(test).mapToInt(Integer::intValue).toArray();
        mContext = Mockito.mock(Context.class);
        mResources = Mockito.mock(Resources.class);
        Mockito.when(mContext.getResources()).thenReturn(mResources);
        Mockito.when(mResources.getIntArray(Mockito.anyInt())).thenReturn(test2);

        mRandomColors = new RandomColors(mContext);

        int result = mRandomColors.getColor();
        assertTrue(Arrays.asList(test).contains(result));
    }
}