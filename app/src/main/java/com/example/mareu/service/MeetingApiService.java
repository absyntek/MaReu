package com.example.mareu.service;

import com.example.mareu.model.Meeting;

import java.util.List;

public interface MeetingApiService {

    List<Meeting> getMeetings();

    void addMeeting (Meeting meeting);

    void deletMeeting (Meeting meeting);

    String [] getMeetingPoints();
}
