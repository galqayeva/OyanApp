/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SeriveLayer.Domain;

/**
 *
 * @author Eldar
 */
public class AlarmDomain extends BaseDomain {

    private long time; 
    private long[] weekDays; 
    private long userId;
    private long currentTime; 
     
    
    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }
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
