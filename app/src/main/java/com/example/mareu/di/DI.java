package com.example.mareu.di;

import com.example.mareu.service.DummyMeetingApiService;
import com.example.mareu.service.MeetingApiService;

public class DI {

    private static MeetingApiService serviceMeet = new DummyMeetingApiService();

    public static MeetingApiService getServiceMeet(){
        return serviceMeet;
    }

    public static MeetingApiService getNewInstanceApiService(){
        return new DummyMeetingApiService();
    }
}
