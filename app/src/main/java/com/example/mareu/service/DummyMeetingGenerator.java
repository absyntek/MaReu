package com.example.mareu.service;

import com.example.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public abstract class DummyMeetingGenerator {

    private static Date sDate1 = new Date(2019,5,5,01,01,00);
    private static Date sDate2 = new Date(2019,5,5,20,01,00);
    private static Date sDate3 = new Date(2019,5,5,13,01,00);

    private static List<String> emailList = Arrays.asList("michel@bibine.com","zebulon@bibine.com");

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting(sDate1,"Wario","test1",emailList, 15277667),
            new Meeting(sDate2,"Peach","test2",emailList, 2201331),
            new Meeting(sDate3,"Yoshi","test3",emailList, 9159498)

    );

    static List<Meeting> generateMeetings () {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}
