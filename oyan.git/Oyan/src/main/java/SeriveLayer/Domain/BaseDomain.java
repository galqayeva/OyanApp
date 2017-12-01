/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SeriveLayer.Domain;

/**
 *
 * @author Eldar Hasanov
 */
public class BaseDomain {

    private long id;
    private String token;
    private String message;
    private int messageId;
    private long pageNumber;
    private long pageSize;
    private long pageResult;
    private double latitude;
    private double longitude;
    private long weekDay;
    private long timeDifference;
    private long firstTime;
    private long isActive;
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getIsActive() {
        return isActive;
    }

    public void setIsActive(long isActive) {
        this.isActive = isActive;
    }
    
    public long getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(long firstTime) {
        this.firstTime = firstTime;
    }
 


    public long getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(long weekDay) {
        this.weekDay = weekDay;
    }

    public long getTimeDifference() {
        return timeDifference;
    }

    public void setTimeDifference(long timeDifference) {
        this.timeDifference = timeDifference;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getPageResult() {
        return pageResult;
    }

    public void setPageResult(long pageResult) {
        this.pageResult = pageResult;
    }

}
