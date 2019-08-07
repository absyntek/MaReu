package com.example.mareu.service;

import com.example.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public abstract class DummyMeetingGenerator {

    private static final Date sDate1 = new Date(119,5,5,1,1,0);
    private static final Date sDate2 = new Date(119,5,5,20,1,0);
    private static final Date sDate3 = new Date(119,5,5,13,1,0);
    private static final Date sDate4 = new Date(119,5,5,15,1,0);

    private static final List<String> emailList = Arrays.asList("michel@lamzone.com","zebulon@lamzone.com","sarah@lamzone.com","jean-louis@lamzone.com");

    private static final List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting(sDate1,"Wario","tuto - 1",emailList, -15277667),
            new Meeting(sDate2,"Peach","tuto - 2",emailList, -2201331),
            new Meeting(sDate4,"Peach","tuto - 4",emailList, -2201331),
            new Meeting(sDate3,"Yoshi","tuto - 3",emailList, -9159498)

    );

    protected DummyMeetingGenerator() { }

    static List<Meeting> generateMeetings () {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}