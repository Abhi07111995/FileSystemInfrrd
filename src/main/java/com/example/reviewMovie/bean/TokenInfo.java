package com.example.reviewMovie.bean;



import org.joda.time.DateTime;


public class TokenInfo {
    private String userId;
    private DateTime issued;
    private DateTime expires;
    private long issueTime;
    private int duration;

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public DateTime getIssued() {
        return issued;
    }
    public void setIssued(DateTime issued) {
        this.issued = issued;
    }
    public DateTime getExpires() {
        return expires;
    }
    public void setExpires(DateTime expires) {
        this.expires = expires;
    }

    public void setIssueTime(long issueTime) {
        this.issueTime = issueTime;
    }
    public Long getIssueTime() {
        return this.issueTime;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    public int getDuration() {
        return this.duration;
    }
}
