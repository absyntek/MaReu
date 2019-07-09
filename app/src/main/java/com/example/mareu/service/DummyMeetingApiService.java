package com.example.mareu.service;

import com.example.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {

    private String mMeetingPoints [] =
            {"Peach", "Mario", "Luigi", "Yoshi", "Toad", "Wario", "Princesse", "Bowser", "KoopaTroopa", "DonkeyKong"};

    private List<Meeting> mMeetingList = new ArrayList<>();

    @Override
    public List<Meeting> getMeetings() {

        return mMeetingList;
    }

    @Override
    public void addMeeting(Meeting meeting) {
        mMeetingList.add(meeting);
    }

    @Override
    public void deletMeeting(Meeting meeting) {
        mMeetingList.remove(meeting);
    }

    @Override
    public String[] getMeetingPoints() {
        return mMeetingPoints;
    }
}
