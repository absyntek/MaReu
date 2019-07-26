package com.example.mareu.utils;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class RandomColorsTest {

    @Before
    public void setUp() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("values/colorList.xml").getFile());
    }

    @Test
    public void getColor() {
    }
}