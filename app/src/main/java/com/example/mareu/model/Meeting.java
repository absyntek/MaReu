package com.example.mareu.model;


import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return mMeetingColor == meeting.mMeetingColor &&
                Objects.equals(mDate, meeting.mDate) &&
                Objects.equals(mMeetingPoint, meeting.mMeetingPoint) &&
                Objects.equals(mTuto, meeting.mTuto) &&
                Objects.equals(mEmails, meeting.mEmails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mDate, mMeetingPoint, mTuto, mEmails, mMeetingColor);
    }
}
