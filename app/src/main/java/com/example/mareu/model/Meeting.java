package com.example.mareu.model;


import java.util.List;

/**
 * Model object representing a Meeting
 */
public class Meeting {

    /**
     * Meeting hour
     */
    private int mHour;

    /**
     * Meeting minutes
     */
    private int mMinutes;

    /**
     * Meeting room
     */
    private String mMeetingPoint;

    /**
     * Meeting tuto
     */
    private String mTuto;

    /**
     * Other people Mails
     */
    private List<String> mEmails;

    public int getHour() {
        return mHour;
    }

    public void setHour(int hour) {
        mHour = hour;
    }

    public int getMinutes() {
        return mMinutes;
    }

    public void setMinutes(int minutes) {
        mMinutes = minutes;
    }

    public String getMeetingPoint() {
        return mMeetingPoint;
    }

    public void setMeetingPoint(String meetingPoint) {
        mMeetingPoint = meetingPoint;
    }

    public String getTuto() {
        return mTuto;
    }

    public void setTuto(String tuto) {
        mTuto = tuto;
    }

    public List<String> getEmails() {
        return mEmails;
    }

    public void setEmails(List<String> emails) {
        mEmails = emails;
    }
}
