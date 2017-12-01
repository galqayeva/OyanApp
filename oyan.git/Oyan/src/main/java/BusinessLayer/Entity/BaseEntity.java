/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLayer.Entity;

/**
 *
 * @author Eldar Hasanov
 */
public class BaseEntity {

    private long id;
    private long createdDate;
    private long updatedDate;
    private long deletedDate;
    private long createdBy;
    private long updatedBy;
    private long deletedBy;
    private long pageNumber;
    private long pageSize;
    private long pageResult;
    private String token;
    private double latitude;
    private double longitude;
    private long firstTime;
    private long weekDay;
    private long timeDifference;
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
    private long isActive;
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
    public long getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(long firstTime) {
        this.firstTime = firstTime;
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

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public long getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(long updatedDate) {
        this.updatedDate = updatedDate;
    }

    public long getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(long deletedDate) {
        this.deletedDate = deletedDate;
    }

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    public long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public long getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(long deletedBy) {
        this.deletedBy = deletedBy;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
