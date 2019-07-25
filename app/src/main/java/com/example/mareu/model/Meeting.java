package com.example.mareu.model;


import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Model object representing a Meeting
 */
public class Meeting {

    public Meeting(Date date, String meetingPoint, String tuto, List<String> emails, int meetingColor) {
        mDate = date;
        mMeetingPoint = meetingPoint;
        mTuto = tuto;
        mEmails = emails;
        mMeetingColor = meetingColor;
    }

    /**
     * Meeting time
     */
    private Date mDate;

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

    /**
     * Color displayed
     */
    private int mMeetingColor;


    /**
     * Getter and Setter
     * @return
     */
    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
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

    public int getMeetingColor() {
        return mMeetingColor;
    }

    public void setMeetingColor(int meetingColor) {
        mMeetingColor = meetingColor;
    }

    public static class MeetingComparatorTime implements Comparator<Meeting>{

        @Override
        public int compare(Meeting meeting, Meeting t1) {
            return meeting.mDate.compareTo(t1.mDate);
        }
    }

    public static class MeetingComparatorRoom implements Comparator<Meeting>{

        @Override
        public int compare(Meeting meeting, Meeting t1) {
            return meeting.mMeetingPoint.compareTo(t1.mMeetingPoint);
        }
    }
}
