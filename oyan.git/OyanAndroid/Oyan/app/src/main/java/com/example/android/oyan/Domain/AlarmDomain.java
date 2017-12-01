
package com.example.android.oyan.Domain;

public class AlarmDomain extends BaseDomain {

    private long time;
    private long[] weekDays;
    private long userId;
    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    private long currentTime;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }


    public long[] getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(long[] weekDays) {
        this.weekDays = weekDays;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


}
