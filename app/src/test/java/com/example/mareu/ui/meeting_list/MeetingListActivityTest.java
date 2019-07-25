package com.example.mareu.ui.meeting_list;

import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.MeetingApiService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class MeetingListActivityTest {

    private MeetingApiService mApiService;

    @Before
    public void setup(){
        mApiService = DI.getServiceMeet();
    }

    @Test
    public void getMeetingwithSucces(){
        List<Meeting> meetings = mApiService.getMeetings();
    }
}