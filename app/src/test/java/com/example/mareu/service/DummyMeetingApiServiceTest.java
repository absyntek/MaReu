package com.example.mareu.service;

import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@RunWith(JUnit4.class)
public class DummyMeetingApiServiceTest {

    private MeetingApiService mApiService;
    private Meeting meeting;
    private List<Meeting> mMeetingList;

    @Before
    public void setup(){
        mApiService = DI.getNewInstanceApiService();
    }

    @Test
    public void getMeetings() {
        List<Meeting> meetings = mApiService.getMeetings();
        List<Meeting> expectedMeetings = DummyMeetingGenerator.generateMeetings();
        assertEquals(meetings.size(),expectedMeetings.size());
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }

    @Test
    public void addMeeting() {
        meeting = new Meeting(null,"dsfs",
                "dfdsdfs",null,1234);
        mApiService.addMeeting(meeting);
        assertTrue(mApiService.getMeetings().contains(meeting));
    }

    @Test
    public void deleteMeeting() {
        meeting = mApiService.getMeetings().get(0);
        mApiService.deletMeeting(meeting);
        assertFalse(mApiService.getMeetings().contains(meeting) );
    }

    @Test
    public void getMeetingID() {
        meeting = mApiService.getMeetings().get(0);
        assertEquals(0,mApiService.getMeetingID(meeting));
    }

    @Test
    public void getMeetingPoints() {
        List<String> toTest = Arrays.asList(mApiService.getMeetingPoints());
        assertEquals(10,toTest.size());
    }

    @Test
    public void sortMeetingByDate(){
        mMeetingList = mApiService.getMeetings();
        Collections.sort(mMeetingList, new DummyMeetingApiService.MeetingComparatorTime());
        Date date1 = null, date2 = null;
        for (Meeting meeting : mMeetingList){
            if (date1 == null && date2 == null){
                date1 = meeting.getDate();
            }else if (date1 != null && date2 == null){
                date2 = meeting.getDate();
                assertTrue(date1.before(date2));
            }else {
                date1 = date2;
                date2 = null;
            }
        }
    }

    @Test
    public void sortMeetingByRoom(){
        mMeetingList = mApiService.getMeetings();
        Collections.sort(mMeetingList, new DummyMeetingApiService.MeetingComparatorRoom());
        String room1 = null, room2 = null;
        for (Meeting meeting : mMeetingList){
            if (room1 == null && room2 == null){
                room1 = meeting.getMeetingPoint();
            }else if (room1 != null && room2 == null){
                room2 = meeting.getMeetingPoint();
                assertTrue(room1.compareTo(room2)<=0);
            }else {
                room1 = room2;
                room2 = null;
            }
        }
    }
}