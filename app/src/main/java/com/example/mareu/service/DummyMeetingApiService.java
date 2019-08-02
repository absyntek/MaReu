package com.example.mareu.service;

import com.example.mareu.model.Meeting;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class DummyMeetingApiService implements MeetingApiService {

    /**
     * Meeting point list
     */
    private final String[] mMeetingPoints =
            {"Peach", "Mario", "Luigi", "Yoshi", "Toad", "Wario", "Princesse", "Bowser", "KoopaTroopa", "DonkeyKong"};

    private final List<Meeting> mMeetingList = DummyMeetingGenerator.generateMeetings();

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

    public static class MeetingComparatorTime implements Comparator<Meeting>{

        @Override
        public int compare(Meeting meeting, Meeting t1) {
            return meeting.getDate().compareTo(t1.getDate());
        }
    }

    public static class MeetingComparatorRoom implements Comparator<Meeting>{

        @Override
        public int compare(Meeting meeting, Meeting t1) {
            return meeting.getMeetingPoint().compareTo(t1.getMeetingPoint());
        }
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
