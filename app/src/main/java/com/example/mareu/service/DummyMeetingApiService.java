package com.example.mareu.service;

import com.example.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public int getMeetingID(Meeting meeting) {
        return mMeetingList.indexOf(meeting);
    }

    @Override
    public String[] getMeetingPoints() {
        return mMeetingPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DummyMeetingApiService that = (DummyMeetingApiService) o;
        return Objects.equals(mMeetingList, that.mMeetingList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mMeetingList);
    }
}
