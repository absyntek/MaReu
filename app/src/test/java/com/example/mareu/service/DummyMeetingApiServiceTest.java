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
import java.util.List;


@RunWith(JUnit4.class)
public class DummyMeetingApiServiceTest {

    private MeetingApiService mApiService;
    private Meeting meeting;
    int SIZE = 3;

    @Before
    public void setup(){
        mApiService = DI.getNewInstanceApiService();
    }

    @Test
    public void getMeetings() { //TODO Commencer par cette ligne SVP
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
    public void deletMeeting() {
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
}