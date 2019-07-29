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


    Integer[] test = {1,2,3,4,5};

    @Test
    public void getColor() {
        int[] test2 = Arrays.stream(test).mapToInt(Integer::intValue).toArray();
        Context context = Mockito.mock(Context.class);
        Resources resources = Mockito.mock(Resources.class);
        Mockito.when(context.getResources()).thenReturn(resources);
        Mockito.when(resources.getIntArray(Mockito.anyInt())).thenReturn(test2);

        RandomColors randomColors = new RandomColors(context);

        int result = randomColors.getColor();
        assertTrue(Arrays.asList(test).contains(result));
    }
}